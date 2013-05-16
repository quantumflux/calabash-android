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
    
    long timeout = 90 * 1000;
    if (args.length > 1) {
      try {
        // the argument is in seconds but robotium takes milliseconds
        timeout = 1000 * Long.parseLong(args[1]);
      } catch (NumberFormatException e) {
        return new Result(false, "Invalid timeout supplied. Should be an long."); 
      }
    }
    
    String target = null;
    if (args.length > 2) {
      target = args[2];
    }
    
    InstrumentationBackend.log("Waiting for text " + text + " on target " + target);
    
    if (target != null && !target.equalsIgnoreCase("anywhere") && isRunningOnTwoPane()) {
      
      InstrumentationBackend.log("Targeting specific pane"); 
      
      ViewGroup pane = null;
      
      if (target.equalsIgnoreCase("first_pane")) {
        pane = (ViewGroup) TestHelpers.getViewById("first_pane");
      } else if (target.equalsIgnoreCase("second_pane")) {
        pane = (ViewGroup) TestHelpers.getViewById("second_pane");
      } else {
        return new Result(false, "Unrecognised target pane: " + target);
      }

      InstrumentationBackend.log("Pane targeted = " + pane.toString()); 

      boolean timedOut = !InstrumentationBackend.solo.waitForTextInView(pane, args[0], 1, timeout);
      if(timedOut) {
          return new Result(false, "Time out while waiting for text:" + args[0] + " in target " + target);
      } else {
          return Result.successResult();
      }
      
    } else {
      
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
