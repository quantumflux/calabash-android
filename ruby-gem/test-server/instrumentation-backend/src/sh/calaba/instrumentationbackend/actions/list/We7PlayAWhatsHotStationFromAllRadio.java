package sh.calaba.instrumentationbackend.actions.list;

import java.util.ArrayList;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

public class We7PlayAWhatsHotStationFromAllRadio implements Action {

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

    // goto themed tab
    InstrumentationBackend.solo.clickOnText("What's Hot");

    // get list views
    ArrayList<ListView> listViews = InstrumentationBackend.solo.getCurrentListViews();

    InstrumentationBackend.log("List items found: " + listViews.size());

    int listIndex = getWhatsHotListIndex();

    InstrumentationBackend.log("whatsHotIndex = " + listIndex);

    // goto hot tab
    InstrumentationBackend.solo.clickOnText("What's Hot");

    ArrayList<TextView> itemText = InstrumentationBackend.solo.clickInList(itemIndex, listIndex);

    InstrumentationBackend.log("Text of clicked item:");
    for (int i = 0 ; i < itemText.size(); i++) {
      InstrumentationBackend.log(itemText.get(i).getText().toString());
    }

    return new Result(true);

  }

  protected int getWhatsHotListIndex() {

    ArrayList<ListView> listViews = InstrumentationBackend.solo.getCurrentListViews();

    for (int i = 0; i < listViews.size(); i++) {

      String contentDescription = listViews.get(i).getContentDescription().toString();

      InstrumentationBackend.log("Content descrition for list index " + i + " = " + contentDescription);

      if (contentDescription.equalsIgnoreCase("What's Hot")) {

        InstrumentationBackend.log("Found What's Hot");

        return i;

      }

    }

    return -1;

  }

  @Override
  public String key() {
    return "play_a_whats_hot_station";
  }

}
