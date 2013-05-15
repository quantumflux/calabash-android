package sh.calaba.instrumentationbackend.actions.we7;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;

public class We7Allow3gOnEmulator extends We7Action implements Action {

  @Override
  public Result execute(String... args) {

    Boolean isRunningOnEmulator = isRunningOnEmulator();
    if (isRunningOnEmulator == null) {
      InstrumentationBackend.log("isRunningOnEmulator() returned a null");
      return new Result(false, "Unable to ascertain if running on emulator!");
    } else if (!isRunningOnEmulator) {
      InstrumentationBackend.log("Ignoring 3g request as its not an emulator");
      return new Result(true, "Ignoring request - is not an emulator");
    }

    if (set3gForEmulator()) {
      InstrumentationBackend.log("Set 3g on emulator");
      return new Result(true, "Set 3g allowed on emulator");
    } else {
      InstrumentationBackend.log("Not able to set 3g on emulator");
      return new Result(false, "Not able to set 3g on Emulator");
    }

  }

  @Override
  public String key() {
    return "allow_3g_on_emulator";
  }

}
