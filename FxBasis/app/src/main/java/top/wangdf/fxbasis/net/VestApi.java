package top.wangdf.fxbasis.net;

import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;
import top.wangdf.fxbasis.common.ApiResult;
import top.wangdf.fxbasis.entity.TripartiteResponseModel;
import top.wangdf.fxbasis.entity.VestEntity;
import top.wangdf.fxbasis.entity.VestResponseModel;

public class VestApi {

    private static final String HOST = "https://api.waizhangkuaiji.com";

    private static final String HOST2 = "https://bb.skr.today";

    private static final String PATH = "/admin/client/vestSign.do?";

    private static final String TAG = "VestApi";



    static Retrofit createRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HOST)
                .build();

        return retrofit;
    }

    static Retrofit createRetrofit2() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HOST2)
                .build();

        return retrofit;
    }

    public void makeVestRequest(VestEntity entity) {
        ApiInterface api = createRetrofit().create(ApiInterface.class);
        Call<VestResponseModel> model3 = api.repo3();//未使用传参
//        ?&version=1.0&channelCode=google&deviceId=ebe2f38b83fcd5d1&timestamp=1584324183767
        Call<VestResponseModel> model4 = api.repo4(entity.getVestCode(), entity.getVersion(), entity.getChannelCode(), entity.getDevicesId(), entity.getTimestamp());
        ApiResult result = null;
        /**
         * 异步网络请求
         */
        model4.enqueue(new Callback<VestResponseModel>() {
            @Override
            public void onResponse(Call<VestResponseModel> call, Response<VestResponseModel> response) {
                ApiResult result = new ApiResult(response.code(), response.body(), response.code() == 200, null, "");
            }

            @Override
            public void onFailure(Call<VestResponseModel> call, Throwable t) {
                new ApiResult(500, null, false, t, "Error");
            }
        });

    }

    /**
     * @author WDF
     * @description 进行三方登录Get请求
     */
    public void tripartiteLogin(String id, String name, String email, int type, String sign, String host, WebView webView) {
        ApiInterface api = createRetrofit2().create(ApiInterface.class);
        Call<TripartiteResponseModel> call = api.googleTripartiteLogin(id, name, email, type, sign);
        call.enqueue(new Callback<TripartiteResponseModel>() {
            @Override
            public void onResponse(Call<TripartiteResponseModel> call, Response<TripartiteResponseModel> response) {
                CookieManager.getInstance().setCookie(host, "token1=" + response.body().getData().getToken1() + ";expires=1; path=/");
                CookieManager.getInstance().setCookie(host, "token2=" + response.body().getData().getToken1() + ";expires=1; path=/");
                webView.loadUrl(host);
                Log.i(TAG, "on Success!!!");
            }

            @Override
            public void onFailure(Call<TripartiteResponseModel> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
