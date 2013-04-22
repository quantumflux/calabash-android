package sh.calaba.instrumentationbackend.actions.we7;

import java.util.ArrayList;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.TestHelpers;
import sh.calaba.instrumentationbackend.actions.Action;
import android.view.View;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

public class We7PopulateStationLists implements Action {

  @Override
  public Result execute(final String... args) {

    // Create a history and a favourites list

    InstrumentationBackend.log("Ensuring there are History and Favourite lists");

    InstrumentationBackend.solo.clickOnText("WE7 Themed");

    TabHost tabHost = (TabHost) InstrumentationBackend.solo.getView(TabHost.class, 0);

    if (tabHost == null) {
      InstrumentationBackend.log("No tab host found");
      return new Result(false, "No TabHost found - am I on All Stations page?");
    }

    InstrumentationBackend.log("Tab host found");

    ArrayList<ListView> listViews = InstrumentationBackend.solo.getCurrentListViews();

    InstrumentationBackend.log("List items found: " + listViews.size());

    for (int i = 0 ; i < listViews.size(); i++) {
      InstrumentationBackend.log("List item " + i + " has " + listViews.get(i).getCount() + " items");
    }

    int we7ThemedIndex;
    switch(listViews.size()) {
      case 0:
        // no list views found
        return new Result(false, "No list views found");
      case 1:
        // Not enough list views found
        return new Result(false, "Only one list views found");
      case 2:
        // both history and favourites are empty
        we7ThemedIndex = 1;
        break;
      case 3:
        // either history or favourites is empty
        we7ThemedIndex = 2;
        break;
      case 4:
        // both history and favourites exist
        we7ThemedIndex = 3;
        break;
      default:
        return new Result(false, "Too many list views found: " + listViews.size());
    }

    ArrayList<TextView> itemText = InstrumentationBackend.solo.clickInList(1, we7ThemedIndex);

    InstrumentationBackend.log("Text of clicked item:");
    for (int i = 0 ; i < itemText.size(); i++) {
      InstrumentationBackend.log(itemText.get(i).getText().toString());
    }

    String viewId = "favourite";
    View favouriteView;

    try {

      favouriteView = getViewById(viewId, 60000);

      if(favouriteView == null ) {
        return new Result(false, "Timed out while waiting for chugger (wait for view with id:'" + viewId + "')");
      }

    } catch( InterruptedException e ) {
      return Result.fromThrowable(e);
    }

    InstrumentationBackend.solo.clickOnView(favouriteView);

    return new Result(true);

  }

  protected View getViewById(final String viewId, final long timeout) throws InterruptedException {

    System.out.println("Waiting for view with id '" + viewId + "'");

    long endTime = System.currentTimeMillis() + timeout;
    while (System.currentTimeMillis() < endTime) {

      View view = TestHelpers.getViewById(viewId);
      System.out.println("Waiting for view with id '" + viewId + "' found view " + view);

      if (view != null) {
        System.out.println("Waiting for view with id '" + viewId + "' Success");
        return view;
      } else {
        System.out.println("Waiting for view with id '" + viewId + "' sleeping...");
        Thread.sleep(500);
      }

    }

    System.out.println("Waiting for view with id '" + viewId + "' Timed out");

    return null;
  }


  @Override
  public String key() {
    return "populate_station_lists";
  }

}
