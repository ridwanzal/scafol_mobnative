package com.example.scafolmobile.restapi;

import com.example.scafolmobile.model.DataResponse;
import com.example.scafolmobile.model.DataResponsePA;
import com.example.scafolmobile.model.DataResponseKegiatan;
import com.example.scafolmobile.model.DataResponsePaket;
import com.example.scafolmobile.model.DataResponseProgress;
import com.example.scafolmobile.model.DataResponseUsers;

import retrofit2.Call;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("users/")
    Call<DataResponse> getUserById(@Query("user_id") String user_id);

    @GET("users/kontraktorall")
    Call<DataResponseUsers> getKontrak();

    @GET("kegiatan/")
    Call<DataResponse> getKegiatan();

    @GET("kegiatan/")
    Call<DataResponseKegiatan> getKegiatanAdmin();

    @GET("kegiatan/dinas")
    Call<DataResponseKegiatan> getKegiatanAdminDinas(@Query("dinas_id") String dinas_id);

    @GET("paket/pptk")
    Call<DataResponsePaket> getPaketPptk(@Query("pptk_id") String pptk_id);

    @GET("paket/")
    Call<DataResponsePaket> getPaketId(@Query("pa_id") String pa_id);

    @GET("progress/fisik")
    Call<DataResponseProgress> getProgressByPaket(@Query("pa_id") String pa_id);

    @GET("kegiatan/bidang/")
    Call<DataResponseKegiatan> getKegiatanBidang(@Query("bi_id") String bi_id);

    @GET("login")
    Call<DataResponse> checkLogin(@Query("username") String username, @Query("password") String password);

    @GET("dashboardpptk/paketall/")
    Call<DataResponsePA> countPaketPPTK(@Query("pptk_id") String pptk_id);

    @GET("dashboardpptk/paketprogress/")
    Call<DataResponsePA> countPaketProgressPPTK(@Query("pptk_id") String pptk_id);

    @GET("dashboardpptk/paketbelum/")
    Call<DataResponsePA> countPaketBelumPPTK(@Query("pptk_id") String pptk_id);

    @GET("dashboardpptk/paketselesai/")
    Call<DataResponsePA> countPaketSelesai(@Query("pptk_id") String pptk_id);

    @GET("dashboardpptk/pagupptk/")
    Call<DataResponsePA> countPaguPPTK(@Query("pptk_id") String pptk_id);

    @GET("dashboardpptk/realpptk/")
    Call<DataResponsePA> countRealPPTK(@Query("pptk_id") String pptk_id);

    @FormUrlEncoded
    @POST("paket/updatemap/")
    Call<DataResponsePaket> updateMap(@Field("pa_id") String pa_id,  @Field("pa_lokasi") String pa_lokasi, @Field("pa_loc_latitude") String pa_loc_latitude, @Field("pa_loc_longitude") String pa_loc_longitude);

    @FormUrlEncoded
    @POST("paket/updatekontrak/")
    Call<DataResponsePaket> updateKontrak(@Field("pa_id") String pa_id,  @Field("pa_nomor_kontrak") String pa_nomor_kontrak, @Field("pa_nilai_kontrak") String pa_nilai_kontrak, @Field("pa_awal_kontrak") String pa_awal_kontak, @Field("pa_akhir_kontrak") String pa_akhir_kontrak);


//    @POST("paket/updatemap/")
//    Call<DataResponsePA> updateMapPaket(@Field("pa_id") String pa_id, @Field("lat") String lat, @Field("long") String longitude);


}
