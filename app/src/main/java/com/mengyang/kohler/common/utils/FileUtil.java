package com.mengyang.kohler.common.utils;

import android.text.TextUtils;
import android.util.Log;

import com.mengyang.kohler.common.net.IConstants;

import java.io.File;
import java.util.List;

/**
 * Created by liusong on 2018/3/2.
 */

public class FileUtil {

    /**
     * 查看本地是否有PDF文件
     */
    public static List<String> judgePdfIsExit(List<String> localTempPdfFileName) {

        File file2 = new File(IConstants.ROOT_PATH);

        if (!file2.exists()) {
            file2.mkdirs();
        }

        File[] files = file2.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();

                if (files[i].isFile() && fileName.endsWith(".pdf")) {
                    if (!TextUtils.isEmpty(fileName)) {
                        localTempPdfFileName.add(fileName);
                    }

                    Log.i("kohler", "本地的pdfName = " + fileName);
                }
            }
        }
        return localTempPdfFileName;
    }
}
