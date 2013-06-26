package com.jayway.android.robotium.solo;

import java.util.Collection;
import java.util.concurrent.Callable;

import android.view.View;
import android.widget.TextView;

public class SearcherEnhanced extends Searcher {

  private Sleeper sleeper;
  private ViewFetcher viewFetcher; 
  
  public SearcherEnhanced(ViewFetcher viewFetcher, WebUtils webUtils, Scroller scroller, Sleeper sleeper) {
    super(viewFetcher, webUtils, scroller, sleeper);
    this.sleeper = sleeper;
    this.viewFetcher = viewFetcher;
  }
  
  public <T extends TextView> T searchForInView(final View view, final Class<T> viewClass, final String regex, int expectedMinimumNumberOfMatches, final long timeout, final boolean scroll, final boolean onlyVisible) {
    if (expectedMinimumNumberOfMatches < 1) {
      expectedMinimumNumberOfMatches = 1;
    }

    final Callable<Collection<T>> viewFetcherCallback = new Callable<Collection<T>>() {
      public Collection<T> call() throws Exception {
        sleeper.sleep();

        if (onlyVisible) {
          return RobotiumUtils.removeInvisibleViews(viewFetcher.getCurrentViews(viewClass, view));
        }
        
        return viewFetcher.getCurrentViews(viewClass, view);
      }
    };

    try {
      
      return searchFor(viewFetcherCallback, regex, expectedMinimumNumberOfMatches, timeout, scroll);
            
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
