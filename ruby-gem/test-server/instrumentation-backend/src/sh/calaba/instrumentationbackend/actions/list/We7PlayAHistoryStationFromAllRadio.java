package sh.calaba.instrumentationbackend.actions.list;

import java.util.ArrayList;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

public class We7PlayAHistoryStationFromAllRadio implements Action {

  @Override
  public Result execute(final String... args) {

    int itemIndex = 1;
    if (args.length > 1) {

      itemIndex = Integer.parseInt(args[0]);

    }

    TabHost tabHost = (TabHost) InstrumentationBackend.solo.getView(TabHost.class, 0);

    if (tabHost == null) {
      InstrumentationBackend.log("No tab host found");
      return new Result(false, "No TabHost found - am I on All Stations page?");
    }

    InstrumentationBackend.log("Tab host found");

    // goto history tab
    InstrumentationBackend.solo.clickOnText("Favourite");

    // get list views
    ArrayList<ListView> listViews = InstrumentationBackend.solo.getCurrentListViews();

    InstrumentationBackend.log("List items found: " + listViews.size());

    boolean[] isListEmpty = getEmptyLists();

    ArrayList<TextView> itemText;

    if (isListEmpty[0]) {

      return new Result(false, "History is empty");

    } else {

      itemText = InstrumentationBackend.solo.clickInList(itemIndex);

    }

    InstrumentationBackend.log("Text of clicked item:");
    for (int i = 0 ; i < itemText.size(); i++) {
      InstrumentationBackend.log(itemText.get(i).getText().toString());
    }

    return new Result(true);

  }

  protected boolean[] getEmptyLists() {

    ArrayList<ListView> listViews = InstrumentationBackend.solo.getCurrentListViews();

    boolean[] isListEmpty = new boolean[listViews.size()];
    for (int i = 0; i < listViews.size(); i++) {

      isListEmpty[i] = listViews.get(i).getAdapter().isEmpty();

      InstrumentationBackend.log("List view " + i + " is empty = " + isListEmpty[i] + " (count = " + listViews.get(i).getCount() + ")");

    }

    return isListEmpty;

  }

  @Override
  public String key() {
    return "play_a_history_station";
  }

}
