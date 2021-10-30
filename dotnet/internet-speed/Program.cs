using SpeedTest;
using SpeedTest.Models;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Net;
using System.Threading.Tasks;

namespace GetInternetSpeed
{
    class Program
    {

        private static SpeedTestClient client;
        private static Settings settings;

        static void Main(string[] args)
        {

            string output = "{\"Download\": <<d>>, \"Upload\": <<u>>}";
            client = new SpeedTestClient();
            settings = client.GetSettings();

            var servers = SelectServers();
            var bestServer = SelectBestServer(servers);

            var downloadSpeed = client.TestDownloadSpeed(bestServer, settings.Download.ThreadsPerUrl);
            var uploadSpeed = client.TestUploadSpeed(bestServer, settings.Upload.ThreadsPerUrl);

            output = output.Replace("<<d>>", CalculateSpeed(downloadSpeed));
            output = output.Replace("<<u>>", CalculateSpeed(uploadSpeed));

            Console.Write(output);

        }

        private static Server SelectBestServer(IEnumerable<Server> servers)
        {
            var bestServer = servers.OrderBy(x => x.Latency).First();
            return bestServer;
        }

        private static IEnumerable<Server> SelectServers()
        {
            var servers = settings.Servers.Take(10).ToList();

            foreach (var server in servers)
            {
                server.Latency = client.TestServerLatency(server);
            }
            return servers;
        }

        private static String CalculateSpeed(double speed)
        {
            return Math.Round(speed / 1024, 2).ToString();
        }

    }
}
