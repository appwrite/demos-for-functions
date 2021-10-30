using Newtonsoft.Json.Linq;
using System;
using System.IO;
using System.Net;

namespace GenerateBitlyURL
{
    class Program
    {

        static void Main(string[] args)
        {
            string url = Environment.GetEnvironmentVariable("APPWRITE_FUNCTION_DATA");
            string token = Environment.GetEnvironmentVariable("BITLY_ACCESS_TOKEN");

            Console.WriteLine(GenerateBitlyURL(url, token));
        }

        public static string GenerateBitlyURL(string urlToGenerate, string token)
        {
            try
            {
                var url = "https://api-ssl.bitly.com/v4/shorten";

                var httpRequest = (HttpWebRequest)WebRequest.Create(url);
                httpRequest.Method = "POST";

                httpRequest.Headers["Authorization"] = "Bearer " + token;
                httpRequest.ContentType = "application/json";

                var data = "{\"long_url\": \"" + urlToGenerate + "\"}";

                using (var streamWriter = new StreamWriter(httpRequest.GetRequestStream()))
                {
                    streamWriter.Write(data);
                }

                var httpResponse = (HttpWebResponse)httpRequest.GetResponse();
                string content = "";
                using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
                {
                    content = streamReader.ReadToEnd();
                }

                JObject jsonObject = JObject.Parse(content);
                return jsonObject["link"].ToString();
            }
            catch(Exception ex)
            {
                return "Error: " + ex.Message;
            }
        }

    }
}
