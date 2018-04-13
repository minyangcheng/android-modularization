package com.min.sample.data.model;

import android.content.Context;

import com.min.sample.data.model.status.OrderStatus;
import com.min.sample.data.model.status.PayTypeStatus;
import com.min.sample.data.model.status.SettleTypeStatus;
import com.min.sample.util.LocationInfoUtil;
import com.min.sample.util.TerminalInfoUtil;
import com.google.gson.JsonObject;
import com.min.core.util.GsonUtil;

/**
 * Created by minyangcheng on 2017/8/23.
 */

public class TransRecord {

    public TerminalInfoUtil.TerminalInfo terminalInfo;

    public LocationInfoUtil.LocationInfo locationInfo;

    public int transType;

    public long amount;

    public String traceNo;

    public PayTypeStatus payType;

    public SettleTypeStatus settleType;

    public JsonObject response;

    public String orderId;

    public OrderStatus orderStatus;

    public String timestamp;

    public TransRecord() {
    }

    public TransRecord(Context context) {
        terminalInfo = TerminalInfoUtil.getInstance().getTerminalInfo();
        locationInfo = LocationInfoUtil.getInstance(context).getLocationInfo();
    }

    @Override
    public String toString() {
        return GsonUtil.toJson(this);
    }
}
