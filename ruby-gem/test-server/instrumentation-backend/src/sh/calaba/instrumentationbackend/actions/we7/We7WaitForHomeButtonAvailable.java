package sh.calaba.instrumentationbackend.actions.we7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.TestHelpers;
import sh.calaba.instrumentationbackend.actions.Action;
import android.app.Activity;
import android.util.SparseArray;
import android.view.View;

public class We7WaitForHomeButtonAvailable extends We7Action implements Action {

  // This MUST be the same value as set in com.we7.player.ui.fragment.We7Fragment.CONTENT_AVAILABILITY_KEY
  public static final Integer CONTENT_AVAILABILITY_KEY = 100;

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

    Activity currentActivity = InstrumentationBackend.solo.getCurrentActivity();
    Integer testTagArrayId = null;
    
    InstrumentationBackend.log("currentActivity class = " + currentActivity.getClass().toString());
    
    try {
      Method method = currentActivity.getClass().getMethod("getTestItemsTagId");
      testTagArrayId = (Integer) method.invoke(null);
    } catch (SecurityException se) {
      // TODO Auto-generated catch block
      se.printStackTrace();
      InstrumentationBackend.log("se thrown " + se.getMessage());
    } catch (NoSuchMethodException nsme) {
      // TODO Auto-generated catch block
      nsme.printStackTrace();
      InstrumentationBackend.log("nsmee thrown " + nsme.getMessage());
    } catch (IllegalArgumentException iarge) {
      // TODO Auto-generated catch block
      iarge.printStackTrace();
      InstrumentationBackend.log("iarge thrown " + iarge.getMessage());
    } catch (IllegalAccessException iacce) {
      // TODO Auto-generated catch block
      iacce.printStackTrace();
      InstrumentationBackend.log("iacce thrown " + iacce.getMessage());
    } catch (InvocationTargetException ite) {
      // TODO Auto-generated catch block
      ite.printStackTrace();
      InstrumentationBackend.log("ite thrown " + ite.getMessage());
    }
    
    if (testTagArrayId == null) {
      return new Result(false, "Unable to retrieve content availability tag id");
    }
    
    InstrumentationBackend.log("Retireved testTagArrayId = " + testTagArrayId);
    


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

  private Result notFoundResult(final String firstArgument) {
    return Result.failedResult(String.format("Home button with id %s was not found", firstArgument));
  }
}
