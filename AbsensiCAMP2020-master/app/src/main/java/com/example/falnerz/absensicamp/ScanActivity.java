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

        //pilihBus = (eventnya.equals("bus_berangkat") || eventnya.equals("bus_pulang"));
        //readyScan = false;

        //new AsyncTaskWaitRetrieving(this, eventnya).execute();

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
//    public class AsyncTaskWaitRetrieving extends AsyncTask<Void, Void, Void> {
//
//        private ShowActivity.AsyncTaskRetrieving proc;
//
//        AsyncTaskWaitRetrieving(Context konteksnya,String eventnya){
//            proc = new ShowActivity.AsyncTaskRetrieving(konteksnya,eventnya);
//        }
//
//        @SuppressLint({"NewApi", "WrongThread"})
//        @Override
//        protected Void doInBackground(Void... voids) {
//            proc.downloadData();
//            while(proc.isDownloading()){
//
//            }
////            System.out.println("udah download");
//            JSONArray downloadedData = proc.getDownloadedData();
//            int len = downloadedData.length();
//            String eventNow=eventnya;
//
//            if(Objects.equals(eventNow, "sesi")){
//                eventNow = "sesi_"+MainActivity.sesiSelection;
//            }
//
//            for (int i = 0; i < len; ++i) {
//                try {
//                    String nrpnya = downloadedData.getJSONObject(i).getString("nrp");
//                    inBus.put(nrpnya, downloadedData.getJSONObject(i).getString(eventNow));
//                    nrpNama.put(nrpnya,downloadedData.getJSONObject(i).getString("nama"));
////                    System.out.println(nrpnya+" -> "+downloadedData.getJSONObject(i).getString("nama"));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            readyScan = true;
//            zXingScannerView.startCamera();
//            return null;
//        }
//    }

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

//    private boolean isNumerical(String nrp){
//        int len=nrp.length();
//        for(int i=0;i<len;++i){
//            if(!Character.isDigit(nrp.charAt(i))){return false;}
//        }
//        return true;
//    }

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
//        if(isNumerical(result.getText())) {
//            scanResult = result;
////            String namanya = nrpNama.get(scanResult.getText());
////            if(namanya.equals(null)){
////                resumeCameranya();
////                Toast.makeText(getApplicationContext(), scanResult.getText()+" Bukan peserta",
////                        Toast.LENGTH_LONG).show();
////                return;
////            }
////            if(pilihBus && MainActivity.eventScan == MainActivity.ABSEN_SCAN){
////                if(!Objects.equals(inBus.get(scanResult.getText()) ,
////                        String.valueOf(MainActivity.busSelection))){//jika tidak termasuk peserta bus
////                    resumeCameranya();
////                    Toast.makeText(getApplicationContext(), "Salah Bus\nBus asal : "+inBus.get(scanResult.getText()),
////                            Toast.LENGTH_LONG).show();
////                    return;
////                }
////            }
//
////            if (isOnline()) {
////                if(MainActivity.eventScan == MainActivity.ABSEN_SCAN) {
////                    new AsyncTaskSending(this, eventnya).execute(
////                            AsyncTaskSending.POST_ABSEN, scanResult.getText(), namanya
////                    );
////                }
////                else{
////                    new AsyncTaskSending(this, eventnya).execute(
////                            AsyncTaskSending.POST_ASSIGN, scanResult.getText(), namanya
////                    );
////                }
////                toaster= Toast.makeText(getApplicationContext(),"Please wait", Toast.LENGTH_SHORT);
////                toaster.show();
////            }
////            else {
////                resumeCameranya();
////                Toast.makeText(getApplicationContext(), "No connection", Toast.LENGTH_LONG).show();
////            }
//        }
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
                //Log.e("Call Error", t.getMessage());
            }
        });
    }

