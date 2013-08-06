package sh.calaba.instrumentationbackend.actions.we7.util;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.TestHelpers;
import sh.calaba.instrumentationbackend.actions.Action;
import android.view.View;

/**
 * This class does the same wait for view as in core calabash but does not throw an illegal argument if the view is not yet in the view heirarchy
 * @author alister
 */
public class We7WaitForViewById implements Action {

  @Override
  public Result execute(String... args) {
    String viewId = args[0];

    try {
      if (getViewById(viewId, 60000) != null) {
        return Result.successResult();
      } else {
        return new Result(false, "Waiting for view with id '" + viewId + "' to be visible timed out");
      }
    } catch (InterruptedException e) {
      return Result.fromThrowable(e);
    }
  }

  protected View getViewById(String viewId, long timeout) throws InterruptedException {

    System.out.println("Waiting for view with id '" + viewId + "' to appear");

    long endTime = System.currentTimeMillis() + timeout;
    while (System.currentTimeMillis() < endTime) {

      View view = TestHelpers.getViewById(viewId);

      if (view != null) {
        InstrumentationBackend.log("Waiting for view with id '" + viewId + "' found view " + view);

        if (view.getVisibility() == View.VISIBLE) {
          InstrumentationBackend.log("View with id '" + viewId + "' is visible, success");
          return view;
        } else {
          InstrumentationBackend.log("View with id '" + viewId + "' is not yet visible");
        }

      } else {
        InstrumentationBackend.log("Waiting for view with id '" + viewId + "' - not found in heirarchy yet");
      }

      System.out.println("View with id '" + viewId + "' is not visible, sleeping...");
      Thread.sleep(500);

    }

    return null;
    
  }

  @Override
  public String key() {
    return "we7_wait_for_view_by_id";
  }
}