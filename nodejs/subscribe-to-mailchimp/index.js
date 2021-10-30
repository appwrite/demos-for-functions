const Mailchimp = require('mailchimp-api-v3');
const listId = process.env.APPWRITE_MAILCHIMP_LIST_ID;
const apiKey = process.env.APPWRITE_MAILCHIMP_API_KEY;
const mailchimp = new Mailchimp(apiKey);

const email = JSON.parse(process.env.APPWRITE_FUNCTION_DATA)['email'];

const body = {
    email_address: email,
    status: 'subscribed'
};

mailchimp.post({
    path: `/lists/${listId}/members`,
    body
}).then(console.log).catch((err) => {
    console.error(err.detail);
});