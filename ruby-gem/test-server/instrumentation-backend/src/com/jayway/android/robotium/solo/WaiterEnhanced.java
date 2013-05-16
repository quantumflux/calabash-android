package com.jayway.android.robotium.solo;

import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

public class WaiterEnhanced extends Waiter {

  Sleeper sleeper;
  SearcherEnhanced searcher;
  
  public WaiterEnhanced(ActivityUtils activityUtils, ViewFetcher viewFetcher, SearcherEnhanced searcher, Scroller scroller, Sleeper sleeper) {
    super(activityUtils, viewFetcher, searcher, scroller, sleeper);
    this.sleeper = sleeper;
    this.searcher = searcher;
  }

  public boolean waitForTextInView(View view, String text, int expectedMinimumNumberOfMatches, long timeout, boolean scroll, boolean onlyVisible) {
    
    final long endTime = SystemClock.uptimeMillis() + timeout;

    while (true) {
      final boolean timedOut = SystemClock.uptimeMillis() > endTime;
      if (timedOut) {
        return false;
      }

      sleeper.sleep();
      
      final boolean foundAnyTextView = searcher.searchForInView(view, TextView.class, text, expectedMinimumNumberOfMatches, timeout, scroll, onlyVisible);

      if (foundAnyTextView) {
        return true;
      }
    }
  }

}
