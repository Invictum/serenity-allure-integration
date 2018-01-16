package com.github.invictum.allure;

import java.util.Arrays;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attach)) return false;

        Attach attach = (Attach) o;

        if (!name.equals(attach.name)) return false;
        if (!mime.equals(attach.mime)) return false;
        if (!extension.equals(attach.extension)) return false;
        return Arrays.equals(body, attach.body);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + mime.hashCode();
        result = 31 * result + extension.hashCode();
        result = 31 * result + Arrays.hashCode(body);
        return result;
    }

    @Override
    public String toString() {
        return "Attach{" +
                "name='" + name + '\'' +
                ", mime='" + mime + '\'' +
                ", extension='" + extension + '\'' +
                ", body=" + Arrays.toString(body) +
                '}';
    }
}
