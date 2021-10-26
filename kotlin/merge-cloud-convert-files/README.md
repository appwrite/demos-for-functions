# 🦠 Merging Files Into A Single PDF Using CloudConvert

A sample Kotlin Cloud Function for merging at least two files into one PDF using CloudConvert. If input files are not
PDFs yet, they are automatically converted to PDF.

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
  case `java -jar lib/merge-cloud-convert-files-1.0.0-SNAPSHOT-all.jar`) as your entrypoint command
- Upload your tarfile
- Click `Activate`

## 🎯 Trigger

Head over to your function in the Appwrite console and under the Overview Tab, click Execute Now and supply the function
data in JSON format, eg.

```json
{
  "fileIds": [
    "FILE_ID_1",
    "FILE_ID_2"
  ],
  "outputFileName": "merged.pdf"
}
```

### Parameters:

- **fileIds** - Mandatory. IDs of files in Appwrite Storage to merge.
- **engine** - Optional. Engine for the conversion (see https://cloudconvert.com/api/v2/merge#merge-tasks for the list
  of options)
- **engineVersion** - Optional. Engine version for the conversion (see https://cloudconvert.com/api/v2/merge#merge-tasks
  for the list of options)
- **outputFileName** - Optional. Name of output file (including extension).
