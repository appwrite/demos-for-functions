package models;

public class File {
    private String $id;
    private String name;
    private Permissions $permissions;
    private int dateCreated;
    private String signature;
    private String mimeType;
    private int sizeOriginal;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Permissions get$permissions() {
        return $permissions;
    }

    public void set$permissions(Permissions $permissions) {
        this.$permissions = $permissions;
    }

    public int getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(int dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public int getSizeOriginal() {
        return sizeOriginal;
    }

    public void setSizeOriginal(int sizeOriginal) {
        this.sizeOriginal = sizeOriginal;
    }

    public File(String $id, String name, Permissions $permissions, int dateCreated, String signature, String mimeType, int sizeOriginal) {
        this.$id = $id;
        this.name = name;
        this.$permissions = $permissions;
        this.dateCreated = dateCreated;
        this.signature = signature;
        this.mimeType = mimeType;
        this.sizeOriginal = sizeOriginal;
    }
}
