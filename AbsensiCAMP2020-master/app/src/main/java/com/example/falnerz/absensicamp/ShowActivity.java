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
    //private AsyncTaskRetrieving proc;
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
        if (eventnya.equals("sesi")){
            eventnya += ("_" + MainActivity.sesiSelection);
        }
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
            case "sesi_1":{
                for (PesertaModel model : models){
                    if (model.getSesi1().equals("1") || model.getSesi1().equals("0")){
                        baru.add(model);
                    }
                }
            }break;
            case "sesi_2":{
                for (PesertaModel model : models){
                    if (model.getSesi2().equals("1") || model.getSesi2().equals("0")){
                        baru.add(model);
                    }
                }
            }break;
            case "sesi_3":{
                for (PesertaModel model : models){
                    if (model.getSesi3().equals("1") || model.getSesi3().equals("0")){
                        baru.add(model);
                    }
                }
            }break;
            case "sesi_4":{
                for (PesertaModel model : models){
                    if (model.getSesi4().equals("1") || model.getSesi4().equals("0")){
                        baru.add(model);
                    }
                }
            }break;
            case "bus_berangkat":{
                for (PesertaModel model : models){
                    if (model.getBusBerangkat().equals("1") || model.getBusBerangkat().equals("0")){
                        baru.add(model);
                    }
                }
            }break;
            case "bus_pulang":{
                for (PesertaModel model : models){
                    if (model.getBusPulang().equals("1") || model.getBusPulang().equals("0")){
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ShowActivity.this,MainActivity.class));
        overridePendingTransition (R.transition.slide_down,R.transition.slide_up);
        finish();
        super.onBackPressed();
    }
}
