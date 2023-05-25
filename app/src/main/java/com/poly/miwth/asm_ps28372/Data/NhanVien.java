package com.poly.miwth.asm_ps28372.Data;

import java.io.Serializable;

public class NhanVien implements Serializable {
    private String phongBan;
    private String hoTen;
    private String maNV;

    public NhanVien(String phongBan, String hoTen, String maNV) {
        this.phongBan = phongBan;
        this.hoTen = hoTen;
        this.maNV = maNV;
    }

    public String getPhongBan() {
        return phongBan;
    }

    public void setPhongBan(String phongBan) {
        this.phongBan = phongBan;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }
}
