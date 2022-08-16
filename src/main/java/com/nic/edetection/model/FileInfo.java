package com.nic.edetection.model;

public class FileInfo {
  private String name;
  private String url;
  private long size;
	  
  public FileInfo(String name, String url,long size) {
    this.name = name;
    this.url = url;
    this.size = size;
  }
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getUrl() {
    return this.url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
public long getSize() {
	return size;
}
public void setSize(long size) {
	this.size = size;
}
  
}