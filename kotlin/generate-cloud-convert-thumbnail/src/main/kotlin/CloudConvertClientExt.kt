import com.cloudconvert.client.CloudConvertClient
import com.cloudconvert.dto.request.CreateThumbnailsTaskRequest
import com.cloudconvert.dto.request.UploadImportRequest
import com.cloudconvert.dto.request.UrlExportRequest
import com.cloudconvert.dto.response.JobResponse
import model.ExportFileDetails

const val IMPORT_FILE_KEY = "import-file"
const val CREATE_THUMBNAIL_KEY = "create-thumbnail"
const val EXPORT_FILE_KEY = "export-file"

fun CloudConvertClient.createThumbnailGenerationJob() = jobs()
    .create(
        mapOf(
            IMPORT_FILE_KEY to UploadImportRequest(),
            CREATE_THUMBNAIL_KEY to CreateThumbnailsTaskRequest()
                .setInput(IMPORT_FILE_KEY)
                .setOutputFormat("png")
                .set<CreateThumbnailsTaskRequest>("width", 400)
                .set("height", 400),
            EXPORT_FILE_KEY to UrlExportRequest().setInput(CREATE_THUMBNAIL_KEY)
        )
    ).body!!

fun CloudConvertClient.exportFileDetailsFor(
    thumbnailGenerationJobId: String
): ExportFileDetails = jobs()
    .wait(thumbnailGenerationJobId)
    .body!!
    .tasks
    .stream()
    .filter { it.name.equals(EXPORT_FILE_KEY) }.findFirst()
    .get()
    .result
    .files[0]
    .run {
        ExportFileDetails(
            url = this["url"]!!,
            filename = "thumbnail-${this["filename"]!!}"
        )
    }

fun JobResponse.uploadFileTask() = tasks
    .stream()
    .filter { it.name.equals(IMPORT_FILE_KEY) }.findFirst()
    .get()

