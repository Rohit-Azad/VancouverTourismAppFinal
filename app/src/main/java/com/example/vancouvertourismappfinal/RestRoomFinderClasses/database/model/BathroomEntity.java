package com.example.vancouvertourismappfinal.RestRoomFinderClasses.database.model;


public class BathroomEntity {

    private Long id;
    private String name;
    private String street;
    private String city;
    private String state;
    private String country;
    private Boolean accessible;
    private Boolean unisex;
    private String directions;
    private String comment;
    private Integer upvote;
    private Integer downvote;
    private Double latitude;
    private Double longitude;
    private Long timestamp;

    public BathroomEntity() {
    }

    public BathroomEntity(Long id) {
        this.id = id;
    }

    public BathroomEntity(Long id, String name, String street, String city, String state, String country, Boolean accessible, Boolean unisex, String directions, String comment, Integer upvote, Integer downvote, Double latitude, Double longitude, Long timestamp) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
        this.accessible = accessible;
        this.unisex = unisex;
        this.directions = directions;
        this.comment = comment;
        this.upvote = upvote;
        this.downvote = downvote;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean getAccessible() {
        return accessible;
    }

    public void setAccessible(Boolean accessible) {
        this.accessible = accessible;
    }

    public Boolean getUnisex() {
        return unisex;
    }

    public void setUnisex(Boolean unisex) {
        this.unisex = unisex;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getUpvote() {
        return upvote;
    }

    public void setUpvote(Integer upvote) {
        this.upvote = upvote;
    }

    public Integer getDownvote() {
        return downvote;
    }

    public void setDownvote(Integer downvote) {
        this.downvote = downvote;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

}
