package top.wangdf.fxbasis.net;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import top.wangdf.fxbasis.common.ApiResult;
import top.wangdf.fxbasis.entity.VestEntity;
import top.wangdf.fxbasis.entity.VestResponseModel;

public class VestApi {

    private static final String HOST = "https://api.waizhangkuaiji.com";

    private static final String PATH = "/admin/client/vestSign.do?";

    private static final String TAG = "VestApi";



    static Retrofit createRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HOST)
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
}
