package com.grubquest.grubquest_android.Models;

import java.util.Date;
import java.util.Map;

/**
 * Created by Derek on 2/27/2016.
 */
public class QuestCoupon {
    // Specific to quest portion
//    String backgroundImg,
//            description;
//
//    Map<String, Object> completionParams;
//
//    Integer orderOnPage;
//
//    // Specific to coupon portion
//    String emailCoupon,
//            frontDescription,
//            logoImg,
//            name,
//            questTypeIcon,
//            redeemIcon,
//            templateName,
//            title,
//            voidCoupon;
//
//    Integer expirationTime;
//
//    Map<String, String> restaurant;
//
//    Integer savings;
//    Boolean isDelivery;
    String address;
    String name;
    String street;

    public QuestCoupon() {}

    public String getName() {
        return name;
    }


}