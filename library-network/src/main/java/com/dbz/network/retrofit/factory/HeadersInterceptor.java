package com.dbz.network.retrofit.factory;

import com.dbz.network.retrofit.utils.LogUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * description:
 *
 * @author Db_z
 * date 2019/4/22 17:01
 * @version V1.0 头信息
 */
public class HeadersInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();
        HttpUrl.Builder urlBuilder = request.url().newBuilder();
        if ("GET".equals(request.method())) { // GET方法
            LogUtils.e("---请求头url：" + urlBuilder.build().url());
            // 这里可以添加一些公共get参数
//            String[] s = request.url().toString().split(Api.API_BASE);
//            if (s[1].contains("?")) {
//                urlBuilder.addEncodedQueryParameter("sign", getMD5Sign(token, String.valueOf(timestamp),
//                        String.valueOf(uid), s[1].split("\\?")[0]));
//            } else {
//                urlBuilder.addEncodedQueryParameter("sign", getMD5Sign(token, String.valueOf(timestamp),
//                        String.valueOf(uid), s[1]));
//            }
//            urlBuilder.addEncodedQueryParameter("timestamp", String.valueOf(timestamp));
            HttpUrl httpUrl = urlBuilder.build();
            // 打印所有get参数
            Set<String> paramKeys = httpUrl.queryParameterNames();
            for (String key : paramKeys) {
                String value = httpUrl.queryParameter(key);
                LogUtils.e("---paramKeys = " + key + " = " + value);
            }
            // 将最终的url填充到request中
            requestBuilder.url(httpUrl);
        } else if ("POST".equals(request.method())) {
            LogUtils.e("---请求头url：" + urlBuilder.build().url());
            // 把已有的post参数添加到新的构造器
            if (request.body() instanceof FormBody) {
                LogUtils.e("---请求头参数：FormBody");
                // FormBody和url不太一样，若需添加公共参数，需要新建一个构造器
                FormBody.Builder bodyBuilder = new FormBody.Builder();
                FormBody formBody = (FormBody) request.body();
                for (int i = 0; i < formBody.size(); i++) {
                    bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                }

                // 这里可以添加一些公共post参数
//                String[] s = request.url().toString().split(Api.API_BASE);
//                bodyBuilder.addEncoded("sign", getMD5Sign(token, String.valueOf(timestamp),
//                        String.valueOf(uid), s[1]));
//                bodyBuilder.addEncoded("timestamp", String.valueOf(timestamp));
                FormBody newBody = bodyBuilder.build();
                // 打印所有post参数
                for (int i = 0; i < newBody.size(); i++) {
                    LogUtils.e("---" + newBody.name(i) + " = " + newBody.value(i));
                }
                // 将最终的表单body填充到request中
                requestBuilder.post(newBody);
            }

            if (request.body() instanceof MultipartBody) {
                LogUtils.e("---请求头参数：MultipartBody");
                MultipartBody.Builder multipartBuilder = new MultipartBody.Builder();
                multipartBuilder.setType(MultipartBody.FORM);
                MultipartBody multipartBody = (MultipartBody) request.body();
                for (int i = 0; i < multipartBody.size(); i++) {
                    MultipartBody.Part part = multipartBody.part(i);
                    multipartBuilder.addPart(part);
                }

                // 这里可以添加一些公共post参数
//            String[] s = request.url().toString().split(Api.API_BASE);
//            multipartBuilder.addFormDataPart("sign", getMD5Sign(token, String.valueOf(timestamp),
//                    String.valueOf(uid), s[1]));
//            multipartBuilder.addFormDataPart("timestamp", String.valueOf(timestamp));
                MultipartBody newBody = multipartBuilder.build();

                // 打印所有post参数
                Buffer buffer1 = new Buffer();
                newBody.writeTo(buffer1);
                String postParams = buffer1.readUtf8();
                String[] split = postParams.split("\n");
                List<String> names = new ArrayList<>();
                for (String str : split) {
                    if (str.contains("Content-Disposition")) {
                        names.add(str.replace("Content-Disposition: form-data; name=", "").replace("\"", ""));
                    }
                }
                List<MultipartBody.Part> parts = newBody.parts();
                for (int i = 0; i < parts.size(); i++) {
                    MultipartBody.Part part = parts.get(i);
                    RequestBody body1 = part.body();
                    if (body1.contentLength() < 100) {
                        Buffer buffer = new Buffer();
                        body1.writeTo(buffer);
                        String value = buffer.readUtf8();
                        //打印 name和value
                        if (names.size() > i) {
                            LogUtils.e("---params = " + names.get(i) + " = " + value);
                        }
                    } else {
                        if (names.size() > i) {
                            LogUtils.e("---params = " + names.get(i));
                        }
                    }
                }
                requestBuilder.post(newBody);
            }
        }
//        requestBuilder.addHeader("user-token", UserUtils.getSPUtils().getString(Constant.ConstantUser.USERTOKEN));
//        requestBuilder.addHeader("app-version", String.valueOf(AppUtils.getAppVersionCode()));
//        requestBuilder.addHeader("app-version-name", AppUtils.getAppVersionName());
//        // 判断里面是否包括中文
//        if (StringUtil.isChinese(SystemUtils.getMobileName())){
//            // 如果包括就转成unicode
//            String unicode = StringUtil.stringToUnicode(SystemUtils.getMobileName());
//            requestBuilder.addHeader("mobile-name", unicode);
//        } else {
//            requestBuilder.addHeader("mobile-name", SystemUtils.getMobileName());
//        }
//        // 判断里面是否包括中文
//        if (StringUtil.isChinese(SystemUtils.getMobileModle())){
//            // 如果包括就转成unicode
//            String unicode = StringUtil.stringToUnicode(SystemUtils.getMobileModle());
//            requestBuilder.addHeader("mobile-type", unicode);
//        } else {
//            requestBuilder.addHeader("mobile-type", SystemUtils.getMobileModle());
//        }
//        requestBuilder.addHeader("mobile-version", SystemUtils.getSystemVersion());
//        requestBuilder.addHeader("AppName", Constant.ConstantUser.APP_NAME);
//        requestBuilder.addHeader("ispad", AppUtil.isPad(App.getAppContext()) ? "1" : "0");
//        requestBuilder.addHeader("approle", Constant.ConstantUser.APP_ROLE);
        return chain.proceed(requestBuilder.build());
    }
}