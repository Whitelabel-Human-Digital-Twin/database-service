import io.github.whdt.core.hdt.interfaces.digital.HttpDigitalInterface
import io.github.whdt.core.hdt.interfaces.digital.MqttDigitalInterface
import io.github.whdt.core.hdt.interfaces.physical.MqttPhysicalInterface
import io.github.whdt.core.hdt.model.property.PropertyValue
import io.github.whdt.db.entities.HumanDigitalTwin
import io.github.whdt.db.entities.Interface
import io.github.whdt.db.entities.Property
import io.github.whdt.db.entities.Value
import io.github.whdt.db.relations.Defines
import io.github.whdt.db.relations.Implements
import io.github.whdt.db.relations.Interacts
import io.github.whdt.db.repository.PostgresHDTRepository
import io.ktor.server.application.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import model.*
import kotlin.collections.component1
import kotlin.collections.component2

fun Application.configureDatabaseWorker(commandChannel: Channel<DomainCommand>) {
    launch {
        for (cmd in commandChannel) {
            when (cmd) {
                is InsertDigitalTwin -> handleInsertDigitalTwin(cmd)
                is InsertDigitalTwinBatch -> TODO()
                is InsertProperty -> handleInsertProperty(cmd)
                is InsertTime -> TODO() //handleInsertTime(cmd)
                is GetPropertyOfDt -> handleGetPropertyOfDt(cmd)
                is GetMaxValue -> handleGetMaxValue(cmd)
                is GetMinValue -> handleGetMinValue(cmd)
                is GetAvgValue -> handleGetAvgValue(cmd)
                is GetDtPropertyRange -> handleGetDtPropertyRange(cmd)
                is GetValueOfTime ->  handleGetValueOfTime(cmd)
                is GetValueOfTimeDt ->  handleGetValueOfTimeDt(cmd)
                is GetValueOfTimeOfPropOfDt ->  handleGetValueOfTimeOfPropOfDt(cmd)
                is NotifyFailureCommand -> log.warn("Failed: ${cmd.reason}")
            }
        }
    }
}

// testata
suspend fun handleInsertDigitalTwin(cmd : InsertDigitalTwin) {
    val db = PostgresHDTRepository()
    val allHdt = db.allHDT()
    if(allHdt.none { it.name == cmd.hdt.hdtId.id }){
        //Aggiunta id dt
        val lastHDT = allHdt.lastOrNull()
        var idHDT = 0
        if (lastHDT != null) {
            idHDT = lastHDT.id + 1
        }
        db.addHDT(HumanDigitalTwin(id = idHDT,name = cmd.hdt.hdtId.id))
        // carico le interfacce fisiche

        val phyInterfaces = cmd.hdt.physicalInterfaces
        if (phyInterfaces.isNotEmpty()) {
            phyInterfaces.forEach { inter ->
                var idInterface = 0
                val int = db.allInterface().firstOrNull()
                if(int != null){
                    idInterface = int.id + 1
                }
                when (inter) {
                    is MqttPhysicalInterface -> {
                        db.addInterface(
                            Interface(
                                id = idInterface,
                                name = "MqttPhysicalInterface-${idHDT}",
                                ipaddress = inter.broker,
                                port = inter.port,
                                clientid = inter.component4(),
                                type = "Physical Mqtt"
                            )
                        )
                        insertInt(db,idHDT)
                    }
                }
            }
        }
        // carico le interfacce digitali
        val digInterfaces = cmd.hdt.digitalInterfaces
        if (digInterfaces.isNotEmpty()) {
            digInterfaces.forEach { inter ->
                var idInterface = 0
                val int = db.allInterface().map{it.id}.firstOrNull()
                if(int != null){
                    idInterface = int + 1
                }
                when (inter) {
                    is HttpDigitalInterface -> {
                        db.addInterface(
                            Interface(
                                id = idInterface,
                                name = "HttpDigitalInterface-${idHDT}",
                                ipaddress = inter.host,
                                port = inter.port,
                                clientid = inter.component4(),
                                type = "Physical Mqtt"
                            )
                        )
                        insertInt(db,idHDT)
                    }
                    is MqttDigitalInterface -> {
                        db.addInterface(
                            Interface(
                                id = idInterface,
                                name = "MqttDigitalInterface-${idHDT}",
                                ipaddress = inter.broker,
                                port = inter.port,
                                clientid = inter.component4(),
                                type = "Physical Mqtt"
                            )
                        )
                        insertInt(db,idHDT)
                    }
                }
            }
        }
        // ottengo l'id dell'ultima proprietà inserita
        val lastProp = db.allProperty().lastOrNull()
        var idLastProp = 0
        if(lastProp != null){
            idLastProp = lastProp.id + 1
        }
        // trasformo i modelli in una lista di proprietà
        val profHDT = cmd.hdt.models.flatMap{it.properties}
        // aggiungo le nuove proprietà al database
        var idLastPropAdd = idLastProp
        profHDT.forEach{ val propTmp= Property(id=idLastPropAdd,name = it.name, description = it.description)
            db.addProperty(propTmp)
            idLastPropAdd++}
        // creo la realazione hdt-proprietà
        idLastPropAdd--
        for (k in (idLastProp)..idLastPropAdd) {
            val relTmp = Implements(property_id = k, humandigitaltwin_id = idHDT)
            db.addImplements(relTmp)
        }
        // inserisco il nuovo valore e lo associo immediatamente alla proprietà
        profHDT.forEach {
            addValueOfPropOndb(db,it.valueMap,idLastProp)
            idLastProp++
        }
        cmd.reply.complete(true)
    }else{
        cmd.reply.complete(false)
    }
}

