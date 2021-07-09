package com.pcw.assignment.models;

import io.vertx.core.json.JsonObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LendEntryKey implements Serializable {
  @Column(name = "book_id")
  private int bookId;
  @Column(name = "user_id")
  private int userId;

  public LendEntryKey(int bookId, int userId) {
    this.bookId = bookId;
    this.userId = userId;
  }

  public int getBookId() {
    return bookId;
  }

  public void setBookId(int bookId) {
    this.bookId = bookId;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof LendEntryKey)) return false;
    LendEntryKey that = (LendEntryKey) o;
    return getBookId() == that.getBookId() && getUserId() == that.getUserId();
  }

  public LendEntryKey() {
  }

  public LendEntryKey(JsonObject jsonObject){
    this.bookId = jsonObject.getInteger("bookId");
    this.userId = jsonObject.getInteger("userId");
  }

  @Override
  public String toString() {
    return "{" +
            "\"bookId\":" + bookId + "," +
            "\"userId\":" + userId +
            "}";
  }

  @Override
  public int hashCode() {
    return Objects.hash(getBookId(), getUserId());
  }
}
