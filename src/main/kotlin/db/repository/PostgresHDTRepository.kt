package io.github.whdt.db.repository

import io.github.whdt.db.mappingEntities.*
import io.github.whdt.db.entities.*
import io.github.whdt.db.mappingRelations.AssociatedTable
import io.github.whdt.db.mappingRelations.DefinesTable
import io.github.whdt.db.mappingRelations.ImplementsTable
import io.github.whdt.db.mappingRelations.InteractsTable
import io.github.whdt.db.mappingRelations.SamplingTable
import io.github.whdt.db.relations.*
import org.jetbrains.exposed.v1.r2dbc.insert
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.jetbrains.exposed.v1.core.lessEq
import org.jetbrains.exposed.v1.r2dbc.selectAll
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.greaterEq

class PostgresHDTRepository : HDTRepository {

    override suspend fun allHDT(): List<HumanDigitalTwin> = suspendTransaction {
        HumanDigitalTwinTable
            .selectAll()
            .map {
                HumanDigitalTwin(
                    id = it[HumanDigitalTwinTable.id],
                    name = it[HumanDigitalTwinTable.name]
                )
            }
            .toList()
    }

    override suspend fun allInterface(): List<Interface> = suspendTransaction {
        InterfaceTable
            .selectAll()
            .map {
                Interface(
                    id = it[InterfaceTable.id],
                    name = it[InterfaceTable.name],
                    ipaddress = it[InterfaceTable.ipaddress],
                    port = it[InterfaceTable.port],
                    clientid = it[InterfaceTable.clientid],
                    type = it[InterfaceTable.type]
                )
            }
            .toList()
    }

    override suspend fun allProperty(): List<Property> = suspendTransaction {
        PropertyTable
            .selectAll()
            .map{
                Property(
                    id = it[PropertyTable.id],
                    name = it[PropertyTable.name],
                    description = it[PropertyTable.description]
                )
            }
            .toList()
    }

    override suspend fun allTime(): List<Time> = suspendTransaction {
        TimeTable
            .selectAll()
            .map {
                Time(
                    id = it[TimeTable.id],
                    dateenter = it[TimeTable.dateenter],
                    datestart = it[TimeTable.datestart],
                    dateend = it[TimeTable.dateend]
                )
            }
            .toList()
    }

    override suspend fun allValue(): List<Value> = suspendTransaction {
        ValueTable
            .selectAll()
            .map{
                Value(
                    id = it[ValueTable.id],
                    name = it[ValueTable.name],
                    value = it[ValueTable.value],
                    type = it[ValueTable.type]
                )
            }
            .toList()
    }

    override suspend fun allAssociated(): List<Associated> = suspendTransaction {
        AssociatedTable
            .selectAll()
            .map {
                Associated(
                    id = it[AssociatedTable.id],
                    property_id = it[AssociatedTable.property_id],
                    interface_id = it[AssociatedTable.interface_id]
                )
            }
            .toList()
    }

    override suspend fun allDefines(): List<Defines> = suspendTransaction {
        DefinesTable
            .selectAll()
            .map{
                Defines(
                    id = it[DefinesTable.id],
                    property_id = it[DefinesTable.property_id],
                    value_id = it[DefinesTable.value_id]
                )
            }
            .toList()
    }

    override suspend fun allImplements(): List<Implements> = suspendTransaction {
        ImplementsTable
            .selectAll()
            .map{
                Implements(
                    id = it[ImplementsTable.id],
                    property_id = it[ImplementsTable.property_id],
                    humandigitaltwin_id = it[ImplementsTable.humandigitaltwin_id]
                )
            }
            .toList()
    }

    override suspend fun allInteracts(): List<Interacts> = suspendTransaction {
        InteractsTable
            .selectAll()
            .map{
                Interacts(
                    id = it[InteractsTable.id],
                    humandigitaltwin_id = it[InteractsTable.humandigitaltwin_id],
                    interface_id = it[InteractsTable.interface_id]
                )
            }
            .toList()
    }

    override suspend fun allSampling(): List<Sampling> = suspendTransaction {
        SamplingTable
            .selectAll()
            .map{
                Sampling(
                    id = it[SamplingTable.id],
                    time_id = it[SamplingTable.time_id],
                    value_id = it[SamplingTable.value_id]
                )
            }
            .toList()
    }

