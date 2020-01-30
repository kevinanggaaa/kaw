package com.example.falnerz.absensicamp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Toast;

import com.example.falnerz.absensicamp.server.Connector;
import com.example.falnerz.absensicamp.server.Connector_builder;
import com.example.falnerz.absensicamp.server.PesertaModel;
import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private static ZXingScannerView zXingScannerView;
    public static Context appContext;
    public static final String URL_PKMBK="http://pkmbk.jonathanrl.com/absen";
    private Result scanResult;
    private String eventnya;
    private static Toast toaster;
    boolean readyScan = true;
    private static Handler UIHandler;
    private HashMap<String,String> inBus = new HashMap<>();
    private HashMap<String,String> nrpNama = new HashMap<>();
    private static boolean pilihBus = false;

    static
    {
        UIHandler = new Handler(Looper.getMainLooper());
    }

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        eventnya = MainActivity.eventnya;

        super.onCreate(savedInstanceState);
        zXingScannerView = new ZXingScannerView(this);
        setContentView(zXingScannerView);

    }


    @SuppressLint("NewApi")
    @Override
    public void onResume() {
        super.onResume();
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
        if(!readyScan && pilihBus) {
            zXingScannerView.stopCamera();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        zXingScannerView.stopCamera();
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = null;
        if (cm != null) {
            netInfo = cm.getActiveNetworkInfo();
        }
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void resumeCameranya(){
        UIHandler.post(new Runnable() {
            @Override
            public void run() {
                zXingScannerView.resumeCameraPreview((ZXingScannerView.ResultHandler) appContext);
            }
        });
    }

    @SuppressLint("NewApi")
    @Override
    public void handleResult(Result result) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        appContext = this;
        final Handler handler = new Handler();

        if (v != null) {
            v.vibrate(200);
        }

        if (!result.getText().isEmpty() && isOnline()){
            //Toast.makeText(appContext, eventnya, Toast.LENGTH_SHORT).show();
            otherPostAbsen(result.getText().toString());
        }
        else{
            resumeCameranya();
            Toast.makeText(getApplicationContext(),"\""+result.getText()+"\" bukan NRP", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ScanActivity.this,MainActivity.class));
        overridePendingTransition (R.transition.fade_in,R.transition.fade_out);
        finish();
        super.onBackPressed();
    }

    private void otherPostAbsen(String nrp){
        if (eventnya.equals("sesi")){
            eventnya += "_" + MainActivity.sesiSelection;
            Toast.makeText(appContext, "", Toast.LENGTH_SHORT).show();
        }
        Connector connector = Connector_builder.getClient().create(Connector.class);
        Call<PesertaModel> postPeserta = connector.postPeserta(nrp, eventnya);
        postPeserta.enqueue(new Callback<PesertaModel>() {
            @Override
            public void onResponse(Call<PesertaModel> call, Response<PesertaModel> response) {
                if (response.isSuccessful()){
                    Toast.makeText(ScanActivity.this, "Presensi diupdate", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ScanActivity.this, MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(ScanActivity.this, "Gagal mengupdate", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ScanActivity.this, MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<PesertaModel> call, Throwable t) {
                Toast.makeText(ScanActivity.this, "Gagal mengupdate", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ScanActivity.this, MainActivity.class));
                finish();
            }
        });
    }

}
