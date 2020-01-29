package com.example.falnerz.absensicamp.server;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Connector {
    @GET("getAbsensi")
    Call<List<PesertaModel>> getPeserta();

    @FormUrlEncoded
    @POST("absen")
    Call<List<PesertaModel>> postPeserta(@Field("nrp") String nrp,
                                         @Field("event") String event);
}