    override suspend fun addHDT(hdt: HumanDigitalTwin): Unit  = suspendTransaction {
        HumanDigitalTwinTable.insert {
            it[HumanDigitalTwinTable.name] = hdt.name
        }
    }

    override suspend fun addInterface(int: Interface): Unit  = suspendTransaction {
        InterfaceTable.insert{
            it[InterfaceTable.name] = int.name
            it[InterfaceTable.ipaddress] = int.ipaddress
            it[InterfaceTable.port] = int.port
            it[InterfaceTable.clientid] = int.clientid
            it[InterfaceTable.type] = int.type
        }
    }

    override suspend fun addProperty(prop: Property): Unit  = suspendTransaction {
        PropertyTable.insert {
            it[PropertyTable.name] = prop.name
            it[PropertyTable.description] = prop.description
        }
    }

    override suspend fun addTime(time: Time): Unit  = suspendTransaction {
        TimeTable.insert {
            it[TimeTable.dateenter] = time.dateenter
            it[TimeTable.datestart] = time.datestart
            it[TimeTable.dateend] = time.dateend
        }
    }

    override suspend fun addValue(valu: Value): Unit  = suspendTransaction {
        ValueTable.insert {
            it[ValueTable.name] = valu.name
            it[ValueTable.value] = valu.value
            it[ValueTable.type] = valu.type
        }
    }

    override suspend fun addAssociated(associated: Associated): Unit  = suspendTransaction {
        AssociatedTable.insert {
            it[AssociatedTable.id] = associated.id
            it[AssociatedTable.property_id] = associated.property_id
            it[AssociatedTable.interface_id] = associated.interface_id
        }
    }

    override suspend fun addDefines(defines: Defines): Unit  = suspendTransaction {
       DefinesTable.insert {
           it[DefinesTable.id] = defines.id
           it[DefinesTable.property_id] = defines.property_id
           it[DefinesTable.value_id] = defines.value_id
       }
    }

    override suspend fun addImplements(implements: Implements): Unit  = suspendTransaction {
        ImplementsTable.insert {
            it[ImplementsTable.id] = implements.id
            it[ImplementsTable.property_id] = implements.property_id
            it[ImplementsTable.humandigitaltwin_id] = implements.humandigitaltwin_id
        }
    }

    override suspend fun addInteracts(interacts: Interacts): Unit  = suspendTransaction {
        InteractsTable.insert {
            it[InteractsTable.id] = interacts.id
            it[InteractsTable.humandigitaltwin_id] = interacts.humandigitaltwin_id
            it[InteractsTable.interface_id] = interacts.interface_id
        }
    }

    override suspend fun addSampling(sampling: Sampling): Unit  = suspendTransaction {
        SamplingTable.insert {
            it[SamplingTable.id] = sampling.id
            it[SamplingTable.time_id] = sampling.time_id
            it[SamplingTable.value_id] = sampling.value_id
        }
    }

    override suspend fun detTime(
        timeLess : LocalDateTime
        ,timeGreter : LocalDateTime
    ): List<Int> = suspendTransaction {
        TimeTable
            .selectAll()
            .where{
                (TimeTable.dateenter lessEq timeGreter ) and
                        (TimeTable.dateenter greaterEq timeLess )
            }
            .map {
                it[TimeTable.id]
            }
            .toList()
    }
    /* farne una per proprietà e una per dt */
    override suspend fun valueOfTime(
        timeLess : LocalDateTime,
        timeGreter : LocalDateTime
    ): List<Value> {
        val timeId = detTime(timeLess, timeGreter)
        val rel = allSampling().filter{timeId.contains(it.component2())}.map{it.value_id}
        return allValue().filter{rel.contains(it.component1())}
    }

    override suspend fun valueOfTimeOfProp(
        propName : String,
        timeLess : LocalDateTime,
        timeGreter : LocalDateTime
    ): List<Value> {
        val valOfTime = valueOfTime(timeLess, timeGreter)
        val propId = detProperty(propName)
        return valOfTime.filter { propId.contains(it.component1()) }
    }

