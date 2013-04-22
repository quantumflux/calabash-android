package sh.calaba.instrumentationbackend.actions.we7;

import java.util.ArrayList;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;
import android.view.View;
import android.widget.ListView;

public class We7WaitForAllStationsListLoaded implements Action {

  @Override
  public Result execute(final String... args) {

    String tabName = args[0];

    int timeout = 90 * 1000;

    if (args.length > 1) {
      try {
        timeout = 1000 * Integer.parseInt(args[1]);
      } catch (NumberFormatException e) {
        return new Result(false, "Invalid timeout supplied. Should be an integer.");
      }
    }

    ListView waitingListView = getListView(tabName);

    InstrumentationBackend.log("Found list. Waiting up to " + (timeout/1000) + " seconds for " + tabName + " to load)");

    long endTime = System.currentTimeMillis() + timeout;
    while (System.currentTimeMillis() < endTime) {

      if (waitingListView.getVisibility() == View.VISIBLE) {

        InstrumentationBackend.log("List " + tabName + " is now visible/loaded");

        return Result.successResult();

      } else {

        InstrumentationBackend.log("List " + tabName + " is not visible/loaded");

        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          return Result.fromThrowable(e);
        }

      }

    }

    InstrumentationBackend.log("Timed out while waiting for list " + tabName);

    return new Result(false, "Timed out while waiting for list " + tabName);

  }

  protected ListView getListView(final String tabName) {

    ArrayList<ListView> listViews = InstrumentationBackend.solo.getCurrentListViews();

    InstrumentationBackend.log("Found " + listViews.size() + " list views. Looking for index for " + tabName);

    for (int i = 0; i < listViews.size(); i++) {

      String contentDescription = listViews.get(i).getContentDescription().toString();

      InstrumentationBackend.log("Content descrition for list index " + i + " = " + contentDescription);

      if (contentDescription.equalsIgnoreCase(tabName)) {

        InstrumentationBackend.log("Found list for " + tabName);

        return listViews.get(i);

      }

    }

    return null;

  }

  @Override
  public String key() {

    InstrumentationBackend.log("returning key name wait_for_list_loaded");

    return "wait_for_all_stations_list_loaded";
  }

}
