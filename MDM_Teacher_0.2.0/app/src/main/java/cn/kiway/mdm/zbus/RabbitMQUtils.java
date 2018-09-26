//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.kiway.mdm.zbus;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class RabbitMQUtils {
    private static final String EXCHANGE_NAME = "wx-reply-exchanger";
    private String routingKey = "";
    private String queueName = "";
    private Connection connection = null;
    private Channel channel = null;
    private static Logger logger = LoggerFactory.getLogger(RabbitMQUtils.class);

    public RabbitMQUtils(String url, String routingKey, String queueName, Integer port) {
        this.routingKey = routingKey;
        this.queueName = queueName;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(url);
        if(port == null) {
            factory.setPort(5672);
        } else {
            factory.setPort(port.intValue());
        }

        factory.setUsername("pig");
        factory.setPassword("lengleng");
        factory.setVirtualHost("/");
        factory.setAutomaticRecoveryEnabled(true);
        factory.setTopologyRecoveryEnabled(true);

        try {
            this.connection = factory.newConnection();
            this.channel = this.connection.createChannel();
            this.channel.basicQos(1);
            this.channel.exchangeDeclare("wx-reply-exchanger", "direct", true);
            this.channel.queueDeclare(queueName, false, false, false, (Map)null);
            this.channel.queueBind(queueName, "wx-reply-exchanger", routingKey);
        } catch (Exception var7) {
            var7.printStackTrace();
        }

    }

    public RabbitMQUtils(String url, Integer port) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(url);
        if(port == null) {
            factory.setPort(5672);
        } else {
            factory.setPort(port.intValue());
        }

        factory.setUsername("pig");
        factory.setPassword("lengleng");
        factory.setVirtualHost("/");
        factory.setAutomaticRecoveryEnabled(true);
        factory.setTopologyRecoveryEnabled(true);

        try {
            this.connection = factory.newConnection();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public Channel createChannel(String routingKey, String queueName) {
        this.routingKey = routingKey;
        this.queueName = queueName;
        Channel channel = null;

        try {
            channel = this.connection.createChannel();
            channel.basicQos(1);
            channel.exchangeDeclare("wx-reply-exchanger", "direct", true);
            channel.queueDeclare(queueName, false, false, false, (Map)null);
            channel.queueBind(queueName, "wx-reply-exchanger", routingKey);
        } catch (IOException var5) {
            var5.printStackTrace();
        }

        return channel;
    }

    public void sendMsg(PushMessageVo vo) throws Exception {
        String msg = JSONObject.toJSONString(vo);
        this.channel.basicPublish("wx-reply-exchanger", this.routingKey, (BasicProperties)null, msg.getBytes());
    }

    public void sendMsg(PushMessageVo vo, Channel channel) throws Exception {
        String msg = JSONObject.toJSONString(vo);
        channel.basicPublish("wx-reply-exchanger", this.routingKey, (BasicProperties)null, msg.getBytes());
    }

    public void sendMsgs(String message) throws Exception {
        this.channel.basicPublish("wx-reply-exchanger", this.routingKey, (BasicProperties)null, message.getBytes());
        System.out.println("send message success....");
    }

    public void sendMsgs(String message, Channel channel) throws Exception {
        channel.basicPublish("wx-reply-exchanger", this.routingKey, (BasicProperties)null, message.getBytes());
        System.out.println("send message success....");
    }

    public void consumeMsg(Consumer consumer) throws Exception {
        this.channel.basicConsume(this.queueName, false, "wx-reply-exchanger", consumer);
    }

    public void consumeMsg(Consumer consumer, Channel channel) throws Exception {
        channel.basicConsume(this.queueName, false, "wx-reply-exchanger", consumer);
    }

    public void close() {
        try {
            if(this.connection != null) {
                this.connection.close();
            }
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    public Channel getChannel() {
        return this.channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
