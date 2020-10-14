package com.example.pemesanangasonline;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.widget.ImageViewCompat;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pemesanangasonline.Model.DataBarang;
import com.example.pemesanangasonline.Model.DataPemesanan;
import com.example.pemesanangasonline.Model.ResponDataBarang;
import com.example.pemesanangasonline.Model.ResponDataGambar;
import com.example.pemesanangasonline.Model.ResponDataPemesanan;
import com.example.pemesanangasonline.api.ApiRequestData;
import com.example.pemesanangasonline.api.Retroserver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PembayaranTransfer extends AppCompatActivity {
    TextView bp,tb, uf, salin, lihat, norek, text_foto, batal;
    AppCompatImageView close_foto;
    ImageView bt, foto_transfer, hapus_foto;
    RelativeLayout bungkus_tf;
    Button pf, lf;
    Bitmap bitmap;
    Uri imageUri;
    final int kodeGallery = 9544;
    Dialog dialog;
    String part_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran_transfer);

        bp = (TextView)findViewById(R.id.batas_pembayaran);
        tb = (TextView)findViewById(R.id.total_bayar);
        bt = (ImageView) findViewById(R.id.bukti_transfer);
        hapus_foto = (ImageView) findViewById(R.id.hapus_foto);
        pf = (Button) findViewById(R.id.pilih_foto);
        lf = (Button) findViewById(R.id.lihat_foto);
        uf = (TextView) findViewById(R.id.unggah_foto);
        batal = (TextView) findViewById(R.id.batalkan_pesanan);
        salin = (TextView)findViewById(R.id.salin);
        lihat = (TextView)findViewById(R.id.lihat_detail);
        norek = (TextView)findViewById(R.id.norek);
        text_foto = (TextView)findViewById(R.id.text_foto);
        bungkus_tf = (RelativeLayout)findViewById(R.id.bungkus_tf);
        dialog = new Dialog(PembayaranTransfer.this);


        Intent data = getIntent();
        final String aid_user = data.getStringExtra("id_user");


        final ApiRequestData api = Retroserver.getClient().create(ApiRequestData.class);
        Call<DataPemesanan> getData = api.getPesananPelanggan(aid_user,"detail_pemesanan");
        getData.enqueue(new Callback<DataPemesanan>() {
            @Override
            public void onResponse(Call<DataPemesanan> call, Response<DataPemesanan> response) {
                final String aid_pemesanan = response.body().getId_pemesanan();
                final String auser_id = response.body().getUser_id();
                final String anama_pemesan = response.body().getNama();
                final String ano_telp = response.body().getNo_telp();
                final String aalamat = response.body().getAlamat();
                final String abarang_id = response.body().getBarang_id();
                final String anama_barang = response.body().getNama_barang();
                final String ajumlah_pemesanan = response.body().getJumlah_pemesanan();
                final String atanggal_pemesanan = response.body().getTanggal_pemesanan();
                final String abiaya_antar = response.body().getBiaya_antar();
                final String atotal_biaya = response.body().getTotal_biaya();
                final String ametode_pembayaran = response.body().getMetode_pembayaran();
                final String astatus_id = response.body().getStatus_id();
                String wp = atanggal_pemesanan.substring(11,13);
                String wpm = atanggal_pemesanan.substring(14,16);
                String wk = atanggal_pemesanan.substring(19,21);
                int iwp = Integer.parseInt(wp);
                iwp = iwp + 1;
                wp = Integer.toString(iwp);
                bp.setText(atanggal_pemesanan.substring(0,10) + " " + wp +":" + wpm + " " + wk);
                tb.setText("Rp"+insertString(atotal_biaya));

                hapus_foto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bitmap = null;
                        bungkus_tf.setVisibility(View.GONE);
                        text_foto.setVisibility(View.VISIBLE);
                    }
                });

                lf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.setContentView(R.layout.layout_foto_bukti_transfer);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();

                        foto_transfer = (ImageView)dialog.findViewById(R.id.foto_transfer);
                        close_foto = (AppCompatImageView) dialog.findViewById(R.id.close_foto);

                        if (bitmap!=null)
                        {
                            foto_transfer.setImageBitmap(bitmap);
                        }

                        close_foto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });


                    }
                });

                batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String bstatus_id = "6";
                        String abukti_transfer = "";
                        Call<ResponDataPemesanan> tolak = api.updatePemesanan(aid_pemesanan, auser_id, abarang_id, ajumlah_pemesanan, atanggal_pemesanan, abiaya_antar, atotal_biaya, ametode_pembayaran, abukti_transfer, bstatus_id);
                        tolak.enqueue(new Callback<ResponDataPemesanan>() {
                            @Override
                            public void onResponse(Call<ResponDataPemesanan> call, Response<ResponDataPemesanan> response) {
                                Log.d("Retro", "onResponse");

                                Call<DataBarang> getData = api.tampilBarang();
                                getData.enqueue(new Callback<DataBarang>() {
                                    @Override
                                    public void onResponse(Call<DataBarang> call, Response<DataBarang> response) {
                                        Log.d("RETRO", "response : " + response.body().toString());
                                        final String anama_barang = response.body().getNama_barang();
                                        final String ajumlah_barang = response.body().getJumlah_barang();
                                        final String aharga_barang = response.body().getHarga_barang();
                                        final String id_barang = response.body().getId_barang();

                                        int ajumlahbarang = Integer.parseInt(ajumlah_barang);
                                        int jumlahpemesanan = Integer.parseInt(ajumlah_pemesanan);
                                        jumlahpemesanan = ajumlahbarang + jumlahpemesanan;
                                        String ajumlahpemesanan = Integer.toString(jumlahpemesanan);

                                        Call<ResponDataBarang> update = api.updateBarang(id_barang,anama_barang,ajumlahpemesanan,aharga_barang);
                                        update.enqueue(new Callback<ResponDataBarang>() {
                                            @Override
                                            public void onResponse(Call<ResponDataBarang> call, Response<ResponDataBarang> response) {
                                                Log.d("Retro", "Response");
                                                Toast.makeText(PembayaranTransfer.this, "Berhasil Batalkan Pesanan", Toast.LENGTH_SHORT).show();
                                                Intent goTampil = new Intent(PembayaranTransfer.this, HomePelanggan.class);
                                                startActivity(goTampil);
                                            }

                                            @Override
                                            public void onFailure(Call<ResponDataBarang> call, Throwable t) {
                                                Log.d("Retro", "Data Error");
                                            }
                                        });
                                    }

                                    @Override
                                    public void onFailure(Call<DataBarang> call, Throwable t) {
                                        Log.d("RETRO", "Failure : " + "Gagal Mengirim Data");
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<ResponDataPemesanan> call, Throwable t) {
                                Log.d("Retro", "OnFailure");
                            }
                        });
                    }
                });

                uf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (bitmap == null)
                        {
                            Toast.makeText(PembayaranTransfer.this, "Upload Bukti Transfer", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            File imagefile = createTempFile(bitmap);
                            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"),imagefile);
                            MultipartBody.Part part_image = MultipartBody.Part.createFormData("gambar", imagefile.getName(),requestBody);
                            final ApiRequestData api = Retroserver.getClient().create(ApiRequestData.class);
                            Call<ResponDataGambar> upload = api.uploadfoto(part_image);
                            upload.enqueue(new Callback<ResponDataGambar>() {
                                @Override
                                public void onResponse(Call<ResponDataGambar> call, Response<ResponDataGambar> response) {
                                    String bukti_transfer = response.body().getPesan();

                                    Call<ResponDataPemesanan> update = api.updatePemesanan(aid_pemesanan, auser_id, abarang_id, ajumlah_pemesanan, atanggal_pemesanan, abiaya_antar, atotal_biaya, ametode_pembayaran, bukti_transfer,astatus_id);
                                    update.enqueue(new Callback<ResponDataPemesanan>() {
                                        @Override
                                        public void onResponse(Call<ResponDataPemesanan> call, Response<ResponDataPemesanan> response) {
                                            Toast.makeText(PembayaranTransfer.this, "Berhasil Mengunggah!", Toast.LENGTH_SHORT).show();
                                            Intent goNotif = new Intent(PembayaranTransfer.this, NotifikasiPelanggan.class);
                                            startActivity(goNotif);
                                        }

                                        @Override
                                        public void onFailure(Call<ResponDataPemesanan> call, Throwable t) {
                                            Toast.makeText(PembayaranTransfer.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                @Override
                                public void onFailure(Call<ResponDataGambar> call, Throwable t) {
                                    Log.d("RETRO", "Failure : " + t.getMessage());
                                    Toast.makeText(PembayaranTransfer.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                });

                pf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,"open gallery"),kodeGallery);
                    }
                });

                lihat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent goDetail = new Intent(PembayaranTransfer.this, DetailPemesananPelanggan.class);
                        startActivity(goDetail);
                    }
                });

                salin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clipData = ClipData.newPlainText("TextView", norek.getText().toString());
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(PembayaranTransfer.this, "Tersalin!", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onFailure(Call<DataPemesanan> call, Throwable t) {

            }
        });
    }

    public String insertString(String originalString){
        String newString = new String();
        String stringToBeInserted = ".";
        for (int i = 0; i < originalString.length(); i++) {

            newString += originalString.charAt(i);

            if (originalString.length() == 6) {
                if (i == 2) {
                    newString += stringToBeInserted;
                }

            }
            if (originalString.length() == 5) {
                if (i == 1) {
                    newString += stringToBeInserted;
                }

            }
            if (originalString.length() == 4) {
                if (i == 0) {
                    newString += stringToBeInserted;
                }
            }
        }

        // return the modified String
        return newString;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageUri = data.getData();
        if(requestCode == kodeGallery && resultCode == RESULT_OK){
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            bt.setImageBitmap(bitmap);
            bungkus_tf.setVisibility(View.VISIBLE);
            text_foto.setVisibility(View.GONE);

        }
    }

    private File createTempFile(Bitmap bitmap) {
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                , System.currentTimeMillis() +"_image.jpg");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG,100, bos);
        byte[] bitmapdata = bos.toByteArray();
        //write the bytes in file

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
