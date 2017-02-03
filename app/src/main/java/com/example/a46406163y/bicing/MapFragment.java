package com.example.a46406163y.bicing;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewGroupCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;


public class MapFragment extends Fragment {

    private MapView map;
    private MyLocationNewOverlay myLocationOverlay;
    private MinimapOverlay mMinimapOverlay;
    private ScaleBarOverlay mScaleBarOverlay;
    private CompassOverlay mCompassOverlay;
    private IMapController mapController;

    public MapFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);

        map = (MapView) view.findViewById(R.id.map);

        initializeMap();

        setZoom();

        setOverlays();

        map.invalidate();

        return view;
    }




    private void initializeMap() {
        map.setTileSource(TileSourceFactory.MAPQUESTOSM);
        map.setTilesScaledToDpi(true);
        map.setMultiTouchControls(true);
        map.setBuiltInZoomControls(true);
    }

    private void setZoom() {
        mapController = map.getController();
        mapController.setZoom(15);
    }

    private void setOverlays(){
            final DisplayMetrics dm = getResources().getDisplayMetrics();
            myLocationOverlay = new MyLocationNewOverlay(
                    getContext(),
                    new GpsMyLocationProvider(getContext()),
                    map
            );
            myLocationOverlay.enableMyLocation();
            myLocationOverlay.runOnFirstFix(new Runnable() {
                public void run() {
                    mapController.animateTo(myLocationOverlay
                            .getMyLocation());
                }
            });

            mScaleBarOverlay = new ScaleBarOverlay(map);
            mScaleBarOverlay.setCentred(true);
            mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);

            mCompassOverlay = new CompassOverlay(

                    getContext(),
                    new InternalCompassOrientationProvider(getContext()),
                    map
            );
            mCompassOverlay.enableCompass();
        map.getOverlays().add(myLocationOverlay);
        map.getOverlays().add(this.mMinimapOverlay);
        map.getOverlays().add(this.mScaleBarOverlay);
        map.getOverlays().add(this.mCompassOverlay);
        }
    }

