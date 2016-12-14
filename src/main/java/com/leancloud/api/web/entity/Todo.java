package com.leancloud.api.web.entity;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

@AVClassName("Todo")
public class Todo extends AVObject {


  public String getContent() {
    return this.getString("content");
  }

  public void setContent(String content) {
    this.put("content", content);
  }
}
