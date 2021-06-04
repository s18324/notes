package com.example.notesbackend;

public class NoteCreateRequestDTO {

    private String title;

    private String content;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "NoteCreateRequestDTO{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

}
