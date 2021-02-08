package com.dbz.network.retrofit;

import com.dbz.network.retrofit.api.HttpsApi;
import com.dbz.network.retrofit.factory.HeadersInterceptor;
import com.dbz.network.retrofit.factory.StringTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * description:
 *
 * @author Db_z
 * date 2020/2/26 16:41
 * @version V1.0
 */
public class RetrofitFactory {

    private static final int DEFAULT_TIMEOUT = 20;
    private static final String BASE_URL = HttpsApi.A_HOST;
    private OkHttpClient mOkHttpClient;
    private OkHttpClient.Builder mOkHttpClientBuilder;
    private Retrofit mRetrofit;

    private static final RetrofitFactory ourInstance = new RetrofitFactory();

    public static RetrofitFactory getInstance() {
        return ourInstance;
    }

    private RetrofitFactory() {
        mOkHttpClientBuilder = new OkHttpClient.Builder();
        // 设置超时时间
        mOkHttpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        mOkHttpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        mOkHttpClientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        mOkHttpClientBuilder.retryOnConnectionFailure(true); // 连接失败时重试
        // 添加头部拦截器
        mOkHttpClientBuilder.addInterceptor(new HeadersInterceptor());
        mOkHttpClient = mOkHttpClientBuilder.build();
        changeBaseUrl(BASE_URL);
    }


    /**
     * 改变baseUrl
     */
    public void changeBaseUrl(String baseUrl) {
        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    /**
     * APi接口服务类
     */
    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }

    /**
     * 支持自己定义事件处理
     * 设置订阅 和 所在的线程环境
     */
    public <T> void subscribe(Observable<T> observable, BaseObserver<T> baseObserver) {
        toSubscribe(observable, baseObserver);
    }

    /**
     * 支持自己定义事件处理
     * 设置订阅 和 所在的线程环境
     */
    public <T> void toSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    /**
     * 解析把null转换成""
     */
    private Gson getGson() {
        return new GsonBuilder()
                .setLenient()
                //设置时间格式
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                //设置解析的时候把null转换成""
                .registerTypeHierarchyAdapter(String.class, new StringTypeAdapter())
                .registerTypeHierarchyAdapter(List.class, jsonDeserializer)
                .create();
    }

    JsonDeserializer<List<?>> jsonDeserializer = (json, typeOfT, context) -> {
        if (json.isJsonArray()) {
            JsonArray array = json.getAsJsonArray();
            Type itemType = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];
            List list = new ArrayList<>();
            for (int i = 0; i < array.size(); i++) {
                JsonElement element = array.get(i);
                Object item = context.deserialize(element, itemType);
                list.add(item);
            }
            return list;
        } else {
            //和接口类型不符，返回空List
            return Collections.EMPTY_LIST;
        }
    };
}