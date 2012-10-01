package com.SmartCampus;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MyItemizedOverlay extends ItemizedOverlay<OverlayItem> {

    private ArrayList<OverlayItem> myOverlays ;

    public MyItemizedOverlay(Drawable defaultMarker) {
        super(boundCenterBottom(defaultMarker));
        myOverlays = new ArrayList<OverlayItem>();
        populate();
    }

    public void addOverlay(OverlayItem overlay){
        myOverlays.add(overlay);
        populate();
    }

    @Override
    protected OverlayItem createItem(int i) {
        return myOverlays.get(i);
    }

    // Removes overlay item i
    public void removeItem(int i){
        myOverlays.remove(i);
        populate();
    }

    // Returns present number of items in list
    @Override
    public int size() {
        return myOverlays.size();
    }
}