package cn.kiway.mdm.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Android on 2016/4/20.
 */
public class HttpDownload {
    private URL url = null;
    private InputStream inputStream = null;

    /**
     * 该函数返回整型     -1：代表下载文件出错     0：代表下载文件成功
     *
     * @param urlStr   下载文件的网络地址
     * @param path     想要把下载过来的文件存放到哪一个SDCARD目录下
     * @param fileName 下载的文件的文件名，可以跟原来的名字不同，所以这里加一个fileName
     * @return
     */
    public int downFile(String urlStr, String path, String fileName) {
        if (new File(path + fileName).exists()) {
            return 0;
        }
        try {
            FileUtils fileUtils = new FileUtils();
            inputStream = getInputStreamFromURL(urlStr);
            File resultFile = fileUtils.writeToSDFromInput(path, fileName, inputStream);
            if (resultFile == null) {
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 根据URL得到输入流
     *
     * @param urlStr
     * @return
     * @throws IOException
     */
    public InputStream getInputStreamFromURL(String urlStr) throws Exception {
        HttpURLConnection urlConn = null;
        url = new URL(urlStr);
        urlConn = (HttpURLConnection) url.openConnection();
        urlConn.setConnectTimeout(10000);
        urlConn.setReadTimeout(10000);
        inputStream = urlConn.getInputStream();
        return inputStream;
    }
}
