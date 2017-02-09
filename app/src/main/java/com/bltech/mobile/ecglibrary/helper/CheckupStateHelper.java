package com.bltech.mobile.ecglibrary.helper;

/**
 * 心电检测状态
 * <p>
 * Created by qindachang on 2017/2/9.
 */

public class CheckupStateHelper {

    private Type mType;

    private enum Type {
        START,
        FINISH
    }

    public void setStart() {
        mType = Type.START;
    }

    public void setFinish() {
        mType = Type.FINISH;
    }

    public boolean getCheckuping() {
        if (mType == Type.START) {
            return true;
        } else if (mType == Type.FINISH) {
            return false;
        }
        return false;
    }

}
