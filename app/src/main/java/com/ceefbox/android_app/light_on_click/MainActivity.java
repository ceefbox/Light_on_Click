package com.ceefbox.android_app.light_on_click;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent mTorchService = new Intent(this, TorchService.class);
        this.startService(mTorchService);
        finish();
    }
}
