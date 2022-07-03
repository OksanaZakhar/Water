package com.ksusha.vel.vodichka.ui.model;

public class TopRecycler {

    int id;
    String topCardImage, topic, description;

    public TopRecycler(int id, String topCardImage, String topic, String description) {
        this.id = id;
        this.topCardImage = topCardImage;
        this.topic = topic;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopCardImage() {
        return topCardImage;
    }

    public void setTopCardImage(String topCardImage) {
        this.topCardImage = topCardImage;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
