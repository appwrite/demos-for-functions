using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text.Json;
using System.Threading.Tasks;

namespace GetCovidStats
{
    public static class Global
    {
        private static string BaseUrl => "https://api.covid19api.com";
        public static string GlobalUrl { get; private set; } = $"{BaseUrl}/world?from=@fromParam@&to=@toParam@";
        public static string CountryUrl { get; private set; } = $"{BaseUrl}/live/country/@countryParam@";
    }

    class Program
    {
        async static Task Main(string[] args)
        {
            var country = Environment.GetEnvironmentVariable("COUNTRY_SLUG");

            using (var client = new HttpClient())
            {
                if (string.IsNullOrEmpty(country))
                {
                    var dateFrom = DateTime.Today.AddDays(-1).ToString("yyyy-MM-ddThh:mm:ssZ");
                    var dateTo = DateTime.Today.AddDays(0).ToString("yyyy-MM-ddThh:mm:ssZ");
                    var globalData = await client.FetchGlobalData(dateFrom, dateTo);
                    PrintResults(globalData);
                }
                else
                {
                    var provicenData = await client.FetchCountryData(country);
                    PrintResults(provicenData);
                }
            }
        }

        static void PrintResults(List<ProvinceData> provinceDataList)
        {
            var todaysDataList = provinceDataList.Where(p => p.Date == DateTime.Today);
            Console.WriteLine("- Country: " + todaysDataList.First().Country);
            Console.WriteLine("- Confirmed cases: " + todaysDataList.Sum(p => p.Confirmed));
            Console.WriteLine("- Death cases: " + todaysDataList.Sum(p => p.Deaths));
            Console.WriteLine("- Recovered cases: " + todaysDataList.Sum(p => p.Recovered));
            Console.WriteLine("- Active cases: " + todaysDataList.Sum(p => p.Active));
        }

        static void PrintResults(GlobalData globalData)
        {
            Console.WriteLine("- Global Stats");
            Console.WriteLine("- Confirmed cases: " + globalData.TotalConfirmed);
            Console.WriteLine("- Death cases: " + globalData.TotalDeaths);
            Console.WriteLine("- Recovered cases: " + globalData.TotalRecovered);
        }
    }

    public class ProvinceData
    {
        public string Country { get; set; }
        public int Confirmed { get; set; }
        public int Deaths { get; set; }
        public int Recovered { get; set; }
        public int Active { get; set; }
        public DateTime Date { get; set; }
    }

    public class GlobalData
    {
        public int NewConfirmed { get; set; }
        public int TotalConfirmed { get; set; }
        public int NewDeaths { get; set; }
        public int TotalDeaths { get; set; }
        public int NewRecovered { get; set; }
        public int TotalRecovered { get; set; }
    }

    public static class Extensions
    {
        public static async Task<List<ProvinceData>> FetchCountryData(this HttpClient client, string country)
        {
            var req = await client.GetAsync(Global.CountryUrl.Replace("@countryParam@", country));
            var provinceDataList = JsonSerializer.Deserialize<List<ProvinceData>>(await req.Content.ReadAsStringAsync());
            return provinceDataList;
        }

        public static async Task<GlobalData> FetchGlobalData(this HttpClient client, string dateFrom, string dateTo)
        {
            var req = await client.GetAsync(Global.GlobalUrl.Replace("@dateFrom@", dateFrom).Replace("@dateTo@", dateTo));
            var globalData = JsonSerializer.Deserialize<List<GlobalData>>(await req.Content.ReadAsStringAsync());
            return globalData.First();
        }
    }
}
