# 🖼️ Compress TinyPNG Image

Demo function to create a compressed version of an image using a provided link and TinyPNG API, then store it in Appwrite storage.

## 📝 Environment Variables

Go to Settings tab of your Cloud Function. Add the following environment variables.

- **APPWRITE_ENDPOINT** - Your Appwrite Endpoint.
- **APPWRITE_FUNCTION_DATA** - The URL of the image you want to compress
- **APPWRITE_API_KEY** - Your Appwrite API key with  `files.write` permission.
- **TINYPNG_API_KEY** - Your TinyPNG API key.
- **IMAGE_OUTPUT_NAME** - The name of the image output. *tinified_image.png* by default.

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/nodejs/compress-tinypng-image

$ npm install
```

- Ensure that your folder structure looks like this

```
.
├── index.js
├── node_modules/
├── package-lock.json
└── package.json
```

- Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz compress-tinypng-image
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case "node index.js") as your entrypoint command
- Upload your tarfile
- Click 'Activate'
