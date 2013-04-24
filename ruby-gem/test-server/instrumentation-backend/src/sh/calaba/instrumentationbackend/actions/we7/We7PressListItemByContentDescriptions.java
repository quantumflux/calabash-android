package sh.calaba.instrumentationbackend.actions.we7;

import java.util.ArrayList;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;
import android.widget.ListView;
import android.widget.TextView;

public class We7PressListItemByContentDescriptions extends We7Action implements Action {

  @Override
  public Result execute(final String... args) {

	String listItemContentDescription = args[0];
	  
	String listContentDescription = args[1];
	
    int listViewIndex = getListViewIndex(listContentDescription);
    
    if (listViewIndex == -1) {
    	return new Result(false, "Could not find list with content description " + listContentDescription);
    }
    
    InstrumentationBackend.log("Found list with content description " + listContentDescription);
    
    ListView listView = getListView(listContentDescription);
    
    for (int i = 0; i < listView.getCount(); i++) {
    	
    	String contentDescription = "";
    	if (listView.getAdapter().getView(i, null, null).getContentDescription() != null) {
    		contentDescription = listView.getAdapter().getView(i, null, null).getContentDescription().toString();
    	}
    	
    	if (contentDescription.equalsIgnoreCase(listItemContentDescription)) {

    		ArrayList<TextView> listItemTextViews = InstrumentationBackend.solo.clickInList(i + 1, listViewIndex);
    		StringBuilder sb = new StringBuilder();
    	    for (TextView tv : listItemTextViews) {
    	      sb.append(tv.getText() + ",");
    	    }
    		
    	    InstrumentationBackend.log("Clicked text = " + sb.toString());
    	    
    		return Result.successResult();
    		
    	}
    	
    }
 
    return new Result(false, "Didn't find item with contentDescription matching " + listItemContentDescription);

  }

  @Override
  public String key() {

    InstrumentationBackend.log("returning key name press_item_by_list_content_description");

    return "press_item_by_list_content_descriptions";
  }

}
