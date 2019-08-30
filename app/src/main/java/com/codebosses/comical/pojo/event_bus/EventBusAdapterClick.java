package com.codebosses.comical.pojo.event_bus;

public class EventBusAdapterClick {

    private int position;
    private String clickType;

    public EventBusAdapterClick(int position, String clickType) {
        this.position = position;
        this.clickType = clickType;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getClickType() {
        return clickType;
    }

    public void setClickType(String clickType) {
        this.clickType = clickType;
    }
}
