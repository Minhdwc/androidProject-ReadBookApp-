package com.example.project_app_book.model;

public class Author {
    private String name;
    private String imgAuthor;

    public Author() {
    }
    public Author(String name, String imgAuthor) {
        this.name = name;
        this.imgAuthor = imgAuthor;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgAuthor() {
        return imgAuthor;
    }

    public void setImgAuthor(String imgAuthor) {
        this.imgAuthor = imgAuthor;
    }
}
