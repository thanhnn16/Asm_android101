package com.poly.miwth.asm_ps28372.Object;

public class NhanVienObject {
    private String maNV;
    private String hoTenNV;
    private String phongBan;

    public NhanVienObject(String maNV, String hoTenNV, String phongBan) {
        this.maNV = maNV;
        this.hoTenNV = hoTenNV;
        this.phongBan = phongBan;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getHoTenNV() {
        return hoTenNV;
    }

    public void setHoTenNV(String hoTenNV) {
        this.hoTenNV = hoTenNV;
    }

    public String getPhongBan() {
        return phongBan;
    }

    public void setPhongBan(String phongBan) {
        this.phongBan = phongBan;
    }
}
