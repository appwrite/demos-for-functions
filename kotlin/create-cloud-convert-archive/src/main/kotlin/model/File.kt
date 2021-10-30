package model

import java.io.InputStream

data class RawFile(
    val name: String,
    val data: InputStream
)