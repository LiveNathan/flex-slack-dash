package dev.nathanlively.domain;

public class Attachment {
    private final String fileName;
    private final String filePath;  // Could be a URL or file system path
    private final long fileSizeInBytes;
    private final String mimeType;  // Optional in case it's not available

    public Attachment(String fileName, String filePath, long fileSizeInBytes, String mimeType) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSizeInBytes = fileSizeInBytes;
        this.mimeType = mimeType;
    }
}
