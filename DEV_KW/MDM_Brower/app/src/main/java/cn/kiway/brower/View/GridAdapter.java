package cn.kiway.brower.View;

import android.content.Context;
import android.net.Uri;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.askerov.dynamicgrid.BaseDynamicGridAdapter;

import java.util.List;

import cn.kiway.brower.Browser.AlbumController;
import cn.kiway.brower.Browser.BrowserController;
import cn.kiway.brower.Ninja.R;
import cn.kiway.brower.Unit.BrowserUnit;
import cn.kiway.brower.Unit.ViewUnit;

public class GridAdapter extends BaseDynamicGridAdapter implements BrowserController {
    @Override
    public void updateAutoComplete() {

    }

    @Override
    public void updateBookmarks() {

    }

    @Override
    public void updateInputBox(String query) {

    }

    @Override
    public void updateProgress(int progress) {

    }

    @Override
    public void showAlbum(AlbumController albumController, boolean anim, boolean expand, boolean capture) {

    }

    @Override
    public void removeAlbum(AlbumController albumController) {

    }

    @Override
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {

    }

    @Override
    public void showFileChooser(ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams
            fileChooserParams) {

    }

    @Override
    public void onCreateView(WebView view, Message resultMsg) {

    }

    @Override
    public boolean onShowCustomView(View view, int requestedOrientation, WebChromeClient.CustomViewCallback callback) {
        return false;
    }

    @Override
    public boolean onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
        return false;
    }

    @Override
    public boolean onHideCustomView() {
        return false;
    }

    @Override
    public void onLongPress(String url) {

    }

    private static class Holder {
        TextView title;
        ImageView cover;
        RelativeLayout webView;
    }

    private List<GridItem> list;

    public List<GridItem> getList() {
        return list;
    }

    private Context context;

    public GridAdapter(Context context, List<GridItem> list, int columnCount) {
        super(context, list, columnCount);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
            holder = new Holder();
            holder.title = (TextView) view.findViewById(R.id.grid_item_title);
            holder.cover = (ImageView) view.findViewById(R.id.grid_item_cover);
            holder.webView= (RelativeLayout) view.findViewById(R.id.readability_webview);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        GridItem item = list.get(position);
        holder.title.setText(item.getTitle());
        if (item.getFilename().startsWith("http://")) {
            holder.cover.setVisibility(View.GONE);
            holder.webView.setVisibility(View.VISIBLE);
            NinjaWebView webView = new NinjaWebView(new NinjaContextWrapper(getContext()));
            webView.setBrowserController(this);
            webView.setFlag(BrowserUnit.FLAG_NINJA);
            webView.setAlbumCover(null);
            webView.loadUrl(item.getURL());
            webView.deactivate();
            holder.webView.addView(webView);
        } else {
            holder.cover.setVisibility(View.VISIBLE);
            holder.webView.setVisibility(View.GONE);
            holder.cover.setImageBitmap(BrowserUnit.file2Bitmap(context, item.getFilename()));
        }
        ViewUnit.setElevation(view, context.getResources().getDimensionPixelSize(R.dimen.elevation_1dp));

        return view;
    }
}
