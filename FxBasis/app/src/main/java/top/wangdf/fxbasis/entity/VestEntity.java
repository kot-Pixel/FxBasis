package top.wangdf.fxbasis.entity;

/*马甲请求域名：api.waizhangkuaiji.com
马甲code：Q11KF9MU*/
/*
vestCode: 马甲代码
        channelCode: 渠道代码(谷歌市场现在可以写死google)
        version: App当前版本名(例如1.0.0)
        deviceId: 设备ID(设备唯一id)
        timestamp: 时间戳(系统时间)*/
public class VestEntity {

    private String vestCode;

    private String version;

    private String devicesId;

    private String timestamp;

    private String channelCode;

    public VestEntity(String vestCode, String version, String devicesId, String timestamp, String channelCode) {
        this.vestCode = vestCode;
        this.version = version;
        this.devicesId = devicesId;
        this.timestamp = timestamp;
        this.channelCode = channelCode;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getVestCode() {
        return vestCode;
    }

    public void setVestCode(String vestCode) {
        this.vestCode = vestCode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDevicesId() {
        return devicesId;
    }

    public void setDevicesId(String devicesId) {
        this.devicesId = devicesId;
    }

    @Override
    public String toString() {
        return "VestPostEntity{" +
                ", vestCode='" + vestCode + '\'' +
                ", version='" + version + '\'' +
                ", devicesId='" + devicesId + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public VestEntity(String vestCode, String version, String devicesId, String timestamp) {
        this.vestCode = vestCode;
        this.version = version;
        this.devicesId = devicesId;
        this.timestamp = timestamp;
    }
}
