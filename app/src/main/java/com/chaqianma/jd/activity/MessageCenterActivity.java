package com.chaqianma.jd.activity;

import android.os.Bundle;
import android.widget.ListView;
import com.chaqianma.jd.R;
import com.chaqianma.jd.adapters.MessageAdapater;
import com.chaqianma.jd.model.UrgeInfo;
import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;
/**
 * Created by zhangxd on 2015/8/11
 * 消息中心
 */
public class MessageCenterActivity extends BaseActivity {
    @InjectView(R.id.list_msgs)
    ListView list_msgs;
    private List<UrgeInfo> mUrgeInfoList = null;
    private MessageAdapater messageAdapater = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_center);
        ButterKnife.inject(this);
        initData();
    }

    /*
    * 初始化数据
    * */
    private void initData() {
        mUrgeInfoList = new ArrayList<UrgeInfo>();
        UrgeInfo urgeInfo = new UrgeInfo();
        urgeInfo.setFlag(1);
        mUrgeInfoList.add(urgeInfo);
        urgeInfo = new UrgeInfo();
        urgeInfo.setFlag(0);
        mUrgeInfoList.add(urgeInfo);
        messageAdapater = new MessageAdapater(MessageCenterActivity.this, mUrgeInfoList);
        list_msgs.setAdapter(messageAdapater);
    }
}
