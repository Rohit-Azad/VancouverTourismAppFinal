package com.example.vancouvertourismappfinal;

import java.io.Serializable;

public class HotelsDB implements Serializable {
    int hotelID,starRating,stdQuantity,stdPrice,dbQuantity,dbPrice,lxQuantity,lxPrice,stQuantity,stPrice;
    String desc,hotelName,phoneNumber,website,location,policies,picture;
}
