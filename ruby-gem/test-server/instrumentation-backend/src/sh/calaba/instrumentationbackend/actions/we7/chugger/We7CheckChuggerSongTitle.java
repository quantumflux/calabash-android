package sh.calaba.instrumentationbackend.actions.we7.chugger;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.TestHelpers;
import sh.calaba.instrumentationbackend.actions.Action;
import sh.calaba.instrumentationbackend.actions.we7.We7Action;
import android.widget.TextView;

public class We7CheckChuggerSongTitle extends We7Action implements Action {

  @Override
  public Result execute(String... args) {

    String songTitle;
    if (args.length > 0) {
      songTitle = args[0];
    } else {
      songTitle = getValue(LAST_CLICK_TEXT);
    }

    songTitle = songTitle.split(",")[0];

    if (songTitle == null) {
      return new Result(false, "No song title provided in args or from LAST_TRACK_NAME in cache");
    }

    InstrumentationBackend.log("Looking for song title: " + songTitle);

    int timeout = 90 * 1000;

    TextView songTitleTextView;

    long endTime = System.currentTimeMillis() + timeout;
    
    while (System.currentTimeMillis() < endTime) {

      songTitleTextView = TestHelpers.getTextViewByDescription("song name");

      // songTitleTextView = InstrumentationBackend.solo.getText(songTitle);

      if (songTitleTextView != null) {

        String playingSongName = songTitleTextView.getText().toString();

        if (playingSongName.contains(songTitle)) {

          InstrumentationBackend.log("Found song title " + songTitle);
          return Result.successResult();

        } else {

          InstrumentationBackend.log("Song title is different: " + playingSongName + " rather than " + songTitle);
          return Result.failedResult();

        }

      } else {

        InstrumentationBackend.log("Not found song_name view yet");

        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          return Result.fromThrowable(e);
        }

      }

    }

    return new Result(false, "Timed out waiting/looking for song title");

  }

  @Override
  public String key() {

    InstrumentationBackend.log("returning key name check_song_title");

    return "check_chugger_song_title";
  }

}
