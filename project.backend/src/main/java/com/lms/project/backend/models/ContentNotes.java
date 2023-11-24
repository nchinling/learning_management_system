package com.lms.project.backend.models;

import java.util.Arrays;

public class ContentNotes {

    private String sectionTitle;
    private String notes;
    // private byte[] image;
    // private String[] image;

    public ContentNotes() {}

    public ContentNotes(String sectionTitle, String notes) {
        this.sectionTitle = sectionTitle;
        this.notes = notes;
    }

    
    // public ContentNotes(String sectionTitle, String notes, byte[] image) {
    //     this.sectionTitle = sectionTitle;
    //     this.notes = notes;
    //     this.image = image;
    // }
    

    // public ContentNotes(String sectionTitle, String notes, String[] image) {
    //     this.sectionTitle = sectionTitle;
    //     this.notes = notes;
    //     this.image = image;
    // }

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

    


    // public byte[] getImage() {
    //     return image;
    // }

    // public void setImage(byte[] image) {
    //     this.image = image;
    // }


    // public String[] getImage() {
    //     return image;
    // }

    // public void setImage(String[] image) {
    //     this.image = image;
    // }

    

    
}
