package com.example.vancouvertourismappfinal.HotelBookingClasses;

import java.io.Serializable;

public class HotelsDB implements Serializable {
    public int hotelID,starRating,stdQuantity,stdPrice,dbQuantity,dbPrice,lxQuantity,lxPrice,stQuantity,stPrice;
    public String desc;
    public String hotelName;
    public String phoneNumber;
    public String website;
    public  String location;
    public  String policies;
    public  String picture;
}
