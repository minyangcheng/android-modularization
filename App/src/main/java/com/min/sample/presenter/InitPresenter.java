package com.min.sample.presenter;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.min.common.util.EmptyUtils;
import com.min.sample.app.AppConstants;
import com.min.sample.contract.InitContract;
import com.min.sample.data.DataManager;
import com.min.sample.util.pos.PosUtil;

/**
 * Created by minyangcheng on 2017/9/20.
 */

public class InitPresenter extends InitContract.Presenter {

    @Override
    public boolean check() {
        return true;
    }

    @Override
    public void openInitSetting(Fragment fragment) {
        PosUtil.openInitSetting(fragment);
    }

    @Override
    public void handleActivityResult(Intent data) {
        if (data == null) return;
        String terminalNo = data.getStringExtra(AppConstants.KEY_TERMINAL_NO);
        String merchantNo = data.getStringExtra(AppConstants.KEY_MERCHANT_NO);
        String merchantName = data.getStringExtra(AppConstants.KEY_MERCHANT_NAME);
        if (EmptyUtils.isNotEmpty(terminalNo)) {
            DataManager.getPreferencesHelper().putTerminalNo(terminalNo);
        }
        if (EmptyUtils.isNotEmpty(merchantNo)) {
            DataManager.getPreferencesHelper().putMerchantNo(merchantNo);
        }
        if (EmptyUtils.isNotEmpty(merchantName)) {
            DataManager.getPreferencesHelper().putMerchantName(merchantName);
        }
    }
}
