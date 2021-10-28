package models;

import java.util.List;

public class Permissions {
    private List<String> read;
    private List<String> write;

    public List<String> getRead() {
        return read;
    }

    public void setRead(List<String> read) {
        this.read = read;
    }

    public List<String> getWrite() {
        return write;
    }

    public void setWrite(List<String> write) {
        this.write = write;
    }

    public Permissions(List<String> read, List<String> write) {
        this.read = read;
        this.write = write;
    }
}
