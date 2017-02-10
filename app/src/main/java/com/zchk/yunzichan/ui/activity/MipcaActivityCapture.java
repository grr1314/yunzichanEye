package com.zchk.yunzichan.ui.activity;

import java.io.IOException;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.zchk.yunzichan.R;
import com.zchk.yunzichan.minterface.DialogCallBack;
import com.zchk.yunzichan.ui.activity.check.CheckInsertActivity;
import com.zchk.yunzichan.ui.activity.main.HomesSpotCheckActivity;
import com.zchk.yunzichan.ui.activity.maintenance.MaintenanceInsertActivity;
import com.zchk.yunzichan.ui.activity.realtime.realTimeDataActivity;
import com.zchk.yunzichan.ui.activity.repair.AddRepairActivity;
import com.zchk.yunzichan.ui.view.SlideButton;
import com.zchk.yunzichan.ui.view.ViewfinderView;
import com.zchk.yunzichan.ui.view.tagView.Tag;
import com.zchk.yunzichan.utils.DialogUtil;
import com.zchk.yunzichan.utils.StringUtils;
import com.zchk.yunzichan.utils.camera.CameraManager;
import com.zchk.yunzichan.utils.decoding.CaptureActivityHandler;
import com.zchk.yunzichan.utils.decoding.InactivityTimer;

/**
 * Initial the camera
 *
 * @author Ryan.Tang
 */
public class MipcaActivityCapture extends Activity implements Callback {

    private static final String TAG = "MipcaActivityCapture";

    public String classNameCn;
    private final static int SCANNIN_GREQUEST_CODE = 1;

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private String device;

    private String taskId;
    private SlideButton btn_switch;
    private Camera camera = null;
    private Camera.Parameters parameter;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        CameraManager.init(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);

        Button mButtonBack = (Button) findViewById(R.id.button_back);
        mButtonBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                MipcaActivityCapture.this.finish();

            }
        });
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);

        Intent intent = getIntent();

        btn_switch = (SlideButton) findViewById(R.id.btn_switch);
        btn_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_switch.setStatus(true);
            }
        });
        btn_switch.setOnSwitchChangedListener(new SlideButton.OnSwitchChangedListener() {
            @Override
            public void onSwitchChanged(SlideButton obj, int status) {
                if (status == 1) {
                    //打开或关闭手电
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    public void openLight() {
        if (camera != null) {
            parameter = camera.getParameters();
            parameter.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameter);
        }
    }

    public void offLight() {
        if (camera != null) {
            parameter = camera.getParameters();
            parameter.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameter);
        }
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        if (resultString.equals("")) {
            Toast.makeText(MipcaActivityCapture.this, "Scan failed!",
                    Toast.LENGTH_SHORT).show();
        } else {
            Intent resultIntent = getIntent();
            classNameCn = resultIntent.getStringExtra("ClassNamecn");
            device = resultIntent.getStringExtra("ID");
            taskId = resultIntent.getStringExtra("taskId");
            resultString = StringUtils.cutString(resultString);

            if (classNameCn.equals("HomeCheckIndexActivity")) {
                resultIntent.setClass(MipcaActivityCapture.this,
                        CheckInsertActivity.class);
                resultIntent.putExtra("ID", resultString);
                startActivity(resultIntent);
                overridePendingTransition(R.anim.in_from_right,
                        R.anim.out_to_left);
            } else if (classNameCn.equals("HomeMaintenanceIndexActivity")) {
                resultIntent.setClass(MipcaActivityCapture.this,
                        MaintenanceInsertActivity.class);
                resultIntent.putExtra("ID", resultString);
                startActivity(resultIntent);
                overridePendingTransition(R.anim.in_from_right,
                        R.anim.out_to_left);
            } else if (classNameCn.equals("RepairIndexActivity")) {
                resultIntent.setClass(MipcaActivityCapture.this,
                        AddRepairActivity.class);
                resultIntent.putExtra("ID", resultString);
                startActivity(resultIntent);
                overridePendingTransition(R.anim.in_from_right,
                        R.anim.out_to_left);
            } else if (classNameCn.equals("HomesSpotCheckActivity")) {
                resultIntent.setClass(MipcaActivityCapture.this,
                        HomesSpotCheckActivity.class);
                resultIntent.putExtra("ID", resultString);

                startActivity(resultIntent);
                overridePendingTransition(R.anim.in_from_right,
                        R.anim.out_to_left);
            } else if (classNameCn.equals("HomeSelfCheckActivity")) {
                Log.e(TAG, resultString);
                if (resultString.equals(device)) {
                    Log.e(TAG, "Mipcp");
                    showMyDialog(resultIntent, resultString);
                    return;
                }
            } else if (classNameCn.equals("realTimeDataActivity")) {

                resultIntent.setClass(MipcaActivityCapture.this,
                        realTimeDataActivity.class);
                resultIntent.putExtra("ID", resultString);
                startActivity(resultIntent);
                overridePendingTransition(R.anim.in_from_right,
                        R.anim.out_to_left);
            } else {
                Toast.makeText(MipcaActivityCapture.this, "ʧ��",
                        Toast.LENGTH_SHORT).show();
            }
        }

        MipcaActivityCapture.this.finish();
    }

    private void showMyDialog(final Intent resultIntent,
                              final String resultString) {
        DialogUtil.showMyDialog(MipcaActivityCapture.this,
                "��ǰ�豸��·���豸��һ�£��Ƿ�������", new DialogCallBack() {
                    @Override
                    public void sure() {
                        // TODO Auto-generated method stub
                        resultIntent.setClass(MipcaActivityCapture.this,
                                CheckInsertActivity.class);
                        resultIntent.putExtra("ID", resultString);
                        resultIntent.putExtra("taskId", taskId);
                        startActivity(resultIntent);
                        overridePendingTransition(R.anim.in_from_right,
                                R.anim.out_to_left);
                        MipcaActivityCapture.this.finish();
                    }

                    @Override
                    public void cancel() {
                        // TODO Auto-generated method stub
                        DialogUtil.dismissMyDialog();
                        MipcaActivityCapture.this.finish(); // �ͷŵ�ǰ�Ĵ���
                    }
                });
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
    }


}