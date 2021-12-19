import java.io.IOException;

public class BackupCollectionsToS3 {
    public static void main(String[] args) throws IOException{
        BackupToS3 backupToS3 = new BackupToS3();
        backupToS3.backupCollectionsToS3();
    }
}
