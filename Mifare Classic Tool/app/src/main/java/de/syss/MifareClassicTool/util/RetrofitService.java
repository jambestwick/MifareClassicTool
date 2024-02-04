package de.syss.MifareClassicTool.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.syss.MifareClassicTool.db.database.ProductDatabases;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <p>文件描述：HTTP协议的Request<p>
 * <p>作者：jambestwick<p>
 * <p>创建时间：2024/2/1<p>
 * <p>更新时间：2024/2/1<p>
 * <p>版本号：<p>
 * <p>邮箱：jambestwick@126.com<p>
 */
public class RetrofitService {

    private static ApiService instance;

    public static ApiService getInstance() {
        synchronized (RetrofitService.class) {
            if (instance == null) {
                return getApiService(ApiService.class, ApiService.SERVER_HOST);
            }
        }
        return instance;
    }

    public static <T> T getApiService(Class<T> cls, String baseUrl) {
        Retrofit retrofit = RetrofitService.getRetrofitBuilder(baseUrl).build();
        return retrofit.create(cls);
    }

    public static Retrofit.Builder getRetrofitBuilder(String baseUrl) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
//        SSLContext sc;
//        try {
//            sc = SSLContext.getInstance("TLSv1.2");
//            sc.init(null, null, null);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//        }
//        ConnectionSpec cs = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
//            .tlsVersions(TlsVersion.TLS_1_2)
//            .build();
//
//        List<ConnectionSpec> specs = new ArrayList<>();
//        specs.add(cs);
//        specs.add(ConnectionSpec.COMPATIBLE_TLS);
//        specs.add(ConnectionSpec.CLEARTEXT);

        return new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            //json 自动解析最外层
            .baseUrl(baseUrl);
    }
}
