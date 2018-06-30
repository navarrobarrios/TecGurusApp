package net.tecgurus.app.tecgurusapp.db.beans;

import android.content.ContentValues;
import android.database.Cursor;

import net.tecgurus.app.tecgurusapp.db.helpers.UserHelper;

public class UserBean {
    //region Variables
    private String username;
    private String password;
    private String name;
    private String lastname;
    private String address;
    //endregion

    //region Constructor

    public UserBean(){

    }

    public UserBean(String username, String password, String name, String lastname, String address) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.address = address;
    }

    public UserBean(Cursor cursor) {
        if (cursor.getColumnIndex(UserHelper.FIELD_USERNAME) != -1){
            this.username = cursor.getString(cursor.getColumnIndex(UserHelper.FIELD_USERNAME));
        }
        if (cursor.getColumnIndex(UserHelper.FIELD_PASSWORD) != -1){
            this.password = cursor.getString(cursor.getColumnIndex(UserHelper.FIELD_PASSWORD));
        }
        if (cursor.getColumnIndex(UserHelper.FIELD_NAME) != -1){
            this.name = cursor.getString(cursor.getColumnIndex(UserHelper.FIELD_NAME));
        }
        if (cursor.getColumnIndex(UserHelper.FIELD_LASTNAME) != -1){
            this.lastname = cursor.getString(cursor.getColumnIndex(UserHelper.FIELD_LASTNAME));
        }
        if (cursor.getColumnIndex(UserHelper.FIELD_ADDRESS) != -1){
            this.address = cursor.getString(cursor.getColumnIndex(UserHelper.FIELD_ADDRESS));
        }
    }

    //endregion

    //region Local Methods

    @Override
    public String toString() {
        return String.format(
                "username: %s\n" +
                        "password: %s\n" +
                        "name: %s\n" +
                        "last_name:%s\n" +
                        "address: %s",
                this.username,
                this.password,
                this.name,
                this.lastname,
                this.address);
    }

    public ContentValues toContentValues(){
        ContentValues userContentValues = new ContentValues();
        userContentValues.put(UserHelper.FIELD_USERNAME, getUsername());
        userContentValues.put(UserHelper.FIELD_PASSWORD, getPassword());
        userContentValues.put(UserHelper.FIELD_NAME, getName());
        userContentValues.put(UserHelper.FIELD_LASTNAME, getLastname());
        userContentValues.put(UserHelper.FIELD_ADDRESS, getAddress());
        return userContentValues;
    }
    //endregion

    //region Setters & Getters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    //endregion
}
