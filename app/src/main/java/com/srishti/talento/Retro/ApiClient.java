package com.srishti.talento.Retro;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit=null;

    public static Retrofit UserData(){

//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();

        retrofit=new Retrofit.Builder()
                .baseUrl("http://campus.sicsglobal.co.in/Project/talento/admin/api_2/")//test
               // .baseUrl("https://talento.srishticampus.com/admin/api_2/")//production url
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

}

