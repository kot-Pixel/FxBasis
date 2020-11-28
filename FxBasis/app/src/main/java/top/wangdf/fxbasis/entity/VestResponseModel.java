package top.wangdf.fxbasis.entity;

/**
 * {
 * 	"code": 200,
 * 	"data": {
 * 			"adjustToken": "11",
 * 			"umKey": "11",
 * 			"fieldCol": "black", //H5的状态栏和titlebar的字体颜色 fieldCol  white(白) ,black(黑)
 * 			"gtSecert": "11",
 * 			"screenDirect": 0,
 * 			"umkeyIOS": "11",
 * 			"version": "1.1,1.2",
 * 			"h5Url": "https://app.zzwpx.com/", //H5业务链接地址
 * 			"gtMaster": "11",
 * 			"gtKey": "11",
 * 			"backgroundCol": "#FFFFFF", //H5的状态栏和titlebar的背景色
 * 			"advOn": 0, //1显示广告
 * 			"advImg": "https://app.zzwpx.com/", //广告图片地址
 * 			"advUrl": "https://app.zzwpx.com/", //广告业务链接地址
 * 			"gtId": "11",
 * 			"um": "11",
 * 			"anUmengKey": "11", //安卓友盟key
 * 			"channelName": "google",
 * 			"screenOn": 1,
 * 			"vestCode": "MMVUT5DX",
 * 			"vestName": "KomTure",
 * 			"channelCode": "google",
 * 			"status": 0 //0显示H5地址
 *        },
 * 	"msg": "成功"
 * }
 */
public class VestResponseModel {
    private String code;
    private String msg;
    private VestResponseSubModel data;

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

    public VestResponseSubModel getData() {
        return data;
    }

    public void setData(VestResponseSubModel data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "VestResponseModel{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
