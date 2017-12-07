// RemoteAidlInterface.aidl
package cn.kiway.aidl;

//即使在同一个包下，也需要手动导入类
import cn.kiway.aidl.ClientCallback;

//编译后生成的Java文件在app/build/generated/source/aidl/dubug/<packagename>目录下
//服务端创建，客户端调用
interface RemoteInterface {
    //初始化
    boolean init(String key);
    //    向服务端注册监听
    void registClientCallback(ClientCallback callback);
    //    取消注册
    void unRegistClientCallback(ClientCallback callback);
    //锁屏解屏
    void lockScreen(boolean b);
    //屏幕共享
    void shareScreen(boolean b,String ip);
      //禁用Home键
    void setHomeButtonDisabled(boolean disabled);

}
