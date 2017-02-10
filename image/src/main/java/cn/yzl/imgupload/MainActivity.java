package cn.yzl.imgupload;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.yzl.imgupload.eventbus.SelctEvent;
import cn.yzl.imgupload.eventbus.UploadImg;
import cn.yzl.imgupload.utils.ImageUtil;
import cn.yzl.imgupload.utils.ToastUtils;
import cz.msebera.android.httpclient.Header;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import me.nereo.multi_image_selector.bean.Image;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_IMAGE = 100;
    private Button button;

    private View proV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        proV = findViewById(R.id.pro_v);
        button = (Button) findViewById(R.id.but);
        button.setOnClickListener(this);
        loadingFinish();
    }

    @Override
    public void onClick(View v) {
        MultiImageSelector.create(this)
                .showCamera(true) // 是否显示相机. 默认为显示
                .count(9) // 最大选择图片数量, 默认为9. 只有在选择模式为多选时有效
//        .single() // 单选模式
                .multi() // 多选模式, 默认模式;
//                .origin(ArrayList < String >) // 默认已选择图片. 只有在选择模式为多选时有效
                .start(this, REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                // 获取返回的图片列表
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                loading();
                EventBus.getDefault().post(new SelctEvent(path));
                // 处理你自己的逻辑 ....
                Logger.d(path.size());
            }
        }
    }

    //压缩图片
    private void zoomImage(List<String> paths) {
        //获取图片 的bitmap
        List<String> temPaths = new ArrayList<>();
        for (int i = 0; i < paths.size(); i++) {
            Bitmap bitmap = getBitmapFromPath(paths.get(i));
            if (bitmap != null) {
                bitmap = ImageUtil.compressImage(bitmap);
                String temp = ImageUtil.savePhotoToSDCard(bitmap, ImageUtil.IMG_TEMP_DIR, "img" + i);
                temPaths.add(temp);
            }
        }
        EventBus.getDefault().post(new UploadImg(temPaths));
    }

    private void uploadImg(List<String> tempPah) {

        File[] imgs = new File[tempPah.size()];

        for (int i = 0; i < tempPah.size(); i++) {
            imgs[i] = new File(tempPah.get(i));
        }

        RequestParams params = new RequestParams();
        try {
            params.put("img", imgs);
        } catch (FileNotFoundException e) {
            ToastUtils.showShort(this, "程序异常,请重试");
            loadingFinish();
        }

        AsyncHttpClient client = new AsyncHttpClient();

        client.post("http://192.168.1.123:8080/Servlet3Upload/imageUpload", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                ToastUtils.showShort(MainActivity.this, "上传成功");
                loadingFinish();
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                ToastUtils.showShort(MainActivity.this, "网络错误");
                loadingFinish();
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
//                super.onProgress(bytesWritten, totalSize);
//                Logger.d("bytesWritten:::" + bytesWritten + "----" + "totalSize:::" + totalSize);
//
//                if (totalSize != 0) {
//                    Float a = Float.valueOf(bytesWritten / totalSize * 100);
//                    Logger.d("jd::::" + a);
//                    tv.setText(a.intValue());
//                }
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(SelctEvent event) {
        zoomImage(event.getPath());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UploadImg event) {
        uploadImg(event.getTempPah());
    }


    public Bitmap getBitmapFromPath(String path) {

        if (!new File(path).exists()) {
            Logger.d(path + ":::图片不存在");
            return null;
        }
        // Bitmap bitmap = Bitmap.createBitmap(1366, 768, Config.ARGB_8888);
        // Canvas canvas = new Canvas(bitmap);
        // Movie movie = Movie.decodeFile(path);
        // movie.draw(canvas, 0, 0);
        //
        // return bitmap;

        byte[] buf = new byte[1024 * 1024];// 1M
        Bitmap bitmap = null;

        try {
            FileInputStream fis = new FileInputStream(path);
            int len = fis.read(buf, 0, buf.length);
            bitmap = BitmapFactory.decodeByteArray(buf, 0, len);
            if (bitmap == null) {
                System.out.println("len= " + len);
                System.err.println("path: " + path + "  could not be decode!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void loading() {
        button.setVisibility(View.GONE);
        proV.setVisibility(View.VISIBLE);
    }

    private void loadingFinish() {
        button.setVisibility(View.VISIBLE);
        proV.setVisibility(View.GONE);
    }
}
