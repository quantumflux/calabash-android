package sh.calaba.instrumentationbackend.actions.list;

import java.util.ArrayList;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;
import android.widget.TabHost;
import android.widget.TextView;

public class We7ClickTabFragmentListItem implements Action {

  @Override
  public Result execute(final String... args) {

    int tabIndex = Integer.parseInt(args[0]);

    int listItemIndex = Integer.parseInt((args[1]));

    TabHost tabHost = (TabHost) InstrumentationBackend.solo.getView(TabHost.class, 0);

    // Check valid index
    if (tabIndex < 0 || tabIndex >= tabHost.getTabWidget().getTabCount())  {
      return new Result(false, "no tab at index: " + tabIndex);
    }

    // Check tab is showing
    if (tabHost.getCurrentTab() != tabIndex) {
      return new Result(false, "tab " + tabIndex + " is not current tab (" + tabHost.getCurrentTab() + ")");
    }

    long startTime = System.currentTimeMillis();
    long timeOut = 90 * 1000;
    Result result = null;

    Exception lastException = null;

    while (System.currentTimeMillis() - startTime <= timeOut && result == null) {

      try {

        ArrayList<TextView> listTextViews = InstrumentationBackend.solo.clickInList(listItemIndex, tabIndex);

        StringBuilder sb = new StringBuilder();
        for (TextView tv : listTextViews) {
          sb.append(tv.getText() + ",");
        }

        result = new Result(true, sb.toString() + (lastException == null ? "":" /nAn exception was thrown: " + lastException.getMessage() + " /n" + lastException.getStackTrace()));

      } catch (Exception e) {

        lastException = e;

      }

    }

    if (result == null) {
      result = new Result(false, (lastException == null ? "":" /nAn exception was thrown: " + lastException.getMessage() + " /n" + lastException.getStackTrace()));
    }

    return result;

  }

  @Override
  public String key() {
    return "click_tab_list_item";
  }

}
