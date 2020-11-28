package top.wangdf.fxbasis;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import top.wangdf.fxbasis.common.Utils;
import top.wangdf.fxbasis.entity.VestEntity;
import top.wangdf.fxbasis.net.VestApi;

public class MainActivity extends AppCompatActivity {


    private static final String vestCode = "Q11KF9MU";

    private static final String version = "1.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VestApi api = new VestApi();
        //请求接口
        api.makeVestRequest(new VestEntity("Q11KF9MU","1.0", Utils.getDeviceId(MainActivity.this), Utils.getTimeStamp(),"google"));
    }
}