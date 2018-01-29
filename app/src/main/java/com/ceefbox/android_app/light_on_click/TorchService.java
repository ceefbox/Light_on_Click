package com.ceefbox.android_app.light_on_click;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by Christian on 20.01.2018.
 */

public class TorchService extends Service {

    private String TAG = "TorchService";

    private CameraManager camManager;
    private String camId;
    private Toast currToast;
    MyTorchCallback torchCallback;
    private boolean isOn = false;


    @Override
    public void onCreate() {
        super.onCreate();
        camManager = (CameraManager) this.getSystemService(Context.CAMERA_SERVICE);
        torchCallback = new MyTorchCallback(this);
        camManager.registerTorchCallback(torchCallback, new Handler());

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Log.i(TAG, "onStartCommand");

        if (currToast != null){
            currToast.cancel();
        }
        if (isOn){
            this.stopSelf();

        }else {
            initLight();
        }

        return START_STICKY;
    }

    private void initLight(){
        currToast = Toast.makeText(this, getResources().getText(R.string.light_on), Toast.LENGTH_SHORT);
        try {
            camId = camManager.getCameraIdList()[0];
            setLightOn(camId);
            currToast.show();
        } catch (CameraAccessException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void uninitLight(){
        if (currToast != null){
            currToast.cancel();
        }
        currToast = Toast.makeText(this,getResources().getText(R.string.light_off), Toast.LENGTH_SHORT);
        setLightOff(camId);
        currToast.show();
    }

    private void setLightOn(String id){
        try {
            camManager.setTorchMode(id, true);
            isOn = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setLightOff(String id){
        try {
            camManager.setTorchMode(id, false);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        camManager.unregisterTorchCallback(torchCallback);
        uninitLight();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
