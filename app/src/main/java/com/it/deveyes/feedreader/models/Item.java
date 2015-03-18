package com.it.deveyes.feedreader.models;


import java.util.Date;

public class Item {

    String title;
    String link;
    String description;
    String enclosure;
    String pubDate;

    public Item(String title, String link, String description, String enclosure, String pubDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.enclosure = enclosure;
        this.pubDate = pubDate;
    }

    public Item() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public long getDate(){
        if(pubDate!=null)return new Date().parse(pubDate);
        else return -1;
    }

    @Override
    public String toString() {
        return "Item{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", enclosure='" + enclosure + '\'' +
                ", pubDate='" + pubDate + '\'' +
                '}';
    }
}
