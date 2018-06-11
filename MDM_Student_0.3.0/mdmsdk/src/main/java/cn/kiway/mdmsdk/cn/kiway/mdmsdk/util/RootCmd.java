package cn.kiway.mdmsdk.cn.kiway.mdmsdk.util;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;

public class RootCmd {

    // 判断机器Android是否已经root，即是否获取root权限
    public static boolean haveRoot() {
        int ret = execRootCmdSilent("echo test"); // 通过执行测试命令来检测
        Log.d("test", "ret = " + ret);
        if (ret == 0) {
            return true;
        }
        return false;
    }

    public static int execRootCmdSilent(String cmd) {
        int result = -1;
        DataOutputStream dos = null;

        try {
            Process p = Runtime.getRuntime().exec("su");
            dos = new DataOutputStream(p.getOutputStream());
            Log.d("test", cmd);
            dos.writeBytes(cmd + "\n");
            dos.flush();
            dos.writeBytes("exit\n");
            dos.flush();
            p.waitFor();
            result = p.exitValue();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}  