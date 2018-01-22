/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: F:\\kiway_xm\\MDM_Student_0.3.0\\aidl\\cn\\kiway\\aidl\\ClientCallback.aidl
 */
package cn.kiway.aidl;
//编译后生成的Java文件在app/build/generated/source/aidl/dubug/<packagename>目录下
//客户端向服务端注册，客户端创建，服务端调用

public interface ClientCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements cn.kiway.aidl.ClientCallback
{
private static final java.lang.String DESCRIPTOR = "cn.kiway.aidl.ClientCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an cn.kiway.aidl.ClientCallback interface,
 * generating a proxy if needed.
 */
public static cn.kiway.aidl.ClientCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof cn.kiway.aidl.ClientCallback))) {
return ((cn.kiway.aidl.ClientCallback)iin);
}
return new cn.kiway.aidl.ClientCallback.Stub.Proxy(obj);
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
case TRANSACTION_goOutClass:
{
data.enforceInterface(DESCRIPTOR);
this.goOutClass();
reply.writeNoException();
return true;
}
case TRANSACTION_attendClass:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.attendClass(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_accpterMessage:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
this.accpterMessage(_arg0, _arg1);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements cn.kiway.aidl.ClientCallback
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
//下课

@Override public void goOutClass() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_goOutClass, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void attendClass(java.lang.String msg) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(msg);
mRemote.transact(Stub.TRANSACTION_attendClass, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//学生消息

@Override public void accpterMessage(java.lang.String msg, java.lang.String token) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(msg);
_data.writeString(token);
mRemote.transact(Stub.TRANSACTION_accpterMessage, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_goOutClass = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_attendClass = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_accpterMessage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
}
//下课

public void goOutClass() throws android.os.RemoteException;
public void attendClass(java.lang.String msg) throws android.os.RemoteException;
//学生消息

public void accpterMessage(java.lang.String msg, java.lang.String token) throws android.os.RemoteException;
}
