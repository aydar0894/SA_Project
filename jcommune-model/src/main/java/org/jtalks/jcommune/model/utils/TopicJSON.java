package org.jtalks.jcommune.model.utils;

/**
 * Created by aydar on 4/19/2017.
 */
public class TopicJSON {
    private String title;
    private String id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TopicJSON(String title, long id) {
        this.title = title;
        this.id = String.valueOf(id);
    }
}
