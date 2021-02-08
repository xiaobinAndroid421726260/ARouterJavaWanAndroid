package com.dbz.network.retrofit;

import android.net.ParseException;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dbz.network.R;
import com.dbz.network.retrofit.utils.LogUtils;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * description:
 *
 * @author Db_z
 * date 2019/4/23 10:31
 * @version V1.0
 */
public abstract class BaseObserver<T> implements Observer<T> {

    public abstract void onSucceed(T result);

    public abstract void onError(String msg);

    @Override
    public void onSubscribe(Disposable d) {
        if (!NetworkUtils.isConnected()) {
            ToastUtils.showShort("请检查网络连接，稍后再试！");
        }
    }

    @Override
    public void onNext(T result) {
//        try {
//            if (result instanceof String) {
//                LogUtils.e("---onNextT = " + result);
//                onSucceed(result);
//            } else {
                LogUtils.e("---onNextT = " + GsonUtils.toJson(result));
//                Method code = result.getClass().getMethod("getCode");
//                Method msg = result.getClass().getMethod("getMsg");
//                Object invoke = code.invoke(result);
//                if ((int) invoke == 1004) {
//                    UserUtils.getSPUtils().clear();
//                    ActivityUtils.startActivity(LoginActivity.class);
//                    ActivityUtils.finishAllActivities();
//                } else {
                    onSucceed(result);
//                }
//            }
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//            LogUtils.e("---NoSuchMethodException = " + e.getMessage());
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//            LogUtils.e("---IllegalAccessException = " + e.getMessage());
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//            LogUtils.e("---InvocationTargetException = " + e.getMessage());
//        }
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {     //   HTTP错误
            onException(ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException || e instanceof UnknownHostException) { // 连接错误
            onException(ExceptionReason.CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {  //  连接超时
            onException(ExceptionReason.CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException || e instanceof JSONException
                || e instanceof ParseException) {  //  解析错误
            onException(ExceptionReason.PARSE_ERROR);
        } else {
            onException(ExceptionReason.UNKNOWN_ERROR);
        }
        LogUtils.e("---onError = " + e.getMessage());
        onError(e.getMessage());
    }

    @Override
    public void onComplete() {

    }

    private void onException(ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
                ToastUtils.showShort(R.string.common_connect_error);
                break;
            case CONNECT_TIMEOUT:
                ToastUtils.showShort(R.string.common_connect_timeout);
                break;
            case BAD_NETWORK:
                ToastUtils.showShort(R.string.common_bad_network);
                break;
            case PARSE_ERROR:
                ToastUtils.showShort(R.string.common_parse_error);
                break;
            case UNKNOWN_ERROR:
            default:
                ToastUtils.showShort(R.string.common_unknown_error);
                break;
        }
    }

    public enum ExceptionReason {
        PARSE_ERROR,
        BAD_NETWORK,
        CONNECT_ERROR,
        CONNECT_TIMEOUT,
        UNKNOWN_ERROR,
    }
}