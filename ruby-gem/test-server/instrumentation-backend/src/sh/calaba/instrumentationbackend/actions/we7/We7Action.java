package sh.calaba.instrumentationbackend.actions.we7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.TestHelpers;
import android.app.Activity;
import android.os.StrictMode;
import android.view.View;
import android.widget.ListView;

public class We7Action {

	protected static final String LAST_CLICK_TEXT = "lastclicktext";
	protected static final String LAST_LONG_CLICK_TEXT = "lastlongclicktext";

	// This MUST be the same value as set in com.we7.player.ui.fragment.We7Fragment.CONTENT_AVAILABILITY_KEY
	public static final Integer CONTENT_AVAILABILITY_KEY = 100;
	
	public We7Action() {

		StrictMode.enableDefaults();

	}

	protected void setValue(String key, String value) {
		We7ActionHelper.setValue(key, value);
	}

	protected String getValue(String key) {
		return We7ActionHelper.getValue(key);
	}

	protected int getListViewIndex(final String listContentDescription) {

		ArrayList<ListView> listViews = InstrumentationBackend.solo
				.getCurrentListViews();

		InstrumentationBackend.log("Found " + listViews.size()
				+ " list views. Looking for index for "
				+ listContentDescription);

		for (int i = 0; i < listViews.size(); i++) {

			String contentDescription = listViews.get(i)
					.getContentDescription().toString();

			InstrumentationBackend.log("Content descrition for list index " + i
					+ " = " + contentDescription);

			if (contentDescription.equalsIgnoreCase(listContentDescription)) {

				InstrumentationBackend.log("Found list for "
						+ listContentDescription);

				return i;

			}

		}

		return -1;

	}

	protected ListView getListView(final String tabName) {

		ArrayList<ListView> listViews = InstrumentationBackend.solo
				.getCurrentListViews();

		InstrumentationBackend.log("Found " + listViews.size()
				+ " list views. Looking for index for " + tabName);

		for (int i = 0; i < listViews.size(); i++) {

			String contentDescription;
			if (listViews.get(i).getContentDescription() != null) {

				contentDescription = listViews.get(i).getContentDescription().toString().replaceAll("\n", " ");

				InstrumentationBackend.log("Content descrition for list index " + i + " = " + contentDescription);

				if (contentDescription.equalsIgnoreCase(tabName)) {

					InstrumentationBackend.log("Found list for " + tabName);

					return listViews.get(i);

				}

			}

		}

		return null;

	}

	protected boolean pressView(String viewId) {

		InstrumentationBackend.log("Clicking on " + viewId);

		final View view = TestHelpers.getViewById(viewId);

		if (view == null) {
			return false;
		}

		try {
			InstrumentationBackend.log("Clicking on view: " + view.getClass());
			InstrumentationBackend.log("" + view.getLeft());
			InstrumentationBackend.log("" + view.getTop());
			InstrumentationBackend.log("" + view.getWidth());
			InstrumentationBackend.log("" + view.getHeight());
			int[] xy = new int[2];

			view.getLocationOnScreen(xy);
			InstrumentationBackend.log("" + xy[0]);
			InstrumentationBackend.log("" + xy[1]);

			InstrumentationBackend.solo.clickOnView(view);
		} catch (junit.framework.AssertionFailedError e) {
			InstrumentationBackend
					.log("solo.clickOnView failed - using fallback");
			if (view.isClickable()) {
				InstrumentationBackend.solo.getCurrentActivity().runOnUiThread(
						new Runnable() {
							public void run() {
								view.performClick();
							}
						});
			}
		}

		return true;

	}

	protected Integer getTestViewTagId() {
	  
	  Activity currentActivity = InstrumentationBackend.solo.getCurrentActivity();
    Integer testTagArrayId = null;
    
    InstrumentationBackend.log("currentActivity class = " + currentActivity.getClass().toString());
    
    try {
      Method method = currentActivity.getClass().getMethod("getTestItemsTagId");
      testTagArrayId = (Integer) method.invoke(null);
    } catch (SecurityException se) {
      // TODO Auto-generated catch block
      se.printStackTrace();
      InstrumentationBackend.log("se thrown " + se.getMessage());
    } catch (NoSuchMethodException nsme) {
      // TODO Auto-generated catch block
      nsme.printStackTrace();
      InstrumentationBackend.log("nsmee thrown " + nsme.getMessage());
    } catch (IllegalArgumentException iarge) {
      // TODO Auto-generated catch block
      iarge.printStackTrace();
      InstrumentationBackend.log("iarge thrown " + iarge.getMessage());
    } catch (IllegalAccessException iacce) {
      // TODO Auto-generated catch block
      iacce.printStackTrace();
      InstrumentationBackend.log("iacce thrown " + iacce.getMessage());
    } catch (InvocationTargetException ite) {
      // TODO Auto-generated catch block
      ite.printStackTrace();
      InstrumentationBackend.log("ite thrown " + ite.getMessage());
    }
    
    if (testTagArrayId == null) {
      return -1;
    }
    
    InstrumentationBackend.log("Retireved testTagArrayId = " + testTagArrayId);
    
	  return testTagArrayId;
	  
	}
	
}
