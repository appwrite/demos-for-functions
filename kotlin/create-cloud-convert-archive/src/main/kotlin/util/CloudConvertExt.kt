package util

import com.cloudconvert.client.CloudConvertClient
import com.cloudconvert.dto.request.CreateArchivesTaskRequest
import com.cloudconvert.dto.request.TaskRequest
import com.cloudconvert.dto.request.UploadImportRequest
import com.cloudconvert.dto.request.UrlExportRequest
import com.cloudconvert.dto.response.JobResponse
import com.cloudconvert.dto.response.TaskResponse
import io.appwrite.services.Storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.serialization.json.Json

private const val IMPORT_FILE_KEY_TEMPLATE = "import-file-"
private const val CREATE_ARCHIVE_KEY = "create-archive"
private const val EXPORT_FILE_KEY = "export-file"

fun CloudConvertClient.createArchiveJob(fileIds: List<String>, outputFormat: String) = jobs()
    .create(
        fileIds.associate<String, String, TaskRequest> {
            "$IMPORT_FILE_KEY_TEMPLATE$it" to UploadImportRequest()
        }.toMutableMap().apply {
            put(
                CREATE_ARCHIVE_KEY,
                CreateArchivesTaskRequest()
                    .setOutputFormat(outputFormat)
                    .setInput(
                        *(fileIds.map {
                            "$IMPORT_FILE_KEY_TEMPLATE$it"
                        }).toTypedArray()
                    )
            )

            put(
                EXPORT_FILE_KEY,
                UrlExportRequest().setInput(CREATE_ARCHIVE_KEY)
            )
        }.toMap()
    ).body!!

fun CloudConvertClient.exportedFileUrlFor(
    thumbnailGenerationJobId: String
): String = jobs()
    .wait(thumbnailGenerationJobId)
    .body!!
    .tasks
    .stream()
    .filter { it.name.equals(EXPORT_FILE_KEY) }.findFirst()
    .get()
    .result
    .files[0]["url"]!!

fun JobResponse.uploadFileTasks(): Flow<TaskResponse> = tasks
    .stream()
    .filter { it.name.contains(IMPORT_FILE_KEY_TEMPLATE) }
    .iterator()
    .asFlow()

suspend fun TaskResponse.uploadFile(
    appwriteStorage: Storage,
    jsonParser: Json,
    cloudConvertClient: CloudConvertClient
) {
    val fileId = name.removePrefix(IMPORT_FILE_KEY_TEMPLATE)
    if (fileId.isEmpty()) {
        throw IllegalStateException("Can not upload a file for a non-import task")
    }
    val rawFile = appwriteStorage.getRawFile(fileId, jsonParser)
    runCatching {
        cloudConvertClient.importUsing().upload(id!!, result.form!!, rawFile.data, rawFile.name)
    }.getOrElse {
        System.err.println("[ERR] Failed to upload file to cloudConvert. Failed File Id: $fileId")
        throw it
    }
}