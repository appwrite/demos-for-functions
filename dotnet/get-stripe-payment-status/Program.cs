using System;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text.Json;
using System.Threading.Tasks;

namespace get_stripe_payment_status
{
  class Program
  {
    static HttpClient client = new HttpClient();

    static async Task Main(string[] args)
    {
      //Stripe API access token
      string token = Environment.GetEnvironmentVariable("STRIPE_API_TOKEN");

      // Payment ID to retrieve status
      string stripePaymentId  = Environment.GetEnvironmentVariable("APPWRITE_FUNCTION_DATA");

      try{
        string url = $"https://api.stripe.com/v1/payment_intents/{stripePaymentId}";
        client.DefaultRequestHeaders.Add("Authorization", $"Bearer {token}");
        var streamTask = client.GetStreamAsync(url);
        var paymentIntent = await JsonSerializer.DeserializeAsync<PaymentIntent>(await streamTask);
        Console.WriteLine(paymentIntent.status);
      }
      catch (Exception e){
          Console.WriteLine("{0} Exception caught.", e.Message);
      }

    }
  }

  public class PaymentIntent
  {
      public string status { get; set; }
  }
}




