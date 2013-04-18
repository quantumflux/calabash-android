package sh.calaba.instrumentationbackend.actions.list;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.TestHelpers;
import sh.calaba.instrumentationbackend.actions.Action;
import android.view.View;
import android.widget.TabHost;

public class We7PopulateStationLists implements Action {

  @Override
  public Result execute(final String... args) {

    // Create a history and a favourites list

    System.out.println("Ensuring there are History and Favourite lists");

    InstrumentationBackend.solo.clickOnText("WE7 Themed");

    TabHost tabHost = (TabHost) InstrumentationBackend.solo.getView(TabHost.class, 0);

    if (tabHost == null) {
      return new Result(false, "No TabHost found");
    }

    int we7ThemedIndex;
    switch(tabHost.getTabWidget().getTabCount()) {
      case 0:
        // no tabs found
        return new Result(false, "No tabs found");
      case 1:
        // Not enough tabs found
        return new Result(false, "Only one tab found");
      case 2:
        // both history or favourites is empty
        we7ThemedIndex = 1;
        break;
      case 3:
        // either history or favourites is empty
        we7ThemedIndex = 2;
        break;
      case 4:
        // both history and favourites exist
        return new Result(true, "History and favourites are populated");
      default:
        return new Result(false, "Too many tabs found");
    }

    InstrumentationBackend.solo.clickInList(1, we7ThemedIndex);

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
