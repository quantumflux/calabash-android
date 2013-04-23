package sh.calaba.instrumentationbackend.actions.we7;

import java.util.HashMap;

public class We7ActionHelper {

	private static HashMap<String, String> sCachedValues;
	
	private static final void initCachedValues(){
		if (sCachedValues == null) {
			sCachedValues = new HashMap<String, String>();
		}
	}
	
	public static final void setValue(String key, String value) {
		
		initCachedValues();
		
		sCachedValues.put(key,  value);
		
	}
	
	public static final String getValue(String key) {
		
		initCachedValues();
		
		return sCachedValues.get(key);
		
	}
	
}
