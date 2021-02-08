package com.dbz.network.retrofit.factory;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Response;

/**
 * description:
 *
 * @author Db_z
 * date 2020/4/2 19:20
 * @version V1.0
 */
public class HandleErrorInterceptor extends ResponseBodyInterceptor {

    @Override
    Response intercept(@NotNull Response response, String url, String body) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(body);
//            if (jsonObject != null) {
//                if (jsonObject.has("msg")) {
//                    LogUtils.e("---jsonObject.has(msg)");
//                    throw new Exception(jsonObject.getString("msg"));
//                }
//            }
            return response;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
