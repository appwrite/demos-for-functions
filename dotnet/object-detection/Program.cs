using Appwrite;
using System;
using System.IO;
using System.Text.Json;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace ObjectDetection
{
    class Program
    {
        static async Task Main(string[] args)
        {
            var filename = "temp.jpg";

            // Triggered by the storage.files.create event
            var payload = json.loads(Environment.GetEnvironmentVariable["APPWRITE_FUNCTION_EVENT_DATA"]);
            var fileID = payload["$id"];

            // Setup appwrite client
            var client = new Client();
            client
              .SetEndPoint(Environment.GetEnvironmentVariable("APPWRITE_ENDPOINT"))
              .SetProject(Environment.GetEnvironmentVariable("APPWRITE_PROJECT_ID"))
              .SetKey(Environment.GetEnvironmentVariable("APPWRITE_API_KEY"))
            ;

            // Get the image file 
            var storage = new Storage(client);
            var result = storage.GetFilePreview(fileID);

            // Save the file to the container
            await File.WriteAllBytesAsync(fileID, result);

            // Configure API key authorization: Apikey
            var configuration = new Cloudmersive.APIClient.NETCore.ImageRecognition.Client.Configuration();
            configuration.ApiKey["Apikey"] = Environment.GetEnvironmentVariable("CLOUDMERSIVE_API_KEY");

            // create an instance of the API class
            var api_instance = new Cloudmersive.APIClient.NETCore.ImageRecognition.Api.RecognizeApi(configuration);
            var image_file = filename; // file | Image file to perform the operation on.  Common file formats such as PNG, JPEG are supported.

            try
            {
                // Detect objects including types and locations in an image
                var api_response = await api_instance.RecognizeDetectObjectsAsync(File.OpenRead(fileID));
                Console.WriteLine(api_response);
            }
            catch (System.Exception ex)
            {
                Console.WriteLine($"Exception when calling RecognizeApi->recognize_detect_objects: {ex}");
            }
        }
    }
}