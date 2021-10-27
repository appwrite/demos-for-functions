# 🦠 Generate Map Preview Using Google Maps

A sample Kotlin Cloud Function for generating a map preview using Google Maps.

## 📝 Environment Variables

Go to Settings tab of your Cloud Function. Add the following environment variables.

- **APPWRITE_ENDPOINT** - Mandatory. Your Appwrite Endpoint
- **APPWRITE_API_KEY** - Mandatory. Your Appwrite API key with `files.write` permissions
- **GOOGLEMAPS_API_KEY** - Mandatory. Your Google Maps API Key for Maps Static API. Created through Google Maps
  Platform > Credentials on Google Cloud Platform

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

- Create a tarfile

```bash
gradle shadowDistTar
```

(the tarfile is created in `build/distributions`)

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this
  case `java -jar lib/generate-google-map-1.0.0-SNAPSHOT-all.jar`) as your entrypoint command
- Upload your tarfile
- Click `Activate`

## 🎯 Trigger

Head over to your function in the Appwrite console and under the Overview Tab, click Execute Now and supply the function
data in JSON format, eg.

```json
{
  "outputFileName": "map.png",
  "width": 600,
  "height": 300,
  "center": {
    "latitude": 40.706086,
    "longitude": -73.996864
  },
  "zoom": 13,
  "mapType": "roadmap",
  "markers": [
    {
      "color": "blue",
      "label": "S",
      "locations": [
        {
          "latitude": 40.702147,
          "longitude": -74.015794
        }
      ]
    },
    {
      "color": "green",
      "label": "G",
      "locations": [
        {
          "latitude": 40.711614,
          "longitude": -74.012318
        }
      ]
    },
    {
      "color": "red",
      "label": "C",
      "locations": [
        {
          "latitude": 40.718217,
          "longitude": -73.998284
        }
      ]
    }
  ]
}
```

### Parameters:

- **outputFileName** - Mandatory. Name of output file (including extension).
- **width** - Mandatory. The width of the map in pixels, when multiplied by the `scale` value
- **height** - Mandatory. The height of the map in pixels, when multiplied by the `scale` value
- **center** - Mandatory. Coordinates to center on. See `Coordinates` definition below
- **zoom** - Optional. Required if `markers` or `path` is not present. The zoom level (
  see https://developers.google.com/maps/documentation/maps-static/start#Zoomlevels for more information)
- **scale** - Optional. Map size pixel multiplier. Defaults to `1`
- **format** - Optional. The image format (
  see https://developers.google.com/maps/documentation/maps-static/start#ImageFormats for the list of options)
- **mapType** - Optional. Type of map to construct (
  see https://developers.google.com/maps/documentation/maps-static/start#MapTypes for the list of options)
- **region** - Optional. A region code, specified as a two-character ccTLD ('top-level domain') value. Defines the
  appropriate borders to display, based on geo-political sensitivities
- **markers** - Optional. A set of markers (map pins) at a set of locations. See `Marker` definition below
- **paths** - Optional. A set of locations connected by a path to overlay on the map image. See `Path` definition below
- **viewport** - Optional. Coordinates of a location to remain visible. See `Coordinates` definition below

#### Marker

- **size** - Optional. The size of marker (
  see https://developers.google.com/maps/documentation/maps-static/start#MarkerStyles for the list of options)
- **color** - Optional. A 24-bit color (example: `0xFFFFCC`) or a predefined color from the set (
  see https://developers.google.com/maps/documentation/maps-static/start#MarkerStyles for the list of options)
- **label** - Optional. A single uppercase alphanumeric character
- **customIcon** - Optional. Custom icon for the marker. See `Custom Icon` definition below
- **locations** - Mandatory. Coordinates of the marker. At least one is required. See `Coordinates` definition below

##### Custom Icon

- **url** - Mandatory. URL for the custom icon
- **anchorPoint** - Mandatory. The anchor point for the custom icon (
  see https://developers.google.com/maps/documentation/maps-static/start#CustomIcons for the list of options)
- **scale** - Optional. The image density scale of the custom icon provided (
  see https://developers.google.com/maps/documentation/maps-static/start#MarkerScale for the list of options)

#### Path

- **weight** - Optional. The thickness of the path in pixels. Defaults to `5` pixels.
- **color** - Optional. A 24-bit color (example: `0xFFFFCC`) or a predefined color from the set (
  see https://developers.google.com/maps/documentation/maps-static/start#PathStyles for the list of options)
- **fillColor** - Optional. A 24-bit color (example: `0xFFFFCC`) or a predefined color from the set (
  see https://developers.google.com/maps/documentation/maps-static/start#PathStyles for the list of options)
- **geodesic** - Optional. If `true`, indicates that the requested path should be interpreted as a geodesic line that
  follows the curvature of the earth. If `false`, the path is rendered as a straight line in screen space. Defaults
  to `false`
- **points** - Mandatory. Coordinates of the points to be connected. At least two are required. See `Coordinates`
  definition below

#### Coordinates

- **latitude** - Mandatory. The latitude of the location. Must be between `-90` and `90`
- **longitude** - Mandatory. The longitude of the location. Must be between `-180` and `180`

See https://developers.google.com/maps/documentation/maps-static/start for more information.
