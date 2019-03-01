package com.rogue.gbf.gbfdemo.result;

/**
 * @author weigaosheng
 * @description
 * @CalssName Result
 * @date 2019/2/28
 * @params
 * @return
 */
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    private Result(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    private Result(CodeMsg cm) {
        if(cm == null){
            return ;
        }
        this.code = cm.getCode();
        this.msg = cm.getMsg();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    /**
     * @return a
     * @Author weigaosheng
     * @Description 成功时的调用
     * @Date 11:56 2019/2/28
    * @Param
     **/
    public static <T> Result<T> success(T data){
        return new Result<T>(data);
    }

    /**
     * @return a
     * @Author weigaosheng
     * @Description 失败时的调用
     * @Date 11:56 2019/2/28
     * @Param
     **/
    public static <T> Result<T> error(CodeMsg cm){
        return new Result<T>(cm);
    }
}
