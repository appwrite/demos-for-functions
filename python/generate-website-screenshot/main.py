from appwrite.client import Client
from appwrite.services.storage import Storage
import cloudconvert
import os


website_url = os.environ["TARGET_URL"]  # Url of the Website to take screenshot
API_KEY = os.environ["CLOUDCONVERT_API_KEY"] # Cloud Convert API Key

# Initialise the Appwrite client
client = Client()
client.set_endpoint(os.environ["APPWRITE_ENDPOINT"]) # Your API Endpoint
client.set_project(os.environ["APPWRITE_FUNCTION_PROJECT_ID"]) # Your project ID
client.set_key(os.environ["APPWRITE_API_KEY"]) # Your secret API key

# Initialise the storage SDK with the client object
storage = Storage(client)

# Initilizing CloudConvert SDK
cloudconvert.configure(api_key = API_KEY)

# Creating CloudConvert Job to capture and export screenshot
jobObj = cloudconvert.Job.create(payload={
    "tasks": {
        "screenshot-url": {
            "operation": "capture-website",
            "url": website_url,
            "output_format": "jpg"
        },
        "export-screenshot": {
            "operation": "export/url",
            "input": [
                "screenshot-url"
            ],
            "inline": False,
            "archive_multiple_files": False
        }
    }
})
print("Job Created!")
jobObj = cloudconvert.Job.wait(jobObj['id'])
print("Job Finished!")
taskList = jobObj['tasks']

# Getting Task with Export URL
if taskList[0]['operation'] == 'export/url':
    exportTask = taskList[0]['id']
if taskList[1]['operation'] == 'export/url':
    exportTask = taskList[1]['id']


# Downloading screenshot from CloudConvert Task
exported_url_task_id = exportTask
res = cloudconvert.Task.wait(id=exported_url_task_id) # Wait for job completion
file = res.get("result").get("files")[0]
res = cloudconvert.download(filename=file['filename'], url=file['url'])
print("Screenshot Saved with file name : ",res)

# Storing the Downloaded file in AppWrite Storage
result = storage.create_file(open(file['filename'], 'rb'))
print(result)
print("Stored in AppWrite Storage")