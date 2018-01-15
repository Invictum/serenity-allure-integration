package com.github.invictum.allure;

/**
 * Attach abstraction. Used to pass attachment details.
 */
public class Attach {

    private String name;
    private String mime;
    private String extension;
    private byte[] body;

    public Attach(String name, String mime, String extension, byte[] body) {
        this.name = name;
        this.mime = mime;
        this.extension = extension;
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public String getMime() {
        return mime;
    }

    public String getExtension() {
        return extension;
    }

    public byte[] getBody() {
        return body;
    }
}
