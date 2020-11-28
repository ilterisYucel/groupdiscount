package kuneri.ersan.com.groupdiscount;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

public class PurchasedLongClickListener implements View.OnLongClickListener {
    Context context;
    String id;
    ObjectProduct obj = null;
    int userId;
    public PurchasedLongClickListener(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean onLongClick(View view) {
        context = view.getContext();
        id = view.getTag().toString();
        final CharSequence[] items = { "Delete", "Keep"};

        new AlertDialog.Builder(context).setTitle("Product Record")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            boolean deleteSuccessful = new TableControllerPurchased(context).delete(Integer.parseInt(id), userId);

                            if (deleteSuccessful) {
                                Toast.makeText(context, "Product record was deleted.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Unable to delete product record.", Toast.LENGTH_SHORT).show();
                            }

                            ((UserPageActivity) context).countRecords2();
                            ((UserPageActivity) context).readRecords2();

                        }
                        else if (item == 1) {
                            Toast.makeText(context, "Product is keep on your cart.", Toast.LENGTH_SHORT).show();
                            ((UserPageActivity) context).countRecords2();
                            ((UserPageActivity) context).readRecords2();

                        }
                        dialog.dismiss();

                    }
                }).show();
        return false;
    }

}
