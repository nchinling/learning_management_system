package com.lms.project.backend.models;

public class ContentNotes {

    private String sectionTitle;
    private String notes;
    public ContentNotes() {}

    public ContentNotes(String sectionTitle, String notes) {
        this.sectionTitle = sectionTitle;
        this.notes = notes;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "ContentNotes [sectionTitle=" + sectionTitle + ", notes=" + notes + "]";
    }

    
}
