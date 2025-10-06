package io.github.whdt

import io.github.whdt.db.entities.HumanDigitalTwin

interface HDTRepository {
    suspend fun allHDT(): List<HumanDigitalTwin>
    suspend fun addHDT(hdt: HumanDigitalTwin)
}