package zeffect.cn.gckc;

import android.app.Application;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.blankj.utilcode.util.LogUtils;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
        SDKInitializer.setCoordType(CoordType.BD09LL);
        LogUtils.getConfig().setGlobalTag("zeffect");
        LogUtils.getConfig().setLogSwitch(BuildConfig.DEBUG);
    }
}
