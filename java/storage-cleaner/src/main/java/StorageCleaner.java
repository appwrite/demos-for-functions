import com.google.gson.Gson;
import io.appwrite.Client;
import io.appwrite.exceptions.AppwriteException;
import io.appwrite.services.Storage;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import models.File;
import models.FileList;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class StorageCleaner {
    public static void main(String[] args) throws AppwriteException {
        Client client = new Client()
                .setEndpoint(System.getenv("APPWRITE_ENDPOINT"))
                .setProject(System.getenv("APPWRITE_FUNCTION_PROJECT_ID"))
                .setKey(System.getenv("APPWRITE_API_KEY"));
        Storage storage = new Storage(client);
        try {
           double daystoExpire = Double.parseDouble(System.getenv("DAYS_TO_EXPIRE"));
            storage.listFiles(
                    new Continuation<Object>() {
                        @NotNull
                        @Override
                        public CoroutineContext getContext() {
                            return EmptyCoroutineContext.INSTANCE;
                        }

                        @Override
                        public void resumeWith(@NotNull Object o) {
                            String fileList = "";
                            try {
                                if (o instanceof Result.Failure) {
                                    Result.Failure failure = (Result.Failure) o;
                                    throw failure.exception;
                                } else {
                                    Response response = (Response) o;
                                    fileList = response.body().string();
                                    FileList files = new Gson().fromJson(fileList, FileList.class);
                                    final int[] deletedFiles = {0};
                                    for (File file: files.getFiles()) {
                                        long diff = new Date().getTime()/1000 - file.getDateCreated();
                                        if (diff > daystoExpire * 24 * 60 * 60) {
                                            storage.deleteFile(
                                                    file.get$id(),
                                            new Continuation<Object>() {
                                                @NotNull
                                                @Override
                                                public CoroutineContext getContext() {
                                                    return EmptyCoroutineContext.INSTANCE;
                                                }

                                                @Override
                                                public void resumeWith(@NotNull Object o) {
                                                    String json = "";
                                                    try {
                                                        if (o instanceof Result.Failure) {
                                                            Result.Failure failure = (Result.Failure) o;
                                                            throw failure.exception;
                                                        } else {
                                                            Response response = (Response) o;
                                                            json = response.body().string();
                                                            System.out.println("Deleted "+ file.get$id());
                                                            deletedFiles[0]++;
                                                        }
                                                    } catch (Throwable th) {
                                                        System.out.println(th.toString());
                                                    }
                                                }
                                            }
        );
                                        }
                                    }
                                    System.out.println("Total files deleted: "+deletedFiles[0]);
                                }
                            } catch (Throwable th) {
                                System.out.println(th.toString());
                            }
                        }
                    }
            );
        } catch (Exception e) {
            System.out.println("Unable to parse DAYS_TO_EXPIRE.");
        }
    }
}