fun convert(prop: PropertyValue): Pair<String, String>{
    return when (prop) {
        is PropertyValue.StringPropertyValue -> {
            prop.value to "String"
        }
        is PropertyValue.IntPropertyValue -> {
            prop.value.toString() to "Int"
        }
        is PropertyValue.BooleanPropertyValue -> {
            prop.value.toString() to "Boolean"
        }
        is PropertyValue.DoublePropertyValue -> {
            prop.value.toString() to "Double"
        }
        is PropertyValue.FloatPropertyValue -> {
            prop.value.toString() to "Float"
        }
        is PropertyValue.LongPropertyValue -> {
            prop.value.toString() to "Long"
        }
        else -> {
            "errore" to "errore"
        }
    }
}

suspend fun insertInt(db: PostgresHDTRepository,idHDT:Int){
    val idInt = db.allInterface().last().id
    db.addInteracts(
        Interacts(
            humandigitaltwin_id = idHDT,
            interface_id = idInt
        )
    )
}
// testata
suspend fun handleInsertProperty(cmd : InsertProperty){
    val db = PostgresHDTRepository()
    val myHdt = db.allHDT().firstOrNull{ it.name == cmd.hdt.id }
    if(myHdt != null) {
        var idLastProp = db.allProperty().last().id
        idLastProp++
        db.addProperty(
            Property(
                id=idLastProp,
                name = cmd.property.name,
                description = cmd.property.description
                )
        )
        var valueProp = cmd.property.valueMap
        if (valueProp.isNotEmpty()) {
            addValueOfPropOndb(db,cmd.property.valueMap,idLastProp--)
        }
        cmd.reply.complete(true)
    }else{
        cmd.reply.complete(false)
    }
}
/*
suspend fun handleInsertTime(cmd : InsertTime){
    val db = PostgresHDTRepository()
    val myHdt = db.allHDT().firstOrNull{ it.name == cmd.hdt }
    if(myHdt != null) {
        var prop = db.allPropOfDt(cmd.hdt)
       if (prop != null) {
           val idProp = prop.firstOrNull{ it.name == cmd.property }
           if (idProp != null) {
               val idValue = db.allDefines().filter{it.property_id == idProp.id}.map { it.value_id }
           }else cmd.reply.complete(false)
       }else cmd.reply.complete(false)
    }else cmd.reply.complete(false)

}*/

