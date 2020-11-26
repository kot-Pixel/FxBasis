package top.wangdf.fxbasis.net;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import top.wangdf.fxbasis.entity.VestResponseModel;

public class VestApi {

    private static final String HOST = "https://d2d0drb98uxrz0.cloudfront.net";

    private static final String PATH = "/admin/client/vestSign.do?";

    private static final String TAG = "VestApi";



    static Retrofit createRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HOST)
                .build();

        return retrofit;
    }

    /**
     * https://d2d0drb98uxrz0.cloudfront.net/admin/client/vestSign.do?
     * vestCode=CUHX4A73
     * &version=1.0
     * &channelCode=google
     * &deviceId=ebe2f38b83fcd5d1
     * &timestamp=1584324183767
     *
     *
     */

    public void test() {
        ApiInterface api = createRetrofit().create(ApiInterface.class);
        Call<VestResponseModel> model = api.repo2(PATH,
                "CUHX4A73",
                "1.0",
                "google",
                "ebe2f38b83fcd5d1",
                "1584324183767"
        );
        Call<VestResponseModel> model2 = api.repo("/admin/client/vestSign.do?vestCode=CUHX4A73&version=1.0&channelCode=google&deviceId=ebe2f38b83fcd5d1&timestamp=1584324183767");

        model2.enqueue(new Callback<VestResponseModel>() {
            @Override
            public void onResponse(Call<VestResponseModel> call, Response<VestResponseModel> response) {
                Log.i(TAG, "onResponse: "+ response.isSuccessful());
                Log.i(TAG, "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<VestResponseModel> call, Throwable t) {
                Log.i(TAG, "onResponse: " + t.getMessage());
            }
        });
    }
}
