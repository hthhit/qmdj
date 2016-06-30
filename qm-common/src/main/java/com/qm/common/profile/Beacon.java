package com.qm.common.profile;

import org.slf4j.Logger;

import com.qm.common.utils.DateUtils;

public class Beacon {

    private static final int ACTION_START = 0;
    private static final int ACTION_FAIL = ACTION_START + 1;
    private static final int ACTION_END = ACTION_FAIL + 1;
    
    private static final String[] ACTION_STRINGS = { "START", "FAIL", "END" };

    // 功能类别分类定义
    public static final String FUNC_CODE = "CODE";
    public static final String FUNC_SQL = "SQL";
    public static final String FUNC_HTTP = "HTTP";
    public static final String FUNC_SERVICE = "SERVICE";
    public static final String FUNC_WEB = "WEB";

    // 功能类别
    private String func;
    // 功能ID
    private String key;
    // 起始时间unix毫秒，同时也算作一个session
    private long start;
    // 额外数据
    private String msg;
    // 日志器
    private Logger logger;

    public Beacon(Logger logger, String func, String key, String msg) {
        this.start = DateUtils.currAbsMillis();
        this.logger = logger;
        this.func = func;
        this.key = key;
        this.msg = msg;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = func;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long start() {
        return log(ACTION_START);
    }

    public long fail() {
        return log(ACTION_FAIL);
    }

    public long end() {
        return log(ACTION_END);
    }

    private long log(int action) {
        long ret = start;
        // 日志格式为：function,key,session,action,cost,msg
        switch (action) {
        case ACTION_START:
            logger.info("{},{},{},{},0,{}", func, key, start, ACTION_STRINGS[action], msg);
            break;
        case ACTION_FAIL:
        case ACTION_END:
            ret = DateUtils.currAbsMillis() - start;
            logger.info("{},{},{},{},{},{}", func, key, start, ACTION_STRINGS[action], ret, msg);
            break;
        default:
        }
        return ret;
    }

    protected Beacon() {

    }
}
