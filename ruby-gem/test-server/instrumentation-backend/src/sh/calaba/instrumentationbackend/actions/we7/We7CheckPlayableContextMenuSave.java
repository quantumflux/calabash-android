package sh.calaba.instrumentationbackend.actions.we7;

import sh.calaba.instrumentationbackend.InstrumentationBackend;
import sh.calaba.instrumentationbackend.Result;
import sh.calaba.instrumentationbackend.actions.Action;
import android.widget.ListView;

public class We7CheckPlayableContextMenuSave extends We7Action implements Action {

  @Override
  public Result execute(String... args) {

    String savedListContentDescription = args[0];

    String lastLongClickText = getValue(LAST_LONG_CLICK_TEXT);

    if (lastLongClickText == null) {
      return new Result(false, "No text LAST_LONG_CLICK_TEXT in cache");
    }

    InstrumentationBackend.log("Searching for saved item" + lastLongClickText);

    ListView savedListView = getListView(savedListContentDescription);

    for (int i = 0; i < savedListView.getCount(); i++) {

      String contentDescription = "";
      if (savedListView.getAdapter().getView(i, null, null).getContentDescription() != null) {
        contentDescription = savedListView.getAdapter().getView(i, null, null).getContentDescription().toString();
      }

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
