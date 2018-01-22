/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: F:\\kiway_xm\\MDM_Student_0.3.0\\aidl\\cn\\kiway\\aidl\\RemoteInterface.aidl
 */
package cn.kiway.aidl;
//编译后生成的Java文件在app/build/generated/source/aidl/dubug/<packagename>目录下
//服务端创建，客户端调用

public interface RemoteInterface extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements cn.kiway.aidl.RemoteInterface
{
private static final java.lang.String DESCRIPTOR = "cn.kiway.aidl.RemoteInterface";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an cn.kiway.aidl.RemoteInterface interface,
 * generating a proxy if needed.
 */
public static cn.kiway.aidl.RemoteInterface asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof cn.kiway.aidl.RemoteInterface))) {
return ((cn.kiway.aidl.RemoteInterface)iin);
}
return new cn.kiway.aidl.RemoteInterface.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_init:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.init(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_registClientCallback:
{
data.enforceInterface(DESCRIPTOR);
cn.kiway.aidl.ClientCallback _arg0;
_arg0 = cn.kiway.aidl.ClientCallback.Stub.asInterface(data.readStrongBinder());
this.registClientCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_unRegistClientCallback:
{
data.enforceInterface(DESCRIPTOR);
cn.kiway.aidl.ClientCallback _arg0;
_arg0 = cn.kiway.aidl.ClientCallback.Stub.asInterface(data.readStrongBinder());
this.unRegistClientCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_lockScreen:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.lockScreen(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_shareScreen:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
java.lang.String _arg1;
_arg1 = data.readString();
this.shareScreen(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_setHomeButtonDisabled:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.setHomeButtonDisabled(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements cn.kiway.aidl.RemoteInterface
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
//初始化

@Override public boolean init(java.lang.String key) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(key);
mRemote.transact(Stub.TRANSACTION_init, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
//    向服务端注册监听

@Override public void registClientCallback(cn.kiway.aidl.ClientCallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registClientCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//    取消注册

@Override public void unRegistClientCallback(cn.kiway.aidl.ClientCallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_unRegistClientCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//锁屏解屏

@Override public void lockScreen(boolean b) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((b)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_lockScreen, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//屏幕共享

@Override public void shareScreen(boolean b, java.lang.String ip) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((b)?(1):(0)));
_data.writeString(ip);
mRemote.transact(Stub.TRANSACTION_shareScreen, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//禁用Home键

@Override public void setHomeButtonDisabled(boolean disabled) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((disabled)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setHomeButtonDisabled, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_init = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_registClientCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_unRegistClientCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_lockScreen = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_shareScreen = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_setHomeButtonDisabled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
}
//初始化

public boolean init(java.lang.String key) throws android.os.RemoteException;
//    向服务端注册监听

public void registClientCallback(cn.kiway.aidl.ClientCallback callback) throws android.os.RemoteException;
//    取消注册

public void unRegistClientCallback(cn.kiway.aidl.ClientCallback callback) throws android.os.RemoteException;
//锁屏解屏

public void lockScreen(boolean b) throws android.os.RemoteException;
//屏幕共享

public void shareScreen(boolean b, java.lang.String ip) throws android.os.RemoteException;
//禁用Home键

public void setHomeButtonDisabled(boolean disabled) throws android.os.RemoteException;
}
