package top.wangdf.fxbasis.services;

import com.google.firebase.iid.FirebaseInstanceIdService;

public class FireTokenService extends FirebaseInstanceIdService {
    public FireTokenService() {
        super();
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
    }
}
