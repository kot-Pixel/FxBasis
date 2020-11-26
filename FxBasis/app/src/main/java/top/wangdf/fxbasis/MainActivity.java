package top.wangdf.fxbasis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import top.wangdf.fxbasis.net.VestApi;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VestApi api = new VestApi();
        api.test();
    }
}