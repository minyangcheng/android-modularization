package com.min.sample.app;

import com.min.core.base.BaseApp;
import com.min.core.util.WebHandler;
import com.min.sample.BuildConfig;
import com.min.sample.util.BuglyUtil;
import com.min.sample.util.pos.PosUtil;

import me.drakeet.floo.Floo;
import me.drakeet.floo.extensions.LogInterceptor;
import me.drakeet.floo.extensions.OpenDirectlyHandler;

/**
 * Created by minyangcheng on 2016/10/13.
 */
public class App extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        BuglyUtil.init(this);
        PosUtil.init(getContext());
        initRouter();
    }

    public void initRouter() {
        Floo.configuration()
                .setDebugEnabled(BuildConfig.DEBUG)
                .addRequestInterceptor(new LogInterceptor("Request"))
                .addTargetInterceptor(new LogInterceptor("Target"))
                .addTargetNotFoundHandler(new WebHandler())
                .addTargetNotFoundHandler(new OpenDirectlyHandler());
    }

}
