package com.example.vancouvertourismappfinal.CarRentalClasses;

import java.io.Serializable;

public class Booking implements Serializable {
    String uniqueBookingID,carName,carColor;
    String time1,pickupDateOrTime,dropOffDateOrTime,uniqueID,name;

    double rentalPrice,insuranceAmt,taxes,totalAmount;
}
