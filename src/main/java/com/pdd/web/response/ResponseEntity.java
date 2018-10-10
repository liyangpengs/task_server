package com.pdd.web.response;

/**
 * @author:liyangpeng
 * @date:2018/10/8 18:07
 */
public class ResponseEntity {
    private String code;
    private String msg;
    private boolean isSuccess;
    private Object data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ResponseEntity success(){
        ResponseEntity entiry=new ResponseEntity();
        entiry.setCode("0000");
        entiry.setSuccess(true);
        return entiry;
    }
    public static ResponseEntity success(String msg){
        ResponseEntity entiry=new ResponseEntity();
        entiry.setCode("0000");
        entiry.setSuccess(true);
        entiry.setMsg(msg);
        return entiry;
    }
    public static ResponseEntity success(Object data){
        ResponseEntity entiry=new ResponseEntity();
        entiry.setCode("0000");
        entiry.setSuccess(true);
        entiry.setData(data);
        return entiry;
    }
    public static ResponseEntity success(String msg, Object data){
        ResponseEntity entiry=new ResponseEntity();
        entiry.setCode("0000");
        entiry.setSuccess(true);
        entiry.setData(data);
        entiry.setMsg(msg);
        return entiry;
    }

    public static ResponseEntity error(String code){
        ResponseEntity entiry=new ResponseEntity();
        entiry.setCode(code);
        entiry.setSuccess(false);
        return entiry;
    }
    public static ResponseEntity error(String code, String msg){
        ResponseEntity entiry=new ResponseEntity();
        entiry.setCode(code);
        entiry.setSuccess(false);
        entiry.setMsg(msg);
        return entiry;
    }
    public static ResponseEntity error(String code, Object data){
        ResponseEntity entiry=new ResponseEntity();
        entiry.setCode(code);
        entiry.setSuccess(false);
        entiry.setData(data);
        return entiry;
    }
    public static ResponseEntity error(String code, String msg, Object data){
        ResponseEntity entiry=new ResponseEntity();
        entiry.setCode(code);
        entiry.setSuccess(false);
        entiry.setData(data);
        entiry.setMsg(msg);
        return entiry;
    }
}
