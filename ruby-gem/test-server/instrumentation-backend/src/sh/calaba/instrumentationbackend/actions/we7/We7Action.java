package sh.calaba.instrumentationbackend.actions.we7;

import java.util.ArrayList;

import android.widget.ListView;
import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.actions.Action;

public abstract class We7Action implements Action {
	
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
