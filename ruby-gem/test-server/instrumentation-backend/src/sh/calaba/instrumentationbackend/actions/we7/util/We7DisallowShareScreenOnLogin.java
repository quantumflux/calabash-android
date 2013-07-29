package sh.calaba.instrumentationbackend.actions.we7.util;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;
import sh.calaba.instrumentationbackend.actions.we7.We7Action;

public class We7DisallowShareScreenOnLogin extends We7Action implements Action {

  @Override
  public Result execute(String... args) {

    if (disallowShareScreenOnLogin()) {
      InstrumentationBackend.log("Set disallow share screen on login");
      return new Result(true, "Set disallow share screen on logi");
    } else {
      InstrumentationBackend.log("Not able to set disallow share screen on login");
      return new Result(false, "Not able to set disallow share screen on login");
    }

  }

  @Override
  public String key() {
    return "disallow_share_screen_on_login";
  }

}
