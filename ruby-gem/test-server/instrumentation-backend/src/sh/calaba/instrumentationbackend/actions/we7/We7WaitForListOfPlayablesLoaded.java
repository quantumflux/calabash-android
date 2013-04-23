package sh.calaba.instrumentationbackend.actions.we7;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;
import android.view.View;
import android.widget.ListView;

public class We7WaitForListOfPlayablesLoaded extends We7Action implements Action {

  @Override
  public Result execute(final String... args) {

    int timeout = 90 * 1000;

    if (args.length > 1) {
      try {
        timeout = 1000 * Integer.parseInt(args[0]);
      } catch (NumberFormatException e) {
        return new Result(false, "Invalid timeout supplied. Should be an integer.");
      }
    }

    ListView waitingListView = InstrumentationBackend.solo.getCurrentListViews().get(0);

    InstrumentationBackend.log("Found list. Waiting up to " + (timeout/1000) + " seconds for it to load)");

    long endTime = System.currentTimeMillis() + timeout;
    while (System.currentTimeMillis() < endTime) {

      if (waitingListView.getVisibility() == View.VISIBLE) {

        InstrumentationBackend.log("List is now visible/loaded");

        return Result.successResult();

      } else {

        InstrumentationBackend.log("List is not visible/loaded");

        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          return Result.fromThrowable(e);
        }

      }

    }

    InstrumentationBackend.log("Timed out while waiting for list");

    return new Result(false, "Timed out while waiting for list");

  }

  @Override
  public String key() {

    InstrumentationBackend.log("returning key name wait_for_playable_list_loaded");

    return "wait_for_list_of_playables_loaded";
  }

}
