package com.dorameet.myapplication.home.data;

public class ArticleData {
    private Integer id;
    private String title;
    private int wordNum;
    private int lexile;
    private int typeId;
    private String type;
    private String cover;
    private String clickRatio;
    private String accuracy;
    private String accuracyRatio;
    private Integer color;
    private Integer isRead;
    private String readTime;
    private String stage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWordNum() {
        return wordNum;
    }

    public void setWordNum(int wordNum) {
        this.wordNum = wordNum;
    }

    public int getLexile() {
        return lexile;
    }

    public void setLexile(int lexile) {
        this.lexile = lexile;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getClickRatio() {
        return clickRatio;
    }

    public void setClickRatio(String clickRatio) {
        this.clickRatio = clickRatio;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public String getAccuracyRatio() {
        return accuracyRatio;
    }

    public void setAccuracyRatio(String accuracyRatio) {
        this.accuracyRatio = accuracyRatio;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }
}
