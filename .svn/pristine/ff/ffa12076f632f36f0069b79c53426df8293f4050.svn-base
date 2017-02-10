package com.zchk.yunzichan.ui.activity.tools;

import android.annotation.TargetApi;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.zchk.yunzichan.R;

/**
 * Created by admin on 2016/11/14.
 */
public class LightActivity extends AppCompatActivity {
    Button btn_OpenLight;
    private Camera camera = null;
    private boolean isTurnOn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.light_activity);
        initViews();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initViews() {
        isTurnOn = false;
        btn_OpenLight = (Button) findViewById(R.id.btn_OpenLight);
        btn_OpenLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isTurnOn) {
                    openLight();
                    return;
                } else {
                    closeLight();
                }
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        closeLight();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeLight();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            closeLight();
            this.finish();
            System.exit(0);
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 打开手电筒
     */
    private void openLight() {
        camera = Camera.open();
        if (camera != null) {
            Camera.Parameters mParameters = camera.getParameters();
            mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(mParameters);
            camera.startPreview();
            camera.autoFocus(new Camera.AutoFocusCallback() {
                public void onAutoFocus(boolean success, Camera camera) {
                }
            });
            btn_OpenLight.setText("关闭手电筒");
            isTurnOn = true;
        }
    }

    /**
     * 关闭手电筒
     */
    private void closeLight() {
        if (isTurnOn)
            if (this.camera != null) {
                Camera.Parameters mParameters = camera.getParameters();
                mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(mParameters);
                camera.stopPreview();
                camera.release();
                btn_OpenLight.setText("打开手电筒");
                isTurnOn = false;
            }
    }
}
