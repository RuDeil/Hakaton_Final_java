package com.example.hakatonfinaljava.responses;

import java.io.Serializable;

public class BaseResponse  implements Serializable {
    private Integer result;
    private String description;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
