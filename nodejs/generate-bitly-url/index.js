// Import axios to make a post request
const axios = require('axios');

// Bitly access token (can be fetched from https://app.bitly.com/settings/api/)
const token = process.env.BITLY_ACCESS_TOKEN;
const config = {
    headers: {
        'Authorization': `Bearer ${token}`,
    }
};

// Getting the URL from Appwrite's environment variable
const payload = JSON.parse(process.env.APPWRITE_FUNCTION_EVENT_DATA);
const data = {
    "long_url": payload['url']
};

// Make a request to bitly and output the shortened URL
axios.post('https://api-ssl.bitly.com/v4/shorten', 
  data,
  config,
  )
  .then(function (response) {
    console.log(response.data.link);
  })
  .catch(function (error) {
    console.log(error);
  });