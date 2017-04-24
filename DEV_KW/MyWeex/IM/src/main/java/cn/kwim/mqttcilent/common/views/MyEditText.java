package cn.kwim.mqttcilent.common.views;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.widget.EditText;

/**
 * 添加复制，显示图片
 *
 * @author Lukas Knuth
 */
@SuppressLint("NewApi")
public class MyEditText extends EditText implements
        MenuItem.OnMenuItemClickListener {
    private static final int ID_SELECTION_MODE = android.R.id.selectTextMode;
    // Selection context mode
    private static final int ID_SELECT_ALL = android.R.id.selectAll;
    private static final int ID_CUT = android.R.id.cut;
    private static final int ID_COPY = android.R.id.copy;
    private static final int ID_PASTE = android.R.id.paste;

    private final Context mContext;

    /*
     * Just the constructors to create a new EditText...
     */
    public MyEditText(Context context) {
        super(context);
        this.mContext = context;
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
    }

    @Override
    protected void onCreateContextMenu(ContextMenu menu) {
//		menu.add(0, ID_PASTE, 0, "粘贴").setOnMenuItemClickListener(this);
//		menu.add(0, ID_CUT, 1, "剪切").setOnMenuItemClickListener(this);
//		menu.add(0, ID_COPY, 1, "复制").setOnMenuItemClickListener(this);
//		menu.add(0, ID_SELECT_ALL, 1, "全选").setOnMenuItemClickListener(this);
        super.onCreateContextMenu(menu);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        return onTextContextMenuItem(item.getItemId());
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(handler(text.toString()), type);
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        boolean consumed = id == android.R.id.paste ? true : super.onTextContextMenuItem(id);
        switch (id) {
            case android.R.id.cut:
                onTextCut();
                //break;
            case android.R.id.paste:
                ClipboardManager clip = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                //clip.setText(sb);
                int cursorSelect = getSelectionStart();
                getEditableText().insert(cursorSelect, handler(clip.getText().toString()));

                onTextPaste();
                //break;
            case android.R.id.copy:
                onTextCopy();
        }
        return consumed;
    }

    /**
     * Text was cut from this EditText.
     */
    public void onTextCut() {
        //Toast.makeText(mContext, "Cut!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Text was copied from this EditText.
     */
    public void onTextCopy() {
        //Toast.makeText(mContext, "Copy!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Text was pasted into the EditText.
     */
    public void onTextPaste() {
        //Toast.makeText(mContext, "Paste!", Toast.LENGTH_SHORT).show();
    }

    //对表情内容转义
    private SpannableStringBuilder handler(String content) {
        SpannableStringBuilder sb = new SpannableStringBuilder(content);
        String regex = "(\\#\\[face/png/f_static_)\\d{3}(.png\\]\\#)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(content);
        while (m.find()) {
            String tempText = m.group();
            String png = tempText.substring("#[".length(), tempText.length() - "]#".length());
            try {
                sb.setSpan(new ImageSpan(mContext, BitmapFactory.decodeStream(mContext.getAssets().open(png))), m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            System.out.println(sb + "--------ooo--" + tempText);
        }

        String regexg1 = "<img[ ]*src.*?gif\'>";
        Pattern p1 = Pattern.compile(regexg1);
        Matcher m1 = p1.matcher(content);
        while (m1.find()) {
            String tempText = m1.group();
            String num = tempText.substring("<img src='images/emoji/".length(), tempText.length() - ".gif'>".length());
            if (num.length() == 1) {
                num = "00" + num;
            } else if (num.length() == 2) {
                num = "0" + num;
            }
            try {
                sb.setSpan(new ImageSpan(mContext, BitmapFactory.decodeStream(mContext.getAssets().open("face/gif/f" + num + ".gif"))), m1.start(), m1.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            System.out.println(sb + "--------ooo--" + tempText);
        }
        if (content.contains("@作业�?")) {
            int hstart = content.indexOf("@作业�?");
            int hend = hstart + "@作业�?".length();
            sb.setSpan(new ForegroundColorSpan(Color.BLUE), hstart, hend, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (content.contains("@通知�?")) {
            int nstart = content.indexOf("@通知�?");
            int nend = nstart + "@通知�?".length();
            sb.setSpan(new ForegroundColorSpan(Color.BLUE), nstart, nend, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (content.contains("@活动�?")) {
            int astart = content.indexOf("@活动�?");
            int aend = astart + "@活动�?".length();
            sb.setSpan(new ForegroundColorSpan(Color.BLUE), astart, aend, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return sb;
    }
}
