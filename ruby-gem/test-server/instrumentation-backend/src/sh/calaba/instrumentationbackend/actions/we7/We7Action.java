package sh.calaba.instrumentationbackend.actions.we7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.TestHelpers;
import sh.calaba.instrumentationbackend.actions.we7.actionbar.We7ActionHelper;
import android.app.Activity;
import android.os.StrictMode;
import android.view.View;
import android.widget.ListView;

public class We7Action {

  protected static final String LAST_CLICK_TEXT = "lastclicktext";
  protected static final String LAST_LONG_CLICK_TEXT = "lastlongclicktext";

  // This MUST be the same value as set in com.we7.player.ui.fragment.We7Fragment.CONTENT_AVAILABILITY_KEY
  public static final Integer CONTENT_AVAILABILITY_KEY = 100;

  public We7Action() {

    StrictMode.enableDefaults();

  }

  protected void setValue(String key, String value) {
    We7ActionHelper.setValue(key, value);
  }

  protected String getValue(String key) {
    return We7ActionHelper.getValue(key);
  }

  protected int getListViewIndex(final String listContentDescription) {

    ArrayList<ListView> listViews = InstrumentationBackend.solo.getCurrentListViews();

    InstrumentationBackend.log("Found " + listViews.size() + " list views. Looking for index for " + listContentDescription);

    for (int i = 0; i < listViews.size(); i++) {

      String contentDescription = listViews.get(i).getContentDescription().toString();

      InstrumentationBackend.log("Content descrition for list index " + i + " = " + contentDescription);

      if (contentDescription.equalsIgnoreCase(listContentDescription)) {

        InstrumentationBackend.log("Found list for " + listContentDescription);

        return i;

      }

    }

    return -1;

  }

  protected ListView getListView(final String tabName) {

    ArrayList<ListView> listViews = InstrumentationBackend.solo.getCurrentListViews();

    InstrumentationBackend.log("Found " + listViews.size() + " list views. Looking for index for " + tabName);

    for (int i = 0; i < listViews.size(); i++) {

      String contentDescription;
      if (listViews.get(i).getContentDescription() != null) {

        contentDescription = listViews.get(i).getContentDescription().toString().replaceAll("\n", " ");

        InstrumentationBackend.log("Content descrition for list index " + i + " = " + contentDescription);

        if (contentDescription.equalsIgnoreCase(tabName)) {

          InstrumentationBackend.log("Found list for " + tabName);

          return listViews.get(i);

        }

      }

    }

    return null;

  }

  protected boolean pressView(String viewId) {

    InstrumentationBackend.log("Clicking on " + viewId);

    final View view = TestHelpers.getViewById(viewId);

    if (view == null) {
      return false;
    }

    try {
      
      InstrumentationBackend.log("Clicking on view: " + view.getClass());
      InstrumentationBackend.solo.clickOnView(view);
      
    } catch (junit.framework.AssertionFailedError e) {
      
      InstrumentationBackend.log("solo.clickOnView failed - using fallback");
      if (view.isClickable()) {
        
        InstrumentationBackend.solo.getCurrentActivity().runOnUiThread(new Runnable() {
          
          public void run() {
            view.performClick();
          }
          
        });
        
      }
      
    }

    return true;

  }

  protected Integer getTestViewTagId() {

    InstrumentationBackend.log("getTestViewTagId called");
    
    Activity currentActivity = getCurrentActivity();
    Method method = getMethod(currentActivity.getClass(), "getTestItemsTagId");
    Integer testTagArrayId = (Integer) invokeMethod(method, null);
    
    if (testTagArrayId == null) {
      testTagArrayId = -1;
    }

    InstrumentationBackend.log("Retireved testTagArrayId = " + testTagArrayId);

    return testTagArrayId;
    
    /*
    Activity currentActivity = InstrumentationBackend.solo.getCurrentActivity();
    Integer testTagArrayId = null;

    InstrumentationBackend.log("currentActivity class = " + currentActivity.getClass().toString());

    try {
      Method method = currentActivity.getClass().getMethod("getTestItemsTagId");
      testTagArrayId = (Integer) method.invoke(null);
    } catch (SecurityException se) {
      // TODO Auto-generated catch block
      se.printStackTrace();
      InstrumentationBackend.log("se thrown " + se.getMessage());
    } catch (NoSuchMethodException nsme) {
      // TODO Auto-generated catch block
      nsme.printStackTrace();
      InstrumentationBackend.log("nsmee thrown " + nsme.getMessage());
    } catch (IllegalArgumentException iarge) {
      // TODO Auto-generated catch block
      iarge.printStackTrace();
      InstrumentationBackend.log("iarge thrown " + iarge.getMessage());
    } catch (IllegalAccessException iacce) {
      // TODO Auto-generated catch block
      iacce.printStackTrace();
      InstrumentationBackend.log("iacce thrown " + iacce.getMessage());
    } catch (InvocationTargetException ite) {
      // TODO Auto-generated catch block
      ite.printStackTrace();
      InstrumentationBackend.log("ite thrown " + ite.getMessage());
    }

    if (testTagArrayId == null) {
      return -1;
    }

    InstrumentationBackend.log("Retireved testTagArrayId = " + testTagArrayId);

    return testTagArrayId;
     */
  }

