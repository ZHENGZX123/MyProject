package com.zk.myweex.mqttclient.mq;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.ByteBuffer;
import java.util.IdentityHashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import hprose.client.ClientContext;
import hprose.client.HproseClient;
import hprose.common.HproseContext;
import hprose.common.HproseFilter;
import hprose.common.InvokeSettings;
import hprose.io.ByteBufferStream;
import hprose.io.HproseMode;
import hprose.util.concurrent.Promise;
import hprose.util.concurrent.Timer;

interface ILoginHprose {
	//public String addsessionuser(String user,String passwd,String clientid);
	public String addsessionuser(String user, String passwd, String clientid, String utype);
}

public class HproseMqttClient extends HproseClient{

	static class ClientSessionFilter implements HproseFilter {
		private final IdentityHashMap<HproseClient, Integer> sidMap = new IdentityHashMap<HproseClient, Integer>();
		@Override
		public ByteBuffer inputFilter(ByteBuffer istream, HproseContext context) {
			HproseClient client = ((ClientContext)context).getClient();
			int len = istream.limit() - 7;
			if (len > 0 &&
					istream.get() == 's' &&
					istream.get() == 'i' &&
					istream.get() == 'd') {
				int sid = ((int)istream.get()) << 24 |
						((int)istream.get()) << 16 |
						((int)istream.get()) << 8  |
						(int)istream.get();
				sidMap.put(client, sid);
				return istream.slice();
			}
			istream.rewind();
			return istream;
		}

		@Override
		public ByteBuffer outputFilter(ByteBuffer ostream, HproseContext context) {
			HproseClient client = ((ClientContext)context).getClient();
			if (sidMap.containsKey(client)) {
				int sid = sidMap.get(client);
				ByteBuffer buf = ByteBufferStream.allocate(ostream.remaining() + 7);
				buf.put((byte)'s');
				buf.put((byte)'i');
				buf.put((byte)'d');
				buf.put((byte)(sid >> 24 & 0xff));
				buf.put((byte)(sid >> 16 & 0xff));
				buf.put((byte)(sid >> 8 & 0xff));
				buf.put((byte)(sid & 0xff));
				buf.put(ostream);
				ByteBufferStream.free(ostream);
				return buf;
			}
			return ostream;
		}
	}

	private final static AtomicInteger nextId = new AtomicInteger(0);
	private KwMqttCli cli;
	private String topicName;
	private String token="";
	public String getToken(){
		return this.token;
	}
	final class Request {
		public final ByteBuffer buffer;
		public final Promise<ByteBuffer> result = new Promise<ByteBuffer>();
		public final int timeout;
		public Request(ByteBuffer buffer, int timeout) {
			this.buffer = buffer;
			this.timeout = timeout;
		}
	}

	public HproseMqttClient(){
		super();
		cli=new KwMqttCli(100000000+new Random().nextInt(100000000));
		topicName="mqttRpcs";
		this.setFilter(new ClientSessionFilter());
	}
	public HproseMqttClient(String topicname){
		super();
		cli=new KwMqttCli(100000000+new Random().nextInt(100000000));
		topicName=topicname;
		this.setFilter(new ClientSessionFilter());
	}
	public HproseMqttClient(String topicname, String username, String passwd){
		super();
		cli=new KwMqttCli(100000000+new Random().nextInt(100000000),username,passwd);
		topicName=topicname;
		cli.subscribe(topicname+"/"+username+"/#");
		this.setFilter(new ClientSessionFilter());
		ILoginHprose login = this.useService(ILoginHprose.class);
		this.token=login.addsessionuser(username, passwd, "", cli.getId());
	}
	public HproseMqttClient(String topicname, String username, String passwd, TopicProcessService lcbk){
		super();
		cli=new KwMqttCli(100000000+new Random().nextInt(100000000),username,passwd,lcbk);
		topicName=topicname;
		cli.subscribe(topicname+"/"+username+"/#");
		this.setFilter(new ClientSessionFilter());
		ILoginHprose login = this.useService(ILoginHprose.class);
		this.token=login.addsessionuser(username, passwd, "", cli.getId());
		if(this.token.equals(""))cli.getLoginerrCbk().process(topicName,null,"401");
	}
	public HproseMqttClient(String topicname, String username, String passwd, String utype, TopicProcessService lcbk){
		super();
		cli=new KwMqttCli(100000000+new Random().nextInt(100000000),username,passwd,lcbk);
		topicName=topicname;
		cli.subscribe(topicname+"/"+username+"/#");
		this.setFilter(new ClientSessionFilter());
		ILoginHprose login = this.useService(ILoginHprose.class);
		this.token=login.addsessionuser(username, passwd, utype, cli.getId());
		if(this.token.equals(""))cli.getLoginerrCbk().process(topicName,null,"401");
	}
	public HproseMqttClient(HproseMode mode) {
		super(mode);
		this.setFilter(new ClientSessionFilter());
	}
	public void registerNotice(TopicProcessService cbk){
		RegTopicProcSrv.INSTANCE.subscribe(topicName+"/"+cli.getUserName()+"/notice", cbk);
	}
	public void register(String tag, TopicProcessService cbk){
		RegTopicProcSrv.INSTANCE.subscribe(topicName+"/"+cli.getUserName()+"/"+tag, cbk);
	}
	public String getId(){
		return cli.getId();
	}


	public int getRecode(){
		return cli.getRecode();
	}

	public String getRemsg(){
		return cli.getRemsg();
	}

	@Override
	public final void close() {
		cli.close();
		super.close();
	}
	@Override
	protected Promise<ByteBuffer> sendAndReceive(ByteBuffer buffer,
												 ClientContext context) {
		final InvokeSettings settings = context.getSettings();
		final int id = nextId.incrementAndGet() & 0x7fffffff;
		int timeout=settings.getTimeout();
		System.out.println("no="+cli.getId()+id);
		final Request request = new Request(buffer, timeout);
		cli.subscribe(topicName+"/mqttcbk/"+cli.getId()+id, new TopicProcessService(){
			@Override
			public void process(String topic, MqttMessage message,
								String time) {
				request.result.resolve(ByteBuffer.wrap(message.getPayload()));
			}
		});

		Timer timer = new Timer(new Runnable() {
			public void run() {
			}
		});
		timer.setTimeout(timeout);
		byte[] buf=new byte[buffer.remaining()];
		buffer.get(buf);
		cli.publish(topicName+"/mqttrpc/"+cli.getId()+id,buf);
		return request.result;
	}

}
