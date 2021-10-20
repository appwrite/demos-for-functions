data class Permissions(
    val read: List<String>,
    val write: List<String>,
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
    val list: List<String>,
)

data class Collection(
    val `$id`: String,
    val `$permissions`: Permissions,
    val name: String,
    val dateCreated: Int,
    val dateUpdated: Int,
    val rules: List<Rule>,
)

data class Document(
    val `$id`: String,
    val `$collection`: String,
    val `$permissions`: Permissions,
)

data class CollectionList(val sum: Int, val collections: List<Collection>)
data class DocumentList(val sum: Int, val documents: List<Document>)
