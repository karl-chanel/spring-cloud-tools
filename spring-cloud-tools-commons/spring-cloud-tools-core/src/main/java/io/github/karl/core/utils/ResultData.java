package io.github.karl.core.utils;

import lombok.Data;

/**
 * @author jasper
 * @email jaspersteelxx@gmail.com
 * @create 2023-11-01 12:27
 **/
@Data
public class ResultData<T> {
    /** 结果状态 ,具体状态码参见ResultData.java*/
    private int status;
    private String message;
    private T data;
    private long timestamp ;


    public ResultData (){
        this.timestamp = System.currentTimeMillis();
    }


    public static <T> ResultData<T> success(T data) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(ReturnCode.RC100.getCode());
        resultData.setMessage(ReturnCode.RC100.getMessage());
        resultData.setData(data);
        return resultData;
    }
    public static <T> ResultData<T> success() {
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(ReturnCode.RC100.getCode());
        resultData.setMessage(ReturnCode.RC100.getMessage());
        resultData.setData(null);
        return resultData;
    }


    public static <T> ResultData<T> fail(int code, String message) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(code);
        resultData.setMessage(message);
        return resultData;
    }

}