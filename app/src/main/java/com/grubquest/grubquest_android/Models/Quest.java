package com.grubquest.grubquest_android.Models;

public class Quest {
    String icon1;
    String icon2;
    String offer;
    String quest_image;
    String quest_info;
    String restaurant;
    String restaurant_icon;

    public Quest(String icon1, String icon2, String offer, String quest_image,
                 String quest_info, String restaurant, String restaurant_icon) {
        this.icon1 = icon1;
        this.icon2 = icon2;
        this.offer = offer;
        this.quest_image = quest_image;
        this.quest_info = quest_info;
        this.restaurant = restaurant;
        this.restaurant_icon = restaurant_icon;
    }
}
