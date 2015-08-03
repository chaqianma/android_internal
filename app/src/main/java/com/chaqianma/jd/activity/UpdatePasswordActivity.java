package com.chaqianma.jd.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.chaqianma.jd.R;
import com.chaqianma.jd.common.AppData;
import com.chaqianma.jd.common.Constants;
import com.chaqianma.jd.common.HttpRequestURL;
import com.chaqianma.jd.model.ErrorInfo;
import com.chaqianma.jd.utils.HttpClientUtil;
import com.chaqianma.jd.utils.JDAppUtil;
import com.chaqianma.jd.utils.JDHttpResponseHandler;
import com.chaqianma.jd.utils.ResponseHandler;
import com.chaqianma.jd.utils.SharedPreferencesUtil;
import com.chaqianma.jd.widget.JDToast;
import java.util.HashMap;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by zhangxd on 2015/7/24.
 */
public class UpdatePasswordActivity extends BaseActivity {
    @InjectView(R.id.et_old_password)
    EditText et_old_password;
    @InjectView(R.id.et_new_password)
    EditText et_new_password;
    @InjectView(R.id.et_confirm_password)
    EditText et_confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        ButterKnife.inject(this);
        setTopBarState("修改密码", true, true);
    }

    @OnClick(R.id.top_right_btn)
    void onSubmit(View v) {
        final String old_password = et_old_password.getText().toString();
        final String new_password = et_new_password.getText().toString();
        final String confirm_password = et_confirm_password.getText().toString();
        if (old_password.length() <= 0) {
            JDToast.showShortText(UpdatePasswordActivity.this, "请输入原始密码");
            return;
        }
        if (new_password.length() <= 0) {
            JDToast.showShortText(UpdatePasswordActivity.this, "请输入新密码");
            return;
        }
        if (confirm_password.length() <= 0) {
            JDToast.showShortText(UpdatePasswordActivity.this, "请再次输入新密码");
            return;
        }
        if (!new_password.equals(confirm_password)) {
            JDToast.showShortText(UpdatePasswordActivity.this, "两次密码不一致，请重新输入新密码");
            return;
        }
        if(!new_password.matches("^(?![^a-zA-Z]+$)(?!\\D+$).{8,16}$"))
        {
            JDToast.showShortText(UpdatePasswordActivity.this,"密码必须为8-16数字和字母组合");
            return;
        }
        HashMap<String, Object> argMaps = new HashMap<String, Object>();
        argMaps.put("oldPassword", old_password);
        argMaps.put("newPassword", new_password);
        try {

            HttpClientUtil.post(HttpRequestURL.updatePasswordUrl, argMaps, new JDHttpResponseHandler(UpdatePasswordActivity.this, new ResponseHandler() {
                @Override
                public void onSuccess(Object o) {
                    JDToast.showLongText(UpdatePasswordActivity.this, "修改密码成功");
                    //保存用户名与密码
                    SharedPreferencesUtil.saveUserAndPassword(UpdatePasswordActivity.this, AppData.getInstance().getUserInfo().getMobile(),new_password);
                }
            },Class.forName(ErrorInfo.class.getName())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
