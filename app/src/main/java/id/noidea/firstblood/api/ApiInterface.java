package id.noidea.firstblood.api;
import java.util.List;

import id.noidea.firstblood.model.Users;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by amaceh on 4/21/2018.
 */

public interface ApiInterface {


    @GET("users/login/")
    Call<ApiData<Users>> getUserLogin(@Query("user") String email, @Query("pass") String password);

    @FormUrlEncoded
    @POST("users/register/")
    Call<ApiData<Integer>> postAccount(@Field("username") String username, @Field("email") String email, @Field("pass") String password);

    @FormUrlEncoded
    @PUT("users/update/")
    Call<ApiData<Integer>> putAccount(@Query("key") String key,
                                      @Field("username") String username, @Field("email") String email,@Field("pass") String password);
    //example code
//    @GET("kuliah/")
//    Call<ApiData<Kuliah>> getAllMatkul(@Query("key") String key);
//
//    @GET("kuliah/")
//    Call<ApiData<Kuliah>> getMatkul(@Query("key") String key, @Query("id") String kode_mk);
//
//    @FormUrlEncoded
//    @POST("kuliah/")
//    Call<ApiData<Kuliah>> postMatkul(@Query("key") String key,
//                                     @Field("nama_mk") String mk, @Field("nama_dosen") String dosen,
//                                     @Field("sks") String sks);
//    @FormUrlEncoded
//    @PUT("kuliah/")
//    Call<ApiData<Kuliah>> putMatkul(@Query("key") String key, @Query("id") String kode_mk,
//                                     @Field("nama_mk") String mk, @Field("nama_dosen") String dosen,
//                                     @Field("sks") String sks);
//    @FormUrlEncoded
//    @DELETE("kuliah/")
//    Call<ApiData<Kuliah>> deleteMatkul(@Query("key") String key, @Query("id") String kode_mk);
}
