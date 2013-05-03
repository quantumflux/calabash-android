package sh.calaba.instrumentationbackend.actions.we7;

import java.util.ArrayList;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;
import android.widget.TextView;

public class We7LongPressListIndexByContentDescriptions extends We7Action implements Action {

  @Override
  public Result execute(final String... args) {

    int listItemIndex = Integer.parseInt(args[0]);

    String listContentDescription = args[1];

    int listViewIndex = getListViewIndex(listContentDescription);
    
    if (listViewIndex < 0) {
      return new Result(false, "Could not find list with content description " + listContentDescription);
    }

    InstrumentationBackend.log("Found list with content description " + listContentDescription);
    
    ArrayList<TextView> listItemTextViews = InstrumentationBackend.solo.clickLongInList(listItemIndex + 1, listViewIndex);
    StringBuilder sb = new StringBuilder();
    for (TextView tv : listItemTextViews) {
      sb.append(tv.getText() + ",");
    }
    
    InstrumentationBackend.log("Clicked text = " + sb.toString());

    We7ActionHelper.setValue(LAST_LONG_CLICK_TEXT, sb.toString());

    return Result.successResult();

  }

  @Override
  public String key() {

    InstrumentationBackend.log("returning key name press_item_by_list_content_description");

    return "long_press_list_index_by_list_content_description";
  }

}
