using Appwrite;
using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
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

            // .NET parser is taking first property starting with "$" as metadata
            var jsonSettings = new Newtonsoft.Json.JsonSerializerSettings()
            {
                MetadataPropertyHandling = Newtonsoft.Json.MetadataPropertyHandling.Ignore
            };
            var json = Environment.GetEnvironmentVariable("APPWRITE_FUNCTION_EVENT_DATA");

            // Triggered by the storage.files.create event
            var payload = Newtonsoft.Json.JsonConvert.DeserializeObject<Dictionary<string, object>>(json, jsonSettings);
            var fileID = payload["$id"];

            // // Setup appwrite client
            var client = new Client();
            client
              .SetEndPoint(Environment.GetEnvironmentVariable("APPWRITE_ENDPOINT"))
              .SetProject(Environment.GetEnvironmentVariable("APPWRITE_PROJECT_ID"))
              .SetKey(Environment.GetEnvironmentVariable("APPWRITE_API_KEY"))
            ;

            // Get the image file 
            var storage = new Storage(client);
            var result = storage.GetFilePreview(fileID.ToString());

            // Read bytes from the url we received
            var clientt = new System.Net.WebClient();
            clientt.Headers.Add("X-Appwrite-Project", Environment.GetEnvironmentVariable("APPWRITE_PROJECT_ID")); // needed as we only get url string without projectID
            var bytes = await clientt.DownloadDataTaskAsync(new Uri(result));
            //Save the file to the container
            await File.WriteAllBytesAsync(filename, bytes);

            // Configure API key authorization: Apikey
            var configuration = new Cloudmersive.APIClient.NETCore.ImageRecognition.Client.Configuration();
            configuration.ApiKey["Apikey"] = Environment.GetEnvironmentVariable("CLOUDMERSIVE_API_KEY");

            // create an instance of the API class
            var api_instance = new Cloudmersive.APIClient.NETCore.ImageRecognition.Api.RecognizeApi(configuration);
            var image_file = filename; // file | Image file to perform the operation on.  Common file formats such as PNG, JPEG are supported.

            try
            {
                //Detect objects including types and locations in an image
                var api_response = await api_instance.RecognizeDetectObjectsAsync(File.OpenRead(image_file));
                if (api_response.Successful == true)
                {
                    foreach (var item in api_response.Objects)
                    {
                        Console.WriteLine(item.ObjectClassName);
                    }
                }
                else
                {
                    Console.WriteLine("It failed");
                }
            }
            catch (System.Exception ex)
            {
                Console.WriteLine($"Exception when calling RecognizeApi->recognize_detect_objects: {ex}");
            }
        }
    }
}