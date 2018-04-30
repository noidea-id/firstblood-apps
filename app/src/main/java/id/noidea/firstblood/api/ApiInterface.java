package id.noidea.firstblood.api;
import java.util.List;

import id.noidea.firstblood.model.Posting;
import id.noidea.firstblood.model.Users;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by amaceh on 4/21/2018.
 */

public interface ApiInterface {


    @GET("users/login/")
    Call<ApiData<Users>> getUserLogin(@Query("user") String email, @Query("pass") String password);

    @FormUrlEncoded
    @POST("users/register/")
    Call<ApiData<Integer>> postAccount(@Field("username") String username, @Field("email") String email,
                                       @Field("password") String password, @Field("nama") String nama,
                                       @Field("goldar") String goldar, @Field("rhesus") String rhesus,
                                       @Field("no_hp") String no_hp, @Field("foto_profil") String foto_profil);

    @FormUrlEncoded
    @PUT("users/update/")
    Call<ApiData<Integer>> putAccount(@Query("key") String key,
                                      @Field("username") String username, @Field("email") String email,
                                      @Field("password") String password, @Field("nama")String nama,
                                      @Field("goldar") String goldar, @Field("rhesus") String rhesus,
                                      @Field("no_hp") String no_hp, @Field("foto_profil") String foto_profil);

    //posting Request
    @GET("posting/")
    Call<ApiData<List<Posting>>> getAllPosting(@Query("key") String key);

    @GET("posting/{id}")
    Call<ApiData<List<Posting>>> getSinglePosting(@Path("id") String id_posting, @Query("key") String key);

    @GET("posting/latest")
    Call<ApiData<List<Posting>>> getLatestPosting(@Query("key") String key, @Query("time") String time);

    @FormUrlEncoded
    @POST("posting/")
    Call<ApiData<Integer>> addPosting(@Query("key") String key,
                                     @Field("username") String username, @Field("goldar") String goldar,
                                     @Field("rhesus") String rhesus, @Field("descrip") String descrip,
                                     @Field("rumah_sakit") String rumah_sakit, @Field("status") String status,
                                     @Field("inserted_at") String inserted_at, @Field("updated_at") String updated_at);

    @FormUrlEncoded
    @PUT("posting/")
    Call<ApiData<Integer>> updatePosting(@Query("key") String key,
                                         @Field("id_posting") String id_posting, @Field("goldar") String goldar,
                                         @Field("rhesus") String rhesus, @Field("descrip") String descrip,
                                         @Field("rumah_sakit") String rumah_sakit, @Field("status") String status,
                                         @Field("updated_at") String updated_at);

    @FormUrlEncoded
    @DELETE("posting/{id_posting}")
    Call<ApiData<Integer>> deleteMatkul(@Query("key") String key, @Path("id_posting") String id_posting);




//  example code
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
