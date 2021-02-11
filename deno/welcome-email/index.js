
// Create a new MailGunClient and pass it your API key and API domain
const domain = Deno.env.get("MAILGUN_DOMAIN")
const apiKey = Deno.env.get("MAILGUN_API_KEY")

// Get the name and email of the newly created user from Appwrite's environment variable
const payload = JSON.parse(Deno.env.get("APPWRITE_FUNCTION_EVENT_PAYLOAD"));
const name = payload['name'];
const email = payload['email'];

// Create your email 
const data = {
	from: 'Welcome to My Awesome App <welcome@my-awesome-app.io>',
	to: email,
	subject: `Welcome on board ${name}!`,
	text: `Hi ${name}\nGreat to have you with us. ! 😍`
};
const form = new FormData();
Object.keys(data).forEach((k) => form.append(k, data[k]));

// Send the email! ❤️
fetch(`https://api.mailgun.net/v3/${domain}/messages`, {
      method: "POST",
      headers: {
        Authorization: `Basic ${btoa("api:" + apiKey)}`,
      },
      body: form,
    });
