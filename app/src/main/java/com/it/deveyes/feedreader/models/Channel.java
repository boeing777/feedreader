package com.it.deveyes.feedreader.models;



import java.util.ArrayList;

public class Channel {

    private int idChannel;
    private String title;
    private String link;
    private String description;
    private String language;
    private ChannelImage image;
    private ArrayList<Item> items = new ArrayList<Item>();


    public Channel() {

    }

    public int getIdChannel() {
        return idChannel;
    }

    public void setIdChannel(int idChannel) {
        this.idChannel = idChannel;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public ChannelImage getImage() {
        return image;
    }

    public void setImage(ChannelImage image) {
        this.image = image;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Channel channel = (Channel) o;

        if (idChannel != channel.idChannel) return false;
        if (link != null ? !link.equals(channel.link) : channel.link != null) return false;
        if (title != null ? !title.equals(channel.title) : channel.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idChannel;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        return result;
    }
}
