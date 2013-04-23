package sh.calaba.instrumentationbackend.actions.we7;

import android.view.View;
import android.widget.TextView;
import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;

public class We7CheckChuggerPlayableTitle extends We7Action implements Action {

	@Override
	public Result execute(String... args) {
		
		String playableTitle;
		if (args.length > 0) {
			playableTitle = args[0];
		} else {
			playableTitle = getValue(LAST_PLAYABLE_NAME);
		}
		
		if (playableTitle == null) {
			return new Result(false, "No playable title provided in args or from LAST_TRACK_NAME in cache");
		}
		
		int timeout = 90 * 1000;

		TextView playableTitleTextView;
		
		long endTime = System.currentTimeMillis() + timeout;
	    while (System.currentTimeMillis() < endTime) {

	      playableTitleTextView = InstrumentationBackend.solo.getText(playableTitle);
	    	
	    	
	      if (playableTitleTextView != null) {

	        InstrumentationBackend.log("Found playable title");

	        return Result.successResult();

	      } else {

	        InstrumentationBackend.log("Not found playable title");

	        try {
	          Thread.sleep(500);
	        } catch (InterruptedException e) {
	          return Result.fromThrowable(e);
	        }

	      }

	    }
		
	    return new Result(false, "Timed out waiting for playable title");

	}

	@Override
	public String key() {
		
		InstrumentationBackend.log("returning key name check_chugger_playable_title");

	    return "check_chugger_playable_title";
	}

}
