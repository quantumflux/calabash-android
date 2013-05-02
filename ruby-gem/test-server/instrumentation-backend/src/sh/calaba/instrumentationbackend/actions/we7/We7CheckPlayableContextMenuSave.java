package sh.calaba.instrumentationbackend.actions.we7;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class We7CheckPlayableContextMenuSave extends We7Action implements Action {

  @Override
  public Result execute(String... args) {

    String savedListContentDescription = args[0];

    String lastLongClickText = getValue(LAST_LONG_CLICK_TEXT);

    if (lastLongClickText == null) {
      return new Result(false, "No text LAST_LONG_CLICK_TEXT in cache");
    }

    lastLongClickText = lastLongClickText.split(",")[0];

    InstrumentationBackend.log("Searching for saved item: " + lastLongClickText);

    ListView savedListView = getListView(savedListContentDescription);

    for (int i = 0; i < savedListView.getCount(); i++) {

      for (int j = 0; j < savedListView.getChildCount(); j++) {
        View childView = savedListView.getChildAt(j);

        if (childView.getClass() == TextView.class) {

          InstrumentationBackend.log("TextView found, text = " + ((TextView) childView).getText().toString());

        }

      }

      String contentDescription = "No content description found";

      savedListView.getItemAtPosition(i);

      if (savedListView.getAdapter().getView(i, null, null).getContentDescription() != null) {
        contentDescription = savedListView.getAdapter().getView(i, null, null).getContentDescription().toString();
      }

      InstrumentationBackend.log("Checking saved item text against: " + contentDescription);

      if (contentDescription.equalsIgnoreCase(lastLongClickText)) {

        return Result.successResult();

      }

    }

    return new Result(false, "Didn't find item with contentDescription matching " + lastLongClickText);

  }

  @Override
  public String key() {
    return "check_last_playable_context_menu_save";
  }

}
