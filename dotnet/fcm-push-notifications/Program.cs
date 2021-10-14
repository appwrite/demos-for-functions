using FirebaseAdmin;
using FirebaseAdmin.Messaging;
using Google.Apis.Auth.OAuth2;
using System.Threading.Tasks;

namespace FCMPushNotifications
{
    class Program
    {
        static async Task Main(string[] args)
        {
            var messaging = FirebaseMessaging.GetMessaging(FirebaseApp.Create(new AppOptions()
            {
                Credential = GoogleCredential.GetApplicationDefault(),
            }));

            await messaging.SendAsync(new Message()
            {
                Notification = new Notification()
                {
                    Title = "Test title",
                    Body = "test body",
                    ImageUrl = "https://icons.iconarchive.com/icons/paomedia/small-n-flat/256/cat-icon.png"
                },
                Android = new AndroidConfig()
                {
                    Priority = Priority.High,
                },
                //Topic = "/topics/some_topic",
                //Token = "d8sG......OApP3"// token taken from FirebaseMessaging.instance.getToken()
            });
        }
    }
}