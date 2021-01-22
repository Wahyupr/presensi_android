package com.example.qrcode.data;


public class Data_Jurusan {
    private String id_jurusan, jurusan;

    public Data_Jurusan() {
    }

    public Data_Jurusan(String id_jurusan, String jurusan) {
        this.id_jurusan = id_jurusan;
        this.jurusan = jurusan;
    }

    public String getIdJurusan() {
        return id_jurusan;
    }

    public void setIdJurusan(String id_jurusan) {
        this.id_jurusan = id_jurusan;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

}
