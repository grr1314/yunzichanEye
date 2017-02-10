package org.lhc.flashlight;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private Camera camera = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.camera = Camera.open();
		if (this.camera != null) {
			Parameters mParameters = camera.getParameters();
			mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH); 
			camera.setParameters(mParameters);
			camera.startPreview();
			camera.autoFocus(new AutoFocusCallback() {
				public void onAutoFocus(boolean success, Camera camera) {
				}
			});
			TextView info = (TextView)this.findViewById(R.id.flashlight_info);
			info.setText("Flashlight open");
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (this.camera != null) {
				Parameters mParameters = camera.getParameters();
				mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
				camera.setParameters(mParameters);
				camera.stopPreview();
				camera.release();
			}
//			Log.i("Flashlight", "exit");
			this.finish();
			System.exit(0);
		}
		
		return super.onKeyDown(keyCode, event);
	}
}
