package sh.calaba.instrumentationbackend.actions.we7;

import java.util.HashMap;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.TestHelpers;
import sh.calaba.instrumentationbackend.actions.Action;
import android.view.View;

public class We7WaitForHomeButtonAvailable implements Action {

  // This MUST be the same value as set in com.we7.player.ui.fragment.We7Fragment.CONTENT_AVAILABILITY_KEY
  public static final String CONTENT_AVAILABILITY_KEY = "available";

  @Override
  public Result execute(String... args) {

    final String firstArgument = args[0];
    final View foundView = TestHelpers.getViewById(firstArgument);

    if( null == foundView) {
      return notFoundResult(firstArgument);
    }

    HashMap<String, Object> tagMap = (HashMap<String, Object>) foundView.getTag();

    if (null == tagMap) {
      return new Result(false, "No availability tag set on home button");
    }

    int timeout = 90 * 1000;
    if (args.length > 1) { // a second argument is a timeout
      try {
        // the argument is in seconds but robotium takes milliseconds
        timeout = 1000 * Integer.parseInt(args[1]);
      } catch (NumberFormatException e) {
        return new Result(false, "Invalid timeout supplied. Should be an integer.");
      }
    }

    long endTime = System.currentTimeMillis() + timeout;
    while (System.currentTimeMillis() < endTime) {

      if ((Boolean) tagMap.get(CONTENT_AVAILABILITY_KEY)) {

        return Result.successResult();

      } else {

        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          return Result.fromThrowable(e);
        }

      }

    }
    return new Result(false, "Timed out while waiting for home button :'" + firstArgument + "' to become available");

  }

  @Override
  public String key() {

    InstrumentationBackend.log("returning key name wait_for_home_button_available");

    return "wait_for_home_button_available";
  }

  private Result notFoundResult(final String firstArgument) {
    return Result.failedResult(String.format("Home button with id %s was not found", firstArgument));
  }
}
