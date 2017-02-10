package cn.yzl.imgupload.eventbus;

import java.util.List;

/**
 * Created by 易点付 伊 on 2016/10/19.
 */

public class UploadImg {
    List<String> tempPah;

    public UploadImg(List<String> tempPah) {
        this.tempPah = tempPah;
    }

    public List<String> getTempPah() {
        return tempPah;
    }

    public void setTempPah(List<String> tempPah) {
        this.tempPah = tempPah;
    }
}
