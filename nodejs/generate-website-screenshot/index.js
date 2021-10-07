// Perform all your imports
const sdk = require('node-appwrite')
const CloudConvert = require('cloudconvert')
const fs = require('fs')
const https = require('https')

const cloudConvert = new CloudConvert(process.env.CLOUDCONVERT_API_KEY)

// Initialise the client SDK
const client = new sdk.Client()
client
    .setEndpoint(process.env.APPWRITE_ENDPOINT) // Your API Endpoint
    .setProject(process.env.APPWRITE_FUNCTION_PROJECT_ID) // Your project ID available by default
    .setKey(process.env.APPWRITE_API_KEY) // Your secret API key

// Initialise the storage SDK
const storage = new sdk.Storage(client)


// PAYLOAD
const payload = JSON.parse(process.env.APPWRITE_FUNCTION_EVENT_DATA)

// Generate website screenshot based on url, format and other API params
async function generateWebsiteScreenshot (url, format = 'jpg') {
  const job = await createCloudconvertJob(url, format)
  const file = await getFileFromCloudconvertJob(job)
  return uploadCaptureToStorage(file)
}

async function createCloudconvertJob (url, format = 'jpg') {
  return cloudConvert.jobs.create({
    tasks: {
      'capture-url': {
        operation: 'capture-website',
        url,
        output_format: format
      },
      'export-my-file': {
        operation: 'export/url',
        input: 'capture-url'
      }
    }
  })
}

async function getFileFromCloudconvertJob (job) {
  job = await cloudConvert.jobs.wait(job.id) // Wait for job completion

  const exportTask = job.tasks.filter(
    task => task.operation === 'export/url' && task.status === 'finished'
  )[0]
  return exportTask.result.files[0]
}

async function uploadCaptureToStorage (file) {
  const temp = fs.createWriteStream(file.filename)

  https.get(file.url, async function (response) {
    response.pipe(temp)
  })

  return new Promise((resolve, reject) => {
    temp.on('finish', async () => {
      const storedFile = await storage.createFile(
        fs.createReadStream(file.filename)
      )
      await fs.unlink(
        file.filename,
        () => console.log(`Stored screenshot with id: ${storedFile['$id']}`)
      )
      resolve()
    })
    temp.on('error', reject)
  })
}

// function call
generateWebsiteScreenshot(payload.url, payload.format);
