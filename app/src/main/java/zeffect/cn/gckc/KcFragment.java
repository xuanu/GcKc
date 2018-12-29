package zeffect.cn.gckc;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
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
import com.blankj.utilcode.util.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

import zeffect.cn.gckc.kc.KcViewModel;
import zeffect.cn.gckc.location.BaiduLocation;
import zeffect.cn.gckc.location.GaodeLocation;
import zeffect.cn.gckc.location.LocModel;
import zeffect.cn.gckc.location.LocationListener;

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
    private BaiduLocation baiduLocation = new BaiduLocation();
    private GaodeLocation gaodeLocation = new GaodeLocation();
    private TextView baiduLoactionTv, gaodeLoactionTv, pointSpaceTv;

    private KcViewModel kcViewModel;


    @SuppressLint("MissingPermission")
    private void initView() {
        kcViewModel = ViewModelProviders.of(this).get(KcViewModel.class);
        mMapView = (MapView) rootView.findViewById(R.id.bmapView);
        baiduLoactionTv = (TextView) rootView.findViewById(R.id.baidu_location_tv);
        gaodeLoactionTv = (TextView) rootView.findViewById(R.id.gaode_location_tv);
        pointSpaceTv = (TextView) rootView.findViewById(R.id.point_space_tv);
        kcViewModel.baiduLocationStr.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (s != null) {
                    baiduLoactionTv.setText(s);
                }
            }
        });
        kcViewModel.gaodeLocationStr.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (s != null) {
                    gaodeLoactionTv.setText(s);
                }
            }
        });
        kcViewModel.pointSpaceStr.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (s != null) {
                    pointSpaceTv.setText(s
                    );
                }
            }
        });
        baiduMap = mMapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        move2Point(28.248458, 108.132272, 17);
        drawGcxy();
        if (PermissionUtils.isGranted(Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            initlocation();
        } else {
            PermissionUtils.permission(Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .callback(new PermissionUtils.FullCallback() {
                        @Override
                        public void onGranted(List<String> permissionsGranted) {
                            initlocation();
                        }

                        @Override
                        public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                            Toast.makeText(getContext(), "权限被拒绝！", Toast.LENGTH_SHORT).show();
                        }
                    }).request();
        }
    }


    @SuppressLint("MissingPermission")
    private void initlocation() {
        baiduLocation.init(getActivity(), baiduListener);
        baiduLocation.start();
        gaodeLocation.init(getActivity(), gaodeListener);
        gaodeLocation.start();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        baiduLocation.destory();
        gaodeLocation.destory();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
        baiduLocation.start();
        gaodeLocation.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
        baiduLocation.stop();
        gaodeLocation.stop();
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


    private void move2Point(double lat, double lot, float zoom) {
        LatLng cenpt = new LatLng(lat, lot);
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(zoom)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        if (baiduMap != null) baiduMap.setMapStatus(mMapStatusUpdate);
    }


    private boolean inSchool() {
        return true;
    }


    private double inSamePos(DPoint dPoint1, DPoint dPoint2) {
        return CoordinateConverter.calculateLineDistance(dPoint1, dPoint2);
    }


    private LocModel baiduLocaModel, gaodeLocaModel;

    private void checkSame() {
        if (baiduLocaModel == null || gaodeLocaModel == null) return;
        if (!baiduLocaModel.isLocSuccess() || !gaodeLocaModel.isLocSuccess()) return;
        double space = CoordinateConverter.calculateLineDistance(new DPoint(baiduLocaModel.getLat(), baiduLocaModel.getLon()), new DPoint(gaodeLocaModel.getLat(), gaodeLocaModel.getLon()));
        kcViewModel.pointSpaceStr.postValue("两点相距：" + space + "米");
    }

    private LocationListener gaodeListener = new LocationListener() {
        @Override
        public void location(LocModel loc) {
            if (kcViewModel != null) {
                gaodeLocaModel = null;
                gaodeLocaModel = loc;
                checkSame();
                kcViewModel.gaodeLocationStr.postValue(loc.getAddStr());
            }
        }
    };

    private LocationListener baiduListener = new LocationListener() {
        @Override
        public void location(LocModel loc) {
            if (kcViewModel != null) {
                baiduLocaModel = null;
                baiduLocaModel = loc;
                checkSame();
                kcViewModel.baiduLocationStr.postValue(loc.getAddStr());
            }
        }
    };

}
