package sh.calaba.instrumentationbackend.actions.we7;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;
import android.view.View;
import android.widget.TabHost;

public class We7SelectTabByContentDescription extends We7Action implements Action {

  @Override
  public Result execute(final String... args) {

    String tabName = args[0];

    TabHost tabHost = (TabHost) InstrumentationBackend.solo.getView(TabHost.class, 0);

    if (tabHost == null) {
      InstrumentationBackend.log("No tab host found");
      return new Result(false, "No TabHost found - am I on a tabbed page?");
    }

    InstrumentationBackend.log("Tab host found");

    int numberOfTabs = tabHost.getTabWidget().getTabCount();

    InstrumentationBackend.log("Looking for tab with content description " + tabName);

    for (int i = 0; i < numberOfTabs; i++) {

      final View tabView = tabHost.getTabWidget().getChildTabViewAt(i);

      if (tabView.getContentDescription() != null) {

        InstrumentationBackend.log("Comparing to content description " + tabView.getContentDescription().toString() + " at index " + i);

        if (tabView.getContentDescription().toString().equals(tabName)) {

          InstrumentationBackend.log("Found " + tabName);
          try {
            
            InstrumentationBackend.log("Trying click on view...");
            InstrumentationBackend.solo.clickOnView(tabView);
            
          } catch (junit.framework.AssertionFailedError e) {
            
            InstrumentationBackend.log("solo.clickOnView failed - using fallback");
            if (tabView.isClickable()) {
              
              InstrumentationBackend.solo.getCurrentActivity().runOnUiThread(new Runnable() {
                public void run() {
                  
                  tabView.performClick();
                  
                }
                
              });
              
            }
          }

          return new Result(true);
          
        }

      } else {
        
        InstrumentationBackend.log("No tab contentDescription at index " + i);
        
      }

    }

    return new Result(false, "Unable to find tab " + tabName);

  }

  @Override
  public String key() {
    return "select_tab_by_content_description";
  }

}
