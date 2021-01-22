package com.example.qrcode.data;

public class Data_Absen {
    private String id_absen, tgl_absensi, jam_masuk,jam_keluar,status;

    public Data_Absen() {
    }

    public Data_Absen(String id_absen, String tgl_absensi, String jam_masuk, String jam_keluar, String status) {
        this.id_absen = id_absen;
        this.tgl_absensi = tgl_absensi;
        this.jam_masuk = jam_masuk;
        this.jam_keluar = jam_keluar;
        this.status = status;
    }

    public String getId() {
        return id_absen;
    }

    public void setId(String id_absen) {
        this.id_absen = id_absen;
    }

    public String getTgl_absensi() {
        return tgl_absensi;
    }

    public void setTgl_absensi(String tgl_absensi) {
        this.tgl_absensi = tgl_absensi;
    }

    public String getJam_masuk() {
        return jam_masuk;
    }

    public void setJam_masuk(String jam_masuk) {
        this.jam_masuk = jam_masuk;
    }

    public String getJam_keluar() {
        return jam_keluar;
    }

    public void setJam_keluar(String jam_keluar) {
        this.jam_keluar = jam_keluar;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
