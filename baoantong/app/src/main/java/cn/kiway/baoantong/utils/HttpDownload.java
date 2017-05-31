package cn.kiway.baoantong.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Android on 2016/4/20.
 */
public class HttpDownload {
    private URL url = null;
    private InputStream inputStream = null;

    /**
     * 根据URL下载文件，前提是这个文件当中的内容是文本，函数的返回值就是文件当中的内容
     * 1.创建一个URL对象
     * 2.通过URL对象，创建一个HttpURLConnection对象
     * 3.得到InputStream
     * 4.从InputStream当中读取数据
     *
     * @param urlStr 要下载的文件地址
     * @return
     */
    public String download(String urlStr) {
        StringBuffer sb = new StringBuffer();
        String line = null;
        //BufferedReader有一个readLine（）方法，可以每次读取一行数据
        BufferedReader buffer = null;
        try {
            //创建一个URL对象
            url = new URL(urlStr);
            //创建一个Http连接
            HttpURLConnection urlConn = (HttpURLConnection) url
                    .openConnection();
            //使用IO流读取数据，InputStreamReader将读进来的字节流转化成字符流
            //但是字符流还不是很方便，所以再在外面套一层BufferedReader，
            //用BufferedReader的readLine（）方法，一行一行读取数据
            buffer = new BufferedReader(new InputStreamReader(urlConn
                    .getInputStream()));
            while ((line = buffer.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 该函数返回整型     -1：代表下载文件出错     0：代表下载文件成功
     *
     * @param urlStr   下载文件的网络地址
     * @param path     想要把下载过来的文件存放到哪一个SDCARD目录下
     * @param fileName 下载的文件的文件名，可以跟原来的名字不同，所以这里加一个fileName
     * @return
     */
    public int downFile(String urlStr, String path, String fileName) {
//        if (new File(path + fileName).exists()) {
//            return 0;
//        }
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
        inputStream = urlConn.getInputStream();
        return inputStream;
    }
}
