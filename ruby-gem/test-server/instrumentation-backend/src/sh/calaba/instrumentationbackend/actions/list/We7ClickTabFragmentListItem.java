package sh.calaba.instrumentationbackend.actions.list;

import java.util.ArrayList;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;
import android.widget.TabHost;
import android.widget.TextView;

public class We7ClickTabFragmentListItem implements Action {

  @Override
  public Result execute(final String... args) {

    int listItemIndex = Integer.parseInt((args[0]));
    int tabIndex = Integer.parseInt(args[1]);

    InstrumentationBackend.log("Clicking " + listItemIndex + " on " + tabIndex);

    TabHost tabHost = (TabHost) InstrumentationBackend.solo.getView(TabHost.class, 0);

    // Check valid index
    if (tabIndex < 0 || tabIndex >= tabHost.getTabWidget().getTabCount())  {
      return new Result(false, "no tab at index: " + tabIndex);
    }

    InstrumentationBackend.log("Current tab index = " + tabHost.getCurrentTab());

    // Check tab is showing
    if (tabHost.getCurrentTab() != tabIndex) {
      return new Result(false, "tab " + tabIndex + " is not current tab (" + tabHost.getCurrentTab() + ")");
    }

    ArrayList<TextView> listTextViews = InstrumentationBackend.solo.clickInList(listItemIndex, tabIndex);

    StringBuilder sb = new StringBuilder();
    for (TextView tv : listTextViews) {
      sb.append(tv.getText() + ",");
    }

    InstrumentationBackend.log("Clicked text = " + sb.toString());

    return new Result(true, sb.toString());

  }

  @Override
  public String key() {
    return "click_tab_list_item";
  }

}
