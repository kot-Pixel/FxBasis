package top.wangdf.fxbasis.net;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import top.wangdf.fxbasis.entity.VestResponseModel;

public interface ApiInterface {

    @GET("/{path}")
    Call<VestResponseModel> repo(@Path("path") String path);

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
    @GET("/{path}")
    Call<VestResponseModel> repo2(@Path("path") String path,
                                  @Query("vestCode") String vestCod0e,
                                  @Query("version") String version,
                                  @Query("channelCode") String channelCode,
                                  @Query("deviceId") String deviceId,
                                  @Query("timestamp") String timestamp);
/*
        @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
            Call<> getCall();
    */
    @Get("admin/client/vestSign.do?vestCode=CUHX4A73&version=1.0&channelCode=google&deviceId=ebe2f38b83fcd5d1&timestamp=1584324183767")
    Call<VestResponseModel> repo3();
}
