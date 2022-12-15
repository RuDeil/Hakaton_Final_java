package com.example.hakatonfinaljava.responses;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class BossResponse extends BaseResponse implements Serializable {
    public ArrayList<Boss> getList() {
        return list;
    }

    public void setList(ArrayList<Boss> list) {
        this.list = list;
    }

    private  ArrayList<Boss> list = new ArrayList<Boss>();





}
