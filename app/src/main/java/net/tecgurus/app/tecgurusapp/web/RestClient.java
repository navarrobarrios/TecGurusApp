package net.tecgurus.app.tecgurusapp.web;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RestClient {

    @GET("{param}/all")
    Call<FirstNode> getAllCountryInfo(@Path("param") String countryCode);
}
