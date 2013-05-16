package sh.calaba.instrumentationbackend.actions.we7.list;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;
import sh.calaba.instrumentationbackend.actions.we7.We7Action;
import android.view.View;
import android.widget.ListView;

public class We7WaitForListByContentDescription extends We7Action implements Action {

  @Override
  public Result execute(final String... args) {

	String listContentDescription = args[0];
	  
    int timeout = 90 * 1000;

    if (args.length > 1) {
      try {
        timeout = 1000 * Integer.parseInt(args[1]);
      } catch (NumberFormatException e) {
        return new Result(false, "Invalid timeout supplied. Should be an integer.");
      }
    }

    ListView waitingListView = getListView(listContentDescription);
    
    if (waitingListView == null) {
    	return new Result(false, "Could not find list with content description " + listContentDescription);
    }
    
    InstrumentationBackend.log("Found list with content description " + listContentDescription + ". Waiting up to " + (timeout/1000) + " seconds for it to load)");

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

    return "wait_for_list_by_content_description_load";
  }

}
