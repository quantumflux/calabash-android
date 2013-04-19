package sh.calaba.instrumentationbackend.actions.list;

import java.util.ArrayList;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

public class We7PlayAFavouriteStationFromAllRadio implements Action {

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

    // goto favourite tab
    InstrumentationBackend.solo.clickOnText("Favourite");

    // get list views
    ArrayList<ListView> listViews = InstrumentationBackend.solo.getCurrentListViews();

    InstrumentationBackend.log("List items found: " + listViews.size());

    int listIndex;
    boolean[] isListEmpty = getEmptyLists();
    if (isListEmpty[0]) {
      listIndex = 0;
    } else {
      listIndex = 1;
    }

    ArrayList<TextView> itemText;

    if (listIndex == 0) {

      itemText = InstrumentationBackend.solo.clickInList(itemIndex);

    } else {

      itemText = InstrumentationBackend.solo.clickInList(itemIndex, listIndex);

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
    return "play_a_favourite_station";
  }

}
