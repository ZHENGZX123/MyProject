package cn.kiway.mdm.hprose.jrf.msg.file;

import java.io.DataInputStream;
import java.io.IOException;

import cn.kiway.mdm.hprose.jrf.ByteBufferOut;
import cn.kiway.mdm.hprose.jrf.Utils;

public class MsgFAString extends MsgFileAction {
	
	protected String val;
	
	public MsgFAString() {
		this(null, null, null);
	}
	
	public MsgFAString(FileAction action, String pathname, String value) {
		super(action, pathname);
		this.val = value;
	}
	
	public String getValue() {
		return val;
	}
	
	@Override
	protected ByteBufferOut encode() throws IOException {
		ByteBufferOut bb = super.encode();
		bb.writeString(val);
		return bb;
	}
	
	@Override
	protected void decode(DataInputStream dis) throws IOException {
		super.decode(dis);
		val = Utils.readString(dis);
	}
	
}
