package com.alphadev.gamesnews.api;

import com.alphadev.gamesnews.api.pojo.MessageResultPOJO;
import com.alphadev.gamesnews.api.pojo.NewNewPOJO;
import com.alphadev.gamesnews.api.pojo.NewPOJO;
import com.alphadev.gamesnews.api.pojo.PlayerPOJO;
import com.alphadev.gamesnews.api.pojo.TokenPOJO;
import com.alphadev.gamesnews.api.pojo.UserPOJO;
import com.alphadev.gamesnews.api.pojo.UserWithFavsPOJO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GamesNewsAPIService {

    //******** ADMINISTRACION DE USUARIOS ********//

    //LOGIN
    @POST("/login")
    @FormUrlEncoded
    Call<TokenPOJO> logIn(@Field("user") String user,
                          @Field("password") String password);

    //OBTENER TODOS LOS USUARIOS
    @GET("/users")
    Call<List<UserPOJO>> getAllUsers(@Header("Authorization") String authHeader);

    //AGREGAR NUEVO USUARIO
    @POST("/users")
    @FormUrlEncoded
    Call<UserPOJO> addUser(@Header("Authorization") String authHeader, @Field("user") String user,
                           @Field("avatar") String avatar, @Field("password") String password);

    //ACTUALIZAR LA CONTRASEÑA DE UN USUARIO
    @PUT("/users/{id}")
    @FormUrlEncoded
    Call<UserPOJO> editUser(@Header("Authorization") String authHeader, @Path("id") String id, @Field("password") String password);

    //OBTENER UN USUARIO POR SU ID
    @GET("/users/{id}")
    @FormUrlEncoded
    Call<UserWithFavsPOJO> getUserByID(@Header("Authorization") String authHeader, @Path("id") String id);

    //BORRAR USUARIO POR SU ID
    @DELETE("/users/{id}")
    @FormUrlEncoded
    Call<UserPOJO> deleteUserByID(@Header("Authorization") String authHeader, @Path("id") String id);

    @GET("users/detail")
    Call<UserWithFavsPOJO> getUserDetail(@Header("Authorization") String authHeader);

    //AGREGAR NOTICIA FAVORITA A UN USUARIO
    @POST("/users/{id}/fav")
    @FormUrlEncoded
    Call<NewNewPOJO> addUserFav(@Header("Authorization") String authHeader, @Path("id") String id, @Field("new") String n_new);


    //BORRAR UNA NOTICIA FAVORITA A UN USUARIO
    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "/users/{id}/fav", hasBody = true)
    Call<MessageResultPOJO> deleteUserFav(@Header("Authorization") String authHeader, @Path("id") String id, @Field("new") String n_new);

    //******** ADMINISTRACION DE NOTICIAS ********//

    //OBTENER TODAS LAS NOTICIAS
    @GET("/news")
    Call<List<NewPOJO>> getAllNews(@Header("Authorization") String authHeader);

    //OBTENER TIPOS DE NOTICIAS
    @GET("/news/type/list")
    Call<List<String>> getNewsCategory(@Header("Authorization") String authHeader);

    //OBTENER NOTICIAS DE UN TIPO
    @GET("/news/type/{category}")
    Call<List<NewPOJO>> getNewsByCategory(@Header("Authorization") String authHeader, @Path("category") String category);

    //AGREGAR NUEVA NOTICIA
    @POST("/news")
    @FormUrlEncoded
    Call<NewPOJO> addNew(@Header("Authorization") String authHeader, @Path("title") String title, @Path("description")
            String description, @Path("coverImage") String coverImage, @Path("body") String body, @Path("game") String category);

    //OBTENER NOTICIA POR ID
    @GET("/news/{id}")
    Call<NewPOJO> getNewByID(@Header("Authorization") String authHeader, @Path("id") String id);

    //******** ADMINISTRACION DE PLAYERS ********//

    //OBTENER TODOS LOS PLAYERS
    @GET("/players")
    Call<List<PlayerPOJO>> getAllPlayers(@Header("Authorization") String authHeader);

    //OBTENER LISTA DE JUEGOS DE LOS PLAYERS
    @GET("/players/type/list")
    Call<List<String>> getPlayersCategory(@Header("Authorization") String authHeader);

    //AGREGAR NUEVO PLAYER
    @GET("/players/type/{game}")
    Call<List<PlayerPOJO>> getPlayersByCategory(@Header("Authorization") String authHeader, @Path("game") String category);

    //OBTENER JUGADOR POR ID
    @GET("/players/{id}")
    Call<PlayerPOJO> getPlayerByID(@Header("Authorization") String authHeader, @Path("id") String id);

}

