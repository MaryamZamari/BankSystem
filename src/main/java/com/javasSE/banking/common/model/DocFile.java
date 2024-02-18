package com.javasSE.banking.common.model;

public class DocFile {
    private String name;
    private FileType type;

    public DocFile(String name, FileType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DocFile{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
