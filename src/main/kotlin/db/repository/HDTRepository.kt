package io.github.whdt.db.repository

import io.github.whdt.db.entities.*
import io.github.whdt.db.relations.*
import kotlinx.datetime.LocalDateTime

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
    suspend fun detTime(timeLess : LocalDateTime,timeGreter : LocalDateTime): List<Int>
    suspend fun valueOfTime(timeLess : LocalDateTime,timeGreter : LocalDateTime): List<Value>?
    suspend fun valueOfTimeOfPropOfDt( hdtName : String,propName : String, timeLess : LocalDateTime, timeGreter : LocalDateTime): List<Value>?
    suspend fun valueOfTimeOfDt(hdtName : String, timeLess : LocalDateTime, timeGreter : LocalDateTime): Map<Int,List<Value>>?
    suspend fun detProperty(propertyName: String): List<Int>
    suspend fun minPropertyOfTimeHdt(hdt : String,propertyName : String,timeLess : LocalDateTime,timeGreter : LocalDateTime): Value?
    suspend fun maxPropertyOfTimeHdt(hdt : String,propertyName : String,timeLess : LocalDateTime,timeGreter : LocalDateTime): Value?
    suspend fun avgPropertyOfTime(hdt : String,propertyName : String,timeLess : LocalDateTime,timeGreter : LocalDateTime): Double?
    suspend fun valueInRange(minValue : String, maxValue : String) : List<Value>?
    suspend fun dtPropertyRange(propertyName : String, minValue : String, maxValue : String): List<HumanDigitalTwin>?
    suspend fun allPropOfDt(hdtName : String): List<Property>?
}