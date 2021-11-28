using System;
using System.Threading.Tasks;
using System.Collections.Generic;
using RestSharp;
using Newtonsoft.Json.Linq;

namespace send_message_bird_sms
{
    class Program
    {
        static async Task Main(string[] args)
        {
            //MessageBird API access token
            var token = Environment.GetEnvironmentVariable("MESSAGE_BIRD_ACCESS_KEY");

            var payload = JObject.Parse(Environment.GetEnvironmentVariable("APPWRITE_FUNCTION_DATA"));
            var phoneNumber = payload["phoneNumber"];
            var text = payload["text"];

            //The sender of the message. This can be a telephone number (including country code) or an alphanumeric string.
            var originator = Environment.GetEnvironmentVariable("ORIGINATOR");

            var client = new RestClient("https://rest.messagebird.com");
            client.Timeout = -1;
            var request = new RestRequest("/messages", Method.POST);

            request.AddHeader("Authorization", $"AccessKey {token}");
            request.AddHeader("Content-Type", "application/x-www-form-urlencoded");
            request.AddParameter("recipients", $"{phoneNumber}");
            request.AddParameter("originator", $"{originator}");
            request.AddParameter("body", $"{text}");
            try
            {
                var response = await client.ExecuteAsync<MessageBirdResponseObject>(request);
                Console.WriteLine(response.Data.recipients.items[0].status);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex);
            }

        }

    }

    public class Item
    {
        public string status { get; set; }
    }

    public class Recipients
    {
        public List<Item> items { get; set; }
    }

    public class MessageBirdResponseObject
    {
        public Recipients recipients { get; set; }
    }

}