package com.example.falnerz.absensicamp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity{
    private static String prefixBus="";
    public static int busSelection = 1;
    public static int sesiSelection = 1;

    private AlphaAnimation buttonClickAnim;
    TextView textView;
    public static String eventnya="", eventTV="";
    private RadioButton rbOpen,rbClose,rbPulang,rbPergi,rbWorship,rbMinggu,rbSesi;

    private static final int PERMISSION_INTERNET_SCAN = 1,
                             PERMISSION_INTERNET_SHOW = 2,
                             PERMISSION_CAMERA = 3;
    public static final int ABSEN_SCAN = 4;
    public static final int ASSIGN_SCAN = 5;

    public static int eventScan = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tvKegiatan);
        rbOpen= findViewById(R.id.rbOpening);
        rbClose= findViewById(R.id.rbClosing);
        rbPulang= findViewById(R.id.rbBusPulang);
        rbPergi= findViewById(R.id.rbBusPergi);
        rbWorship= findViewById(R.id.rbWorshipNight);
        rbMinggu= findViewById(R.id.rbIbadahMinggu);
        rbSesi= findViewById(R.id.rbSesi);

        buttonClickAnim = new AlphaAnimation(1F, 0.5F);
        buttonClickAnim.setDuration(400);

        eventnya="";
        eventTV="";
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }


    @SuppressLint("NewApi")
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSesi1:
                sesiSelection = 1;
                break;
            case R.id.menuSesi2:
                sesiSelection = 2;
                break;
            case R.id.menuSesi3:
                sesiSelection = 3;
                break;
            case R.id.menuSesi4:
                sesiSelection = 4;
                break;
            case R.id.bus1:
                busSelection=1;
                break;
            case R.id.bus2:
                busSelection=2;
                break;
            case R.id.bus3:
                busSelection=3;
                break;
            case R.id.bus4:
                busSelection=4;
                break;
            case R.id.bus5:
                busSelection=5;
                break;
            case R.id.bus6:
                busSelection=6;
                break;
            case R.id.bus7:
                busSelection=7;
                break;
        }

        if(eventnya.equals("sesi")){
            eventTV = "Sesi " + sesiSelection;
        }
        else {
            eventTV = prefixBus + busSelection;
        }
        textView.setText(eventTV);
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();

        if(v.getId() == R.id.rbBusPulang){
            menuInflater.inflate(R.menu.no_bus, menu);
            menu.getItem(busSelection-1).setChecked(true);
        }
        else if(v.getId() == R.id.rbBusPergi){
            menuInflater.inflate(R.menu.no_bus, menu);
            menu.getItem(busSelection-1).setChecked(true);
        }
        else if (v.getId() == R.id.rbSesi){
            menuInflater.inflate(R.menu.sesi_menu, menu);
            menu.getItem(sesiSelection-1).setChecked(true);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_INTERNET_SHOW: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    moveToShow();
                } else {
                    Toast.makeText(getApplicationContext(), "No connection", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case PERMISSION_INTERNET_SCAN:{
                int permissionCheck = ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA);

                if (permissionCheck == PackageManager.PERMISSION_GRANTED
                        &&grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    moveToScan();
                }
                else if(permissionCheck != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.INTERNET},
                            PERMISSION_CAMERA);
                }
                else {
                    Toast.makeText(getApplicationContext(), "No connection", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case PERMISSION_CAMERA:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    moveToScan();
                }
                else{
                    Toast.makeText(getApplicationContext(), "No camera found", Toast.LENGTH_SHORT).show();
                }
                break;
            }

        }
    }
    protected boolean isOnline() {
        try{
            ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = null;
            if (cm != null) {
                netInfo = cm.getActiveNetworkInfo();
            }
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }
        catch (Exception e){
            return false;
        }
    }

    public void showButtonClicked(View view) throws IOException, JSONException {
        view.startAnimation(buttonClickAnim);
        //Toast.makeText(this, eventnya, Toast.LENGTH_SHORT).show();
        if(eventnya.length()==0){
            Toast.makeText(getApplicationContext(), "Pilih salah satu", Toast.LENGTH_SHORT).show();
            return;
        }
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET);
        if(permissionCheck!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    PERMISSION_INTERNET_SHOW);
            return;
        }

        if(isOnline()){
            moveToShow();
        }
        else{
            Toast.makeText(getApplicationContext(), "No Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void moveToShow(){

        Intent intent =new Intent(MainActivity.this, ShowActivity.class);
        startActivity(intent);
        overridePendingTransition(R.transition.slide_down,R.transition.slide_up);
        finish();

    }


    @SuppressLint("NewApi")
    public void scanButtonClicked(View view) {
        view.startAnimation(buttonClickAnim);
        if(eventnya.length()==0){
            Toast.makeText(getApplicationContext(), "Pilih salah satu", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (view.getId()){
            case R.id.absen:
                eventScan = ABSEN_SCAN;
                break;
            case R.id.assignBus:
                eventScan = ASSIGN_SCAN;
                break;
        }
        if(eventScan == ASSIGN_SCAN && !(eventnya.equals("bus_berangkat") ^ eventnya.equals("bus_pulang"))){
            Toast.makeText(getApplicationContext(), "Please Select Bus", Toast.LENGTH_SHORT).show();
            return;
        }

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET);
        if(permissionCheck!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    PERMISSION_INTERNET_SCAN);
            return;
        }

        permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        if(permissionCheck!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_CAMERA);
            return;
        }

        if(isOnline()) {
            moveToScan();
        }
        else{
            Toast.makeText(getApplicationContext(), "No Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void moveToScan(){
        //Toast.makeText(this, "Masuk ini", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, ScanActivity.class);
        startActivity(intent);
        overridePendingTransition (R.transition.fade_in,R.transition.fade_out);
        //finish();
    }

    public void radioButtonClicked(View v){
        if(v.getId()==R.id.rbOpening){
            eventnya="opening";
            eventTV="Opening";
        }else{
            rbOpen.setChecked(false);
        }

        if(v.getId()==R.id.rbWorshipNight){
            eventnya="worship_night";
            eventTV="Worship Night";
        }else{
            rbWorship.setChecked(false);
        }

        if(v.getId()==R.id.rbClosing){
            eventnya="closing";
            eventTV="Closing";
        }else{
            rbClose.setChecked(false);
        }

        if(v.getId()==R.id.rbIbadahMinggu){
            eventnya="ibadah_minggu";
            eventTV="Ibadah Minggu";
        }else{
            rbMinggu.setChecked(false);
        }

        if(v.getId()==R.id.rbSesi){
            eventnya="sesi";
            eventTV="Sesi " + sesiSelection;
            registerForContextMenu(v);
            openContextMenu(v);
        }else{
            rbSesi.setChecked(false);
        }

        if(v.getId()==R.id.rbBusPergi){
            prefixBus="Bus pergi , No : ";
            eventnya="bus_berangkat";
            Toast.makeText(this, eventnya, Toast.LENGTH_SHORT).show();
            eventTV = prefixBus + busSelection;
            registerForContextMenu(v);
            openContextMenu(v);
        }else{
            rbPergi.setChecked(false);
        }

        if(v.getId()==R.id.rbBusPulang){
            prefixBus="Bus pulang , No : ";
            eventnya="bus_pulang";
            eventTV = prefixBus + busSelection;
            registerForContextMenu(v);
            openContextMenu(v);
        }else{
            rbPulang.setChecked(false);
        }
        textView.setText(eventTV);
    }

}







