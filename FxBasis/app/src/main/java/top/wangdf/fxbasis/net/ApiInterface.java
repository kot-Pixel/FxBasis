package top.wangdf.fxbasis.net;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import top.wangdf.fxbasis.entity.VestResponseModel;

public interface ApiInterface {

    @GET("/{path}")
    Call<VestResponseModel> repo(@Path("path") String path);

    @GET("/{path}")
    Call<VestResponseModel> repo2(@Path("path") String path,
                                  @Query("vestCode") String vestCod0e,
                                  @Query("version") String version,
                                  @Query("channelCode") String channelCode,
                                  @Query("deviceId") String deviceId,
                                  @Query("timestamp") String timestamp);
}
