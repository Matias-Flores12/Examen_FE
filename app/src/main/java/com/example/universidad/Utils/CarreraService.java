package com.example.universidad.Utils;

import com.example.universidad.Model.Carrera;
import com.example.universidad.dto.CarreraDto;

import java.util.List;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CarreraService {
    @GET("listar")
    Call<List<Carrera>> getCarrera();

    @POST("agregar")
    Call<Carrera> create(@Body CarreraDto dto);

    @PUT("{id}")
    Call<Carrera> edit(@Path("id") int id, @Body CarreraDto dto);

    @DELETE("{id}")
    Call<List<Carrera>> delete(@Path("id") int id);

}
