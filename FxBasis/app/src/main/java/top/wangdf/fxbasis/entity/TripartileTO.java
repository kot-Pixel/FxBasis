package top.wangdf.fxbasis.entity;

public class TripartileTO {
    //{"sign":"781h18fn1u34n","host":"https://bb.skr.today"}
    private String sign;
    private String host;

    public TripartileTO() {
    }

    public TripartileTO(String sign, String host) {
        this.sign = sign;
        this.host = host;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
