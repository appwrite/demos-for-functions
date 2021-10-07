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
            Environment.SetEnvironmentVariable("COUNTRY_SLUG", "brazil");
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
            foreach (var provicenData in provinceDataList)
            {
                Console.WriteLine($"- Country: {provicenData.Country}");
                Console.WriteLine($"    - Province: {provicenData.Province}");
                Console.WriteLine($"        - Confirmed cases: {provicenData.Confirmed}");
                Console.WriteLine($"        - Death cases: {provicenData.Deaths}");
                Console.WriteLine($"        - Recovered cases: {provicenData.Recovered}");
                Console.WriteLine($"        - Active cases: {provicenData.Active}");
                Console.WriteLine($"        - Date: {provicenData.Date.ToShortDateString()}");
            }
        }

        static void PrintResults(GlobalData globalData)
        {
            Console.WriteLine($"- New Confirmed: {globalData.NewConfirmed}");
            Console.WriteLine($"- Total Confirmed: {globalData.TotalConfirmed}");
            Console.WriteLine($"- New Deaths: {globalData.NewDeaths}");
            Console.WriteLine($"- Total Deaths: {globalData.TotalDeaths}");
            Console.WriteLine($"- New Recovered: {globalData.NewRecovered}");
            Console.WriteLine($"- Total Recovered: {globalData.TotalRecovered}");
            Console.WriteLine($"- Date: {globalData.Date.ToShortDateString()}");
        }
    }

    public class ProvinceData
    {
        public string Country { get; set; }
        public string Province { get; set; }
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
        public DateTime Date { get; set; }
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
