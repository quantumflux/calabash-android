package sh.calaba.instrumentationbackend.actions.we7;

import java.util.ArrayList;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;
import android.widget.TextView;

public class We7PressListItemByListContentDescription extends We7Action implements Action {

  @Override
  public Result execute(final String... args) {

	int listItem = Integer.parseInt(args[0]);
	  
	String listContentDescription = args[1];
	
    int listViewIndex = getListViewIndex(listContentDescription);
    
    if (listViewIndex == -1) {
    	return new Result(false, "Could not find list with content description " + listContentDescription);
    }
    
    InstrumentationBackend.log("Found list with content description " + listContentDescription);
    
    ArrayList<TextView> listTextViews = InstrumentationBackend.solo.clickInList(listItem, listViewIndex);
    
    if (listTextViews == null) {
    	InstrumentationBackend.log("List item does not exist");
    	return new Result(false, "List item does not exist");
    }
    
    StringBuilder sb = new StringBuilder();
    for (TextView tv : listTextViews) {
      sb.append(tv.getText() + ",");
    }

    InstrumentationBackend.log("Clicked text = " + sb.toString());
    
    return new Result(true, "Clicked text = " + sb.toString());

  }

  @Override
  public String key() {

    InstrumentationBackend.log("returning key name press_item_by_list_content_description");

    return "press_item_by_list_content_description";
  }

}
