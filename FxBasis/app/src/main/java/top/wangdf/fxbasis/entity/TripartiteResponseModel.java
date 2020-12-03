package top.wangdf.fxbasis.entity;

public class TripartiteResponseModel {
    public TripartiteResponseModel() {
    }

    public TripartiteResponseModel(String code, TripartSubResponse data) {
        this.code = code;
        this.data = data;
    }

    private String code;
    private TripartSubResponse data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public TripartSubResponse getData() {
        return data;
    }

    public void setData(TripartSubResponse data) {
        this.data = data;
    }
}
