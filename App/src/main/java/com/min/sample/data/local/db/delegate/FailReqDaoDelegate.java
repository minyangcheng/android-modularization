package com.min.sample.data.local.db.delegate;

import com.min.sample.data.local.db.DBOperate;
import com.min.sample.data.model.FailReqRecord;
import com.min.sample.data.local.db.dao.DaoSession;
import com.min.sample.data.local.db.dao.FailReqRecordDao;
import com.min.common.util.EmptyUtils;

import java.util.List;

public class FailReqDaoDelegate {

    private FailReqRecordDao dao;

    public FailReqDaoDelegate() {
        DaoSession daoSession = DBOperate.getInstance().getDaoSession();
        dao = daoSession.getFailReqRecordDao();
    }

    public void save(String str) {
        if (EmptyUtils.isEmpty(str)) {
            return;
        }
        FailReqRecord reqRecord = new FailReqRecord();
        reqRecord.setJsonStr(str);
        dao.save(reqRecord);
    }

    public List<FailReqRecord> query() {
        return dao.queryBuilder().list();
    }

    public void delete(FailReqRecord failReqRecord) {
        dao.deleteAll();
    }

}
