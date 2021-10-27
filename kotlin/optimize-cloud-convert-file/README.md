# 🦠 Optimize and Compress a File Using CloudConvert

A sample Kotlin Cloud Function for optimizing and compressing a file using CloudConvert.

## 📝 Environment Variables

Go to Settings tab of your Cloud Function. Add the following environment variables.

- **APPWRITE_ENDPOINT** - Mandatory. Your Appwrite Endpoint
- **APPWRITE_API_KEY** - Mandatory. Your Appwrite API key with `files.read` and `files.write` permissions
- **CLOUDCONVERT_API_KEY** - Mandatory. Your CloudConvert API key with `task.read` and `task.write` permissions. Created
  through Authorization > API Keys on Dashboard
- **CLOUDCONVERT_SANDBOX** - Optional. `true` to use Sandbox API instead of Live API. Defaults to `false`.

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

- Create a tarfile

```bash
gradle shadowDistTar
```

(the tarfile is created in `build/distributions`)

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this
  case `java -jar lib/optimize-cloud-convert-file-1.0.0-SNAPSHOT-all.jar`) as your entrypoint command
- Upload your tarfile
- Click `Activate`

## 🎯 Trigger

Head over to your function in the Appwrite console and under the Overview Tab, click Execute Now and supply the function
data in JSON format, eg.

```json
{
  "fileId": "FILE_ID_1",
  "outputFileName": "optimized.pdf"
}
```

### Parameters:

- **fileId** - Mandatory. ID of file in Appwrite Storage to optimize.
- **inputFormat** - Optional. The current format of the file, e.g. pdf. If not set, the extension of the input file is
  used as input format.
- **engine** - Optional. Engine for the conversion (see https://cloudconvert.com/api/v2/optimize#optimize-tasks for the
  list of options)
- **engineVersion** - Optional. Engine version for the conversion (
  see https://cloudconvert.com/api/v2/optimize#optimize-tasks
  for the list of options)
- **outputFileName** - Optional. Name of output file (including extension).
- **profile** - Optional. Optimization profile for specific target needs (
  see https://cloudconvert.com/api/v2/optimize#optimize-tasks for the list of options)
