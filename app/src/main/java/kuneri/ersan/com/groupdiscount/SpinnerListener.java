package kuneri.ersan.com.groupdiscount;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

public class SpinnerListener implements AdapterView.OnItemSelectedListener {
    Context context;

    public SpinnerListener() {

    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        context = view.getRootView().getContext();
        ((MainActivity) context).readRecords(parent.getItemAtPosition(pos).toString());
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
