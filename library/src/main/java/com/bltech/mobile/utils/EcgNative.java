package com.bltech.mobile.utils;


class EcgNative {


    static void initialize() {
        System.loadLibrary("ecgalgo");
    }

    public static native int EcgIni(int a);

    public static native int EcgInserData(short data);

    public static native int EcgProcessData(short[] ecgData, short[] heartBeat);

    public static native int EcgProcessDebug();

    public static native boolean TestCalTime();

    public static native int EcgGetAnalyzedData(int id, short[] b);

    public static native int EcgGetAnalyzedResult(byte[] b);

    public static native int EcgGetString(byte[] b);

    public static native int EcgSetDetectedSeconds(int seconds);

    public static native short EcgGetExceptionData(int index);

    public static native byte decode_raw(byte raw_data);

    public static native float HRV_des(String path, int[] HRV_des, int[] analyze);

    public static native byte calculate_crc8(byte[] crc8);

    public static native byte calculate_chksum(byte[] chksum);

    public static native int business_data(byte[] all, byte[] services);

    public static native void business_init();

    public static native void breath_init(int number);

    public static native int breath_data_input(byte data, int[] array);

    public static native int get_breath_quality();
}
