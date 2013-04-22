package sh.calaba.instrumentationbackend.actions.we7;

import java.util.ArrayList;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

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

    int listIndex = getTabListIndex(tabName);

    if (listIndex == -1) {
      return new Result(false, "Could not find list.");
    }

    ArrayList<TextView> itemText = InstrumentationBackend.solo.clickInList(itemIndex, listIndex);

    InstrumentationBackend.log("Text of clicked item:");
    for (int i = 0 ; i < itemText.size(); i++) {
      InstrumentationBackend.log(itemText.get(i).getText().toString());
    }

    return new Result(true);

  }

  protected int getTabListIndex(final String tabName) {

    ArrayList<ListView> listViews = InstrumentationBackend.solo.getCurrentListViews();

    for (int i = 0; i < listViews.size(); i++) {

      String contentDescription = listViews.get(i).getContentDescription().toString();

      InstrumentationBackend.log("Content descrition for list index " + i + " = " + contentDescription);

      if (contentDescription.equalsIgnoreCase(tabName)) {

        InstrumentationBackend.log("Found list for " + tabName);

        return i;

      }

    }

    return -1;

  }


  @Override
  public String key() {
    return "play_an_all_stations_station";
  }

}
