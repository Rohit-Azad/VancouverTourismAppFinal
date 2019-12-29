package com.example.vancouvertourismappfinal.RestRoomFinderClasses.database.model;

import com.example.vancouvertourismappfinal.RestRoomFinderClasses.models.Bathroom;

import java.util.ArrayList;
import java.util.List;


public class DatabaseEntityConverter {
    public List<Bathroom> convertBathroomEntity(List<BathroomEntity> bathroomEntities){
        List<Bathroom> convertedBathrooms = new ArrayList<>();
        for (BathroomEntity bathroom: bathroomEntities) {
            Long id = bathroom.getId();
            String name = bathroom.getName();
            String city = bathroom.getCity();
            String street = bathroom.getStreet();

            String state = bathroom.getState();
            String country = bathroom.getCountry();
            boolean isAccessible = bathroom.getAccessible();
            boolean isUniSex = bathroom.getUnisex();
            String directions = bathroom.getDirections();
            String comment = bathroom.getComment();
            int upVote = bathroom.getUpvote();
            int downVote = bathroom.getDownvote();
            Double latitude = bathroom.getLatitude();
            Double longitude = bathroom.getLongitude();
            Long timestamp = bathroom.getTimestamp();


            Bathroom bathroomObject = new Bathroom();
            bathroomObject.setmId(id);
            bathroomObject.setName(name);
            bathroomObject.setmCity(city);
            bathroomObject.setmState(state);
            bathroomObject.setmCountry(country);
            bathroomObject.setmAccessible(isAccessible);
            bathroomObject.setmUnisex(isUniSex);
            bathroomObject.setmDirections(directions);
            bathroomObject.setmComments(comment);
            bathroomObject.setmUpvote(upVote);
            bathroomObject.setmDownvote(downVote);
            bathroomObject.setmLatitude(latitude);
            bathroomObject.setmLongitude(longitude);
            bathroomObject.setmStreet(street);
            bathroomObject.setmTimestamp(timestamp);
              convertedBathrooms.add(bathroomObject);
        }
        return  convertedBathrooms;
    }
}
