# 📧 Send Request

Demo function to send an HTTP request.

## 📝 Environment Variables

This doesn't use any specific environment variables - just the data you give to function.

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/deno/send-request
```

* Ensure that your folder structure looks like this 
```
.
├── index.js
```
* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz send-request
```

* Navigate to Overview Tab of your Cloud Function
* Deploy Tag
* Input the command that will run your function (in this case "deno run --allow-net --allow-env index.js") as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 🎯 Trigger

You can trigger the function using the SDK, HTTP API, or the Appwrite Console.

Supported fields in data:

* `url` - URL of the endpoint to make request to (required).
* `method` - HTTP method, `GET` by default.
* `headers` - Headers to use in the request, defaults to `{}`.
* `body` - Request body, defaults to none.

Example request:

```json
{
    "url": "https://http-echo.deno.dev",
    "method": "POST",
    "body": "Hello World!",
}
```

and response:

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
