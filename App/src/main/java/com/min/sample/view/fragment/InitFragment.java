package com.min.sample.view.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.min.common.util.FragmentUtils;
import com.min.common.util.SpanUtils;
import com.min.common.util.ToastUtils;
import com.min.core.base.BaseDialog;
import com.min.core.base.BaseFragment;
import com.min.core.base.BasePopupWindow;
import com.min.sample.R;
import com.min.sample.contract.InitContract;
import com.min.sample.presenter.InitPresenter;
import com.min.sample.util.Util;
import com.min.sample.view.dialog.PayDialogFragment;
import com.min.ui.widget.CenterTitleToolbar;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import me.drakeet.floo.Floo;

/**
 * Created by minyangcheng on 2017/8/21.
 */

public class InitFragment extends BaseFragment implements InitContract.View {

    public static final int FROM_MAIN_ACTIVITY = 1;
    public static final int FROM_MAIN_FRAGMENT = 2;

    @BindView(R.id.toolbar)
    CenterTitleToolbar mToolbar;
    @BindView(R.id.tv_explain)
    TextView mExplainTv;
    @BindView(R.id.tv_init)
    TextView mInitTv;

    private int mFromType;
    private int mFinishCount;

    private InitContract.Presenter mPresenter;

    public static InitFragment newInstance(int fromType) {
        InitFragment fragment = new InitFragment();
        fragment.setFromType(fromType);
        return fragment;
    }

    private void setFromType(int fromType) {
        this.mFromType = fromType;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_init;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new InitPresenter();
        mPresenter.attachView(this);

        initToolbar();
        SpanUtils spanUtils = new SpanUtils();
        spanUtils.append("一.系统参数设置").setFontSize(15, true).setForegroundColor(Color.parseColor("#333333"))
                .appendLine()
                .append("1.点击“初始化设置”，输入终端设备号。").setFontSize(14, true).setForegroundColor(Color.parseColor("#666666"))
                .appendLine()
                .append("2.设备会自动下载密钥数据，下载完成后，点击“签到”按钮。").setFontSize(14, true).setForegroundColor(Color.parseColor("#666666"))
                .appendLine()
                .append("二.重新设置").setFontSize(15, true).setForegroundColor(Color.parseColor("#333333"))
                .appendLine();
        mExplainTv.setText(spanUtils.create());

        RxView.clicks(mInitTv)
                .throttleFirst(3, TimeUnit.SECONDS)
                .subscribe((Void) -> openInitSettingUI());
    }

    private void initToolbar() {
        if (mFromType == FROM_MAIN_FRAGMENT) {
            initToolbar(mToolbar);
        }
        mToolbar.setTitle(R.string.system_init);
        mToolbar.inflateMenu(R.menu.init);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_setting) {
                    Util.openSettingUi(getContext());
                }
                return false;
            }
        });
    }

    @OnClick(R.id.tv_info)
    public void clickInfoFinish() {
        mFinishCount++;
        if (mFinishCount % 5 == 0) {
            try {
                getActivity().finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.tv_web)
    void clickOpenWeb() {
        Floo.navigation(getContext(), "http://www.baidu.com")
                .start();
    }

    @OnClick(R.id.tv_order)
    void clickOpenOrder() {
        /**
         * 对应于intent-filter配置
         * <data
         *    android:host="cheguo.com"
         *    android:path="/order"
         *    android:scheme="cg" />
         */
        Floo.navigation(getContext(), "cg://cheguo.com/order")
                .appendQueryParameter("company_name", "cheguo")
                .appendQueryParameter("user_id", "minych")
                .putExtra("isLogin", true)
                .start();
    }

    @OnClick(R.id.btn_popup)
    void clickPopup(View view) {
        BasePopupWindow popupWindow = new BasePopupWindow(getActivity()) {

            @Override
            protected void onViewCreate(View view) {
                super.onViewCreate(view);
                view.findViewById(R.id.view_fl).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });
            }

            @Override
            protected int getLayoutId() {
                return R.layout.popup_pay;
            }
        };
        popupWindow.showAsDropDown(view);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ToastUtils.showShort("popup window dismiss");
            }
        });
    }

    @OnClick(R.id.btn_dialog)
    void clickDialog() {
        final BaseDialog dialog = new BaseDialog(getActivity()) {
            @Override
            protected int getLayoutId() {
                return R.layout.dialog_fragment_pay;
            }

            @Override
            protected void onViewCreate(View view) {
                super.onViewCreate(view);
                view.findViewById(R.id.view_fl).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });
            }
        };
        dialog.show();
    }

    @OnClick(R.id.btn_fragment_dialog)
    void clickFragmentDialog() {
        PayDialogFragment dialogFragment = new PayDialogFragment();
        dialogFragment.show(getFragmentManager(), dialogFragment.getTag());
    }

    @OnClick(R.id.tv_done)
    void clickDoneTv() {
        if (mPresenter.check()) {
            if (mFromType == FROM_MAIN_ACTIVITY) {
                FragmentUtils.replaceFragment(getFragmentManager(), MainFragment.newInstance(), android.R.id.content, false);
            } else if (mFromType == FROM_MAIN_FRAGMENT) {
                getFragmentManager().popBackStackImmediate();
            }
        }
    }

    private void openInitSettingUI() {
        showHudDialog(false);
        mPresenter.openInitSetting(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        hideHudDailog();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.handleActivityResult(data);
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        super.onDestroyView();
    }

}
