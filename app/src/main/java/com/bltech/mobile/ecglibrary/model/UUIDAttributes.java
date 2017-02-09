package com.bltech.mobile.ecglibrary.model;

import java.util.UUID;

/**
 * Created by qindachang on 2017/2/9.
 */

public interface UUIDAttributes {
    interface ecg {
        UUID SERVICE_ECG = UUID.fromString("0000180d-0000-1000-8000-00805f9b34fb");
        UUID NOTIFICATION_HEART_RATE = UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb");
        UUID NOTIFICATION_ECG_SIGNAL = UUID.fromString("0000fff3-0000-1000-8000-00805f9b34fb");
    }

    interface command {
        UUID SERVICE_COMMAND = UUID.fromString("0000180d-0000-1000-8000-00805f9b34fb");
        UUID WRITE = UUID.fromString("0000fff5-0000-1000-8000-00805f9b34fb");
        UUID READ = UUID.fromString("0000fff5-0000-1000-8000-00805f9b34fb");
    }

    interface step {
        UUID SERVICE_STEP = UUID.fromString("");
        UUID NOTIFICATION_STEP = UUID.fromString("");
        UUID NOTIFICATION_STEP_STATUS = UUID.fromString("");
    }

}
