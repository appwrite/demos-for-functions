using System;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;


namespace welcome_email
{
  class Program
  {
    static HttpClient client = new HttpClient();
    static async Task Main(string[] args)
    {
      string url = $"https://api.mailgun.net/v3/{Environment.GetEnvironmentVariable("MAILGUN_DOMAIN")}/messages";
      string base64authorization = Convert.ToBase64String(Encoding.ASCII.GetBytes($"api:{Environment.GetEnvironmentVariable("MAILGUN_API_KEY")}"));
      HttpRequestMessage request = new HttpRequestMessage(new HttpMethod("POST"), url);
      request.Headers.TryAddWithoutValidation("Authorization", $"Basic {base64authorization}");

      User user = JsonSerializer.Deserialize<User>(Environment.GetEnvironmentVariable("APPWRITE_FUNCTION_EVENT_PAYLOAD"));

      MultipartFormDataContent multipartContent = new MultipartFormDataContent();
      multipartContent.Add(new StringContent("Mailgun Sandbox <welcome@my-awesome-app.io>"), "from");
      multipartContent.Add(new StringContent(user.email), "to");
      multipartContent.Add(new StringContent($"Welcome on board {user.name}!"), "subject");
      multipartContent.Add(new StringContent($"Hi {user.name}\nGreat to have you with us!😍"), "text");
      request.Content = multipartContent;

      HttpResponseMessage response = await client.SendAsync(request);
      Console.WriteLine(response);
    }
  }
  public class User
  {
    public string name
    {
      get;
      set;
    }
    public string email
    {
      get;
      set;
    }
  }
}
