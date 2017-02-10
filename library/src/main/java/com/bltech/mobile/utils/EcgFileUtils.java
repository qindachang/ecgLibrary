package com.bltech.mobile.utils;

import android.os.Environment;

import com.bltech.mobile.utils.annotation.FilePathMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

    public boolean isSDExist() {
        return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    private FileOutputStream mFileOutputStream;

    public void readyWriteData(String filePath) {
        try {
            mFileOutputStream = null;
            mFileOutputStream = new FileOutputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeDataAsFile(byte[] data) {
        if (mFileOutputStream != null) {
            try {
                mFileOutputStream.write(data, 0, data.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void finishWriteData() {
        if (mFileOutputStream != null) {
            try {
                mFileOutputStream.close();
                mFileOutputStream = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
