package sh.calaba.instrumentationbackend.actions.we7;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;
import android.widget.TextView;

public class We7CheckPlayableContextMenuSave extends We7Action implements Action {

	@Override
	public Result execute(String... args) {
		
		String lastLongClickText = getValue(LAST_LONG_CLICK_TEXT);

		if (lastLongClickText == null) {
			return new Result(false, "No text LAST_LONG_CLICK_TEXT in cache");
		}

		InstrumentationBackend.log("Waiting for saved item" + lastLongClickText);
		
		int timeout = 90 * 1000;

		TextView lastLongClickTextView;
		
		long endTime = System.currentTimeMillis() + timeout;
	    while (System.currentTimeMillis() < endTime) {

	      lastLongClickTextView = InstrumentationBackend.solo.getText(lastLongClickText);
	    	
	      if (lastLongClickTextView != null) {

	        InstrumentationBackend.log("Found saved item");

	        return Result.successResult();

	      } else {

	        InstrumentationBackend.log("Not found saved item");

	        try {
	          Thread.sleep(500);
	        } catch (InterruptedException e) {
	          return Result.fromThrowable(e);
	        }

	      }

	    }
		
	    return new Result(false, "Timed out waiting for saved item");

	}

	@Override
	public String key() {
		return "check_last_playable_context_menu_save";
	}

}
