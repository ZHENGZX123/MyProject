package cn.kwim.mqttcilent.mqttclient.mq;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.HashMap;
import java.util.Map;

public class RegTopicProcSrv implements TopicProcessService {
	public final static RegTopicProcSrv INSTANCE = new RegTopicProcSrv();
	private Map<String,TopicProcessService> cbks=new HashMap<String,TopicProcessService>();
	public void subscribe(String topicName,TopicProcessService tps) {
		cbks.put(topicName, tps);
	}
	@Override
	public void process(String topic, MqttMessage message, String time) {
    	if(cbks.containsKey(topic)){
    		TopicProcessService tps=cbks.get(topic);
    		 tps.process(topic, message, time);    		
        }else{
        	System.out.println("topic="+topic+"----"+time);
        }
		
	}

}
