package com.hrj.rn.runtime.common.app.constants;

import android.os.Environment;

import com.hrj.rn.runtime.common.app.App;

import java.io.File;

/**
 * Created by Song on 2017/2/15.
 */
public class FileConstant {

    /**
     * zip的文件名
     */
    public static final String ZIP_NAME = "partner";

    /**
     * bundle文件名
     */
    public static final String JS_BUNDLE_LOCAL_FILE = "index.android.bundle";
    public static final String PATCH_IMG_FILE = "patch_imgs.txt";

    /**
     * 第一次解压zip后的文件目录
     */
    public static final String JS_PATCH_LOCAL_FOLDER = Environment.getExternalStorageDirectory().toString()
            + File.separator + App.getInstance().getAppPackageName();

    public static final String LOCAL_FOLDER = JS_PATCH_LOCAL_FOLDER + "/" + ZIP_NAME;

    public static final String DRAWABLE_PATH = JS_PATCH_LOCAL_FOLDER + "/" + ZIP_NAME + "/drawable-mdpi/";

    /**
     * 除第一次外，未来解压zip后的文件目录
     */
    public static final String FUTURE_JS_PATCH_LOCAL_FOLDER = JS_PATCH_LOCAL_FOLDER + "/future";

    public static final String FUTURE_DRAWABLE_PATH = FUTURE_JS_PATCH_LOCAL_FOLDER + "/" + ZIP_NAME + "/drawable-mdpi/";
    public static final String FUTURE_PAT_PATH = FUTURE_JS_PATCH_LOCAL_FOLDER + "/partner/" + "bundle.pat";

    /**
     * zip文件
     */
    public static final String JS_PATCH_LOCAL_PATH = JS_PATCH_LOCAL_FOLDER + File.separator + ZIP_NAME + ".zip";


    /**
     * 合并后的bundle文件保存路径
     */
    public static final String JS_BUNDLE_LOCAL_PATH = JS_PATCH_LOCAL_FOLDER + "/partner/" + JS_BUNDLE_LOCAL_FILE;

    /**
     * .pat文件
     */
    public static final String JS_PATCH_LOCAL_FILE = JS_PATCH_LOCAL_FOLDER + "/partner/bundle.pat";

    /**
     * 增量图片名称文件路径
     */
    public static final String PATCH_IMG_NAMES_PATH = JS_PATCH_LOCAL_FOLDER + "/partner/" + PATCH_IMG_FILE;

    /**
     * 下载URL
     */
    public static final String JS_BUNDLE_REMOTE_URL = "http://192.168.86.93:8080/" + ZIP_NAME + ".zip";
}
