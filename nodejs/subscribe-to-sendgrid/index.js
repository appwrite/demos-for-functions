// Perform all your imports
const sdk = require('node-appwrite');
const sgClient = require('@sendgrid/client');

// Initialise the appwrite client SDK
let awClient = new sdk.Client();
awClient
	.setEndpoint(process.env.APPWRITE_ENDPOINT) // Your API Endpoint
	.setProject(process.env.APPWRITE_FUNCTION_PROJECT_ID) // Your project ID available by default
	.setKey(process.env.APPWRITE_API_KEY) // Your secret API key
	;

// Initialise the SendGrid client SDK
sgClient.setApiKey(process.env.SENDGRID_API_KEY);

// Get the name and email of the newly created user from Appwrite's environment variable
const payload = JSON.parse(process.env.APPWRITE_FUNCTION_EVENT_DATA);
const email = payload['email'];

// creating request body object, to be sent to the SendGrid API.
const requestBody = {
	method: 'PUT',
	url: '/v3/marketing/contacts',
	body: {
		list_ids: [process.env.NEWSLETTER_LIST_ID],
		contacts: [
			{
				email
			}
		]
	}
};

// sending the request
try {
	sgClient.request(requestBody).then(([response, body]) => {
		console.log(response.statusCode);
		console.log(body);

		// do something with body if required.
	})
} catch (error) {
	// handle error 
}

