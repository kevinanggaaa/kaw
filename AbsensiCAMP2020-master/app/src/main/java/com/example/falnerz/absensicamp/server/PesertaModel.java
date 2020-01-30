package com.example.falnerz.absensicamp.server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PesertaModel {
    @SerializedName("id")
    @Expose
    private String id;
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
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("jenis_kelamin")
    @Expose
    private String jenisKelamin;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("departemen")
    @Expose
    private String departemen;
    @SerializedName("posisi")
    @Expose
    private String posisi;
    @SerializedName("nama_pkk")
    @Expose
    private String namaPkk;
    @SerializedName("alergi")
    @Expose
    private Object alergi;
    @SerializedName("penyakit")
    @Expose
    private Object penyakit;
    @SerializedName("hp")
    @Expose
    private String hp;
    @SerializedName("hp_ortu")
    @Expose
    private String hpOrtu;
    @SerializedName("line")
    @Expose
    private String line;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("bukti_transfer")
    @Expose
    private String buktiTransfer;
    @SerializedName("konfirmasi")
    @Expose
    private String konfirmasi;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartemen() {
        return departemen;
    }

    public void setDepartemen(String departemen) {
        this.departemen = departemen;
    }

    public String getPosisi() {
        return posisi;
    }

    public void setPosisi(String posisi) {
        this.posisi = posisi;
    }

    public String getNamaPkk() {
        return namaPkk;
    }

    public void setNamaPkk(String namaPkk) {
        this.namaPkk = namaPkk;
    }

    public Object getAlergi() {
        return alergi;
    }

    public void setAlergi(Object alergi) {
        this.alergi = alergi;
    }

    public Object getPenyakit() {
        return penyakit;
    }

    public void setPenyakit(Object penyakit) {
        this.penyakit = penyakit;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getHpOrtu() {
        return hpOrtu;
    }

    public void setHpOrtu(String hpOrtu) {
        this.hpOrtu = hpOrtu;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getBuktiTransfer() {
        return buktiTransfer;
    }

    public void setBuktiTransfer(String buktiTransfer) {
        this.buktiTransfer = buktiTransfer;
    }

    public String getKonfirmasi() {
        return konfirmasi;
    }

    public void setKonfirmasi(String konfirmasi) {
        this.konfirmasi = konfirmasi;
    }
}
