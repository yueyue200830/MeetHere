package com.proj.meethere.entity;
import javax.persistence.*;
import java.sql.Blob;


/**
 * @Author Yiqing Tao
 * @Date 2019-10-29
 * News Entity
 */
@Entity
@Table(name="news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    int id;

    @Column(name = "news_content")
    String newsContent;

    @Column(name = "news_photo")
    Blob newsPhoto;

    @Column(name = "news_title")
    String newsTitle;

    @Column(name = "news_time")
    String newsTime;

    public News() {
    }

    public News(String newsContent, Blob newsPhoto, String newsTitle, String newsTime) {
        this.newsContent = newsContent;
        this.newsPhoto = newsPhoto;
        this.newsTitle = newsTitle;
        this.newsTime = newsTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public Blob getNewsPhoto() {
        return newsPhoto;
    }

    public void setNewsPhoto(Blob newsPhoto) {
        this.newsPhoto = newsPhoto;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(String newsTime) {
        this.newsTime = newsTime;
    }
}
