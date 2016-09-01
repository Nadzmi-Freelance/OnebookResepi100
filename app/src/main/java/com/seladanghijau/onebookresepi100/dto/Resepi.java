package com.seladanghijau.onebookresepi100.dto;

import android.graphics.Bitmap;
import android.util.Pair;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by seladanghijau on 7/8/2016.
 */
public class Resepi {
    private int id, category;
    private String name, ringkasan;
    private Bitmap resepiImg;
    private String[] langkah;
    private ArrayList<Pair<String, String>> bahan;
    private Bitmap[] bahanImg;

    // constructors
    public Resepi(int id, String name, String ringkasan, int category, Bitmap resepiImg, String[] langkah, ArrayList<Pair<String, String>> bahan, Bitmap[] bahanImg) {
        this.id = id; // resepi id
        this.name = name; // resepi category
        this.ringkasan = ringkasan; // resepi name
        this.category = category; // resepi ringkasan
        this.resepiImg = resepiImg; // resepi img
        this.langkah = langkah; // resepi langkah
        this.bahan = bahan; // resepi bahan pair -> bahanDesc:bahanName
        this.bahanImg = bahanImg; // resepi bahan img
    }

    public Resepi(int id, String name, Bitmap resepiImg) {
        this.id = id;
        this.name = name;
        this.resepiImg = resepiImg;
    }

    public Resepi(int id, String name, int category, Bitmap resepiImg) {
        this.id = id;
        this.name = name;
        this.resepiImg = resepiImg;
        this.category = category;
    }

    // setter & getter
    public int getId() { return id; }
    public int getCategory() { return category; }
    public String getName() { return name; }
    public String getRingkasan() { return ringkasan; }
    public Bitmap getResepiImg() { return resepiImg; }
    public String[] getLangkah() { return langkah; }
    public ArrayList<Pair<String, String>> getBahan() { return bahan; }
    public Bitmap[] getBahanImg() { return bahanImg; }

    public void setId(int id) { this.id = id; }
    public void setCategory(int category) { this.category = category; }
    public void setName(String name) { this.name = name; }
    public void setRingkasan(String ringkasan) { this.ringkasan = ringkasan; }
    public void setResepiImg(Bitmap resepiImg) { this.resepiImg = resepiImg; }
    public void setLangkah(String[] langkah) { this.langkah = langkah; }
    public void setBahan(ArrayList<Pair<String, String>> bahan) { this.bahan = bahan; }
    public void setBahanImg(Bitmap[] bahanImg) { this.bahanImg = bahanImg; }
}
