package sh.calaba.instrumentationbackend.actions.we7;

import java.util.ArrayList;
import java.util.HashMap;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import android.widget.ListView;

public class We7Action {
	
	protected static final String LAST_TRACK_NAME = "lasttrackname";
	protected static final String LAST_PLAYABLE_NAME = "lastplayablename";
	
	protected void setValue(String key, String value) {
		We7ActionHelper.setValue(key, value);
	}
	
	protected String getValue(String key) {
		return We7ActionHelper.getValue(key);
	}
	
	protected int getListViewIndex(final String listContentDescription) {

		ArrayList<ListView> listViews = InstrumentationBackend.solo.getCurrentListViews();

		InstrumentationBackend.log("Found " + listViews.size() + " list views. Looking for index for " + listContentDescription);

		for (int i = 0; i < listViews.size(); i++) {

			String contentDescription = listViews.get(i).getContentDescription().toString();

		 	InstrumentationBackend.log("Content descrition for list index " + i + " = " + contentDescription);

		  	if (contentDescription.equalsIgnoreCase(listContentDescription)) {

		  		InstrumentationBackend.log("Found list for " + listContentDescription);

		        return i;

			}
			
		}

		return -1;

	  }
	
	protected ListView getListView(final String tabName) {

	    ArrayList<ListView> listViews = InstrumentationBackend.solo.getCurrentListViews();

	    InstrumentationBackend.log("Found " + listViews.size() + " list views. Looking for index for " + tabName);

	    for (int i = 0; i < listViews.size(); i++) {

	      String contentDescription = listViews.get(i).getContentDescription().toString();

	      InstrumentationBackend.log("Content descrition for list index " + i + " = " + contentDescription);

	      if (contentDescription.equalsIgnoreCase(tabName)) {

	        InstrumentationBackend.log("Found list for " + tabName);

	        return listViews.get(i);

	      }

	    }

	    return null;

	  }

}
