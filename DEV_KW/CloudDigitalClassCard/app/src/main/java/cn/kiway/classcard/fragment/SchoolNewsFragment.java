package cn.kiway.classcard.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.kiway.classcard.R;
import cn.kiway.classcard.entity.News;

/**
 * Created by Administrator on 2019/3/5.
 */

public class SchoolNewsFragment extends BaseFragment {

    private ListView lv;
    private MyAdapter adapter;
    private ArrayList<News> Newss = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_schoolnews, null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lv = (ListView) getView().findViewById(R.id.lv);
        adapter = new MyAdapter();
        lv.setAdapter(adapter);


        Newss.add(new News());
        Newss.add(new News());
        Newss.add(new News());
        Newss.add(new News());
        adapter.notifyDataSetChanged();

    }


    private class MyAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public MyAdapter() {
            inflater = LayoutInflater.from(getActivity());
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_news, null);
                holder = new ViewHolder();

                holder.title = (TextView) rowView.findViewById(R.id.title);
                holder.time = (TextView) rowView.findViewById(R.id.time);
                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }

            return rowView;
        }

        public class ViewHolder {
            public TextView title;
            public TextView time;
        }

        @Override
        public int getCount() {
            return Newss.size();
        }

        @Override
        public News getItem(int arg0) {
            return Newss.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }
    }
}
