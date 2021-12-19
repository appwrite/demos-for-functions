package io.appwrite.generate.google.map

import com.google.maps.GeoApiContext
import com.google.maps.StaticMapsApi
import com.google.maps.StaticMapsRequest
import com.google.maps.model.LatLng
import com.google.maps.model.Size
import io.appwrite.generate.google.map.model.FunctionData
import io.appwrite.generate.google.map.util.Env

class GoogleMaps {
    companion object {
        private const val ENV_GOOGLEMAPS_API_KEY = "GOOGLEMAPS_API_KEY"
    }

    private val context = GeoApiContext.Builder()
        .apiKey(Env.getMandatory(ENV_GOOGLEMAPS_API_KEY))
        .build()

    fun generateMapImage(functionData: FunctionData): ByteArray {
        val request = StaticMapsApi.newRequest(context, Size(functionData.width, functionData.height))
            .center(LatLng(functionData.center.latitude, functionData.center.longitude))
        functionData.zoom?.let(request::zoom)
        functionData.scale?.let(request::scale)
        functionData.format?.let(request::format)
        functionData.mapType?.let(request::maptype)
        functionData.region?.let(request::region)
        functionData.markers.map { markerData ->
            StaticMapsRequest.Markers().apply {
                size(markerData.size)
                color(markerData.color)
                markerData.label?.let { label(it.toString()) }
                markerData.customIcon?.let {
                    if (it.scale != null) {
                        customIcon(it.url, it.anchorPoint, it.scale)
                    } else {
                        customIcon(it.url, it.anchorPoint)
                    }
                }
                markerData.locations.forEach { addLocation(LatLng(it.latitude, it.longitude)) }
            }
        }.forEach(request::markers)
        functionData.paths.map { pathData ->
            StaticMapsRequest.Path().apply {
                pathData.weight?.let { weight(it) }
                color(pathData.color)
                fillcolor(pathData.fillColor)
                pathData.geodesic?.let { geodesic(it) }
                pathData.points.forEach { addPoint(LatLng(it.latitude, it.longitude)) }
            }
        }.forEach(request::path)
        functionData.viewport?.let { request.visible(LatLng(it.latitude, it.longitude)) }

        return request.await().imageData
    }
}
