package cn.kiway.mdm.hprose.examples;

import cn.kiway.mdm.hprose.hprose.net.KwHproseTcpClient;
import hprose.util.concurrent.Action;

interface IHello {
    String hello(String name);
}

public class TCPHelloClient {
    public static void main(String[] args) throws Throwable {
        System.out.println("START");
        KwHproseTcpClient client = new KwHproseTcpClient();
        IHello helloClient = client.useService(IHello.class);
        System.out.println(helloClient.hello("World"));
        client.subscribe("ssss", new Action<Object>() {

            @Override
            public void call(Object o) throws Throwable {
                System.out.println("o==" + o.toString());
            }
        });
        client.getFile("log2.log", "aaa/log.log");
        Thread.sleep(30000);
        System.out.println("END");
    }
}
