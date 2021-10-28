package models;

import java.util.List;

public class FileList {
    private int sum;

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public FileList(int sum, List<File> files) {
        this.sum = sum;
        this.files = files;
    }

    private List<File> files;
}
