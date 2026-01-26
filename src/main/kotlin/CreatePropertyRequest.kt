import io.github.whdt.core.hdt.model.id.HdtId
import io.github.whdt.core.hdt.model.property.Property

import kotlinx.serialization.Serializable

@Serializable
data class CreatePropertyRequest(
    val hdt: HdtId,
    val prop: Property
)