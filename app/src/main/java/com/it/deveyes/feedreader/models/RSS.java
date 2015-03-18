package com.it.deveyes.feedreader.models;


public class RSS {
    Channel c;
    String version;

    public RSS(Channel c, String version) {
        this.c = c;
        this.version = version;
    }

    public Channel getC() {
        return c;
    }

    public void setC(Channel c) {
        this.c = c;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
