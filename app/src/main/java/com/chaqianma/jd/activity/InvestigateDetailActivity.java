package com.chaqianma.jd.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.chaqianma.jd.R;
import com.chaqianma.jd.adapters.InvestigateDetailFragmentAdapter;
import com.chaqianma.jd.common.AppData;
import com.chaqianma.jd.common.Constants;

import org.w3c.dom.Text;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by zhangxd on 2015/7/28.
 * <p/>
 * 尽调详情
 */
public class InvestigateDetailActivity extends FragmentActivity {
    @InjectView(R.id.radiogroup_check)
    RadioGroup mRadioGroup;
    @InjectView(R.id.viewpager)
    ViewPager mViewPager;
    @InjectView(R.id.top_title)
    TextView top_title;
    @InjectView(R.id.top_right_btn)
    ImageView top_right_btn;
    public String mBorrowRequestId = "18";

    private InvestigateDetailFragmentAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investigate_detail);
        ButterKnife.inject(this);
        mViewPager.setOffscreenPageLimit(4);
        if (AppData.getInstance().getBorrowRequestInfo() != null)
            mBorrowRequestId = AppData.getInstance().getBorrowRequestInfo().getBorrowRequestId();
        top_title.setText("尽调详情");
        top_right_btn.setVisibility(View.VISIBLE);
        mAdapter = new InvestigateDetailFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case R.id.radio_person:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.radio_company:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.radio_asset:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.radio_relation:
                        mViewPager.setCurrentItem(3);
                        break;
                    default:
                        break;
                }
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                switch (position) {
                    case 0:
                        mRadioGroup.check(R.id.radio_person);
                        break;
                    case 1:
                        mRadioGroup.check(R.id.radio_company);
                        break;
                    case 2:
                        mRadioGroup.check(R.id.radio_asset);
                        break;
                    case 3:
                        mRadioGroup.check(R.id.radio_relation);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    @OnClick(R.id.top_back_btn)
    void onBack(View v) {
        this.finish();
    }


    @OnClick(R.id.top_right_btn)
    void onSubmit() {
        mAdapter.saveData(mViewPager.getCurrentItem());
    }
}
