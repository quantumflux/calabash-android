package sh.calaba.instrumentationbackend.actions.we7.list;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;
import sh.calaba.instrumentationbackend.actions.we7.We7Action;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;

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

    TabWidget tabWidget = tabHost.getTabWidget();
    
    if (tabWidget == null) {
      InstrumentationBackend.log("No TabWidget found");
      return new Result(false, "No TabWidget found - why not");
    }
    
    int numberOfTabs = tabWidget.getTabCount();

    InstrumentationBackend.log("Looking for tab with content description " + tabName);

    for (int i = 0; i < numberOfTabs; i++) {

      final View tabView = tabHost.getTabWidget().getChildTabViewAt(i);

      String tabContentDescription = tabView.getContentDescription().toString();
      
      if (tabContentDescription != null) {

        tabContentDescription = tabContentDescription.replace("\n", " ");
        tabContentDescription = tabContentDescription.replace("  ", " ");
        
        InstrumentationBackend.log("Comparing to content description " + tabContentDescription + " at index " + i);

        if (tabContentDescription.equals(tabName)) {

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
