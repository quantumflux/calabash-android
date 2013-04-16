package sh.calaba.instrumentationbackend.actions.list;

import java.util.ArrayList;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;

public class We7ClickListItemWithText implements Action {

	@Override
	public Result execute(String... args) {
		
        String listItemText = args[0];

		int listIndex;
		if( args.length <= 1 ) {
			listIndex = 0;
		} else {
			listIndex = (Integer.parseInt(args[1]) - 1);
		}
		
		ArrayList<ListView> listViews = InstrumentationBackend.solo.getCurrentListViews();
		
		if( listViews == null || listViews.size() <= listIndex ) {
			return new Result(false, "Could not find list #" + (listIndex + 1));
		}
		
		ListView list = listViews.get(listIndex);

		int count = list.getAdapter().getCount();
		for( int i = 0; i < count; i++ ) {
            
            View item = list.getAdapter().getItem(i)
            
            ArrayList<TextView> textViews = InstrumentationBackend.solo.getCurrentTextViews(item);
            
            for (TextView tv : textViews) {
                
                if (tv.getText().toString().equals(listItemText)){
                    
                    InstrumentationBackend.solo.clickInList(i);
                    
                    return new Result(true);
                    
                }
                
            }
			
		}
		
		return new Result(false, "Could not find text to click: " + listItemText);
	}

	@Override
	public String key() {
		return "click_list_text";
	}
}
