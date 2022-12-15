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
/*200 OK
 {
    "username": "Ўакал Ўакальевич Ўакальский",
    "status": "online",
    "phone": "79782314343",
    "user": 1,
    "user_id": "123",
    "date": "13-12-2022 17:23",
},
{
    "username": " альмар Ўухеевич ∆ивомЄртво",
    "status": "offline",
    "phone": "79782384023",
    "user": 2,
    "user_id": "123",
    "date": "13-12-2022 17:23"
},
{
    "username": " альмар Ўухеевич ∆ивомЄртво",
    "status": "alert",
    "user": 0,
    "user_id": "123",
    "date": "13-12-2022 17:23"
}

 */
