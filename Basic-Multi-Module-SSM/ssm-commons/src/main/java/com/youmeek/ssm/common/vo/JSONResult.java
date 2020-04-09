package com.youmeek.ssm.common.vo;

public class JSONResult {
    
    private String message;
    private String result;
    private String success;

    public JSONResult() {
    }

    public JSONResult(String message, String result, String success) {
        this.message = message;
        this.result = result;
        this.success = success;
    }



    public JSONResult(String message, String success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}