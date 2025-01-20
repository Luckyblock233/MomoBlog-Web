package org.example.src.entity;

public class Slide {
    private int id;
    private int blogId;
    private String username;
    private String title;
    private String imageURL;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getBlogId() {
        return blogId;
    }
    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }
    public String getImageURL() {
        return imageURL;
    }
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
