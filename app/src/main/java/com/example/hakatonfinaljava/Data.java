package com.example.hakatonfinaljava;

import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("author")
    String author;
    @SerializedName("content")
    String content;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
