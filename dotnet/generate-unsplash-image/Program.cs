using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text.Json;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

namespace GenerateUnsplashImage
{
    class Program
    {
        private static string UnsplashApiUrl = "https://api.unsplash.com/search/photos";
        static async Task Main(string[] args)
        {
            var keyword = Environment.GetEnvironmentVariable("UNSPLASH_KEYWORD");
            var accessKey = Environment.GetEnvironmentVariable("UNSPLASH_ACCESS_KEY");

            using (var client = new HttpClient())
            {
                client.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Client-ID", accessKey);
                var req = await client.GetAsync($"{UnsplashApiUrl}?query={keyword}&per_page=1");
                var strigContent = await req.Content.ReadAsStringAsync();
                var result = JsonSerializer.Deserialize<UnsplashSearchResult>(strigContent);
                Console.WriteLine($"Image URL: {result.Results.First().Links.Download}");
                Console.WriteLine($"Image Author: {result.Results.First().User.Name}");
            }
        }
    }

    public class UnsplashSearchResult
    {
        [JsonPropertyName("results")]
        public List<UnsplashResult> Results { get; set; }
    }

    public class UnsplashResult
    {
        [JsonPropertyName("links")]
        public Link Links { get; set; }
        [JsonPropertyName("user")]
        public User User { get; set; }
    }

    public class Link
    {
        [JsonPropertyName("download")]
        public string Download { get; set; }
    }

    public class User
    {
        [JsonPropertyName("name")]
        public string Name { get; set; }
    }
}
