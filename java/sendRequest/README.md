# 📧 Send Request

A Demo function to send an HTTP request.

## 📝 Environment Variables

This doesn't use any specific environment variables - just the data you give to the function.

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/java/sendRequest
$ maven clean package
$ cd target
$ tar -zcvf code.tar.gz send-request
```

* Navigate to Overview Tab of your Cloud Function
* Deploy Tag
* Input the command that will run your function (in this case "java -jar ./sendRequest-1.0-SNAPSHOT-jar-with-dependencies.jar") as your entrypoint command
* Upload your tarfile
* Click 'Activate'

## 🎯 Trigger

You can trigger the function using the SDK, HTTP API, or the Appwrite Console.

Supported fields in data:

* `url` - URL of the endpoint to make request to (required).
* `method` - HTTP method, `GET` by default.
* `headers` - Headers to use in the request, defaults to non.
* `body` - Request body, defaults to none.

Example request:

```json
{
    "url": "https://postman-echo.com/headers",
    "method": "GET",
    "headers": {
      "my-sample-header": "Lorem ipsum dolor sit amet"
    }
}
```

and response:

```json
{
  "headers": {
    "host": "postman-echo.com",
    "accept": "*/*",
    "accept-encoding": "gzip, deflate, sdch",
    "accept-language": "en-US,en;q=0.8",
    "cache-control": "no-cache",
    "my-sample-header": "Lorem ipsum dolor sit amet",
    "postman-token": "3c8ea80b-f599-fba6-e0b4-a0910440e7b6",
    "user-agent": "Java-http-client/16-ea",
    "x-forwarded-port": "443",
    "x-forwarded-proto": "https",
    "x-amzn-trace-id": "Root=1-617bb9c5-13699b490ba3178a37b51615"
  }
}
```