    override suspend fun valueOfTimeOfDt(
        hdtName : String,
        timeLess : LocalDateTime,
        timeGreter : LocalDateTime
    ): List<Value> {
        val idHdt = allHDT().filter { it.component2() == hdtName }.map{ it.id }.first()
        val idProperty = allImplements().filter { it.humandigitaltwin_id == idHdt }.map{it.property_id}
        val idValue = allDefines().filter { idProperty.contains(it.property_id) }.map { it.value_id }
        return valueOfTime(timeLess,timeGreter).filter{idValue.contains(it.component1())}
    }

    override suspend fun detProperty(propertyName: String): List<Int> = suspendTransaction{
        PropertyTable
            .selectAll()
            .where{
                (PropertyTable.name.eq(propertyName))
            }
            .map {
                it[PropertyTable.id]
            }.toList()
    }

    fun parsePrimitive(data: String, type: String): Any? {
        val cleanType = type.trim().lowercase()
        return try {
            when (cleanType) {
                "int"     -> data.toInt()
                "long"    -> data.toLong()
                "float"   -> data.toFloat()
                "double"  -> data.toDouble()
                "boolean" -> data.toBoolean()
                "string"  -> data
                else -> {
                    println("Tipo non supportato: $type")
                    null
                }
            }
        } catch (e: NumberFormatException) {
            println("Errore: Impossibile convertire '$data' in $cleanType")
            null
        }
    }

    override suspend fun minPropertyOfTime(
        propertyName: String,
        timeLess: LocalDateTime,
        timeGreter: LocalDateTime
    ): Value? {
        val valu = valueOfTime(timeLess, timeGreter)
        val idValue = allDefines().filter{ it.property_id == detProperty(propertyName).first()}.map{it.value_id}
        return valu.filter{idValue.contains(it.component1())}.minWithOrNull { a, b ->
            val valA = parsePrimitive(a.component3(),a.component4()) as? Number
            val valB = parsePrimitive(b.component3(),b.component4()) as? Number
            compareValues(valA?.toDouble(), valB?.toDouble())
        }
    }

    override suspend fun maxPropertyOfTime(
        propertyName: String,
        timeLess: LocalDateTime,
        timeGreter: LocalDateTime
    ): Value? {
        val valu = valueOfTime(timeLess, timeGreter)
        val idValue = allDefines().filter{ it.property_id == detProperty(propertyName).first()}.map{it.value_id}
        return valu.filter{idValue.contains(it.component1())}.maxWithOrNull{ a, b ->
            val valA = parsePrimitive(a.component3(),a.component4()) as? Number
            val valB = parsePrimitive(b.component3(),b.component4()) as? Number
            compareValues(valA?.toDouble(), valB?.toDouble())
        }
    }
    override suspend fun avgPropertyOfTime(
        propertyName: String,
        timeLess: LocalDateTime,
        timeGreter: LocalDateTime
    ): Double {
        val valu = valueOfTime(timeLess, timeGreter)
        val idValue = allDefines().filter{ it.property_id == detProperty(propertyName).first()}.map{it.value_id}
        return valu.filter{idValue.contains(it.component1())}
                   .map{parsePrimitive(it.component3(),it.component4())}
                   .filterIsInstance<Number>()
                   .map{ it.toDouble() }
                   .average()
    }

    override suspend fun valueInRange(
        minValue: String,
        maxValue: String
    ): List<Value> {
        return allValue().filter {
            val data = (parsePrimitive(it.component3(), it.component4()) as? Number)?.toDouble()
            val min = (parsePrimitive(minValue, it.component4()) as? Number)?.toDouble()
            val max = (parsePrimitive(maxValue, it.component4()) as? Number)?.toDouble()
            data != null && min != null && max != null && data >= min && data <= max
        }
    }

    override suspend fun dtPropertyRange(
        propertyName: String,
        minValue: String,
        maxValue: String
    ): List<HumanDigitalTwin> {
        val rangeValue = valueInRange(minValue, maxValue).map { it.id }
        val idPropValu = allDefines().filter { rangeValue.contains(it.value_id) }.map{it.property_id}
        val idProperty = detProperty(propertyName).filter { idPropValu.contains(it)}
        val idHdt = allImplements().filter { idProperty.contains(it.property_id)}.map{it.component3()}
        return allHDT().filter { idHdt.contains(it.id) }
    }


}