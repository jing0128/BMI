package com.example.bmiapplication.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class Bmi {
    private String name;
    private double height;
    private double weight;
    private double bmi;

    public Bmi(String name, double height, double weight) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        setBmi();
    }

    private void setBmi() {// Math.pow(7,2)=49
        bmi = weight / (Math.pow(height / 100, 2));//輸入的是公分 所以要除100才是公尺
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public JSONObject getJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("height", height);
            jsonObject.put("weight", weight);
            jsonObject.put("bmi", bmi);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
