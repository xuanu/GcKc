package zeffect.cn.gckc.location;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.blankj.utilcode.util.LogUtils;

/**
 * Created by zeffect on 18-12-29.
 */

public class GaodeLocation extends LocationAction {
    @Override
    public void init(Context pConext, LocationListener tmpListener) {
        initLocation(pConext);
        this.locationListener = tmpListener;
    }

    @Override
    public void start() {
        if (mLocationClient != null) {
            if (!mLocationClient.isStarted()) {
                mLocationClient.startLocation();
            }
        }
    }

    @Override
    public void stop() {
        if (mLocationClient != null) {
            if (mLocationClient.isStarted()) {
                mLocationClient.stopLocation();
            }
        }
    }

    @Override
    public void destory() {
        if (mLocationClient != null) {
            mLocationClient.onDestroy();
            mLocationClient = null;
        }
        mAMapLocationListener = null;
    }

    public AMapLocationClient mLocationClient = null;

    private void initLocation(Context pContext) {
        mLocationClient = new AMapLocationClient(pContext.getApplicationContext());
        mLocationClient.setLocationListener(mAMapLocationListener);
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(false);
//        option.setOnceLocationLatest(true);//3秒内高精度定位一次
        option.setInterval(1000 * 10);
        option.setNeedAddress(true);
        option.setMockEnable(false);//不允许模拟位置
        mLocationClient.setLocationOption(option);
    }

    private AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            LogUtils.e("gaode location:" + amapLocation.getErrorCode());
            if (locationListener != null) {
                LocModel locModel = new LocModel();
                if (amapLocation.getErrorCode() == 0) {
                    locModel.setAddStr(amapLocation.getAddress());
                    locModel.setLocSuccess(true);
                    locModel.setLat(amapLocation.getLatitude());
                    locModel.setLon(amapLocation.getLongitude());
                } else {
                    locModel.setAddStr("定位失败");
                    locModel.setLocSuccess(false);
                }
                locationListener.location(locModel);
            }
        }
    };

}
