package sh.calaba.instrumentationbackend.actions.we7;

import android.view.ViewGroup;
import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.TestHelpers;
import sh.calaba.instrumentationbackend.actions.Action;

public class We7PressTextTwoPaneAware extends We7Action implements Action {

  @Override
  public Result execute(String... args) {
    
    String text = args[0];
    String target = null;
    
    if (args.length > 1) {
      target = args[1];
    }
    
    InstrumentationBackend.log("Pressing text " + text + " on target " + target);
    
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
      
      InstrumentationBackend.log("Clicking text"); 
      InstrumentationBackend.solo.clickOnTextInView(pane, text);
      
    } else {
      
      InstrumentationBackend.log("No specific pane target"); 
      
      InstrumentationBackend.log("Clicking text");
      InstrumentationBackend.solo.clickOnText(text);
      
    }
    
    return Result.successResult();
  }

  @Override
  public String key() {
    
    return "press_text_two_pane_aware";
  }

}
