package zeffect.cn.gckc.location;

import android.content.Context;
import android.support.annotation.RequiresPermission;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.blankj.utilcode.util.LogUtils;

/**
 * Created by zeffect on 18-12-29.
 */

public class BaiduLocation extends LocationAction {


    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    public BaiduLocation() {
    }


    @Override
    public void init(Context pConext, LocationListener tmpListener) {
        this.locationListener = tmpListener;
        initLocationOption(pConext);
    }

    @Override
    public void start() {
        if (mLocationClient != null) {
            if (!mLocationClient.isStarted()) {
                mLocationClient.start();
            }
        }
    }

    @Override
    public void stop() {
        if (mLocationClient != null) {
            if (mLocationClient.isStarted()) {
                mLocationClient.stop();
            }
        }
    }

    @Override
    public void destory() {
        if (mLocationClient != null) {
            mLocationClient = null;
        }
        myListener = null;
    }


    /**
     * 初始化定位参数配置
     */

    private void initLocationOption(Context pContext) {
        mLocationClient = new LocationClient(pContext.getApplicationContext());
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("GCJ02");
        option.setScanSpan(1000 * 10);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(false);
        option.setWifiCacheTimeOut(5 * 60 * 1000);
        option.setEnableSimulateGps(false);
        option.setIsNeedAddress(true);
        option.setIsNeedLocationPoiList(true);
        option.setIsNeedLocationDescribe(true);
        mLocationClient.setLocOption(option);
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            LogUtils.e("baidu location:" + location.getLocType());
            if (locationListener != null) {
                int errorCode = location.getLocType();
                LocModel locModel = new LocModel();
                if (errorCode == 61 || errorCode == 66 || errorCode == 161) {
                    locModel.setAddStr(location.getAddrStr());
                    locModel.setLocSuccess(true);
                    locModel.setLon(location.getLongitude());
                    locModel.setLat(location.getLatitude());
                } else {
                    locModel.setAddStr("定位失败");
                    locModel.setLocSuccess(false);
                }
                locationListener.location(locModel);
            }
        }
    }


}
