package top.wangdf.fxbasis.common;

public class ApiResult {
    private int code;//Api 调用结果Code
    private Object data;//Api 数据解析结果对象
    private Boolean success;//Api 是否是否调用成功
    private Throwable t;//Api 调用调用过程中出现的异常
    private String Message;//Api 调用过程返回的Message
}