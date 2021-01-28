using Appwrite;
using System;
using System.Net.Http;
using System.Threading.Tasks;

namespace template
{
  class Program
  {
    static async Task Main(string[] args)
    {
      Client client = new Client();
      client
        .SetEndPoint("http://localhost:9501/v1") // Your API Endpoint 
        .SetProject("5df5acd0d48c2") // Your project ID 
        .SetKey("919c2d18fb5d4...a2ae413da83346ad2") // Your secret API key 
      ;

      Storage storage = new Storage(client);
      HttpResponseMessage result = await storage.ListFiles();
      
      Console.WriteLine(result.ToString());
    }
  }
}