//    private static class AsyncTaskSending extends AsyncTask<String, Void, Void> {
//        private String eventNow;
//        public static String POST_ABSEN = "1",
//                            POST_ASSIGN = "2";
//
//
//        @SuppressLint("StaticFieldLeak")
//        private Context konteks;
//        AsyncTaskSending(Context konteks,String eventnya){
//            eventNow = eventnya;
//            this.konteks = konteks;
//        }
//
//        @SuppressLint("NewApi")
//        private Void postAssign(final String nrp, final String nama){
//            try {
//                String querynya="";
//                URL obj;
//                HttpURLConnection con;
//                int responseCode;
//                querynya = URL_PKMBK + "/assign?nrp=" + nrp + "&event=" + eventNow.substring(4)+ "&bus=" + MainActivity.busSelection;
//                obj = new URL(querynya);
//                con = (HttpURLConnection) obj.openConnection();
//                con.setRequestMethod("GET");
//                responseCode = con.getResponseCode();
//                if(responseCode == 200){
//                    UIHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            toaster.cancel();
//                            Toast.makeText(konteks,"Nama: "+nama+"\nNrp: "+nrp+"\n"+"assigned to bus "+
//                                            MainActivity.busSelection, Toast.LENGTH_SHORT).show();
//
//                            zXingScannerView.resumeCameraPreview((ZXingScannerView.ResultHandler) appContext);
//                        }
//                    });
//                }
//                else{
//                    final int finalResponseCode = responseCode;
//                    UIHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(konteks,"Failed: "+ finalResponseCode, Toast.LENGTH_LONG).show();
//                        }
//                    });
//                }
//
//            }
//            catch (final Exception e) {
//                UIHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(konteks,"Failed: "+String.valueOf(e), Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//            return null;
//        }
//
//        @SuppressLint("NewApi")
//        private Void postAbsen(final String nrp, final String nama){
//            try {
//                String querynya="";
//                URL obj;
//                HttpURLConnection con;
//                int responseCode;
//
//                if(pilihBus) {
//                    querynya=URL_PKMBK+"/absen?nrp="+nrp+"&event="+eventNow.substring(4);
//                }
//                else if(Objects.equals(eventNow,"sesi")){
//                    if(MainActivity.sesiSelection == 4){//karna satu kali absen untuk sesi 4 dan 5
//                        querynya=URL_PKMBK+"/absen?nrp="+nrp+"&event=sesi_5";
//                        obj=new URL(querynya);
//                        con=(HttpURLConnection)obj.openConnection();
//                        con.setRequestMethod("GET");
//                        responseCode = con.getResponseCode();
//                        if(responseCode!=200){
//                            throw new Exception();
//                        }
//                    }
//                    querynya=URL_PKMBK+"/absen?nrp="+nrp+"&event=sesi_"+MainActivity.sesiSelection;
//                }
//                else{
//                    querynya=URL_PKMBK+"/absen?nrp="+nrp+"&event="+eventNow;
//
//                }
//                obj=new URL(querynya);
//                con = (HttpURLConnection) obj.openConnection();
//                con.setRequestMethod("GET");
//                responseCode = con.getResponseCode();
//                if(responseCode == 200){
//                    UIHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            toaster.cancel();
//                            Toast.makeText(konteks,"Nama: "+nama+"\nNrp: "+nrp+"\n"+"registered to "+
//                                    MainActivity.eventTV, Toast.LENGTH_SHORT).show();
//                            zXingScannerView.resumeCameraPreview((ZXingScannerView.ResultHandler) appContext);
//                        }
//                    });
//                }
//                else{
//                    final int finalResponseCode = responseCode;
//                    UIHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(konteks,"Failed: "+ finalResponseCode, Toast.LENGTH_LONG).show();
//                        }
//                    });
//                }
//            }
//            catch (Exception e) {
//                UIHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(konteks,"Failed: ", Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//            return null;
//        }
//
//        @SuppressLint("NewApi")
//        @Override
//        protected Void doInBackground(String... arg) {
//            if(Objects.equals(arg[0], POST_ABSEN)){
//                postAbsen(arg[1],arg[2]);
//            }
//            else if(Objects.equals(arg[0], POST_ASSIGN)){
//                postAssign(arg[1],arg[2]);
//            }
//            return null;
//        }
//    }
}
