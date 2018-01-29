package com.ceefbox.android_app.light_on_click;

import android.app.Service;
import android.hardware.camera2.CameraManager;

/**
 * Created by Christian on 20.01.2018.
 */

public class MyTorchCallback extends CameraManager.TorchCallback {
    Service service;

    public MyTorchCallback(Service service) {
        super();
        this.service = service;
    }

    @Override
    public void onTorchModeUnavailable(String cameraId) {
        if (cameraId != null){
            super.onTorchModeUnavailable(cameraId);
        }
        service.stopSelf();
    }

    @Override
    public void onTorchModeChanged(String cameraId, boolean enabled) {
        if (cameraId != null) {
            super.onTorchModeChanged(cameraId, enabled);
        }
    }
}
