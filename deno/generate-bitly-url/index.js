// URL from Appwrite's environment variable
const url = Deno.env.get("APPWRITE_FUNCTION_DATA")

// Bitly API access token https://app.bitly.com/settings/api/
const token = Deno.env.get("BITLY_ACCESS_TOKEN")

// HTTP request config
const config = {
    method: "POST",
    headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
    },
    body: JSON.stringify({
        "long_url": url
    })
}

fetch("https://api-ssl.bitly.com/v4/shorten", config) // Makes HTTP request to the API
.then(response => response.json()) // Parses the JSON
.then(data => console.log(data.link)) // Outputs the shortened link
.catch(error => console.error(error)) // Catches errors if any and outputs it