package com.bltech.mobile.utils;

import android.os.Environment;

import com.bltech.mobile.utils.annotation.FilePathMode;

import java.io.File;
import java.io.IOException;

/**
 * Created by qindachang on 2017/2/8.
 */

public class EcgFileUtils {

    public File initFilePath(@FilePathMode int mode, String path, String fileName) {
        File file;
        File file1;
        if (mode == FilePathMode.SD && isSDExist()) {
            file = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + path);
        } else {
            file = new File(File.separator + path, fileName);
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        file1 = new File(file.getPath() + File.separator + fileName);
        if (!file1.exists()) {
            try {
                file1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file1;
    }

    /**
     * 判断SD卡是否存在
     *
     * @return true已存在，false不存在
     */
    public boolean isSDExist() {
        return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    public void readyWriteData() {

    }

    public void writeDataToFile(byte[] data) {

    }

    public void finishWriteData() {

    }
}
