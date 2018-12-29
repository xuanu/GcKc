package zeffect.cn.gckc.location;

import android.Manifest;
import android.content.Context;
import android.support.annotation.RequiresPermission;

/**
 * Created by zeffect on 18-12-29.
 */

public abstract class LocationAction {

    protected LocationListener locationListener;


    public abstract void init(Context pConext, LocationListener tmpListener);

    @RequiresPermission(anyOf = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public abstract void start();

    public abstract void stop();

    public abstract void destory();
}
