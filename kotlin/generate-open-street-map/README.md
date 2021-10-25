# 🗺️ Creates a new Map Tile using Open Street Maps API
A Kotlin Cloud Function that saves an [Open Street Map Tile](https://wiki.openstreetmap.org/wiki/Slippy_map_tilenames) to appwrite storage with given latitude and longitude.

## 📝 Environment Variables
When running function, pass data in the following format.
```json
{
    "latitude": 37.7822403,
    "longitude": -122.3910414
}
```

* **APPWRITE_API_KEY** - Create a key from the Appwrite console with the following scope (`files.write`)
* **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
* **ZOOM_LEVEL** *(optional)* - Pass a zoom level as described [here](https://wiki.openstreetmap.org/wiki/Slippy_map_tilenames#Zoom_levels)

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

* Import the project into IntelliJ, or any other IDE that has support for Kotlin projects.

* Build a jar for the project. Follow the instructions [here](https://hardiksachan.hashnode.dev/build-a-jar-with-gradle)

* Create a tarfile

```bash
$ cd demos-for-functions/kotlin/generate-open-street-map/build/libs/
$ tar -zcvf code.tar.gz ./generate-open-street-map-1.0-SNAPSHOT.jar
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `java -jar generate-open-street-map-1.0-SNAPSHOT.jar`) as your entry point command
* Upload your `tarfile`
* Click 'Activate'

## 🎯 Trigger
Can be triggered from manually from the Appwrite Console.