package com.zk.myweex.mqttclient.mq;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class KwMqttCli implements MqttCallback {
	//public final static KwMqttCli INSTANCE = new KwMqttCli(Settings.INSTANCE.clientid());
	private MqttClient client=null;
	private MqttConnectOptions options;
	private String clientid;
	private String username;
	private String passwd;
	private int recode=0;
	private String remsg="";
	private TopicProcessService loginerrcbk=new LoginErrorCbk();
    private Set<String> cbkwildcard=new HashSet<String>();
    private Map<String,TopicProcessService> cbks=new HashMap<String,TopicProcessService>();
    public KwMqttCli(int i){
		clientid= Conf.getInstance().getId()+i;
		username=Conf.getInstance().getuserName();
		passwd=Conf.getInstance().getPasswd();
    	init(clientid);
    }
    public KwMqttCli(int i, String u, String p){
    	clientid=Conf.getInstance().getId()+i;
    	username=u;
    	passwd=p;
    	init(clientid);
    }
    
    public KwMqttCli(int i, String u, String p, TopicProcessService lcbk){
    	clientid=Conf.getInstance().getId()+i;
    	username=u;
    	passwd=p;
    	init(clientid);
    	loginerrcbk=lcbk;
    }
    
    public KwMqttCli(String id){
    	clientid=id;
    	username=Conf.getInstance().getuserName();
    	passwd=Conf.getInstance().getPasswd();
    	init(clientid);
    }
	public TopicProcessService getLoginerrCbk(){
		return loginerrcbk;
	}
    public String getId(){
    	return clientid;
    }
    public String getUserName(){
    	return username;
    }
    public int getRecode(){
    	return recode;
    }
    
    public String getRemsg(){
    	return remsg;
    }
    
    private void init(String clientid){
    	if(client==null)
        	try {
            	client = new MqttClient("tcp://"+Conf.getInstance().getHost()+":"+Conf.getInstance().getPort(), clientid, new MemoryPersistence());
                client.setCallback(this);
                options = new MqttConnectOptions();
                options.setCleanSession(true);
                options.setUserName(username);
                options.setPassword(passwd.toCharArray());
                options.setConnectionTimeout(300);
                options.setKeepAliveInterval(10);
                conn();
                client.subscribe(clientid+"/+/#",2);
            }catch (MqttException e) {
            	recode=e.getReasonCode();
            	remsg=e.getMessage();
            	loginerrcbk.process(remsg,null,""+recode);
            }
    }
    private void conn() {
    	try {
        	client.connect(options);
        } catch (MqttException e) {
        	recode=e.getReasonCode();
        	remsg=e.getMessage();
        	loginerrcbk.process(remsg,null,""+recode);
        }
    }
    
    public void publish(String topic, byte[] data) {
    	try {
    		if(connect())client.publish(topic, data, 2, false);    		
    	} catch (MqttException e) {
            
        }
    	
    }
    public void unsubscribe(String topicName) {
    	try {
    		if(connect()){
    			client.unsubscribe(topicName);
    		}    		
    	} catch (MqttException e) {
            e.printStackTrace();
        }
    	
    }

    public void subscribe(String topicName) {
    	try {
    		if(connect()){
    			client.subscribe(topicName,2);
    		}    		
    	} catch (MqttException e) {
            e.printStackTrace();
        }
    	
    }
    
    public void subscribe(String topicName, TopicProcessService tps) {
    	try {
    		if(connect()){
    			client.subscribe(topicName,2);
    			if((topicName.indexOf("#")>0)
    					||(topicName.indexOf("+")>0))cbkwildcard.add(topicName);
    			cbks.put(topicName, tps);
    		}
    		
    	} catch (MqttException e) {
            e.printStackTrace();
        }
    	
    }
    
    public boolean connect() {
    	while (!client.isConnected()&&(recode!=4)){        
        	try {
					Thread.sleep(5000);
			} catch (InterruptedException e) {
			}        	
        	conn();
        }
    	return client.isConnected();
    }
    public void close(){
    	try {
			if(client.isConnected())client.disconnect();
		} catch (MqttException e) {
			
		}
    }
    
    @Override
    public void connectionLost(Throwable msg) {
    	System.out.println(msg.getMessage());
    	connect();
    }

	@Override
	public void deliveryComplete(IMqttDeliveryToken imtk) {
		switch(imtk.getResponse().getType()){
			case MqttWireMessage.MESSAGE_TYPE_CONNACK:
				System.out.println(""+imtk.getMessageId()+"MESSAGE_TYPE_CONNACK");
			break;
			case MqttWireMessage.MESSAGE_TYPE_CONNECT:
				System.out.println(""+imtk.getMessageId()+"MESSAGE_TYPE_CONNECT");
			break;
			case MqttWireMessage.MESSAGE_TYPE_PUBACK:
				System.out.println(""+imtk.getMessageId()+"MESSAGE_TYPE_PUBACK");
			break;
			case MqttWireMessage.MESSAGE_TYPE_PUBCOMP:
				System.out.println(""+imtk.getMessageId()+"MESSAGE_TYPE_PUBCOMP");
			break;
			case MqttWireMessage.MESSAGE_TYPE_PUBREC:
				System.out.println(""+imtk.getMessageId()+"MESSAGE_TYPE_PUBREC");
			break;
			default:
				System.out.println(""+imtk.getMessageId()+"    "+imtk.getResponse().getType());
		}
        
    }

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		String time = new Timestamp(System.currentTimeMillis()).toString();
		System.out.println(topic+"  "+message.hashCode()+"    "+time);
    	if(cbks.containsKey(topic)){
    		TopicProcessService tps=cbks.get(topic);
    		 tps.process(topic, message, time);    		
        }else{
        	boolean nofind=true;
        	for(String topicfilter:cbkwildcard){
        		if(TopicMatcher.match(topicfilter, topic) && cbks.containsKey(topicfilter)){
        			TopicProcessService tps=cbks.get(topicfilter);
           		 	tps.process(topic, message, time);
           		 	nofind=false;
        		}
        	}
        	if(nofind)RegTopicProcSrv.INSTANCE.process(topic, message, time);
        }
		
	}

}
