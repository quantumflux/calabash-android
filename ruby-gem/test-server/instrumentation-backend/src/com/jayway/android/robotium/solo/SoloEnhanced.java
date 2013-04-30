package com.jayway.android.robotium.solo;

import java.util.ArrayList;
import java.util.List;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.TestHelpers;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

public class SoloEnhanced extends Solo {
	// private Pincher pincher;
	private MapViewUtils mapViewUtils;

	public SoloEnhanced(Instrumentation instrumentation, Activity activity) {
		super(instrumentation, activity);
		this.mapViewUtils = new MapViewUtils(instrumentation, viewFetcher,
				sleeper, waiter);
		// this.pincher = new Pincher(instrumentation, viewFetcher);
	}

	public void setMapCenter(double lat, double lon) {
		mapViewUtils.setCenter(lat, lon);
	}

	/** @return {lat,lon} */
	public double[] getMapCenter() {
		return mapViewUtils.getMapCenter();
	}

	public void panMapTo(double lat, double lon) {
		mapViewUtils.panTo(lat, lon);
	}

	public boolean zoomInOnMap() {
		return mapViewUtils.zoomIn();
	}

	public boolean zoomOutOnMap() {
		return mapViewUtils.zoomOut();
	}

	public int setMapZoom(int zoomLevel) {
		return mapViewUtils.setZoom(zoomLevel);
	}

	public int getMapZoom() {
		return mapViewUtils.getZoom();
	}

	public List<String> getMapMarkerItems() {
		return mapViewUtils.getMarkerItems();
	}

	public String getMapMarkerItem(String title) {
		return mapViewUtils.getMarkerItem(title);
	}

	/**
	 * @param title
	 * @param timeout
	 *            in ms
	 * @return
	 */
	public boolean tapMapMarkerItem(String title, long timeout) {
		return mapViewUtils.tapMarkerItem(title, timeout);
	}

	public boolean tapMapAwayFromMarkers(int step) {
		return mapViewUtils.tapAwayFromMarkerItems(step);
	}

	/**
	 * @return [top, right, bottom, left] in decimal degrees
	 */
	public List<String> getMapBounds() {
		return mapViewUtils.getBounds();
	}

	/**
	 * Get all Views that are children of the parent view
	 * 
	 * @param parentView
	 * @return
	 */
	public ArrayList<View> getCurrentViews(View parentView) {
		
		ArrayList<View> currentViews = getViews(parentView);
		
		return currentViews;
		
	}
	
	public static enum ActionBarItems {
		
		UP("android:id/up"),
		HOME("android:id/home"),
		ACTION_BAR("android:id/action_bar"),
		SEARCH("app:id/menu_search");
		
		private String mItemResourceIdName;

		ActionBarItems(String id) {
			
			mItemResourceIdName = id;
			
		}
		
		public String getId(){
			return mItemResourceIdName;
		}
		
	}
	
	/**
	 * Click on action bar item
	 * 
	 * @param item
	 * @return
	 */
	public boolean clickOnActionBar(ActionBarItems item) {
		
		String resourceName = item.getId();
		
		InstrumentationBackend.log("clickOnActionBar item " + resourceName);

		Window window = InstrumentationBackend.solo.getCurrentActivity().getWindow();
		
		View decorView = window.getDecorView();
		
		int resId = decorView.getContext().getResources().getIdentifier(resourceName, null, null);
		
		InstrumentationBackend.log("resource resId = " + resId);
		
		ArrayList<View> currentViews = this.getCurrentViews(decorView);
		for (int i = 0; i < currentViews.size(); i++) {
			
			final View v = currentViews.get(i);
			
			InstrumentationBackend.log(v.toString());
			
			if (v.getId() == resId) {
			
				InstrumentationBackend.log("view found - performing click on UI thread");
				
				InstrumentationBackend.solo.getCurrentActivity().runOnUiThread(new Runnable() {
					public void run() {
						v.performClick();
					}
				});
				
				return true;
				
			}
			
		}
		
		InstrumentationBackend.log("view not found");
		
		return false;
		
	}
	
	
	/**
	 * Get all Image buttons that are children of the parent view
	 * 
	 * @param parentView
	 * @return
	 */
	public ArrayList<ImageButton> getCurrentImageButtons(View parentView) {
		
		ArrayList<ImageButton> currentImageButtons = new ArrayList<ImageButton>();
		
		ArrayList<View> currentViews = getViews(parentView);
		
		for (View view : currentViews) {
			
			if (view instanceof ImageButton) {
				
				currentImageButtons.add((ImageButton) view);
				
			}
			
		}
		
		return currentImageButtons;
		
	}

	// /**
	// * Sorry, doesn't work yet
	// * @param togetherOrApart - {@link Pincher#TOGETHER} or {@link
	// Pincher#APART}
	// */
	// public void pinch(int togetherOrApart) {
	// switch( togetherOrApart ) {
	// case Pincher.TOGETHER: pincher.pinch(Pincher.Direction.TOGETHER); break;
	// case Pincher.APART: pincher.pinch(Pincher.Direction.APART); break;
	// }
	// }
	//
	// /**
	// * Sorry, doesn't work yet
	// * @param togetherOrApart - {@link Pincher#TOGETHER} or {@link
	// Pincher#APART}
	// * @param view
	// */
	// public void pinch(int togetherOrApart, View view) {
	// switch( togetherOrApart ) {
	// case Pincher.TOGETHER: pincher.pinch(Pincher.Direction.TOGETHER, view);
	// break;
	// case Pincher.APART: pincher.pinch(Pincher.Direction.APART, view); break;
	// }
	// }

}
