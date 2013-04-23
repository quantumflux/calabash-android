package sh.calaba.instrumentationbackend.actions.we7;

import android.view.View;
import android.widget.TextView;
import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;

public class We7CheckChuggerSongTitle extends We7Action implements Action {

	@Override
	public Result execute(String... args) {
		
		String songTitle;
		if (args.length > 0) {
			songTitle = args[0];
		} else {
			songTitle = getValue(LAST_TRACK_NAME);
		}
		
		if (songTitle == null) {
			return new Result(false, "No song title provided in args or from LAST_TRACK_NAME in cache");
		}
		
		int timeout = 90 * 1000;

		TextView songTitleTextView;
		
		long endTime = System.currentTimeMillis() + timeout;
	    while (System.currentTimeMillis() < endTime) {

	      songTitleTextView = InstrumentationBackend.solo.getText(songTitle);
	    	
	    	
	      if (songTitleTextView != null) {

	        InstrumentationBackend.log("Found song title");

	        return Result.successResult();

	      } else {

	        InstrumentationBackend.log("Not found song_title");

	        try {
	          Thread.sleep(500);
	        } catch (InterruptedException e) {
	          return Result.fromThrowable(e);
	        }

	      }

	    }
		
	    return new Result(false, "Timed out waiting for song title");

	}

	@Override
	public String key() {
		
		InstrumentationBackend.log("returning key name check_song_title");

	    return "check_chugger_song_title";
	}

}
