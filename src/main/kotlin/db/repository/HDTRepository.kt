package io.github.whdt.db.repository

import io.github.whdt.db.entities.HumanDigitalTwin
import io.github.whdt.db.entities.Interface
import io.github.whdt.db.entities.Property
import io.github.whdt.db.entities.Time
import io.github.whdt.db.entities.Value
import io.github.whdt.db.relations.Associated
import io.github.whdt.db.relations.Defines
import io.github.whdt.db.relations.Implements
import io.github.whdt.db.relations.Interacts
import io.github.whdt.db.relations.Sampling

interface HDTRepository {
    suspend fun allHDT(): List<HumanDigitalTwin>
    suspend fun allInterface(): List<Interface>
    suspend fun allProperty(): List<Property>
    suspend fun allTime(): List<Time>
    suspend fun allValue(): List<Value>
    suspend fun allAssociated(): List<Associated>
    suspend fun allDefines(): List<Defines>
    suspend fun allImplements(): List<Implements>
    suspend fun allInteracts(): List<Interacts>
    suspend fun allSampling(): List<Sampling>
    suspend fun addHDT(hdt: HumanDigitalTwin)
    suspend fun addInterface(int: Interface)
    suspend fun addProperty(prop: Property)
    suspend fun addTime(time: Time)
    suspend fun addValue(valu: Value)
    suspend fun addAssociated(associated: Associated)
    suspend fun addDefines(defines: Defines)
    suspend fun addImplements(implements: Implements)
    suspend fun addInteracts(interacts: Interacts)
    suspend fun addSampling(sampling: Sampling)
}