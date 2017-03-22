package mqttclient.mq;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface TopicProcessService {
	public void process(String topic, MqttMessage message, String time);
	
}
