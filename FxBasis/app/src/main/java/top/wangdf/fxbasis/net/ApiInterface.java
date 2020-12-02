package top.wangdf.fxbasis.net;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
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

/*
    马甲code：Q11KF9MU*/
    @GET("admin/client/vestSign.do?vestCode=Q11KF9MU&version=1.0&channelCode=google&deviceId=ebe2f38b83fcd5d1&timestamp=1584324183767")
    Call<VestResponseModel> repo3();

    @HTTP(method = "GET", path = "/admin/client/vestSign.do")
    Call<VestResponseModel> repo4(@Query("vestCode") String code
            , @Query("version") String version, @Query("channelCode") String channelCode, @Query("deviceId") String deviceId, @Query("timestamp") String timestamp);

    /**
     * 三方请求
     * id:
     * name:
     * email:
     * type: 0 : Facebook | 1 : Google
     * sign: H5传过来的值
     */
    @HTTP(method = "GET", path = "/user/google/doLogin2.do")
    Call<String> googleTripartiteLogin(@Query("id") String id,
                                       @Query("name") String name,
                                       @Query("email") String email,
                                       @Query("type") int type,
                                       @Query("sign") String sign);

}
