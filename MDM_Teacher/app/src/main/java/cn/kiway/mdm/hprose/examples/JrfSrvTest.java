package cn.kiway.mdm.hprose.examples;

import java.net.InetSocketAddress;

import cn.kiway.mdm.hprose.jrf.server.JRFServer;

public class JrfSrvTest {
	 public static void main(String[] args) throws Exception {
		 int port = 2205; // Port on which JRF Server will listen for connection (should be TCP-opened in the firewall)
		 JRFServer srv = JRFServer.get(new InetSocketAddress(port));
		 srv.start();
		 Thread.sleep(30000);
		 System.in.read();
		 srv.requestStop(); // Disconnects all connected clients
		 srv.join(); // Wait for graceful shutdown
	 }
}
