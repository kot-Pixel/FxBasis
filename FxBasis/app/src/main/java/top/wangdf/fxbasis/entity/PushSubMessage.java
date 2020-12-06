package top.wangdf.fxbasis.entity;

/**
 * @description: 消息推送TO Model
 * @author WDF
 */

public class PushSubMessage {
    private String createTime;
    private String pushTopic;
    private String pushContent;
    private String url;

    public PushSubMessage(String createTime, String pushTopic, String pushContent, String url) {
        this.createTime = createTime;
        this.pushTopic = pushTopic;
        this.pushContent = pushContent;
        this.url = url;
    }

    public PushSubMessage() {
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPushTopic() {
        return pushTopic;
    }

    public void setPushTopic(String pushTopic) {
        this.pushTopic = pushTopic;
    }

    public String getPushContent() {
        return pushContent;
    }

    public void setPushContent(String pushContent) {
        this.pushContent = pushContent;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
