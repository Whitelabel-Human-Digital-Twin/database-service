package io.github.whdt.db.mappingEntities

import org.jetbrains.exposed.v1.core.Table

object InterfaceTable : Table("interface") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val ipaddress = varchar("ipaddress", 15)
    val port = integer( "port")
    val clientid = varchar("clientid", 50)
    val type = varchar("type", 50)
    override val primaryKey = PrimaryKey(id)
}