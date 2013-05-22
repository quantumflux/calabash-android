package sh.calaba.instrumentationbackend.actions.we7.actionbar;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.TestHelpers;
import sh.calaba.instrumentationbackend.actions.Action;
import sh.calaba.instrumentationbackend.actions.we7.We7Action;
import android.view.View;
import android.widget.EditText;

import com.jayway.android.robotium.solo.SoloEnhanced;

public class We7ActionBarEnterSearchText extends We7Action implements Action {

  @Override
  public Result execute(String... args) {

    String text = args[0];

    InstrumentationBackend.log("Searching for text: " + text);
    
    View actionLogoView = TestHelpers.getViewById("action_logo");

    if (actionLogoView == null) {
    
      InstrumentationBackend.log("Cannot find action_logo so using 'real' action bar methods");
      
      InstrumentationBackend.solo.clickOnActionBarItem(TestHelpers.getViewById(SoloEnhanced.ActionBarItems.SEARCH.getId()).getId());
      
      InstrumentationBackend.log("Entering search text " + text);
      if (!InstrumentationBackend.solo.enterTextOnActionBar(SoloEnhanced.ActionBarItems.SEARCH_TEXT, text)) {
        return new Result(false, "Action bar search TextView not found");
      }

      InstrumentationBackend.log("Enter search text done");
      
    } else {

      InstrumentationBackend.log("Found action_logo calling clickOnVew so assuming ActionBarCompat");
      
      InstrumentationBackend.log("Clicking on menu_search");
      if (!pressView("menu_search")) {
        return new Result(false, "Unable to find view with id menu_search");
      }
      InstrumentationBackend.log("menu_search clicked");

      InstrumentationBackend.log("Entering search text " + text);
      EditText searchText = (EditText) TestHelpers.getViewById("search_text");
      if (searchText == null) {
        return new Result(false, "Unable to find view with id search_text");
      }
      InstrumentationBackend.log("search_text view found");
      
      InstrumentationBackend.solo.clearEditText(searchText);
      InstrumentationBackend.solo.enterText(searchText, text);
      InstrumentationBackend.log("Enter search text done");
      
    }

    return new Result(true);

  }

  @Override
  public String key() {
    return "action_bar_enter_search_text";
  }

}
