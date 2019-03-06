package cn.kiway.classcard.utils;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.webkit.MimeTypeMap;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.kiway.classcard.R;

/**
 * Created by Administrator on 2019/3/5.
 */

public class Utils {
    public static List<String> getSpinner1List() {
        List<String> list = new ArrayList<String>();
        list.add("全部");
        list.add("数学");
        list.add("语文");
        list.add("英语");
        list.add("政治");
        list.add("美术");
        return list;
    }

    public static List<String> getSpinner1List2() {
        List<String> list = new ArrayList<String>();
        list.add("全部");
        list.add("今天");
        list.add("昨天");
        list.add("三天以内");
        list.add("一周以内");
        list.add("一个月以内");
        list.add("一年以内");
        return list;
    }

    public static void setTextSpannableSize(TextView tx, String content) {
        SpannableStringBuilder spannable = new SpannableStringBuilder(content);
        spannable.setSpan(new AbsoluteSizeSpan(20), content.indexOf("\n"), content.length(), Spanned
                .SPAN_EXCLUSIVE_EXCLUSIVE);
        tx.setText(spannable);
    }

    public static void setTextSpannableColor(Context context, TextView tx, String content) {
        SpannableStringBuilder spannable = new SpannableStringBuilder(content);
        ForegroundColorSpan bule = new ForegroundColorSpan(context.getResources().getColor(R.color._1E90FF));
        spannable.setSpan(bule, content.indexOf("\n"), content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new AbsoluteSizeSpan(17), content.indexOf("\n"), content.length(), Spanned
                .SPAN_EXCLUSIVE_EXCLUSIVE);
        tx.setText(spannable);
    }
    public static String getMimeType(File file){
        String suffix = getSuffix(file);
        if (suffix == null) {
            return "file/*";
        }
        String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(suffix);
        if (type != null || !type.isEmpty()) {
            return type;
        }
        return "file/*";
    }
    private static String getSuffix(File file) {
        if (file == null || !file.exists() || file.isDirectory()) {
            return null;
        }
        String fileName = file.getName();
        if (fileName.equals("") || fileName.endsWith(".")) {
            return null;
        }
        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            return fileName.substring(index + 1).toLowerCase(Locale.US);
        } else {
            return null;
        }
    }

}
