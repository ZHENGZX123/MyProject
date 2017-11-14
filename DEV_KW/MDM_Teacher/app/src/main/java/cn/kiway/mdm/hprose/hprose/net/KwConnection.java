/**********************************************************\
|                                                          |
|                          hprose                          |
|                                                          |
| Official WebSite: http://www.hprose.com/                 |
|                   http://www.hprose.org/                 |
|                                                          |
\**********************************************************/
/**********************************************************\
 *                                                        *
 * Connection.java                                        *
 *                                                        *
 * hprose Connection interface for Java.                  *
 *                                                        *
 * LastModified: Sep 19, 2016                             *
 * Author: Ma Bingyao <andot@hprose.com>                  *
 *                                                        *
\**********************************************************/
package cn.kiway.mdm.hprose.hprose.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import cn.kiway.mdm.hprose.hprose.codec.Message;
import hprose.io.ByteBufferStream;
import hprose.net.TimeoutType;
import hprose.util.concurrent.Timer;

public final class KwConnection {
	private Message msg = null;
	private int bufferSize = 1024*8;
	private ByteBuffer inbuf = ByteBufferStream.allocate(bufferSize);
	
    private final SocketChannel channel;
    private final KwConnectionHandler handler;
    private final InetSocketAddress address;
    private volatile SelectionKey key;
    private volatile TimeoutType timeoutType;
    private final Timer timer = new Timer(new Runnable() {
        public void run() {
            try {
                handler.onTimeout(KwConnection.this, timeoutType);
            }
            finally {
                close();
            }
        }
    });
    
    
    private Integer id = null;
    private final Queue<Message> outqueue = new ConcurrentLinkedQueue<Message>();
    private KwReactor reactor = null;
    private boolean closed = false;
    public KwConnection(SocketChannel channel, KwConnectionHandler handler, InetSocketAddress address) {
        this.channel = channel;
        this.handler = handler;
        this.address = address;
    }

    public final void connect(Selector selector) throws ClosedChannelException, IOException {
        key = channel.register(selector, SelectionKey.OP_CONNECT, this);
        setTimeout(handler.getConnectTimeout(), TimeoutType.CONNECT_TIMEOUT);
        channel.connect(address);
    }

    public final void connected(KwReactor reactor, Selector selector) throws ClosedChannelException {
        clearTimeout();
        this.reactor = reactor;
        key = channel.register(selector, SelectionKey.OP_READ, this);
        handler.onConnected(this);
    }

    public final boolean isConnected() {
        return channel.isOpen() && channel.isConnected();
    }

    public final SocketChannel socketChannel() {
        return channel;
    }

    private void closeWithError(boolean error) {
        synchronized (this) {
            if (closed) {
                return;
            }
            closed = true;
        }
        try {
            clearTimeout();
            if (error) {
                try {
                    handler.onTimeout(KwConnection.this, timeoutType);
                }
                catch (Exception e) {
                }
            }
            handler.onClose(this);
            channel.close();
        }
        catch (IOException e) {
        }
        finally {
            key.cancel();
        }
    }

    public final void close() {
        closeWithError(false);
    }

    final void errorClose() {
        closeWithError(true);
    }
    
	
	
    public final boolean receive() {
        try {
            setTimeout(handler.getReadTimeout(), TimeoutType.READ_TIMEOUT);
            int n = channel.read(inbuf);
            if(n < 0){ 
            	//System.out.println("n="+n);
    			close(); 
    			return false;
    		}
            if (n == 0){
            	//System.out.println("n="+n);
            	return true;
            }
            //IoBuffer readBuffer = IoBuffer.allocate(n);
            inbuf.flip();
            Message rmsg = null;
            byte[] buf = new byte[n];
            inbuf.get(buf);
            //System.out.println(new String(buf));
            
            ByteBufferStream.free(inbuf);
            rmsg = Message.parse(buf);
            		//codec.decode(readBuffer)
        	clearTimeout();
			handler.onReceived(this, ByteBuffer.wrap(rmsg.getBody()), id);
        }
        catch (Exception e) {
            handler.onError(this, e);
            close();
            return false;
        }
        return true;
    }

    public final void send(ByteBuffer buffer, Integer id) {
    	byte[] buf=
				//buffer.array();
				new byte[buffer.remaining()];
		buffer.get(buf);
		Message qmsg=new Message(buf);
		if(id!=null)qmsg.setHeader("timeout", id);    	
        outqueue.offer(qmsg);
       // System.out.println(qmsg.toString());
        key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        reactor.write(this);
        
    }

    public final void send() {
    	
        if (msg == null) {
            msg = outqueue.poll();
            if (msg == null) {
            	System.out.println("msg==null");
                return;
            }
        }
        try {
            for (;;) {
            	setTimeout(handler.getWriteTimeout(), TimeoutType.WRITE_TIMEOUT);
            	ByteBuffer buf = 
            			//codec.encode(msg).buf()
            			ByteBuffer.wrap(msg.toBytes());
            	
            	int wbytes = this.channel.write(buf);
            	//System.out.println("len="+buf.limit()+"   send="+wbytes);
                if(wbytes == 0 && buf.remaining() > 0){//TODO
                	key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                    return;
				}
                if (wbytes < 0) {
                    close();
                    return;
                }
                ByteBufferStream.free(buf);
                clearTimeout();
                String timeout = msg.getHeader("timeout");
                if(timeout==null){
                	handler.onSended(this, null );
                }else{
                	handler.onSended(this,Integer.parseInt(timeout));
                }
                
                synchronized (outqueue) {
                    msg = outqueue.poll();
                    if (msg == null) {
                        key.interestOps(SelectionKey.OP_READ);
                        return;
                    }
                }
                                
            }
        }
        catch (Exception e) {
            close();
        }
    }

    public final void setTimeout(int timeout, TimeoutType type) {
        timeoutType = type;
        if (type == TimeoutType.IDLE_TIMEOUT) {
            timer.setTimeout(timeout);
        }
        else {
            timer.setTimeout(timeout, true);
        }
    }

    public final void clearTimeout() {
        timer.clear();
    }
}
