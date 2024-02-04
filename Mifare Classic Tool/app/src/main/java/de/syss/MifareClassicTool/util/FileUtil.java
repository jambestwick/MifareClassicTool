package de.syss.MifareClassicTool.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * <p>文件描述：<p>
 * <p>作者：jambestwick<p>
 * <p>创建时间：2024/2/2<p>
 * <p>更新时间：2024/2/2<p>
 * <p>版本号：<p>
 * <p>邮箱：jambestwick@126.com<p>
 */
public class FileUtil {
    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        String dir = sdDir.toString();
        return dir;
    }

    public static void makeDir(File dir) {
        if (!dir.getParentFile().exists()) {
            makeDir(dir.getParentFile());
        }
        dir.mkdir();
    }


    public static boolean exportFile(String filePath) {
        PrintWriter PrintWriter = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("adb shell");
            PrintWriter = new PrintWriter(process.getOutputStream());
            PrintWriter.println("su");
            PrintWriter.println("chmod 777 " + filePath);
            //PrintWriter.println("export LD_LIBRARY_PATH=/vendor/lib:/system/lib");
            PrintWriter.println("adb pull " + filePath + "  ~/Desktop/");
//          PrintWriter.println("exit");
            PrintWriter.flush();
            PrintWriter.close();
            int value = process.waitFor();
            return returnResult(value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return false;
    }

    private static boolean returnResult(int value) {
        // 代表成功
        if (value == 0) {
            return true;
        } else if (value == 1) { // 失败
            return false;
        } else { // 未知情况
            return false;
        }
    }

    private static final int REQUEST_CODE = 100; // 请求码，用于识别操作

    public static void exportFileToComputer(String filePath, Activity context) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.ms-excel"); // 设置文件类型和路径
        intent.setPackage("com.android.mtp"); // 设置MTP包名（可能需要根据实际情况调整）
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // 授予读取权限
        context.startActivityForResult(intent, REQUEST_CODE); // 启动活动以发送文件
    }


    public static byte[] dsrcReadOBUAnd0015() {
        byte[] STX = new byte[]{(byte) 0xFF, (byte) 0xFF};
        byte[] RSCL = new byte[]{(byte) 0x08};
        byte[] DATA = new byte[]{(byte) 0x51, (byte) 0x03, (byte) 0x0A, (byte) 0x14};
        byte[] BCC = new byte[]{ByteConvert.xor(ByteConvert.byteMerger(RSCL, DATA))};
        byte[] ETX = new byte[]{(byte) 0xFF};
        return ByteConvert.byteMergerAll(STX, RSCL, DATA, BCC, ETX);
    }


    public static MultipartBody.Part buildUploadFile(String filePath) {
        // use the FileUtils to get the actual file by uri
        File file = new File(filePath);

        // create RequestBody instance from file
        RequestBody requestFile =
            RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
            MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        return body;
    }

}
