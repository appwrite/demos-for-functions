// Bitly API access token https://app.bitly.com/settings/api/
const token = Deno.env.get("BITLY_ACCESS_TOKEN")

// URL from Appwrite's environment variable
const url = Deno.env.get("APPWRITE_FUNCTION_DATA")

const body = {
    "long_url": url
}

const config = {
    method: "POST",
    headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
    },
    body: JSON.stringify(body)
}

fetch("https://api-ssl.bitly.com/v4/shorten", config) // Makes HTTP request to the API
.then(response => console.log(response.data.link)) // Outputs the shortened link
.catch(error => console.error(error)) // Catches errors if any and outputs it

