package cn.kiway.mdm.hprose.examples;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import cn.kiway.mdm.hprose.jrf.client.JRFClient;

public class JsfCliTest {
	public static void main(String[] args) throws Exception {
		JRFClient cli = new JRFClient(new InetSocketAddress("127.0.0.1", 2205));
		cli.start();
		byte[] buf = new byte[1500];
		long len = 0l;
		String file = "C:/Windows/System32/drivers/etc/hosts"; // ANY file, as seen from the JRF Server (a Windows machine, in this case)
		int deflate = 3; // Use chunk compression for network data transfer
		OutputStream os = new BufferedOutputStream(new FileOutputStream("hosts"));
		try (InputStream is = cli.getRemoteInputStream(file, deflate)) {
			int n;
			for (;;) {
				n = is.read(buf);
				if (n < 0)
					break;
				os.write(buf, 0, n);
				len += n;
			}
			System.out.println("len="+len);
			is.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}

		os.close();
		cli.requestStop();
		cli.join();
	 }
}
