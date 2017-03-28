package cn.kwim.mqttcilent.common.cache.javabean;


/**
 * Created by Administrator on 2017/1/6.
 */

public class Converse {
    private String StatusCode;
    private Object data;

    public String getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(String statusCode) {
        StatusCode = statusCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Converse{" +
                "StatusCode='" + StatusCode + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
