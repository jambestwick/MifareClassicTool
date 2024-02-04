package de.syss.MifareClassicTool.util;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * <p>文件描述：接口<p>
 * <p>作者：jambestwick<p>
 * <p>创建时间：2024/2/1<p>
 * <p>更新时间：2024/2/1<p>
 * <p>版本号：<p>
 * <p>邮箱：jambestwick@126.com<p>
 */
public interface ApiService {
    String SERVER_HOST = "https://api.microapp.metalcar.cn";

    @POST("api/AccessInterface/GetEmpFakaInfo")
    public Call getEmpFakaInfo(@Body Map<String, Object> reqMap);

    //上传库存货物到后台
    @POST("api/upload/product")
    @Multipart
    Call<ResponseBody> upload(@Part MultipartBody.Part file);
}
