package kuneri.ersan.com.groupdiscount;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class OnLongClickListenerProduct implements  View.OnLongClickListener {
        Context context;
        String id;
        ObjectProduct obj = null;
        int userId;
        int count;
        public OnLongClickListenerProduct(int userId) {
            this.userId = userId;
            this.count = 0;
        }

    @Override
    public boolean onLongClick(View view) {
        context = view.getContext();
        id = view.getTag().toString();
        final CharSequence[] items = { "Add to Cart", "Back"};

        new AlertDialog.Builder(context).setTitle("Product Record")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0 && count > 0) {
                            ObjectCarts objectCarts = new ObjectCarts();
                            objectCarts.userId = userId;
                            objectCarts.itemId = Integer.parseInt(id);
                            objectCarts.count = count;
                            boolean addSuccessful = new TableControllerCart(context).create(objectCarts);
                            if (addSuccessful){
                                Toast.makeText(context, "Product add to your cart.", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "Product can't add to cart.", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else if (count <= 0){
                            Toast.makeText(context, "Can't add 0 products.", Toast.LENGTH_SHORT).show();
                        }

                        else if (item == 1) {

                            /*boolean deleteSuccessful = new TableControllerProduct(context).delete(Integer.parseInt(id));

                            if (deleteSuccessful){
                                Toast.makeText(context, "Product record was deleted.", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "Unable to delete product record.", Toast.LENGTH_SHORT).show();
                            }*/

                            ((MainActivity) context).countRecords();
                            ((MainActivity) context).readRecords();

                        }
                        dialog.dismiss();

                    }
                }).show();
        return false;
    }

    /*public void editRecord(final int productId) {
        final TableControllerProduct tableControllerProduct = new TableControllerProduct(context);
        ObjectProduct objectProduct = tableControllerProduct.readSingleRecord(productId);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.product_details, null, false);
        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Edit Record")
                .setPositiveButton("Save Changes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ObjectProduct objectProduct = new ObjectProduct();
                                objectProduct.id = productId;
                                objectProduct.name = productName.getText().toString();
                                objectProduct.price = Double.parseDouble(productPrice.getText().toString());
                                objectProduct.count = Integer.parseInt(productCount.getText().toString());
                                boolean updateSuccessful = tableControllerProduct.update(objectProduct);

                                if(updateSuccessful){
                                    Toast.makeText(context, "Student record was updated.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Unable to update student record.", Toast.LENGTH_SHORT).show();
                                }
                                ((UserPageActivity) context).countRecords();
                                ((UserPageActivity) context).readRecords();
                                dialog.cancel();
                            }

                        }).show();


    }*/



}
