package br.com.ciberian.agendatelefonica.data;

import java.util.List;

import br.com.ciberian.agendatelefonica.models.Contato;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Edmilson Neto on 22/10/2017.
 */

public interface APIInterface {

    @GET("/contatos")
    Call<List<Contato>> list();

    @GET("/contatos/{id}")
    Call<Contato> get(@Path("id") Integer id);

    @POST("/contatos")
    Call<Contato> save(@Body Contato contato);

    @PUT("/contatos/{id}")
    Call<Contato> update(@Body Contato contato, @Path("id") Integer id);

    @DELETE("/contatos/{id}")
    Call<Contato> delete(@Path("id") Integer id);
}
