package com.felixmasila.v2b.activity;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by felixmasila on 12/10/16.
 */

public class GCMTokenRefreshListenerService extends InstanceIDListenerService {
    //If the token is changed registering the device again
    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, GCMRegistrationIntentService.class);
        startService(intent);
    }
}
