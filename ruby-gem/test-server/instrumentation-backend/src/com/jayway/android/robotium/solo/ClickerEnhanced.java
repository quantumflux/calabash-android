package com.jayway.android.robotium.solo;

import java.util.ArrayList;

import junit.framework.Assert;

import android.app.Instrumentation;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ClickerEnhanced extends Clicker {

  private final static String LOG_TAG = ClickerEnhanced.class.getName();
  private Waiter waiter;
  private ViewFetcher viewFetcher;
  private Scroller scroller;
  private final int TIMEOUT = 10000;
  
  public ClickerEnhanced(ActivityUtils activityUtils, ViewFetcher viewFetcher, Scroller scroller, RobotiumUtils robotiumUtils, Instrumentation inst, Sleeper sleeper, Waiter waiter) {
    super(activityUtils, viewFetcher, scroller, robotiumUtils, inst, sleeper, waiter);
    this.waiter = waiter;
    this.viewFetcher = viewFetcher;
    this.scroller = scroller;
  }

  public void clickOnTextInView(View view, String regex, boolean longClick, int match, boolean scroll, int time) {

    waiter.waitForText(regex, 0, TIMEOUT, scroll, true);
    TextView textToClick = null;

    ArrayList<TextView> allTextViews = viewFetcher.getCurrentViews(TextView.class, view);
    allTextViews = RobotiumUtils.removeInvisibleViews(allTextViews);
    if (match == 0) {
      match = 1;
    }

    for (TextView textView : allTextViews) {
      if (RobotiumUtils.checkAndGetMatches(regex, textView, uniqueTextViews) == match) {
        uniqueTextViews.clear();
        textToClick = textView;
        break;
      }
    }
    if (textToClick != null) {
      clickOnScreen(textToClick, longClick, time);
    } else if (scroll && scroller.scroll(Scroller.DOWN)) {
      clickOnText(regex, longClick, match, scroll, time);
    } else {
      int sizeOfUniqueTextViews = uniqueTextViews.size();
      uniqueTextViews.clear();
      if (sizeOfUniqueTextViews > 0)
        Assert.assertTrue("There are only " + sizeOfUniqueTextViews + " matches of " + regex, false);
      else {
        for (TextView textView : allTextViews) {
          Log.d(LOG_TAG, regex + " not found. Have found: " + textView.getText());
        }
        Assert.assertTrue("The text: " + regex + " is not found!", false);
      }
    }
  }

}
