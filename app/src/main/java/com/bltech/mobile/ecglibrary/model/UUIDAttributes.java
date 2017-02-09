package com.bltech.mobile.ecglibrary.model;

import java.util.UUID;

/**
 * Created by qindachang on 2017/2/9.
 */

public interface UUIDAttributes {
    interface ecg {
        UUID SERVICE_ECG = UUID.fromString("");
        UUID NOTIFICATION_HEART_RATE = UUID.fromString("");
        UUID NOTIFICATION_ECG_SIGNAL = UUID.fromString("");
    }

    interface command {
        UUID SERVICE_COMMAND = UUID.fromString("");
        UUID WRITE = UUID.fromString("");
        UUID READ = UUID.fromString("");
    }

    interface step {
        UUID SERVICE_STEP = UUID.fromString("");
        UUID NOTIFICATION_STEP = UUID.fromString("");
        UUID NOTIFICATION_STEP_STATUS = UUID.fromString("");
    }

}
