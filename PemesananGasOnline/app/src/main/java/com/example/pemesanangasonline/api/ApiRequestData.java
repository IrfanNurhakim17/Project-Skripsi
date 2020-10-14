package com.example.pemesanangasonline.api;

import com.example.pemesanangasonline.Model.DataBarang;
import com.example.pemesanangasonline.Model.DataPemesanan;
import com.example.pemesanangasonline.Model.DataUser;
import com.example.pemesanangasonline.Model.ResponDataBarang;
import com.example.pemesanangasonline.Model.ResponDataGambar;
import com.example.pemesanangasonline.Model.ResponDataPemesanan;
import com.example.pemesanangasonline.Model.ResponDataUser;
import com.example.pemesanangasonline.Model.ResponLogin;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiRequestData {


    @FormUrlEncoded
    @POST("Login/Login.php")
    Call<ResponLogin> sendData(@Field("username") String username,
                               @Field("password") String password);

    @GET("Barang/Tampil_Barang.php")
    Call<ResponDataBarang> getBarang();

    @GET("Barang/Tampil_Barang_Pelanggan.php")
    Call<DataBarang> tampilBarang();

    @FormUrlEncoded
    @POST("Barang/Ubah_Barang.php")
    Call<ResponDataBarang> updateBarang(@Field("id_barang") String id_barang,
                                        @Field("nama_barang") String nama_barang,
                                        @Field("jumlah_barang") String jumlah_barang,
                                        @Field("harga_barang") String harga_barang);

    @FormUrlEncoded
    @POST("Barang/Tambah_Barang.php")
    Call<ResponDataBarang> insertBarang(@Field("id_barang") String id_barang,
                                        @Field("nama_barang") String nama_barang,
                                        @Field("jumlah_barang") String jumlah_barang,
                                        @Field("harga_barang") String harga_barang);

    @FormUrlEncoded
    @POST("Barang/Hapus_Barang.php")
    Call<ResponDataBarang> deleteBarang(@Field("id_barang") String id_barang);

    @FormUrlEncoded
    @POST("Pemesanan/Tampil_Pemesanan.php")
    Call<ResponDataPemesanan> getpesanan(@Field("key") String nama,
                                         @Field("ket_request") String ket_request);

    @GET("Pengiriman_Barang/Tampil_Pengiriman_Barang.php")
    Call<ResponDataPemesanan> getpengiriman();

    @GET("Pengiriman_Barang/Tampil_Riwayat_Transaksi.php")
    Call<ResponDataPemesanan> getriwayat();

    @FormUrlEncoded
    @POST("User/Tampil_Riwayat_Transaksi.php")
    Call<ResponDataPemesanan> getriwayatpelanggan(@Field("user_id") String user_id,
                                                  @Field("ket_request") String ket_request);

    @FormUrlEncoded
    @POST("Pemesanan/Tampil_Pemesanan_Pelanggan.php")
    Call<DataPemesanan> getPesananPelanggan(@Field("user_id") String user_id,
                                            @Field("ket_request") String ket_request);



    @FormUrlEncoded
    @POST("Pemesanan/Ubah_Pemesanan.php")
    Call<ResponDataPemesanan> updatePemesanan(@Field("id_pemesanan") String id_pemesanan,
                                              @Field("user_id") String user_id,
                                              @Field("barang_id") String barang_id,
                                              @Field("jumlah_barang") String jumlah_barang,
                                              @Field("tanggal_pemesanan") String tanggal_pemesanan,
                                              @Field("biaya_antar") String biaya_antar,
                                              @Field("total_biaya") String total_biaya,
                                              @Field("metode_pembayaran") String metode_pembayaran,
                                              @Field("bukti_transfer") String bukti_transfer,
                                              @Field("status_id") String status_id);

    @FormUrlEncoded
    @POST("Pemesanan/Tambah_Pemesanan.php")
    Call<ResponDataPemesanan> tambahPesanan(@Field("id_pemesanan") String id_pemesanan,
                                            @Field("user_id") String user_id,
                                            @Field("barang_id") String barang_id,
                                            @Field("jumlah_barang") String jumlah_barang,
                                            @Field("tanggal_pemesanan") String tanggal_pemesanan,
                                            @Field("biaya_antar") String biaya_antar,
                                            @Field("total_biaya") String total_biaya,
                                            @Field("metode_pembayaran") String metode_pembayaran,
                                            @Field("bukti_transfer") String bukti_transfer,
                                            @Field("status_id") String status_id);

    @Multipart
    @POST("Pemesanan/Tambah_Pemesanan.php")
    Call<ResponDataGambar> uploadfoto(@Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("Pemesanan/Hapus_Pemesanan.php")
    Call<ResponDataPemesanan> deletePemesanan(@Field("id_pemesanan") String id_pemesanan);

    @GET("User/Tampil_User.php")
    Call<ResponDataUser> getuser();

    @FormUrlEncoded
    @POST("User/Ubah_User.php")
    Call<ResponDataUser> updateuser(@Field("id_user") String id_user,
                                    @Field("username") String username,
                                    @Field("password") String password,
                                    @Field("nama") String nama,
                                    @Field("alamat") String alamat,
                                    @Field("no_telp") String no_telp,
                                    @Field("role_id") String role_id);

    @FormUrlEncoded
    @POST("User/Tambah_User.php")
    Call<ResponDataUser> insertUser(@Field("id_user") String id_user,
                                    @Field("username") String username,
                                    @Field("password") String password,
                                    @Field("nama") String nama,
                                    @Field("alamat") String alamat,
                                    @Field("no_telp") String no_telp,
                                    @Field("role_id") String role_id);

    @FormUrlEncoded
    @POST("User/Hapus_User.php")
    Call<ResponDataUser> deleteUser(@Field("id_user") String id_user);

    @FormUrlEncoded
    @POST("User/Tampil_Profil.php")
    Call<DataUser> getprofil(@Field("username") String username);

    @FormUrlEncoded
    @POST("User/regist_token.php")
    Call<DataUser> getToken(@Field("Token") String token,
                            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("User/update_token.php")
    Call<DataUser> updateToken(@Field("Token") String token,
                                @Field("user_id") String user_id,
                               @Field("status_id") String status_id);
}
