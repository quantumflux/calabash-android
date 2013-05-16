package sh.calaba.instrumentationbackend.actions.we7.util;

import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;
import sh.calaba.instrumentationbackend.actions.we7.We7Action;

public class We7ActionAssertFail extends We7Action implements Action {

  @Override
  public Result execute(String... args) {
    
    
    if (args.length > 0) {
      return new Result(false, args[0]);
    } else {
      return new Result(false);
    }
    
    
  }

  @Override
  public String key() {
    return "assert_fail";
  }

}
