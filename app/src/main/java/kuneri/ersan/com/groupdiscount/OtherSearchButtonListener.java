package kuneri.ersan.com.groupdiscount;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class OtherSearchButtonListener implements View.OnClickListener {
    Context context;
    String id;
    ObjectProduct obj = null;
    @Override
    public void onClick(View view){
        context = view.getRootView().getContext();

        final EditText searchedProduct = (EditText) view.getRootView().findViewById(R.id.searchForm);
        String productN = searchedProduct.getText().toString();

        obj = new TableControllerProduct(context).search(productN);
        if(obj == null){
            searchedProduct.setText("No Result");
        }else {
            final CharSequence[] items = { "Update", "Delete"};
            new AlertDialog.Builder(context).setTitle("Product Record")
                    .setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            if (item == 0) {
                                final TableControllerProduct tableControllerProduct = new TableControllerProduct(context);
                                ObjectProduct objectProduct = tableControllerProduct.readSingleRecord(obj.id);
                                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                final View formElementsView = inflater.inflate(R.layout.student_input_form, null, false);
                                final EditText productName = (EditText) formElementsView.findViewById(R.id.editTextStudentFirstname);
                                final EditText productPrice = (EditText) formElementsView.findViewById(R.id.editTextStudentEmail);
                                final EditText productCount = (EditText) formElementsView.findViewById(R.id.editTextStudentCount);
                                productName.setText(objectProduct.name);
                                productPrice.setText(objectProduct.price.toString());
                                productCount.setText(objectProduct.count.toString());
                                new AlertDialog.Builder(context)
                                        .setView(formElementsView)
                                        .setTitle("Edit Record")
                                        .setPositiveButton("Save Changes",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        ObjectProduct objectProduct = new ObjectProduct();
                                                        objectProduct.id = obj.id;
                                                        objectProduct.name = productName.getText().toString();
                                                        objectProduct.price = Double.parseDouble(productPrice.getText().toString());
                                                        objectProduct.count = Integer.parseInt(productCount.getText().toString());
                                                        boolean updateSuccessful = tableControllerProduct.update(objectProduct);

                                                        if(updateSuccessful){
                                                            Toast.makeText(context, "Product record was updated.", Toast.LENGTH_SHORT).show();
                                                        }else{
                                                            Toast.makeText(context, "Unable to update product record.", Toast.LENGTH_SHORT).show();
                                                        }
                                                        ((UserPageActivity) context).countRecords();
                                                        ((UserPageActivity) context).readRecords();
                                                        dialog.cancel();
                                                    }

                                                }).show();

                            }
                            else if (item == 1) {

                                boolean deleteSuccessful = new TableControllerProduct(context).delete(obj.id);

                                if (deleteSuccessful) {
                                    Toast.makeText(context, "Product record was deleted.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Unable to delete product record.", Toast.LENGTH_SHORT).show();
                                }

                                ((UserPageActivity) context).countRecords();
                                ((UserPageActivity) context).readRecords();

                            }
                            dialog.dismiss();

                        }
                    }).show();

        }
    }
}
