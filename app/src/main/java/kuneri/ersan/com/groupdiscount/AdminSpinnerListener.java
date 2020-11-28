package kuneri.ersan.com.groupdiscount;


import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

public class AdminSpinnerListener implements AdapterView.OnItemSelectedListener {
    Context context;

    public AdminSpinnerListener() {

    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        context = view.getRootView().getContext();
        ((AdminActivity) context).readRecords(parent.getItemAtPosition(pos).toString());
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }
}