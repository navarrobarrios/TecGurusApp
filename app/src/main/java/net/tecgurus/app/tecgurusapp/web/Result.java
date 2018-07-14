package net.tecgurus.app.tecgurusapp.web;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("abbr")
    @Expose
    private String abbr;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("largest_city")
    @Expose
    private String largestCity;
    @SerializedName("capital")
    @Expose
    private String capital;

    //region Setters && Getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLargestCity() {
        return largestCity;
    }

    public void setLargestCity(String largestCity) {
        this.largestCity = largestCity;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }
    //endregion

    @Override
    public String toString() {
        return "id: " + this.id + "\n" +
                "country : " + this.country + "\n" +
                "name : " + this.name + "\n" +
                "area : " + this.area + "\n" +
                "largest_city : " + this.largestCity + "\n" +
                "capital : " + this.capital + "\n" +
                "abbr: " + this.abbr + "\n";
    }
}