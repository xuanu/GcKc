package zeffect.cn.gckc;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class KcFragment extends Fragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.kc_layout, container, false);
            initView();
        }
        return rootView;
    }

    private MapView mMapView = null;
    private BaiduMap baiduMap;


    private void initView() {
        mMapView = (MapView) rootView.findViewById(R.id.bmapView);
        baiduMap = mMapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        LatLng cenpt = new LatLng(28.247259, 108.131532);
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                //要移动的点
                .target(cenpt)
                //放大地图到20倍
                .zoom(17)
                .build();

        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);

        //改变地图状态
        baiduMap.setMapStatus(mMapStatusUpdate);
        drawGcxy();
        mLocationClient = new LocationClient(this.getContext().getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        mLocationClient.start();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }


    private void drawGcxy() {
        List<LatLng> pts = new ArrayList<>();
        pts.add(new LatLng(28.248458, 108.132272));
        pts.add(new LatLng(28.24633, 108.136998));
        pts.add(new LatLng(28.245483, 108.136548));
        pts.add(new LatLng(28.245165, 108.136791));
        pts.add(new LatLng(28.244314, 108.136301));
        pts.add(new LatLng(28.245002, 108.134675));
        pts.add(new LatLng(28.244823, 108.134294));
        pts.add(new LatLng(28.24392, 108.134226));
        pts.add(new LatLng(28.24322, 108.134091));
        pts.add(new LatLng(28.243061, 108.134365));
        pts.add(new LatLng(28.242621, 108.134249));
        pts.add(new LatLng(28.24208, 108.13539));
        pts.add(new LatLng(28.240986, 108.134878));
        pts.add(new LatLng(28.24134, 108.133822));
        pts.add(new LatLng(28.241587, 108.133966));
        pts.add(new LatLng(28.242072, 108.133018));
        pts.add(new LatLng(28.242764, 108.133072));
        pts.add(new LatLng(28.243989, 108.133449));
        pts.add(new LatLng(28.243989, 108.133449));
        pts.add(new LatLng(28.244733, 108.133638));
        pts.add(new LatLng(28.245855, 108.133252));
        pts.add(new LatLng(28.2464, 108.13309));
        pts.add(new LatLng(28.247259, 108.131532));


        OverlayOptions polygonOption = new PolygonOptions()
                .points(pts)
                .stroke(new Stroke(5, 0xAA00FF00))
                .fillColor(0xAAFFFF00);
        baiduMap.addOverlay(polygonOption);
    }

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();


    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            Log.e("zeffect", "loca:" + location.toString());
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息
            float radius = location.getRadius();    //获取定位精度，默认值为0.0f

            String coorType = location.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

            int errorCode = location.getLocType();

            if (location.getFloor() != null) {
                // 当前支持高精度室内定位
                String buildingID = location.getBuildingID();// 百度内部建筑物ID
                String buildingName = location.getBuildingName();// 百度内部建筑物缩写
                String floor = location.getFloor();// 室内定位的楼层信息，如 f1,f2,b1,b2
                mLocationClient.startIndoorMode();// 开启室内定位模式（重复调用也没问题），开启后，定位SDK会融合各种定位信息（GPS,WI-FI，蓝牙，传感器等）连续平滑的输出定位结果；
            }
        }
    }


}
