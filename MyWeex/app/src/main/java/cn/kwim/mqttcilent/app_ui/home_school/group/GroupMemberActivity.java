package cn.kwim.mqttcilent.app_ui.home_school.group;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.zk.myweex.R;
import cn.kwim.mqttcilent.BaseActivity;
import cn.kwim.mqttcilent.app_ui.home_school.ChatActivity;
import cn.kwim.mqttcilent.common.cache.dao.GroupMemberListDao;
import cn.kwim.mqttcilent.common.cache.javabean.GroupListMember;
import cn.kwim.mqttcilent.common.views.sort.CharacterParser;
import cn.kwim.mqttcilent.common.views.sort.ClearEditText;
import cn.kwim.mqttcilent.common.views.sort.PinyinComparator;
import cn.kwim.mqttcilent.common.views.sort.SideBar;

public class GroupMemberActivity extends BaseActivity implements View.OnClickListener {

    private ClearEditText filter_edit;
    private ListView listView;
    private String groupId;
    private ImageView iv_back;
    private List<GroupListMember> lst;
    private CharacterParser characterParser;
    private GroupMemberAdapter adapter;
    private TextView dialog;
    private SideBar sidebar;
    private PinyinComparator pinyinComparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.im_activity_group_member);
        groupId = getIntent().getStringExtra(ChatActivity.GROUPID);
        initView();
        // saveMember(groupId);
        pinyinComparator = new PinyinComparator();
        lst = GroupMemberListDao.getGroupListMember(groupId);

        characterParser = CharacterParser.getInstance();
        dealData();
    }

    public void dealData() {
        // 根据a-z进行排序源数据
        lst = filledData(lst);
        Collections.sort(lst, pinyinComparator);
        adapter = new GroupMemberAdapter(this);
        listView.setAdapter(adapter);
        Log.i("dealData", lst.toString());
        adapter.setLst(lst);

    }

    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<GroupListMember> filledData(List<GroupListMember> date) {
        List<GroupListMember> mSortList = new ArrayList<GroupListMember>();

        for (int i = 0; i < date.size(); i++) {
            GroupListMember sortMemberlst = date.get(i);
            //sortMemberlst.setName(date.get(i).getName());
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(date.get(i).getName());
            String sortString = pinyin;
            if (pinyin.length() > 1)
                sortString = pinyin.substring(0, 1).toUpperCase();
            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                GroupMemberListDao.updateGrouplistMember(sortMemberlst.getKey(), sortString);
            } else {
                GroupMemberListDao.updateGrouplistMember(sortMemberlst.getKey(), "#");
            }

            mSortList.add(sortMemberlst);
        }
        return mSortList;

    }

    public void initView() {
        filter_edit = (ClearEditText) findViewById(R.id.filter_edit);
        listView = (ListView) findViewById(R.id.listView);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        sidebar = (SideBar) findViewById(R.id.sidebar);
        dialog = (TextView) findViewById(R.id.dialog);

        sidebar.setTextView(dialog);
        iv_back.setOnClickListener(this);
        //根据输入框输入值的改变来过滤搜索
        filter_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        sidebar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    listView.setSelection(position);
                }
                //

            }
        });
    }

    private void filterData(String content) {
        List<GroupListMember> list = new ArrayList<>();
        if (TextUtils.isEmpty(content)) {
            list = lst;
        } else {
            list.clear();
            for (int i = 0; i < lst.size(); i++) {
                String name = lst.get(i).getName();
                if (name.indexOf(content.toString()) != -1 || characterParser.getSelling(name).startsWith(content
                        .toString())) {
                    list.add(lst.get(i));
                }
            }

        }
        adapter.setLst(list);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back: {
                finish();
                overridePendingTransition(R.anim.im_slide_in_from_left, R.anim.im_slide_out_to_right);

            }
        }
    }
}
