package com.example.progmob2;

public class Drama {
    private String dramaId, dramaTitle, dramaYear;

    public Drama(String dramaId, String dramaTitle, String dramaYear){
        this.dramaId = dramaId;
        this.dramaTitle = dramaTitle;
        this.dramaYear = dramaYear;
    }

    public String getDramaId(){
        return  dramaId;
    }

    public void setDramaId(String dramaId){
        this.dramaId = dramaId;
    }

    public String getDramaTitle(){
        return  dramaTitle;
    }

    public void setDramaTitle(String dramaTitle){
        this.dramaTitle = dramaTitle;
    }

    public String getDramaYear(){
        return  dramaYear;
    }

    public void setDramaYear(String dramaYear){
        this.dramaYear = dramaYear;
    }
}
