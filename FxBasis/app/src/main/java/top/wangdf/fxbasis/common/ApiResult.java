package top.wangdf.fxbasis.common;

public class ApiResult {
    private int code;//Api 调用结果Code
    private Object data;//Api 数据解析结果对象
    private Boolean success;//Api 是否是否调用成功
    private Throwable t;//Api 调用调用过程中出现的异常
    private String Message;//Api 调用过程返回的Message

    public ApiResult() {
    }

    public ApiResult(int code, Object data, Boolean success, Throwable t, String message) {
        this.code = code;
        this.data = data;
        this.success = success;
        this.t = t;
        Message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Throwable getT() {
        return t;
    }

    public void setT(Throwable t) {
        this.t = t;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}