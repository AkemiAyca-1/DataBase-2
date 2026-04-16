package org.models;

import java.util.Date;

public class TaskDashboardRow {
    private String title;
    private Date   createdAt;
    private String categoryName;
    private String userName;

    public TaskDashboardRow(String title, Date createdAt,
                            String categoryName, String userName) {
        this.title        = title;
        this.createdAt    = createdAt;
        this.categoryName = categoryName;
        this.userName     = userName;
    }

    public String getTitle()        { return title; }
    public Date   getCreatedAt()    { return createdAt; }
    public String getCategoryName() { return categoryName; }
    public String getUserName()     { return userName; }
}