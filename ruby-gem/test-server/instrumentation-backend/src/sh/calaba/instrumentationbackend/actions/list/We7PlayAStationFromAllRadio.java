package sh.calaba.instrumentationbackend.actions.list;

import java.util.ArrayList;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;
import android.widget.ListView;
import android.widget.TabHost;

public class We7PlayAStationFromAllRadio implements Action {

  @Override
  public Result execute(final String... args) {

    String tabName = args[0];

    int itemIndex = 1;
    if (args.length > 1) {

      itemIndex = Integer.parseInt(args[1]);

    }

    TabHost tabHost = (TabHost) InstrumentationBackend.solo.getView(TabHost.class, 0);

    if (tabHost == null) {
      InstrumentationBackend.log("No tab host found");
      return new Result(false, "No TabHost found - am I on All Stations page?");
    }

    InstrumentationBackend.log("Tab host found");

    // goto tab
    InstrumentationBackend.solo.clickOnText(tabName);

    // get list views
    ArrayList<ListView> listViews = InstrumentationBackend.solo.getCurrentListViews();

    InstrumentationBackend.log("List items found: " + listViews.size());

    ListView targetListView = null;
    for (int i = 0; i < listViews.size(); i++) {

      ListView lv = listViews.get(i);

      InstrumentationBackend.log("Found list view with content description: " + lv.getContentDescription());

      if (lv.getContentDescription().equals(tabName)) {

        InstrumentationBackend.log("Found correct list");

        targetListView = lv;
        break;

      }

    }

    if (targetListView == null) {
      return new Result(false, "Target list not found");
    }

    InstrumentationBackend.log("Selecting and clicking on list itemIndex: " + itemIndex);
    targetListView.setSelection(itemIndex);
    InstrumentationBackend.solo.clickOnView(targetListView.getSelectedView());

    return new Result(true);

  }

  @Override
  public String key() {
    return "play_a_station";
  }

}
