package top.wangdf.fxbasis.entity;

public class PushMessage {
    private PushSubMessage data;

    @Override
    public String toString() {
        return "PushMessage{" +
                "data=" + data +
                '}';
    }

    public PushSubMessage getData() {
        return data;
    }

    public void setData(PushSubMessage data) {
        this.data = data;
    }

    public PushMessage(PushSubMessage data) {
        this.data = data;
    }
}