suspend fun addValueOfPropOndb(db: PostgresHDTRepository,map: Map<String, PropertyValue>,idProp: Int){
    var idLastValue = 0
    val lastValue = db.allValue().lastOrNull()
    if(lastValue != null) {
        idLastValue = lastValue.id + 1
    }
    map.forEach {  (chiave,valore) ->
        val coppia = convert(valore)
        db.addValue(
            Value(
                id = idLastValue,
                name = chiave,
                value = coppia.first,
                type = coppia.second
            )
        )
        db.addDefines(Defines(property_id = idProp,value_id=idLastValue))
        idLastValue++
    }

}

suspend fun handleGetValueOfTime(cmd: GetValueOfTime){
    val db = PostgresHDTRepository()
    val valueTime = db.valueOfTime(cmd.timeLess, cmd.timeGreter)
    if(valueTime != null) {
        cmd.reply.complete(valueTime)
    }else cmd.reply.complete(false)
}

suspend fun handleGetValueOfTimeDt(cmd: GetValueOfTimeDt){
    val db = PostgresHDTRepository()
    val valueTimeDtProp = db.valueOfTimeOfDt(cmd.hdt,cmd.timeLess, cmd.timeGreter)
    if(valueTimeDtProp != null) {
        cmd.reply.complete(valueTimeDtProp)
    }else cmd.reply.complete(false)
}

suspend fun handleGetValueOfTimeOfPropOfDt(cmd: GetValueOfTimeOfPropOfDt){
    val db = PostgresHDTRepository()
    val valueTimeDt = db.valueOfTimeOfPropOfDt(cmd.hdtName,cmd.propName,cmd.timeLess, cmd.timeGreter)
    if(valueTimeDt != null) {
        cmd.reply.complete(valueTimeDt)
    }else cmd.reply.complete(false)
}

suspend fun handleGetMaxValue(cmd: GetMaxValue) {
    val db = PostgresHDTRepository()
    val maxValue = db.maxPropertyOfTimeHdt(cmd.hdtName,cmd.propName,cmd.timeLess,cmd.timeGreter)
    if(maxValue != null) {
        cmd.reply.complete(maxValue)
    }else cmd.reply.complete(false)
}

suspend fun handleGetMinValue(cmd: GetMinValue) {
    val db = PostgresHDTRepository()
    val minValue = db.minPropertyOfTimeHdt(cmd.hdtName, cmd.propName, cmd.timeLess, cmd.timeGreter)
    if (minValue != null) {
        cmd.reply.complete(minValue)
    } else cmd.reply.complete(false)

}

suspend fun handleGetAvgValue(cmd: GetAvgValue) {
    val db = PostgresHDTRepository()
    val minValue = db.avgPropertyOfTime(cmd.hdtName,cmd.propName,cmd.timeLess,cmd.timeGreter)
    if(minValue != null) {
        cmd.reply.complete(minValue)
    }else cmd.reply.complete(false)


}

// testata
suspend fun handleGetDtPropertyRange(cmd: GetDtPropertyRange){
    val db = PostgresHDTRepository()
    val hdt = db.dtPropertyRange(cmd.propName,cmd.minValue,cmd.maxValue)
    if(hdt != null){
        cmd.reply.complete(hdt)
    }else cmd.reply.complete(false)
}

// testata
suspend fun handleGetPropertyOfDt(cmd: GetPropertyOfDt) {
    val db = PostgresHDTRepository()
    val hdt = db.allHDT().firstOrNull{ it.name == cmd.hdt }
    if(hdt != null){
        val prop = db.allPropOfDt(cmd.hdt)
        if(prop != null){
            cmd.reply.complete(prop)
        }else cmd.reply.complete(false)
    }else cmd.reply.complete(false)

}



