import MailgunClient from "https://gist.githubusercontent.com/webdeb/837886746bf43e46effb269c68f0f5ce/raw/1eff4888e78ea1d8d2998c62284d1f7711ce205c/Mailgun.ts"

// Create a new MailGunClient and pass it your API key and API domain
const mg = new MailgunClient(Deno.env.get("MAILGUN_API_KEY"), Deno.env.get("MAILGUN_DOMAIN"));

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
// Send the email! ❤️
mg.send(data);
