using System;
using Newtonsoft.Json;
using Twilio;
using Twilio.Rest.Api.V2010.Account;
using Twilio.Types;

namespace SendSMS
{
    public class Program
    {
        public static void Main(string[] args)
        {
            try
            {
                string requestBody = Environment.GetEnvironmentVariable("APPWRITE_FUNCTION_DATA");
                dynamic data = JsonConvert.DeserializeObject(requestBody);
                string toPhoneNumber = data?.toPhoneNumber;
                string messageBody = data?.messageBody;

                TwilioClient.Init(Environment.GetEnvironmentVariable("TWILIO_ACCOUNTSID"),Environment.GetEnvironmentVariable("TWILIO_AUTHTOKEN"));

                var message = MessageResource.Create(
                    to: new PhoneNumber(toPhoneNumber),
                    from: new PhoneNumber(Environment.GetEnvironmentVariable("TWILIO_PHONENUMBER")),
                    body: messageBody
                );

                Console.WriteLine(message.Sid);
            }
            catch(Exception ex)
            {
                Console.Error.WriteLine(ex.Message);
            }
        }
    }
}
