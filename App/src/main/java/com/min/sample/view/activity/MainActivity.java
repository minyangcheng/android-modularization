package com.min.sample.view.activity;

import android.os.Bundle;

import com.min.sample.data.DataManager;
import com.min.sample.util.UpdateManager;
import com.min.sample.view.fragment.InitFragment;
import com.min.sample.view.fragment.MainFragment;
import com.min.common.util.EmptyUtils;
import com.min.common.util.FragmentUtils;
import com.min.core.base.BaseActivity;
import com.min.core.util.PermissionUtil;

public class MainActivity extends BaseActivity {

    private UpdateManager mUpdateManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PermissionUtil.requestAppPermission(this);
        if (EmptyUtils.isEmpty(DataManager.getPreferencesHelper().getMerchantNo())) {
            FragmentUtils.replaceFragment(getSupportFragmentManager(), InitFragment.newInstance(InitFragment.FROM_MAIN_ACTIVITY), android.R.id.content, false);
        } else {
            FragmentUtils.replaceFragment(getSupportFragmentManager(), MainFragment.newInstance(), android.R.id.content, false);
        }
        mUpdateManger = new UpdateManager(this);
        mUpdateManger.checkUpdate(true);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

}
