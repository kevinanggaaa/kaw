package com.example.falnerz.absensicamp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.falnerz.absensicamp.server.Connector;
import com.example.falnerz.absensicamp.server.Connector_builder;
import com.example.falnerz.absensicamp.server.PesertaModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShowActivity extends AppCompatActivity {

    private String eventnya;
    private static TextView tvcount;
    private AsyncTaskRetrieving proc;
    private static Toast toaster;
    private RecyclerView recyclerView;
    private RvAdapter adapter;
    private Connector connector;
    private SearchView searchView;
    private Button button;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        tvcount = findViewById(R.id.tvCount);
        TextView kegiatan = findViewById(R.id.tvKegiatan2);
        eventnya = MainActivity.eventnya;
        kegiatan.setText(MainActivity.eventTV);
        AlphaAnimation buttonClickAnim = new AlphaAnimation(1F, 0.5F);
        buttonClickAnim.setDuration(400);
        proc = new AsyncTaskRetrieving(this,eventnya);
        proc.execute();

        init_view();
        connect_db();
        set_action();
    }

    private void set_action(){
        if (searchView.isIconified()){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    adapter.filter(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.filter(newText);
                    return true;
                }
            });
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect_db();
            }
        });
    }

    private void init_view(){
        recyclerView = findViewById(R.id.rv_nama);
        adapter = new RvAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchView = findViewById(R.id.searchItem);
        button = findViewById(R.id.button);
        progressBar = findViewById(R.id.loadings);
    }

    private void connect_db(){
        progressBar.setVisibility(View.VISIBLE);
        connector = new Connector_builder().getClient().create(Connector.class);
        Call<List<PesertaModel>> caller = connector.getPeserta();
        caller.enqueue(new Callback<List<PesertaModel>>() {
            @Override
            public void onResponse(Call<List<PesertaModel>> call, Response<List<PesertaModel>> response) {
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    List<PesertaModel> models = response.body();
                    set_data(models);
                }else{
                    progressBar.setVisibility(View.GONE);
                    toasting("Terjadi kesalahan");
                }
            }

            @Override
            public void onFailure(Call<List<PesertaModel>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                toasting("Terjadi kegagalan");
            }
        });
    }

    private void set_data(List<PesertaModel> models){
        List<PesertaModel> baru = new ArrayList<>();
        switch (eventnya){
            case "opening":{
                for (PesertaModel model : models){
                    if (model.getOpening().equals("1") || model.getOpening().equals("0")){
                        baru.add(model);
                    }
                }
            }break;
            case "closing":{
                for (PesertaModel model : models){
                    if (model.getClosing().equals("1") || model.getOpening().equals("0")){
                        baru.add(model);
                    }
                }
            }break;
            case "worship_night":{
                for (PesertaModel model : models){
                    if (model.getWorshipNight().equals("1") || model.getOpening().equals("0")){
                        baru.add(model);
                    }
                }
            }break;
            case "ibadah_minggu":{
                for (PesertaModel model : models){
                    if (model.getIbadahMinggu().equals("1") || model.getOpening().equals("0")){
                        baru.add(model);
                    }
                }
            }break;
            default:
        }
        adapter.setGangguan(baru, eventnya);
        recyclerView.setAdapter(adapter);
    }

    private void toasting(final String textnya){
        Toast.makeText(this, textnya, Toast.LENGTH_SHORT).show();
    }


    public static class AsyncTaskRetrieving extends AsyncTask<Void, Void, Void> {
        private boolean running,downloading;
        private String eventNow;
        private Context konteks;
        JSONArray dataJson;
        AsyncTaskRetrieving(Context inkonteks,String eventnya){
            eventNow = eventnya;
            dataJson = null;
            konteks = inkonteks;
            running=false;
            downloading=false;

        }

        public boolean isRunning(){
            return running;
        }

        public boolean isDownloading(){return dataJson == null;}

        public void downloadData(){
            try {
                URL url = new URL("https://pkmbk.jonathanrl.com/");
                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                StringBuffer stringBuffer = null;
                stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
                dataJson = new JSONArray(stringBuffer.toString());
            }
            catch (Exception e){
                //do nothing
                System.out.println("download error : "+e);
            }
        }

        public JSONArray getDownloadedData(){
            return dataJson;
        }

        @SuppressLint("NewApi")
        @Override
        protected Void doInBackground(Void... params)
        {
            running = true;
            downloading = true;
            try {
                downloadData();
                while(isDownloading()){
                    //do nothing
                }
                List<Pair<String, String>> listData = new ArrayList<>();
                List<String> listNama = new ArrayList<>();
                int count = 0, total = 0;
                System.out.println("eventnya : "+eventNow);
                if (Objects.equals(eventNow, "bus_berangkat") ||
                        Objects.equals(eventNow, "bus_pulang")) {
                    String kehadiran = eventNow.substring(4);
                    int tempLen = dataJson.length();
                    String displayedName;
                    for (int i = 0; i < tempLen ; i++) {
                        try {
                            String noBus = dataJson.getJSONObject(i).getString(eventNow);
                            if (!Objects.equals(noBus, String.valueOf(MainActivity.busSelection))) {
                                continue;
                            }
                            total++;
                            String namanya=dataJson.getJSONObject(i).getString("nama");
                            if(namanya.length()>30) {
                                displayedName = namanya.substring(0,26) + ".....";
                            }
                            else{
                                displayedName=namanya;
                            }
                            listNama.add(namanya);
                            String stat = dataJson.getJSONObject(i).getString(kehadiran);
                            listData.add(new Pair(displayedName+"\n"+
                                    dataJson.getJSONObject(i).getString("nrp"),
                                    stat
                            ));
                            if (Objects.equals(stat, "1")) {
                                count++;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                else {//sisa event lain
                    if (Objects.equals(eventNow, "sesi")) {
                        eventNow = "sesi_" + MainActivity.sesiSelection;
                    }
                    total = dataJson.length();
                    String displayedName;
                    for (int i = 0; i < total; i++) {
                        try {
                            String stat = dataJson.getJSONObject(i).getString(eventNow);
                            String namanya=dataJson.getJSONObject(i).getString("nama");
                            if(namanya.length()>30) {
                                displayedName = namanya.substring(0,26) + ".....";
                            }
                            else{
                                displayedName=namanya;
                            }
                            listNama.add(namanya);
                            listData.add(new Pair(displayedName+"\n"+
                                    dataJson.getJSONObject(i).getString("nrp"),
                                    stat
                            ));
                            if (Objects.equals(stat, "1")) {
                                count++;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                initListView((ArrayList<Pair<String, String>>) listData, (ArrayList<String>) listNama,count, total);
            }
            catch (final Exception e){
//                UIHandler.post(new Runnable() {
//                    @SuppressLint("SetTextI18n")
//                    @Override
//                    public void run() {
//                        Toast.makeText(konteks, "Error:"+e, Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
            return null;

        }

        private void initListView(final ArrayList<Pair<String, String>> listData,final ArrayList<String> listNama,
                                  final int count, final int total){
            System.out.println("masuk init");
//            UIHandler.post(new Runnable() {
//                @SuppressLint("SetTextI18n")
//                @Override
//                public void run() {
//                    try {
//                        toaster.cancel();
//
//                        listAdapter = new ListAdapter(konteks,listData,listNama);
//                        lview.setAdapter(listAdapter);
//                        tvcount.setText("Participant : " + count + " / " + total);
//                        Toast.makeText(konteks, "Updated", Toast.LENGTH_SHORT).show();
//                    } catch (Exception e) {
//                        Toast.makeText(konteks, "Error :"+e, Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
            running = false;
        }
    }

    public void refreshClicked(View view) {
        if(proc.isRunning()){return;}
        proc= new AsyncTaskRetrieving(this,eventnya);
        proc.execute();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ShowActivity.this,MainActivity.class));
        overridePendingTransition (R.transition.slide_down,R.transition.slide_up);
        finish();
        super.onBackPressed();
    }
}
