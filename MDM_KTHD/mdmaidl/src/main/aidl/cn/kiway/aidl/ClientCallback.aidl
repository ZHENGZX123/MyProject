// ClientAidlCallback.aidl
package cn.kiway.aidl;

//编译后生成的Java文件在app/build/generated/source/aidl/dubug/<packagename>目录下
//客户端向服务端注册，客户端创建，服务端调用
interface ClientCallback {

   //下课
   void goOutClass();
   //上课
   void attendClass(String msg);
   //学生消息
   void accpterMessage(String msg,String token);
}
