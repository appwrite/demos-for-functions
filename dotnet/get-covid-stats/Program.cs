using System;
using System.Net.Http;
using System.Threading.Tasks;

namespace GetCovidStats
{
    class Program
    {
        async static Task Main(string[] args)
        {
            var country = Environment.GetEnvironmentVariable("COUNTRY_SLUG");
            var dateFrom = DateTime.Today.AddDays(-1).ToString("yyyy-MM-ddThh:mm:ssZ");
            var dateTo = DateTime.Today.AddDays(0).ToString("yyyy-MM-ddThh:mm:ssZ");
            using (var client = new HttpClient())
            {
                if (string.IsNullOrEmpty(country))
                {
                    var req = await client.GetAsync($"https://api.covid19api.com/world?from={dateFrom}&to={dateTo}");
                    Console.WriteLine(await req.Content.ReadAsStringAsync());
                }
                else
                {
                    var req = await client.GetAsync($"https://api.covid19api.com/live/country/{country}");
                    Console.WriteLine(await req.Content.ReadAsStringAsync());
                }
            }
        }
    }
}
