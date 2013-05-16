package sh.calaba.instrumentationbackend.actions.we7.text;

import android.view.ViewGroup;
import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.TestHelpers;
import sh.calaba.instrumentationbackend.actions.Action;
import sh.calaba.instrumentationbackend.actions.we7.We7Action;

public class We7WaitForTextTwoPaneAware extends We7Action implements Action {

  @Override
  public Result execute(String... args) {
    String text = args[0];
    
    String target = null;
    if (args.length > 1) {
      target = args[1];
    }
    
    long timeout = 90 * 1000;
    if (args.length > 2) {
      try {
        // the argument is in seconds but robotium takes milliseconds
        timeout = 1000 * Long.parseLong(args[2]);
      } catch (NumberFormatException e) {
        return new Result(false, "Invalid timeout supplied. Should be an long."); 
      }
    }
    
    InstrumentationBackend.log("Waiting for text " + text + " on target " + target + " timeout = " + timeout);
    
    if (isRunningOnTwoPane() && target != null && !target.equalsIgnoreCase("anywhere")) {
      
      InstrumentationBackend.log("Targeting specific pane"); 
      
      ViewGroup pane = null;
      
      if (target.equalsIgnoreCase("first_pane")) {
        pane = (ViewGroup) TestHelpers.getViewById("first_pane");
      } else if (target.equalsIgnoreCase("second_pane")) {
        pane = (ViewGroup) TestHelpers.getViewById("second_pane");
      } else {
        return new Result(false, "Unrecognised target pane: " + target);
      }

      InstrumentationBackend.log("Pane targeted = " + pane.toString() + ". Waiting..."); 

      boolean timedOut = !InstrumentationBackend.solo.waitForTextInView(pane, args[0], 1, timeout);
      if(timedOut) {
          return new Result(false, "Time out while waiting for text:" + args[0] + " in target " + target);
      } else {
          return Result.successResult();
      }
      
    } else {
      
      InstrumentationBackend.log("No target specified. Waiting..."); 

      boolean timedOut = !InstrumentationBackend.solo.waitForText(args[0], 1, timeout);
      if(timedOut) {
          return new Result(false, "Time out while waiting for text:" + args[0]);
      } else {
          return Result.successResult();
      }
      
    }
    
  }

  @Override
  public String key() {
    return "wait_for_text_two_pane_aware";
  }

}
