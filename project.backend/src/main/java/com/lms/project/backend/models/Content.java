package com.lms.project.backend.models;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Content {

    private String accountId;
    private String contentId;
    private String title;
    private ContentNotes[] contentNotes;
    private String[] classes;
    private Date dateCreated;
    private Date dateEdited;
    public Content() {}
    
    public Content(String accountId, String contentId, String title, ContentNotes[] contentNotes,
            String[] classes) {
        this.accountId = accountId;
        this.contentId = contentId;
        this.title = title;
        this.contentNotes = contentNotes;
        this.classes = classes;
    }

    

    // Content eachContent = new Content(content.getTitle(), content.getContentId(), content.getDateCreated());

    // Content eachContent = new Content(content.getTitle(), content.getContentId(), content.getDateCreated(), content.getDateEdited())

    
    public Content(String title, String contentId, Date dateCreated) {
        this.contentId = contentId;
        this.title = title;
        this.dateCreated = dateCreated;
    }

    public Content(String title , String contentId, Date dateCreated, Date dateEdited) {
        this.contentId = contentId;
        this.title = title;
        this.dateCreated = dateCreated;
        this.dateEdited = dateEdited;
    }
    
    public Content(String accountId, String contentId, String title, ContentNotes[] contentNotes,
            String[] classes, Date dateCreated, Date dateEdited) {
        this.accountId = accountId;
        this.contentId = contentId;
        this.title = title;
        this.contentNotes = contentNotes;
        this.classes = classes;
        this.dateCreated = dateCreated;
        this.dateEdited = dateEdited;
    }
    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public String getContentId() {
        return contentId;
    }
    public void setContentId(String contentId) {
        this.contentId = contentId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public ContentNotes[] getContentNotes() {
        return contentNotes;
    }
    public void setContentNotes(ContentNotes[] contentNotes) {
        this.contentNotes = contentNotes;
    }
    public String[] getClasses() {
        return classes;
    }
    public void setClasses(String[] classes) {
        this.classes = classes;
    }
    public Date getDateCreated() {
        return dateCreated;
    }
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    public Date getDateEdited() {
        return dateEdited;
    }
    public void setDateEdited(Date dateEdited) {
        this.dateEdited = dateEdited;
    }

        public String getFormattedDateCreated() {
        return formatDate(dateCreated);
    }

    public String getFormattedDateEdited() {
        return formatDate(dateEdited);
    }

    private String formatDate(Date date) {
        if (date == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
        return sdf.format(date);
    }
    @Override
    public String toString() {
        return "Content [accountId=" + accountId + ", contentId=" + contentId + ", title=" + title
                + ", contentNotes=" + Arrays.toString(contentNotes) + ", classes="
                + Arrays.toString(classes) + ", dateCreated=" + dateCreated + ", dateEdited="
                + dateEdited + "]";
    }

}
