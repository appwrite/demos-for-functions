package io.appwrite.optimize.cloud.convert.file

import com.cloudconvert.client.CloudConvertClient
import com.cloudconvert.client.setttings.AbstractSettingsProvider
import com.cloudconvert.dto.Operation
import com.cloudconvert.dto.Status
import com.cloudconvert.dto.request.OptimizeFilesTaskRequest
import com.cloudconvert.dto.request.UploadImportRequest
import com.cloudconvert.dto.request.UrlExportRequest
import io.appwrite.optimize.cloud.convert.file.model.AppwriteFile
import io.appwrite.optimize.cloud.convert.file.model.FunctionData
import kotlin.system.exitProcess

private const val TASK_NAME_UPLOAD = "upload-import"
private const val TASK_NAME_MERGE = "merge-files"
private const val TASK_NAME_DOWNLOAD = "url-export"

class CloudConvert {
    private val client = CloudConvertClient(object : AbstractSettingsProvider(
        System.getenv(API_KEY),
        "", // we are not using CloudConvert webhooks
        System.getenv(USE_SANDBOX),
    ) {})

    fun optimize(functionData: FunctionData, file: AppwriteFile): AppwriteFile {
        val tasks = mutableMapOf(
            TASK_NAME_UPLOAD to UploadImportRequest(),
            TASK_NAME_MERGE to OptimizeFilesTaskRequest()
                .setInput(TASK_NAME_UPLOAD)
                .setInputFormat(functionData.inputFormat)
                .setEngine(functionData.engine)
                .setEngineVersion(functionData.engineVersion)
                .setFilename(functionData.outputFileName)
                .setProfile(functionData.profile),
            TASK_NAME_DOWNLOAD to UrlExportRequest()
                .setInput(TASK_NAME_MERGE),
        )

        val createJobResponse = client.jobs().create(tasks)
        val createdJob = createJobResponse.body
            ?: throw RuntimeException("Error creating CloudConvert job: ${createJobResponse.message}")
        createdJob.tasks
            .find { task -> task.name == TASK_NAME_UPLOAD }
            ?.let { task ->
                val uploadResponse = client.importUsing().upload(task.id, task.result.form, file.stream, file.name)
                if (uploadResponse.status != 200) {
                    System.err.println("Error uploading file ${file.name}: ${uploadResponse.message}")
                }
            }

        val finishedJob = client.jobs().wait(createdJob.id).body
            ?: throw RuntimeException("Error waiting for CloudConvert job to finish: Empty response")
        val downloadTask = finishedJob.tasks
            .find { task -> task.operation == Operation.EXPORT_URL }
            ?: throw RuntimeException("Error waiting for CloudConvert job to finish: No task with ${Operation.EXPORT_URL.label} operation")
        if (downloadTask.status == Status.ERROR) {
            finishedJob.tasks.asSequence()
                .filter { task -> task.status == Status.ERROR }
                .forEach { task -> System.err.println("Task ${task.name} failed with ${task.code} error: ${task.message}") }
            exitProcess(1)
        }
        val downloadFileMetadata = downloadTask.result.files.first()
        val downloadFileUrl = downloadFileMetadata["url"]
            ?: throw RuntimeException("Error waiting for CloudConvert job to finish: No download url")
        val downloadFileName = downloadFileMetadata["filename"]
            ?: throw RuntimeException("Error waiting for CloudConvert job to finish: No download filename")
        val downloadFileInputStream = client.files().download(downloadFileUrl).body
            ?: throw RuntimeException("Error downloading file: Empty response")
        return AppwriteFile(downloadFileName, downloadFileInputStream)
    }
}
