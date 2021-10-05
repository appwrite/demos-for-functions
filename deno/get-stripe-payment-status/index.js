//Stripe API access token
const token = Deno.env.get("STRIPE_API_TOKEN")

// Payement ID to retreive status 
const stripePaymentId  = Deno.env.get("APPWRITE_FUNCTION_DATA")

const api = `https://api.stripe.com/v1/payment_intents/${stripePaymentId}`

const config = {
    method: 'GET',
    headers: {
        'Authorization': `Bearer ${token}`,
    },
}

fetch(api, config) // Makes HTTP request to the API
.then(response => console.log(response.data.status)) // Outputs the payment status
.catch(error => console.error(error)) // Catches errors if any and outputs it