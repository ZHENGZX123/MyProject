package com.zk.myweex.mqttclient.mq;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public class LoginErrorCbk implements TopicProcessService{

	@Override
	public void process(String topic, MqttMessage message, String time) {
		System.out.println("登录失败");
		System.out.println(topic);
		System.out.println(time);
	}

}
