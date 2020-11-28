package kuneri.ersan.com.groupdiscount;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AnyOtherSearchButtonListener implements View.OnClickListener {
    Context context;
    String id;
    ObjectProduct obj = null;
    int userId;

    public AnyOtherSearchButtonListener(int userId) {
        this.userId = userId;
    }
    @Override
    public void onClick(View view){
        context = view.getRootView().getContext();

        final EditText searchedProduct = (EditText) view.getRootView().findViewById(R.id.searchForm);
        String productN = searchedProduct.getText().toString();

        obj = new TableControllerProduct(context).search(productN);
        if(obj == null){
            searchedProduct.setText("No Result");
        }else {
            final CharSequence[] items = { "Delete", "Keep"};
            new AlertDialog.Builder(context).setTitle("Product Record")
                    .setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            if (item == 0) {
                                boolean deleteSuccessful = new TableControllerCart(context).delete(obj.id, userId);

                                if (deleteSuccessful) {
                                    Toast.makeText(context, "Product record was deleted on your cart.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Unable to delete product record.", Toast.LENGTH_SHORT).show();
                                }

                                ((UserPageActivity) context).countRecords1();
                                ((UserPageActivity) context).readRecords1();

                            }
                            else if (item == 1) {
                                Toast.makeText(context, "Product is keep on your cart.", Toast.LENGTH_SHORT).show();
                                ((UserPageActivity) context).countRecords1();
                                ((UserPageActivity) context).readRecords1();

                            }
                            dialog.dismiss();

                        }
                    }).show();

        }
    }
}
