package sh.calaba.instrumentationbackend.actions.we7.util;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.TestHelpers;
import sh.calaba.instrumentationbackend.actions.Action;
import sh.calaba.instrumentationbackend.actions.we7.We7Action;
import android.util.SparseArray;
import android.view.View;

public class We7WaitForHomeButtonAvailable extends We7Action implements Action {

  @Override
  public Result execute(String... args) {

    final String viewIdName = args[0];
    
    int timeout = 90 * 1000;
    if (args.length > 1) { // a second argument is a timeout
      try {
        // the argument is in seconds but robotium takes milliseconds
        timeout = 1000 * Integer.parseInt(args[1]);
      } catch (NumberFormatException e) {
        return new Result(false, "Invalid timeout supplied. Should be an integer.");
      }
    }
    
    InstrumentationBackend.log("Waiting upto " + (timeout/1000) + " seconds for " + viewIdName + " available...");
    
    final View foundView = TestHelpers.getViewById(viewIdName);

    if (null == foundView) {
      
      InstrumentationBackend.log("Did not find view for " + viewIdName);
      return notFoundResult(viewIdName);
      
    }

    int testTagArrayId = getTestViewTagId();

    long endTime = System.currentTimeMillis() + timeout;
    while (System.currentTimeMillis() < endTime) {

      @SuppressWarnings("unchecked")
      SparseArray<Object> testItemsArray = (SparseArray<Object>) foundView.getTag(testTagArrayId);
      
      if (testItemsArray != null) {
        
        InstrumentationBackend.log("Found testItemsArray");
        
        Boolean isIconAvailable = (Boolean) testItemsArray.get(CONTENT_AVAILABILITY_KEY);
        
        if (isIconAvailable != null) {
        
          InstrumentationBackend.log("Found isIconAvailable = " + isIconAvailable.toString());
          
          if (isIconAvailable) {

            return Result.successResult();

          }
          
        } else {
          
          InstrumentationBackend.log("Not found isIconAvailable");
          
        }

      } else {
        
        InstrumentationBackend.log("Not found testItemsArray");
        
      }
      
      InstrumentationBackend.log("Sleeping");
      
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        return Result.fromThrowable(e);
      }

    }
    
    return new Result(false, "Timed out while waiting for home button :'" + viewIdName + "' to become available");

  }

  @Override
  public String key() {

    InstrumentationBackend.log("returning key name wait_for_home_button_available");

    return "wait_for_home_button_available";
  }

  private Result notFoundResult(final String viewIdName) {
    return Result.failedResult(String.format("Home button with id %s was not found", viewIdName));
  }
}
