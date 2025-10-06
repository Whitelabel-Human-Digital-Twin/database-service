package io.github.whdt

import db.mappingEntities.HumanDigitalTwinDAO
import db.mappingEntities.HumanDigitalTwinTransaction
import db.mappingEntities.daoToModel
import io.github.whdt.db.entities.HumanDigitalTwin

class PostgresHDTRepository : HDTRepository {
    override suspend fun allHDT(): List<HumanDigitalTwin> = HumanDigitalTwinTransaction {
        HumanDigitalTwinDAO.all().map(::daoToModel)
    }

    override suspend fun addHDT(hdt: HumanDigitalTwin): Unit = HumanDigitalTwinTransaction {
        HumanDigitalTwinDAO.new {
            name = hdt.name
        }
    }
}
