import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
class CreateTimeRequest (
    val hdt: String,
    val prop: String,
    val valu: String,
    val time: LocalDateTime
)