package cn.kwim.mqttcilent.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Properties;
import java.util.TreeSet;

/**
 *
 *
 * UncaughtExceptionHandler：线程未捕获异常控制器是用来处理未捕获异常的�?
 *                           如果程序出现了未捕获异常默认情况下则会出现强行关闭对话框
 *                           实现该接口并注册为程序中的默认未捕获异常处理
 *                           这样当未捕获异常发生时，就可以做些异常处理操�?
 *                           例如：收集异常信息，发�?�错误报�? 等�??
 *
 * UncaughtException处理�?,当程序发生Uncaught异常的时�?,由该类来接管程序,并记录发送错误报�?.
 */
public class CrashHandler implements UncaughtExceptionHandler {
    /** Debug Log Tag */
    public static final String TAG = "CrashHandler";
    /** CrashHandler实例 */
    private static CrashHandler INSTANCE;
    /** 程序的Context对象 */
    private Context mContext;
    /** 系统默认的UncaughtException处理�? */
    private UncaughtExceptionHandler mDefaultHandler;

    private SendReports sendReports=null;

    /** 使用Properties来保存设备的信息和错误堆栈信�? */
    private Properties mDeviceCrashInfo = new Properties();
    private static final String VERSION_NAME = "versionName";
    private static final String VERSION_CODE = "versionCode";
    private static final String STACK_TRACE = "STACK_TRACE";
    /** 错误报告文件的扩展名 */
    private static final String CRASH_REPORTER_EXTENSION = ".cr";

    /** 保证只有�?个CrashHandler实例 */
    private CrashHandler() {
    }

    /** 获取CrashHandler实例 ,单例模式 */
    public static CrashHandler getInstance() {
        if (INSTANCE == null)
            INSTANCE = new CrashHandler();
        return INSTANCE;
    }

    /**
     * 初始�?,注册Context对象, 获取系统默认的UncaughtException处理�?, 设置该CrashHandler为程序的默认处理�? 
     *
     * @param ctx
     */
    public void init(Context ctx) {
        mContext = ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理 
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处�?  
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            // Sleep�?会后结束程序  
            // 来让线程停止�?会是为了显示Toast信息给用户，然后Kill程序  
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(10);
        }
    }

    /**
     * 自定义错误处�?,收集错误信息 发�?�错误报告等操作均在此完�?. �?发�?�可以根据自己的情况来自定义异常处理逻辑 
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false 
     */
    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return true;
        }
        final String msg = ex.getLocalizedMessage();
        // 使用Toast来显示异常信�?  
        new Thread() {
            @Override
            public void run() {
                // Toast 显示�?要出现在�?个线程的消息队列�?  
                Looper.prepare();
                Toast.makeText(mContext, "程序出错�?,我们会尽快修�?:" + msg, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();

        // 收集设备信息  
        collectCrashDeviceInfo(mContext);
        String crashFileName = saveCrashInfoToFile(ex);
        // 发�?�错误报告到服务�?
        //已经使用了友盟SDK上传，故此处注掉
        //sendCrashReportsToServer(mContext);  

        return true;
    }

    /**
     * 收集程序崩溃的设备信�? 
     *
     * @param ctx
     */
    public void collectCrashDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                mDeviceCrashInfo.put(VERSION_NAME, pi.versionName == null ? "not set" : pi.versionName);
                mDeviceCrashInfo.put(VERSION_CODE, String.valueOf(pi.versionCode));
            }
            //添加渠道�?

        } catch (NameNotFoundException e) {
        }
        // 使用反射来收集设备信�?.在Build类中包含各种设备信息,  
        // 例如: 系统版本�?,设备生产�? 等帮助调试程序的有用信息  
        // 返回 Field 对象的一个数组，这些对象反映�? Class 对象�?表示的类或接口所声明的所有字�?  
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                // setAccessible(boolean flag)  
                // 将此对象�? accessible 标志设置为指示的布尔值�??  
                // 通过设置Accessible属�?�为true,才能对私有变量进行访问，不然会得到一个IllegalAccessException的异�?  
                field.setAccessible(true);
                mDeviceCrashInfo.put(field.getName(), String.valueOf(field.get(null)));
            } catch (Exception e) {
            }
        }
    }

    /**
     * 保存错误信息到文件中 
     *
     * @param ex
     * @return
     */
    private String saveCrashInfoToFile(Throwable ex) {
        Writer info = new StringWriter();
        PrintWriter printWriter = new PrintWriter(info);
        ex.printStackTrace(printWriter);

        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        // toString() 以字符串的形式返回该缓冲区的当前值�??  
        String result = info.toString();
        printWriter.close();
        mDeviceCrashInfo.put(STACK_TRACE, result);
        String log=mDeviceCrashInfo.toString();
        return null;
    }

    /**
     * 把错误报告发送给服务�?,包含新产生的和以前没发�?�的. 
     *
     * @param ctx
     */
    public void sendCrashReportsToServer(Context ctx) {
        String[] crFiles = getCrashReportFiles(ctx);
        if (crFiles != null && crFiles.length > 0) {
            TreeSet<String> sortedFiles = new TreeSet<String>();
            sortedFiles.addAll(Arrays.asList(crFiles));

            for (String fileName : sortedFiles) {
                File cr = new File(ctx.getFilesDir(), fileName);
                postReport(ctx,cr);
                cr.delete();// 删除已发送的报告  
            }
        }
    }

    /**
     * 获取错误报告文件�? 
     *
     * @param ctx
     * @return
     */
    private String[] getCrashReportFiles(Context ctx) {
        File filesDir = ctx.getFilesDir();
        // 实现FilenameFilter接口的类实例可用于过滤器文件�?  
        FilenameFilter filter = new FilenameFilter() {
            // 测试指定文件是否应该包含在某�?文件列表中�??  
            public boolean accept(File dir, String name) {
                return name.endsWith(CRASH_REPORTER_EXTENSION);
            }
        };
        // 返回�?个字符串数组，这些字符串指定此抽象路径名表示的目录中满足指定过滤器的文件和目�?  
        return filesDir.list(filter);
    }

    private void postReport(Context ctx,File file) {
        try{
            FileInputStream fin = new FileInputStream(file);
            StringBuffer localStringBuffer = new StringBuffer();
            byte [] buffer = new byte[1024];
            int i = 0;
            while ((i = fin.read(buffer)) != -1){
                localStringBuffer.append(new String(buffer, 0, i));
            }
            fin.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 在程序启动时�?, 可以调用该函数来发�?�以前没有发送的报告 
     */
    public void sendPreviousReportsToServer() {
        if(null!=sendReports&&!sendReports.isAlive()){
            sendReports.start();
        }
    }

    private final class SendReports extends Thread{

        private Context mContext;
        private final Object lock = new Object();

        public SendReports(Context mContext) {
            this.mContext=mContext;
        }
        public void run(){
            try{
                synchronized (this.lock){
                    sendCrashReportsToServer(mContext);
                }
            }catch(Exception e){
            }
        }

    }
}  

