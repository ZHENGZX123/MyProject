package cn.kiway.mdm.hprose.examples;

import cn.kiway.mdm.hprose.hprose.net.KwHproseTcpServer;

public class TCPHelloServer {
    public static String hello(String name) {
        return "Hello " + name + "!";
    }
    public static void main(String[] args) throws Exception {
        KwHproseTcpServer server = new KwHproseTcpServer("tcp://localhost:4321");
        server.add("hello", TCPHelloServer.class);
        server.publish("ssss");
        server.setSharedir("d:/tmp/");
        server.shareFile("aaa/log.log");
        server.shareFile("gzsfzh.csv");
        server.start();        
        System.out.println("START");
        System.in.read();
        server.push("ssss", "aaaaaaaaaaaa");
        server.push("ssss", "bbbbbbbbb");
        server.push("ssss", "bbbbbbbbb1");
        server.push("ssss", "bbbbbbbbb2");
        server.push("ssss", "bbbbbbbbb3");
        server.push("ssss", "bbbbbbbbb4");
        Thread.sleep(30000);
        server.stop();
        System.out.println("STOP");
    }
}
