using Appwrite;
using System;
using System.Text.Json;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace StorageCleaner
{
    class Program
    {
        static async Task Main(string[] args)
        {
            var client = new Client();
            client
              .SetEndPoint(Environment.GetEnvironmentVariable("APPWRITE_ENDPOINT"))
              .SetProject(Environment.GetEnvironmentVariable("APPWRITE_PROJECT_ID"))
              .SetKey(Environment.GetEnvironmentVariable("APPWRITE_API_KEY"))
            ;

            var storage = new Storage(client);
            var result = await storage.ListFiles(orderType: OrderType.DESC, limit: 100);
            var content = await result.Content.ReadAsStringAsync();

            var listFile = JsonSerializer.Deserialize<ListFiles>(content);
            var days = Convert.ToDouble(Environment.GetEnvironmentVariable("DAYS_TO_EXPIRE"));
            var timestamp = DateTimeOffset.Now.AddDays(-days).ToUnixTimeSeconds();
            var deletedFiles = 0;
            foreach (File file in listFile.Files)
            {
                if (file.DateCreated < timestamp)
                {
                    await storage.DeleteFile(file.Id);
                    Console.WriteLine($"Deleted {file.Id}");
                    deletedFiles++;
                }
            }
            Console.WriteLine($"Total files deleted: {deletedFiles}");
        }
    }
}

public class File
{
    [JsonPropertyNameAttribute("$id")]
    public string Id { get; set; }

    [JsonPropertyNameAttribute("dateCreated")]
    public int DateCreated { get; set; }
}

public class ListFiles
{
    [JsonPropertyNameAttribute("sum")]
    public int Sum { get; set; }

    [JsonPropertyNameAttribute("files")]
    public File[] Files { get; set; }
}