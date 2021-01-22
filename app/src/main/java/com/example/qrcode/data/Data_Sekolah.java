package com.example.qrcode.data;


public class Data_Sekolah {
    private String id_sekolah, nama_sekolah;

    public Data_Sekolah() {
    }

    public Data_Sekolah(String id_sekolah, String nama_sekolah) {
        this.id_sekolah = id_sekolah;
        this.nama_sekolah = nama_sekolah;
    }

    public String getId() {
        return id_sekolah;
    }

    public void setId(String id_sekolah) {
        this.id_sekolah = id_sekolah;
    }

    public String getSekolah() {
        return nama_sekolah;
    }

    public void setSekolah(String nama_sekolah) {
        this.nama_sekolah = nama_sekolah;
    }

}
