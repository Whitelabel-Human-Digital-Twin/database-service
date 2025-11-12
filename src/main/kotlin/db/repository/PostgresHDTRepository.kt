package io.github.whdt.db.repository

import io.github.whdt.db.mappingEntities.*
import io.github.whdt.db.entities.*
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
                    dateEnter = it[TimeTable.dateEnter],
                    dateStart = it[TimeTable.dateStart],
                    dateEnd = it[TimeTable.dateEnd]
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
        TODO("Not yet implemented")
    }

    override suspend fun allDefines(): List<Defines> = suspendTransaction {
        TODO("Not yet implemented")
    }

    override suspend fun allImplements(): List<Implements> = suspendTransaction {
        TODO("Not yet implemented")
    }

    override suspend fun allInteracts(): List<Interacts> = suspendTransaction {
        TODO("Not yet implemented")
    }

    override suspend fun allSampling(): List<Sampling> = suspendTransaction {
        TODO("Not yet implemented")
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
            it[TimeTable.dateEnter] = time.dateEnter
            it[TimeTable.dateStart] = time.dateStart
            it[TimeTable.dateEnd] = time.dateEnd
        }
    }

    override suspend fun addValue(valu: Value): Unit  = suspendTransaction {
        ValueTable.insert {
            it[ValueTable.name] = valu.name
            it[ValueTable.value] = valu.value
            it[ValueTable.type] = valu.type
        }
    }

    override suspend fun addAssociated(associated: Associated) {
        TODO("Not yet implemented")
    }

    override suspend fun addDefines(defines: Defines) {
        TODO("Not yet implemented")
    }

    override suspend fun addImplements(implements: Implements) {
        TODO("Not yet implemented")
    }

    override suspend fun addInteracts(interacts: Interacts) {
        TODO("Not yet implemented")
    }

    override suspend fun addSampling(sampling: Sampling) {
        TODO("Not yet implemented")
    }


}