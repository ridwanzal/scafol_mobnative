package com.example.scafolmobile.restapi;

import com.example.scafolmobile.model.DataResponse;
import com.example.scafolmobile.model.DataResponsePA;
import com.example.scafolmobile.model.DataResponseKegiatan;
import com.example.scafolmobile.model.DataResponsePaket;

import retrofit2.Call;

import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("users/")
    Call<DataResponse> getUserById(@Query("user_id") String user_id);

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

}
