package sh.calaba.instrumentationbackend.actions.view;

import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.TestHelpers;
import sh.calaba.instrumentationbackend.actions.Action;
import sh.calaba.instrumentationbackend.InstrumentationBackend;

import android.widget.ImageView;
import android.graphics.ColorFilter;
import java.lang.reflect.Field;

public class We7WaitForHomeButtonAvailable implements Action {

    @Override
    public Result execute(String... args) {
        
        final String firstArgument = args[0];
        final ImageView foundView = (ImageView) TestHelpers.getViewById(firstArgument);
        
        if( null == foundView) {
            return notFoundResult(firstArgument);
        }

        ColorFilter colorFilterInstance = null;
        Field mColorFilterField;
        try {
            mColorFilterField = foundView.getClass().getDeclaredField("mColorFilter");
            mColorFilterField.setAccessible(true);
            colorFilterInstance = (ColorFilter) mColorFilterField.get(foundView);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return new Result(false, "NoSuchFieldException");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new Result(false, "IllegalArgumentException");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return new Result(false, "IllegalAccessException");
        }
        
        int timeout = 90 * 1000;
        if (args.length > 1) { // a second argument is a timeout
            try {
                // the argument is in seconds but robotium takes milliseconds
                timeout = 1000 * Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                return new Result(false, "Invalid timeout supplied. Should be an integer.");
            }
        }
        
        long endTime = System.currentTimeMillis() + timeout;
        while (System.currentTimeMillis() < endTime) {
            
            if (null == colorFilterInstance) {
                
               return Result.successResult();
                
           } else {
               
               try {
                   Thread.sleep(500);
               } catch (InterruptedException e) {
                   return Result.fromThrowable(e);
               }
               
           }
            
        }
        return new Result(false, "Timed out while waiting for home button :'" + firstArgument + "' to become available");
        
    }

    @Override
    public String key() {
                
        InstrumentationBackend.log("returning key name wait_for_home_button_available");
                
        return "wait_for_home_button_available";
    }

    private Result notFoundResult(final String firstArgument) {
        return Result.failedResult(String.format("Home button with id %s was not found", firstArgument));
    }
}
