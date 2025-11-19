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
import org.jetbrains.exposed.v1.r2dbc.selectAll
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction

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


}