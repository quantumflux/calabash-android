package sh.calaba.instrumentationbackend.actions.we7.text;


import java.util.ArrayList;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;
import sh.calaba.instrumentationbackend.actions.we7.We7Action;


public class We7AssertTextOr extends We7Action implements Action{

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
        		return new Result(true, "Text '" + text + "' was found");
        	}
        	
        }
        
        return new Result(false, "None of: " + textList + "' were found");

    }

    @Override
    public String key() {
        return "assert_text_or";
    }

}
