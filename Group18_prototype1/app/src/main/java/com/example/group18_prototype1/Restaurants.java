package com.example.group18_prototype1;

public class Restaurants {
    public static final String TABLE_NAME = "restaurant_table";
    public static final String COLUMN_ID = "id";
    public static final String RESTAURANT_NAME = "name";
    public static final String RESTAURANT_ADDRESS = "address";
    public static final String PHONE_NUM = "phone_num";
    public static final String RESTAURANT_TAGS = "tags";
    public static final String RESTAURANT_DESCRIPTION = "description";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + RESTAURANT_NAME + " TEXT, "
            + RESTAURANT_ADDRESS + " TEXT, "
            + PHONE_NUM + " TEXT, "
            + RESTAURANT_TAGS + " TEXT, "
            + RESTAURANT_DESCRIPTION + " TEXT " + ")";

    private int id;
    private String name;
    private String address;
    private String phoneNum;
    private String tags;
    private String description;

    public Restaurants(int id, String name, String address, String phoneNum, String tags, String description) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNum = phoneNum;
        this.tags = tags;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
