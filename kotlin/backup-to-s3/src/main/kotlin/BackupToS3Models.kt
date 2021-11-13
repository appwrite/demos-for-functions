data class Collections(
    val sum: Int,
    val collections: List<Collection>
)

data class Collection(
    val `$id`: String,
    val `$permissions`: Permissions,
    val name: String,
    val dateCreated: Long,
    val dateUpdated: Long,
    val rules: List<Rule>
)

data class Rule(
    val `$id`: String,
    val `$collection`: String,
    val type: String,
    val key: String,
    val label: String,
    val default: String,
    val array: Boolean,
    val required: Boolean,
    val list: List<String>
)

data class Permissions(
    val read: List<String>,
    val write: List<String>,
)

data class Document(
    val `$id`: String,
    val `$collection`: String,
    val `$permissions`: Permissions,
    val name: String?,
    val age: String?,
)

data class Documents(
    val sum: Int,
    val documents: List<Document>
)
