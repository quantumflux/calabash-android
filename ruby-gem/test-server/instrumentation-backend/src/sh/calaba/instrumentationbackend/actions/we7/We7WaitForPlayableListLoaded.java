package sh.calaba.instrumentationbackend.actions.we7;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;
import android.view.View;
import android.widget.ListView;

public class We7WaitForPlayableListLoaded extends We7Action implements Action {

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

    if (waitingListView == null) {
    	return new Result(false, "Could not find list.");
    }
    
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

  @Override
  public String key() {

    InstrumentationBackend.log("returning key name wait_for_list_loaded");

    return "wait_for_playable_list_loaded";
  }

}
