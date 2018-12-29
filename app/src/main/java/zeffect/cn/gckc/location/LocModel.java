package zeffect.cn.gckc.location;

/**
 * Created by zeffect on 18-12-29.
 */

public class LocModel {
    private boolean locSuccess;
    private double lat;
    private double lon;
    private String addStr;


    public boolean isLocSuccess() {
        return locSuccess;
    }

    public void setLocSuccess(boolean locSuccess) {
        this.locSuccess = locSuccess;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getAddStr() {
        return addStr;
    }

    public void setAddStr(String addStr) {
        this.addStr = addStr;
    }
}
