package kuneri.ersan.com.groupdiscount;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class OnClickListenerCreateStudent implements View.OnClickListener {
    int userId;

    public OnClickListenerCreateStudent(int userId) {
        this.userId = userId;
    }

    @Override
    public void onClick(View view) {
        final Context context = view.getRootView().getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        final View formElementsView = inflater.inflate(R.layout.student_input_form, null, false);

        final EditText productName =  formElementsView.findViewById(R.id.editTextStudentFirstname);
        final EditText productPrice =  formElementsView.findViewById(R.id.editTextStudentEmail);
        final EditText productCount =  formElementsView.findViewById(R.id.editTextStudentCount);

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Create Product")
                .setPositiveButton("Add",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String studentFirstname = productName.getText().toString();
                                String studentEmail = productPrice.getText().toString();
                                String studentCount = productCount.getText().toString();
                                ObjectProduct objectProduct = new ObjectProduct();
                                objectProduct.name = studentFirstname;
                                objectProduct.price = Double.parseDouble(studentEmail);
                                objectProduct.count = Integer.parseInt(studentCount);
                                objectProduct.sellerId = userId;
                                boolean createSuccessful = new TableControllerProduct(context).create(objectProduct);
                                if(createSuccessful){
                                    Toast.makeText(context, "Product information was saved.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Unable to save product information.", Toast.LENGTH_SHORT).show();
                                }
                                ((UserPageActivity) context).countRecords();
                                ((UserPageActivity) context).readRecords();
                                dialog.cancel();

                            }

                        }).show();


    }

}
