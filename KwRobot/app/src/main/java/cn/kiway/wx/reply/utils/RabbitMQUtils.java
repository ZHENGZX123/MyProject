package cn.kiway.wx.reply.utils;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import cn.kiway.wx.reply.vo.PushMessageVo;

/**
 * rabbitMQ 工具类
 * @author yimin
 * @date 2018/03/21
 */
public class RabbitMQUtils {

    private static final String EXCHANGE_NAME = "wx-reply-exchanger";

    private String routingKey = "";

    private String queueName = "";

    private Connection connection = null ;

    private Channel channel = null;

    private static Logger logger = LoggerFactory.getLogger(RabbitMQUtils.class);

    public RabbitMQUtils(String url ,String routingKey,String queueName,Integer port) {
        this.routingKey = routingKey;
        this.queueName = queueName;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(url);
        if(port == null){
            factory.setPort(5672);
        }else{
            factory.setPort(port);
        }
        //105.196用的
//        factory.setPort(5672);
        //robot.kiway.cn用
//        factory.setPort(5676);
        factory.setUsername("pig");
        factory.setPassword("lengleng");
        factory.setVirtualHost("/");

        //设置网络异常重连
        factory.setAutomaticRecoveryEnabled(true);
        // 设置 每5s ，重试一次
        factory.setNetworkRecoveryInterval(5000);
        // 设置不重新声明交换器，队列等信息
        factory.setTopologyRecoveryEnabled(false);

        try {
            connection =  factory.newConnection();

            channel = connection.createChannel();

            //每次取1条消息
            channel.basicQos(1);

            channel.exchangeDeclare(EXCHANGE_NAME,"direct",true);
            channel.queueDeclare(queueName,false,false,false,null);
            channel.queueBind(queueName,EXCHANGE_NAME,routingKey);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public RabbitMQUtils(String url ,Integer port) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(url);
        if(port == null){
            factory.setPort(5672);
        }else{
            factory.setPort(port);
        }
        //105.196用的
//        factory.setPort(5672);
        //robot.kiway.cn用
//        factory.setPort(5676);
        factory.setUsername("pig");
        factory.setPassword("lengleng");
        factory.setVirtualHost("/");

        //设置网络异常重连
        factory.setAutomaticRecoveryEnabled(true);
        // 设置 每5s ，重试一次
        factory.setNetworkRecoveryInterval(5000);
        // 设置不重新声明交换器，队列等信息
        factory.setTopologyRecoveryEnabled(false);

        try {
            connection =  factory.newConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Channel createChannel(String routingKey,String queueName){
        this.routingKey = routingKey;
        this.queueName = queueName;
        Channel channel = null;
        try {
            channel = connection.createChannel();
            //每次取1条消息
            channel.basicQos(1);

            channel.exchangeDeclare(EXCHANGE_NAME,"direct",true);
//            //设置过期时间
//            Map<String, Object> args = new HashMap<String, Object>();
//            args.put("x-message-ttl", 60000);
//
//            channel.queueDeclare(queueName,false,false,false,args);

            channel.queueDeclare(queueName,false,false,false,null);
            channel.queueBind(queueName,EXCHANGE_NAME,routingKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return channel;
    }


    /**
     * 发送消息
     * @throws IOException
     * @throws InterruptedException
     */
    public void sendMsg(PushMessageVo vo) throws Exception {
        String msg = JSONObject.toJSONString(vo);
        //发送消息
        channel.basicPublish(EXCHANGE_NAME,routingKey,null,msg.getBytes());
    }

    /**
     * 发送消息
     * @throws IOException
     * @throws InterruptedException
     */
    public void sendMsg(PushMessageVo vo,Channel channel) throws Exception {
        String msg = JSONObject.toJSONString(vo);
        //发送消息
        channel.basicPublish(EXCHANGE_NAME,routingKey,null,msg.getBytes());
        System.out.println("sendMsg success....");
    }

    /**
     * 发送消息
     * @throws IOException
     * @throws InterruptedException
     */
    public void sendMsgs(String message) throws Exception {
        // 获取json文件内容
        channel.basicPublish(EXCHANGE_NAME,routingKey,null,message.getBytes());
        System.out.println("send message success....");
    }

    /**
     * 发送消息
     * @throws IOException
     * @throws InterruptedException
     */
    public void sendMsgs(String message,Channel channel) throws Exception {
        // 获取json文件内容
        channel.basicPublish(EXCHANGE_NAME,routingKey,null,message.getBytes());
        System.out.println("sendMsgs success....");
    }

    /***
     * 收取消息
     * @param consumer 消息处理类
     * @throws IOException
     */
    public void consumeMsg(Consumer consumer) throws Exception {
//        Consumer consumer = new DefaultConsumer(channel){
//            @Override
//            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                //消费消费
//                String msg = new String(body,"utf-8");
//                System.out.println("consume msg: "+msg);
//                //处理逻辑

//                //手动消息确认
//                getChannel().basicAck(envelope.getDeliveryTag(),false);
//            }
//        };

        //调用消费消息
        channel.basicConsume(queueName,false,EXCHANGE_NAME,consumer);
    }

    /***
     * 收取消息
     * @param consumer 消息处理类
     * @throws IOException
     */
    public void consumeMsg(Consumer consumer,Channel channel) throws Exception {
        //调用消费消息
        channel.basicConsume(queueName,false,EXCHANGE_NAME,consumer);
    }


    /**
     * 关闭
     */
    public void close(){
        try {
            if(connection != null){
                connection.close();
            }

//            if(channel != null){
//                channel.abort();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