  private Method getMethod(Class<?> targetClass, String methodName) {
    
    try {
      Method method = targetClass.getMethod(methodName);
      return method;
    } catch (SecurityException se) {
      se.printStackTrace();
      InstrumentationBackend.log("se thrown " + se.getMessage());
    } catch (NoSuchMethodException nsme) {
      nsme.printStackTrace();
      InstrumentationBackend.log("nsmee thrown " + nsme.getMessage());
    } catch (IllegalArgumentException iarge) {
      iarge.printStackTrace();
      InstrumentationBackend.log("iarge thrown " + iarge.getMessage());
    } catch (IllegalStateException ise) {
      ise.printStackTrace();
      InstrumentationBackend.log("ise thrown " + ise.getMessage());
    }
    
    return null;
    
  }
  
  private boolean invokeVoidMethod(Method method, Object reciever) {
    
    return invokeVoidMethod(method, reciever, (Object[]) null);
      
  }
  
  private Object invokeMethod(Method method, Object reciever) {
    
    return invokeMethod(method, reciever, (Object[]) null);
      
  }
    
  private boolean invokeVoidMethod(Method method, Object reciever, Object... args) {
    
    try {
      method.invoke(reciever);
      return true;
    } catch (IllegalArgumentException iarge) {
      iarge.printStackTrace();
      InstrumentationBackend.log("iarge thrown " + iarge.getMessage());
    } catch (IllegalAccessException iacce) {
      iacce.printStackTrace();
      InstrumentationBackend.log("iacce thrown " + iacce.getMessage());
    } catch (InvocationTargetException ite) {
      ite.printStackTrace();
      InstrumentationBackend.log("ite thrown " + ite.getMessage());
    }
    
    return false;
    
  }
  
  private Object invokeMethod(Method method, Object reciever, Object... args) {
    
    try {
      return method.invoke(reciever);
    } catch (IllegalArgumentException iarge) {
      iarge.printStackTrace();
      InstrumentationBackend.log("iarge thrown " + iarge.getMessage());
    } catch (IllegalAccessException iacce) {
      iacce.printStackTrace();
      InstrumentationBackend.log("iacce thrown " + iacce.getMessage());
    } catch (InvocationTargetException ite) {
      ite.printStackTrace();
      InstrumentationBackend.log("ite thrown " + ite.getMessage());
    }
    
    return null;
    
  }
  
  private Activity getCurrentActivity() {
    
    Activity currentActivity = InstrumentationBackend.solo.getCurrentActivity();
    InstrumentationBackend.log("currentActivity class = " + currentActivity.getClass().toString());
    return currentActivity;
    
  }
  
  protected Boolean isRunningOnTwoPane() {
    
    Activity currentActivity = getCurrentActivity();
    Method method = getMethod(currentActivity.getClass(), "isRunningOnTwoPane");
    Boolean isOnTwoPane = (Boolean) invokeMethod(method, currentActivity);
    InstrumentationBackend.log("isRunningOnTwoPane = " + isOnTwoPane.toString());
    return isOnTwoPane;
    
  }
  
  protected Boolean isRunningOnEmulator() {

    Activity currentActivity = getCurrentActivity();
    Method method = getMethod(currentActivity.getClass(), "isRunningOnEmulator");
    Boolean isEmulator = (Boolean) invokeMethod(method, null);
    InstrumentationBackend.log("isRunningOnEmulator = " + isEmulator.toString());
    return isEmulator;
    
  }
  
  
  protected boolean set3gForEmulator() {

    Activity currentActivity = getCurrentActivity();
    Method method = getMethod(currentActivity.getClass(), "setEnable3gForEmulator");
    boolean success = invokeVoidMethod(method, null);
    InstrumentationBackend.log("Invoked setEnable3gForEmulator result=" + success);
    return success;
    
  }
  
  protected int getOsVersion() {
    return android.os.Build.VERSION.SDK_INT;
  }

  protected boolean isGingerbread() {
    return (android.os.Build.VERSION.SDK_INT >= 9 && android.os.Build.VERSION.SDK_INT < 11);
  }

  protected boolean isHoneycomb() {
    return (android.os.Build.VERSION.SDK_INT >= 11 && android.os.Build.VERSION.SDK_INT < 14);
  }
  
  protected boolean isICS() {
    return (android.os.Build.VERSION.SDK_INT >= 14 && android.os.Build.VERSION.SDK_INT < 16);
  }
  
  protected boolean isJellybean() {
    return (android.os.Build.VERSION.SDK_INT >= 16);
  }
  
  protected boolean isLaterThanGingerbread() {
    return (android.os.Build.VERSION.SDK_INT >= 11);
  }
  
}
