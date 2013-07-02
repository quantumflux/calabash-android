package sh.calaba.instrumentationbackend.actions.we7.list;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;
import sh.calaba.instrumentationbackend.actions.we7.We7Action;

public class We7ScrollViewHorizontally extends We7Action implements Action {
  
  @Override
  public Result execute(String... args) {

    String viewResourceIdName = args[0];

    ScrollDirection direction;

    if (args[1].equalsIgnoreCase("left")) {
      direction = ScrollDirection.LEFT;
    } else if (args[1].equalsIgnoreCase("right")) {
      direction = ScrollDirection.RIGHT;
    } else {
      return new Result(false, "Unrecognised scroll direction arg (must be 'left' or 'right'): " + args[1]);
    }

    InstrumentationBackend.log("We7ScrollViewHorizontally (" + direction + ") for " + viewResourceIdName); 
    
    if (this.scrollViewHorizontally(viewResourceIdName, direction)) {
      
      return new Result(true);
      
    } else {
      
      return new Result(false, viewResourceIdName + " not found");
      
    }
    
  }

  @Override
  public String key() {
    
    return "scroll_view_horizontally";
    
  }

}
