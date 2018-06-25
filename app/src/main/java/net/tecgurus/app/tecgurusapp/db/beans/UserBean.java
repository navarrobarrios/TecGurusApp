package net.tecgurus.app.tecgurusapp.db.beans;

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
