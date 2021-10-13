package util

import com.cloudconvert.client.CloudConvertClient
import com.cloudconvert.dto.request.CreateArchivesTaskRequest
import com.cloudconvert.dto.request.TaskRequest
import com.cloudconvert.dto.request.UploadImportRequest
import com.cloudconvert.dto.request.UrlExportRequest
import com.cloudconvert.dto.response.JobResponse
import com.cloudconvert.dto.response.TaskResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

const val IMPORT_FILE_KEY_TEMPLATE = "import-file-"
const val CREATE_ARCHIVE_KEY = "create-archive"
const val EXPORT_FILE_KEY = "export-file"

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
