package com.notetaking.model;

import com.sun.istack.NotNull;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Note
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @Column(nullable = false, updatable = false)
    private Date dateCreated;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date dateUpdated;

    public Note()
    {
    }

    public Note(String title, String content, Date dateCreated, Date dateUpdated) {
        this.title = title;
        this.content = content;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return dateCreated;
    }

    public void setCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getUpdated() {
        return dateUpdated;
    }

    public void setUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}
