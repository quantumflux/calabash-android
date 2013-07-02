package sh.calaba.instrumentationbackend.actions.we7.chugger;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.TestHelpers;
import sh.calaba.instrumentationbackend.actions.Action;
import sh.calaba.instrumentationbackend.actions.we7.We7Action;
import android.widget.TextView;

public class We7CheckChuggerPlayableTitle extends We7Action implements Action {

	@Override
	public Result execute(String... args) {
		
		String playableTitle;
		if (args.length > 0) {
			playableTitle = args[0];
		} else {
			playableTitle = getValue(LAST_CLICK_TEXT);
		}
		
		if (playableTitle == null) {
			return new Result(false, "No playable title provided in args or from LAST_CLICK_TEXT in cache");
		}
		
		playableTitle = playableTitle.split(",")[0];
		
		InstrumentationBackend.log("Looking for playable title " + playableTitle);
		
		int timeout = 90 * 1000;

		TextView playableNameTextView;
		
		long endTime = System.currentTimeMillis() + timeout;
	    while (System.currentTimeMillis() < endTime) {

	      playableNameTextView = TestHelpers.getTextViewByDescription("playable name");
	      
	      //playableTitleTextView = InstrumentationBackend.solo.getText(playableTitle);
	    	
	      if (playableNameTextView != null) {
	        
	        String playingPlayableName = playableNameTextView.getText().toString();

	        if (playingPlayableName.contains(playableTitle)) {

	          InstrumentationBackend.log("Found playable title " + playingPlayableName);
	          return Result.successResult();

	        } else {

	          InstrumentationBackend.log("Wrong playable title: " + playingPlayableName + " rather than " + playableTitle);
	          return Result.failedResult();

	        }

	      } else {

	        InstrumentationBackend.log("Not found playable title view yet");

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
