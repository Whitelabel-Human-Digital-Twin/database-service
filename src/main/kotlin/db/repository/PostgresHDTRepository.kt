package io.github.whdt.db.repository

import io.github.whdt.db.mappingEntities.*
import io.github.whdt.db.entities.HumanDigitalTwin
import io.github.whdt.db.entities.Interface
import io.github.whdt.db.entities.Property
import io.github.whdt.db.entities.Time
import io.github.whdt.db.entities.Value
import io.github.whdt.db.mappingRelations.AssociatedDAO
import io.github.whdt.db.mappingRelations.AssociatedTransaction
import io.github.whdt.db.mappingRelations.DefinesDAO
import io.github.whdt.db.mappingRelations.DefinesTransaction
import io.github.whdt.db.mappingRelations.ImplementsDAO
import io.github.whdt.db.mappingRelations.ImplementsTransaction
import io.github.whdt.db.mappingRelations.InteractsDAO
import io.github.whdt.db.mappingRelations.InteractsTransaction
import io.github.whdt.db.mappingRelations.SamplingDAO
import io.github.whdt.db.mappingRelations.SamplingTransaction
import io.github.whdt.db.mappingRelations.daoToModel
import io.github.whdt.db.relations.Associated
import io.github.whdt.db.relations.Defines
import io.github.whdt.db.relations.Implements
import io.github.whdt.db.relations.Interacts
import io.github.whdt.db.relations.Sampling

class PostgresHDTRepository : HDTRepository {

    override suspend fun allHDT(): List<HumanDigitalTwin> = HumanDigitalTwinTransaction {
        HumanDigitalTwinDAO.all().map(::daoToModel)
    }

    override suspend fun allInterface(): List<Interface> = InterfaceTransaction {
        InterfaceDAO.all().map(::daoToModel)
    }

    override suspend fun allProperty(): List<Property> = PropertyTransaction {
        PropertyDAO.all().map(::daoToModel)
    }

    override suspend fun allTime(): List<Time> = TimeTransaction {
        TimeDAO.all().map(::daoToModel)
    }

    override suspend fun allValue(): List<Value> = ValueTransaction {
        ValueDAO.all().map(::daoToModel)
    }

    override suspend fun allAssociated(): List<Associated> = AssociatedTransaction {
        AssociatedDAO.all().map(::daoToModel)
    }

    override suspend fun allDefines(): List<Defines> = DefinesTransaction {
        DefinesDAO.all().map(::daoToModel)
    }

    override suspend fun allImplements(): List<Implements> = ImplementsTransaction {
        ImplementsDAO.all().map(::daoToModel)
    }

    override suspend fun allInteracts(): List<Interacts> = InteractsTransaction {
        InteractsDAO.all().map(::daoToModel)
    }

    override suspend fun allSampling(): List<Sampling> = SamplingTransaction {
        SamplingDAO.all().map(::daoToModel)
    }

    override suspend fun addHDT(hdt: HumanDigitalTwin): Unit = HumanDigitalTwinTransaction {
        HumanDigitalTwinDAO.new {
            name = hdt.name
        }
    }

    override suspend fun addInterface(int: Interface): Unit = InterfaceTransaction {
        InterfaceDAO.new {
            name = int.name
            ipAddress = int.ipAddress
            port = int.port
            clientId = int.clientId
            type = int.type
        }
    }

    override suspend fun addProperty(prop: Property): Unit = PropertyTransaction {
        PropertyDAO.new {
            name = prop.name
            description = prop.description
        }
    }

    override suspend fun addTime(time: Time): Unit = TimeTransaction {
        TimeDAO.new {
            dateEnter = time.dateEnter
            dateStart = time.dateStart
            dateEnd = time.dateEnd
        }
    }

    override suspend fun addValue(valu: Value): Unit = ValueTransaction {
        ValueDAO.new {
            name = valu.name
            value = valu.value
            type = valu.type
        }
    }

    override suspend fun addAssociated(associated: Associated): Unit = AssociatedTransaction {
        AssociatedDAO.new {
            property_id = associated.property_id
            interface_id = associated.inteface_id
        }
    }

    override suspend fun addDefines(defines: Defines): Unit = DefinesTransaction {
        DefinesDAO.new {
            property_id = defines.property_id
            value_id = defines.value_id
        }
    }

    override suspend fun addImplements(implements: Implements): Unit = ImplementsTransaction {
        ImplementsDAO.new {
            property_id = implements.property_id
            humandigitaltwin_id = implements.humandigitaltwin_id
        }
    }

    override suspend fun addInteracts(interacts: Interacts): Unit = InteractsTransaction {
        InteractsDAO.new {
            humandigitaltwin_id = interacts.humandigitaltwin_id
            interface_id = interacts.inteface_id
        }
    }

    override suspend fun addSampling(sampling: Sampling): Unit = SamplingTransaction {
        SamplingDAO.new {
            time_id = sampling.time_id
            value_id = sampling.value_id
        }
    }


}