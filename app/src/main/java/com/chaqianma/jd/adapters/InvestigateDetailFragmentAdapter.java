package com.chaqianma.jd.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.chaqianma.jd.fragment.CompanyInfoFragment;
import com.chaqianma.jd.fragment.PersonInfoFragment;
import com.chaqianma.jd.fragment.PersonalAssetsFragment;
import com.chaqianma.jd.fragment.SocialRelationFragment;

/**
 * Created by zhangxd on 2015/7/28.
 * <p>
 * 尽调详情Adapter
 */
public class InvestigateDetailFragmentAdapter extends FragmentStatePagerAdapter {

    private int mPageCount = 4;
    private PersonInfoFragment personInfoFragment = null;
    private CompanyInfoFragment companyInfoFragment = null;
    private PersonalAssetsFragment personalAssetsFragment = null;
    private SocialRelationFragment socialRelationFragment = null;

    public InvestigateDetailFragmentAdapter(FragmentManager fm) {
        super(fm);
        /*personInfoFragment = PersonInfoFragment.newInstance();
        companyInfoFragment = CompanyInfoFragment.newInstance();
        personalAssetsFragment = PersonalAssetsFragment.newInstance();
        socialRelationFragment = SocialRelationFragment.newInstance();*/
    }

    public void saveData(int currItemIdx) {
        switch (currItemIdx) {
            case 0:
                personInfoFragment.saveDataSubmit();
                break;
            case 1:
                companyInfoFragment.saveDataSubmit();
                break;
            case 2:
                personalAssetsFragment.saveDataSubmit();
                break;
            case 3:
                socialRelationFragment.saveDataSubmit();
                break;
            default:
                break;
        }
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                if (personInfoFragment == null)
                    personInfoFragment = PersonInfoFragment.newInstance();
                fragment = personInfoFragment;
                break;
            case 1:
                if(companyInfoFragment==null)
                    companyInfoFragment= CompanyInfoFragment.newInstance();
                fragment = companyInfoFragment;
                break;
            case 2:
                if(personalAssetsFragment==null)
                    personalAssetsFragment = PersonalAssetsFragment.newInstance();
                fragment = personalAssetsFragment;
                break;
            case 3:
                if(socialRelationFragment==null)
                    socialRelationFragment = SocialRelationFragment.newInstance();
                fragment = socialRelationFragment;
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return mPageCount;
    }
}
