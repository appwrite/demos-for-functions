using System;
using System.Net.Http;
using System.Threading.Tasks;
using System.Text.Json;

namespace GenerateGiphyGif
{
    class Program
    {
        static async Task Main(string[] args)
        {
            var giphyApiKey = Environment.GetEnvironmentVariable("GIPHY_API_KEY");
            var keyword = Environment.GetEnvironmentVariable("APPWRITE_FUNCTION_DATA");
            var httpClient = new HttpClient();
            var url = $"https://api.giphy.com/v1/gifs/search?api_key={giphyApiKey}&q={keyword}&limit=1&offset=0&rating=g&lang=en";
            var response = await httpClient.GetAsync(url);
            var responseContentString = await response.Content.ReadAsStringAsync();
            if (response.IsSuccessStatusCode)
            {
                var options = new JsonSerializerOptions { PropertyNamingPolicy = JsonNamingPolicy.CamelCase };
                var giphyResponse = JsonSerializer.Deserialize<GiphyResponseRoot>(responseContentString, options);
                if (giphyResponse?.Data != null && giphyResponse.Data.Length > 0)
                {
                    Console.WriteLine(giphyResponse.Data[0].Url);
                }
                else
                {
                    Console.Error.WriteLine("Gif not found");
                }
            }
            else
            {
                Console.Error.WriteLine(responseContentString);
            }
        }
    }

    class GiphyResponseRoot
    {
        public GiphyResponseDataItem[] Data
        {
            get;
            set;
        }
    }

    class GiphyResponseDataItem
    {
        public string Url
        {
            get;
            set;
        }
    }
}