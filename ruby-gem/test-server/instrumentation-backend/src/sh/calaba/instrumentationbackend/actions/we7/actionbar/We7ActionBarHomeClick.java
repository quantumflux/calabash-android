package sh.calaba.instrumentationbackend.actions.we7.actionbar;

import android.view.View;
import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.TestHelpers;
import sh.calaba.instrumentationbackend.actions.Action;
import sh.calaba.instrumentationbackend.actions.we7.We7Action;

public class We7ActionBarHomeClick extends We7Action implements Action {

	@Override
	public Result execute(String... args) {
		
		View actionLogoView = TestHelpers.getViewById("action_logo");
		
		if (actionLogoView == null) {
			InstrumentationBackend.log("Unable to find action_logo so assuming v4.0+ and calling clickOnActionBarHomeButton");
			
			InstrumentationBackend.solo.getCurrentActivity().runOnUiThread(new Runnable() {
				public void run() {
					InstrumentationBackend.solo.clickOnActionBarHomeButton();
				}	
			});
			
		} else {
			
			InstrumentationBackend.log("Found action_logo calling clickOnVew");
			InstrumentationBackend.solo.clickOnView(actionLogoView);
			
		}
		
		return Result.successResult();
	}
	
	@Override
	public String key() {
		
		return "click_on_action_bar_home";
	}

}
