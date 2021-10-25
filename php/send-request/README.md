# 📧  sendRequest()
Appwrite Cloud Function to send HTTP requests to an endpoint.

## 📝 Environment Variables
This function does not need on any custom environment variables.

## 🚀 Building and Packaging
To package this example as a cloud function, follow these steps.

### Package the function

```bash
$ cd demos-for-functions/php/send-request
$ composer install
$ tar -zcvf code.tar.gz .
```

### Create Function through the Appwrite Console, Deploy with the CLI
1. You can add your function from your Appwrite project's dashboard. [Click here](https://appwrite.io/docs/functions#addFunction) for more instructions. It's also possible to achieve this using the Appwrite CLI.
2. Deploy your function with the Appwrite CLI:
   ```bash
   appwrite functions createTag \
       --functionId=<functionId> \
       --command="php index.php" \
       --code="<code-directory>"
   ```
## 🎯 Trigger
You can trigger the function using the SDK, HTTP API, or the Appwrite Console.

Supported fields in data:

* `url` - URL of the endpoint to make request to (required).
* `method` - HTTP method, `POST` by default.
* `headers` - Headers to use in the request, defaults to `{}`.
* `body` - Request body, defaults to none.

Example request (function data):

```json
{
    "url": "https://http-echo.deno.dev",
    "method": "POST",
    "body": "Hello World!"
}
```

function response:

```json
{
    "url": "https://http-echo.deno.dev/",
    "redirected": false,
    "status": "200 (OK)",
    "headers": {
        "date": "Tue, 05 Oct 2021 11:24:06 GMT",
        "server": "deploy/asia-useast1-a",
        "transfer-encoding": "chunked"
    },
    "body": "Hello World!"
}
```
