package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Response(
    @SerialName("ID") val iD : String,
    @SerialName("Message") val message : String,
    @SerialName("Global") val global : GlobalData,
    @SerialName("Countries") val countries : List<CountryData>,
    @SerialName("Date") val date : String
)
