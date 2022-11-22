package com.service.microservices.messages;

public class ResponseFile {

    private String name, url, type;
    private long size;

    public ResponseFile(String name, String url, String type, long size){
        this.name = name;
        this.url = url;
        this.type = type;
        this.size = size;
    }

    public String getName(){
        return name;
    }
    public String getUrl(){

        return url;
    }
    public String getType(){

        return type;
    }

    public long getSize(){
        return size;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
