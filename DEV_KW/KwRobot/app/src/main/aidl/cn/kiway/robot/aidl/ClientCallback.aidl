// ClientAidlCallback.aidl
package cn.kiway.robot.aidl;

//编译后生成的Java文件在app/build/generated/source/aidl/dubug/<packagename>目录下
//客户端向服务端注册，客户端创建，服务端调用
interface ClientCallback {
      //客户端消息
   void accpterMessage(String msg);
}
