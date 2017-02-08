package com.bltech.mobile.utils;

/**
 * 心率内衣的心电图算法调用
 */
class EcgNative {


    static void initialize() {
        System.loadLibrary("ecgalgo");
    }

    /**
     * 功能：算法初始化,滤除50Hz工频干扰
     *
     * @param a 工频
     */
    public static native int EcgIni(int a);

    /**
     * 功能: 心电原始采样输入 参数: 蓝牙收到的原始采样数据
     *
     * @return 1，成功；其他，失败
     */
    public static native int EcgInserData(short data);

    /**
     * 功能：通过算法处理心电数据 ，
     * 参数: heartBeat [0] 心率返回值
     * ecgData 返回滤波后的数据有500个
     * <p>
     *
     * @return 1: 成功; 其他 ：失败;
     */
    public static native int EcgProcessData(short[] ecgData, short[] heartBeat);

    // 用不到这个方法
    public static native int EcgProcessDebug();

    //用不到这个方法
    public static native boolean TestCalTime();

    // 用不到这个方法
    /**
     * 返回异常的数据
     *
     * @param id 是异常类型（0到8）
     * @param b  b[]：为该异常的评分 返回值为是否有异常。 1：有异常 0：无异常
     */
    public static native int EcgGetAnalyzedData(int id, short[] b);

    //用不到这个方法
    /**
     * 没用的
     * 返回心电异常的结果 --->[0, 0, 0, 0, 0, 1, 0, 0, 0]
     *
     * @param b 输入一个空的数组并且数组的长度是9 ，返回一个9种心电异常情况 ，如果数组元素有1 ，就有对应心电异常情况
     * @return 1
     */
    public static native int EcgGetAnalyzedResult(byte[] b);

    //用不到这个方法
    public static native int EcgGetString(byte[] b);

    // 用不到这个方法
    /**
     * 功能：启用心电异常分析
     *
     * @param Second 分析的时间长度 注意Second长度不能超过180秒
     * @return 1
     */
    public static native int EcgSetDetectedSeconds(int seconds);

    // 用不到这个方法
    /**
     * 九种心律异常的情况（1~9）
     *
     * @param index 1~9中的其中的一个。
     * @return 每种心律异常情况。
     */

    public static native short EcgGetExceptionData(int index);

    /**
     * 解密
     *
     * @param raw_data
     * @return 解密后的数据。
     */

    public static native byte decode_raw(byte raw_data);

    /**
     * 应该调用这个
     * HRV具体信息说明
     *
     * @param path    文件路径
     * @param HRV_des 6位int数组。【精神压力，疲劳程度，身心负荷，身体素质，心脏功能年龄（1：青少年，2：青壮年，3：中年，4：年轻老人，
     *                5：老年），心率】
     * @param analyze 6种异常情况：【心率不齐，心动过速，心动过缓，停搏情况，漏搏情况，房性早搏】
     * @return 交感-副交感神经平衡度
     */
    public static native float HRV_des(String path, int[] HRV_des, int[] analyze);

    // Crc校验
    public static native byte calculate_crc8(byte[] crc8);

    // 和校验
    public static native byte calculate_chksum(byte[] chksum);

    // 业务数据
    public static native int business_data(byte[] all, byte[] services);

    // 获取业务数据初始化
    public static native void business_init();

    // 初始化呼吸训练 （1初，2中，3高。4爆）
    public static native void breath_init(int number);

    /**
     * @param data  心电数据
     * @param array //第一个心率，第二个是进程条。第三个是吸气 1，呼气0.
     * @return
     */
    public static native int breath_data_input(byte data, int[] array);

    // 呼吸训练的结果
    public static native int get_breath_quality();
}
