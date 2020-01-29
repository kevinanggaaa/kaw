package com.example.falnerz.absensicamp.server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PesertaModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nrp")
    @Expose
    private String nrp;
    @SerializedName("berangkat")
    @Expose
    private String berangkat;
    @SerializedName("bus_berangkat")
    @Expose
    private String busBerangkat;
    @SerializedName("opening")
    @Expose
    private String opening;
    @SerializedName("sesi_1")
    @Expose
    private String sesi1;
    @SerializedName("sesi_2")
    @Expose
    private String sesi2;
    @SerializedName("sesi_3")
    @Expose
    private String sesi3;
    @SerializedName("sesi_4")
    @Expose
    private String sesi4;
    @SerializedName("sesi_5")
    @Expose
    private String sesi5;
    @SerializedName("worship_night")
    @Expose
    private String worshipNight;
    @SerializedName("ibadah_minggu")
    @Expose
    private String ibadahMinggu;
    @SerializedName("closing")
    @Expose
    private String closing;
    @SerializedName("bus_pulang")
    @Expose
    private String busPulang;
    @SerializedName("pulang")
    @Expose
    private String pulang;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNrp() {
        return nrp;
    }

    public void setNrp(String nrp) {
        this.nrp = nrp;
    }

    public String getBerangkat() {
        return berangkat;
    }

    public void setBerangkat(String berangkat) {
        this.berangkat = berangkat;
    }

    public String getBusBerangkat() {
        return busBerangkat;
    }

    public void setBusBerangkat(String busBerangkat) {
        this.busBerangkat = busBerangkat;
    }

    public String getOpening() {
        return opening;
    }

    public void setOpening(String opening) {
        this.opening = opening;
    }

    public String getSesi1() {
        return sesi1;
    }

    public void setSesi1(String sesi1) {
        this.sesi1 = sesi1;
    }

    public String getSesi2() {
        return sesi2;
    }

    public void setSesi2(String sesi2) {
        this.sesi2 = sesi2;
    }

    public String getSesi3() {
        return sesi3;
    }

    public void setSesi3(String sesi3) {
        this.sesi3 = sesi3;
    }

    public String getSesi4() {
        return sesi4;
    }

    public void setSesi4(String sesi4) {
        this.sesi4 = sesi4;
    }

    public String getSesi5() {
        return sesi5;
    }

    public void setSesi5(String sesi5) {
        this.sesi5 = sesi5;
    }

    public String getWorshipNight() {
        return worshipNight;
    }

    public void setWorshipNight(String worshipNight) {
        this.worshipNight = worshipNight;
    }

    public String getIbadahMinggu() {
        return ibadahMinggu;
    }

    public void setIbadahMinggu(String ibadahMinggu) {
        this.ibadahMinggu = ibadahMinggu;
    }

    public String getClosing() {
        return closing;
    }

    public void setClosing(String closing) {
        this.closing = closing;
    }

    public String getBusPulang() {
        return busPulang;
    }

    public void setBusPulang(String busPulang) {
        this.busPulang = busPulang;
    }

    public String getPulang() {
        return pulang;
    }

    public void setPulang(String pulang) {
        this.pulang = pulang;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
