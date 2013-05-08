package sh.calaba.instrumentationbackend.actions.we7;


import java.util.ArrayList;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;


public class We7ClickOnTextOr extends We7Action implements Action {

    @Override
    public Result execute(String... args) {
    	
    	ArrayList<String> textToOr = new ArrayList<String>();
    	String textList = "";
    	for (int i = 0; i < args.length; i++) {
    		
    		textToOr.add(args[i]);
    		textList += args[i] + ",";
    		
    	}
    	
    	for (String text: textToOr) {
        	
        	if (InstrumentationBackend.solo.searchText(text, true)) {
        		InstrumentationBackend.solo.clickOnText(text);
        		return new Result(true, "Clicked on " + text);
        	}
        	
        }
    	
    	return new Result(false, "Unable to find any of " + textList);

    }

    @Override
    public String key() {
        return "click_on_text_or";
    }

}
