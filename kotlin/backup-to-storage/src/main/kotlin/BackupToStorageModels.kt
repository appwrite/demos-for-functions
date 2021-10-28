import com.fasterxml.jackson.annotation.JsonProperty

data class Collections(
    val sum: Int,
    val collections: List<Collection>
)

data class Collection(
    @JsonProperty("\$id")
    val id: String,

    @JsonProperty("\$permissions")
    val permissions: List<Permission>,
    val name: String,
    val dateCreated: Long,
    val dateUpdated: Long,
    val rules: List<Rule>
)

data class Permission(
    val read: List<String>,
    val write: List<String>
)

data class Rule(
    @JsonProperty("\$id")
    val id: String,

    @JsonProperty("\$collection")
    val collection: String,
    val type: String,
    val key: String,
    val label: String,
    val default: String,
    val array: Boolean,
    val required: Boolean,
    val list: List<String>
)