package sh.calaba.instrumentationbackend.actions.list;

import java.util.ArrayList;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

public class We7ClickTabFragmentListItem implements Action {

  @Override
  public Result execute(String... args) {

    int tabIndex = Integer.parseInt(args[0]);

    int listItemIndex = Integer.parseInt((args[1]));

    // Get tabhost view

    TabHost tabHost = (TabHost) InstrumentationBackend.solo.getView(TabHost.class, 0);

    if (tabIndex >= tabHost.getTabWidget().getTabCount())  {

      return new Result(false, "no tab at index: " + tabIndex);

    }

    ListView listView = (ListView) InstrumentationBackend.solo.getView(ListView.class, tabIndex);

    ArrayList<TextView> listTextViews = InstrumentationBackend.solo.clickInList(listItemIndex, tabIndex);

    StringBuilder sb = new StringBuilder();
    for (TextView tv : listTextViews) {
      sb.append(tv.getText() + ",");
    }

    return new Result(true, sb.toString());

  }

  @Override
  public String key() {
    return "click_tab_list_item";
  }

}
