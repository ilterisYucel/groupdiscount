package kuneri.ersan.com.groupdiscount;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class UserPageActivity extends AppCompatActivity {
    static Integer userId;
    static String username;
    static int mod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        StatusSwitch statusSwitch = (StatusSwitch) i.getSerializableExtra("SS");
        userId = new Integer(statusSwitch.userId);
        username = new TableControllerUser(this).searchID(userId);
        mod = statusSwitch.mod;
        if(statusSwitch.mod == 0){
            setContentView(R.layout.selling_view);
            TextView text = findViewById(R.id.sellerName);
            text.setText(username);
            Button buttonCreateStudent = findViewById(R.id.buttonCreateStudent);
            buttonCreateStudent.setOnClickListener(new OnClickListenerCreateStudent(userId));

            Button searchButton = findViewById(R.id.searchButton);
            searchButton.setOnClickListener(new OtherSearchButtonListener());

            countRecords();
            readRecords();
        }

        if(statusSwitch.mod == 1){
            setContentView(R.layout.purchased);
            TextView text = findViewById(R.id.sellerName);
            text.setText(username);

            Button searchButton = findViewById(R.id.searchButton);
            searchButton.setOnClickListener(new PurchaseSearchButtonListener(userId));

            Button purchaseButton = findViewById(R.id.loginButton);
            purchaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dellAll();

                }
            });

            countRecords2();
            readRecords2();

        }

        if(statusSwitch.mod == 2){
            setContentView(R.layout.my_cart);
            TextView text = findViewById(R.id.sellerName);
            text.setText(username);

            Button searchButton = findViewById(R.id.searchButton);
            searchButton.setOnClickListener(new AnyOtherSearchButtonListener(userId));

            Button purchaseButton = findViewById(R.id.loginButton);
            purchaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    purchased();

                }
            });

            countRecords1();
            readRecords1();


        }

        if(statusSwitch.mod == 3){
            setContentView(R.layout.settings);

            Button purchaseButton = findViewById(R.id.btnLogin);
            purchaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    updateSettings();

                }
            });

            readUserInf();


        }
    }

    public void readUserInf(){
        final ObjectUser user = new TableControllerUser(this).getObjWithName(username);
        TextView name = findViewById(R.id.edtUsername);
        TextView email = findViewById(R.id.edtEmail);
        TextView password = findViewById(R.id.edtPassword);
        if(user != null){
            name.setText(user.name);
            email.setText(user.email);
            password.setText(user.password);
        } else{
            name.setText("User Not Found");
        }

    }

    public void updateSettings(){
        final Context context = this;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        final View formElementsView = inflater.inflate(R.layout.update_user, null, false);

        final EditText productName =  formElementsView.findViewById(R.id.editTextStudentFirstname);
        final EditText productPrice =  formElementsView.findViewById(R.id.editTextStudentEmail);
        final EditText productCount =  formElementsView.findViewById(R.id.editTextStudentCount);
        final EditText productCount1 =  formElementsView.findViewById(R.id.editTextStudentCount1);
        final EditText productCount2 =  formElementsView.findViewById(R.id.editTextStudentCount2);

        final ObjectUser user = new TableControllerUser(this).getObjWithName(username);
        if(user != null){
            productName.setText(user.name);
            productPrice.setText(user.email);
        }


        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Update User")
                .setPositiveButton("Edit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String studentFirstname = productName.getText().toString();
                                String studentEmail = productPrice.getText().toString();
                                String studentCount = productCount.getText().toString();
                                ObjectUser objectUser = new ObjectUser();
                                objectUser.id = userId;
                                objectUser.name = studentFirstname;
                                objectUser.email = studentEmail;

                                if (!productCount.getText().toString().equals(user.password)) {
                                    Toast.makeText(context, "Incorrect password", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (!productCount1.getText().toString().isEmpty()) {
                                        if (productCount1.getText().toString().equals(productCount2.getText().toString())) {
                                            objectUser.password = productCount1.getText().toString();
                                        } else {
                                            Toast.makeText(context, "New passwords do not match.", Toast.LENGTH_SHORT).show();
                                            objectUser.password = user.password;
                                        }

                                    } else {
                                        objectUser.password = user.password;
                                    }

                                    boolean createSuccessful = new TableControllerUser(context).update(objectUser);
                                    if (createSuccessful) {
                                        Toast.makeText(context, "User information was saved.", Toast.LENGTH_SHORT).show();
                                        ((UserPageActivity) context).username = objectUser.name;
                                        ((MainActivity) ((UserPageActivity) context).getParent()).name = objectUser.name;
                                    } else {
                                        Toast.makeText(context, "Unable to save user information.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                //((UserPageActivity) context).countRecords();
                                //((UserPageActivity) context).readRecords();
                                ((UserPageActivity) context).readUserInf();
                                //((UserPageActivity) context).recreate();
                                dialog.cancel();

                            }

                        }).show();


    }


    public void countRecords() {
        int recordCount = new TableControllerProduct(this).countSellerItems(userId);
        TextView textViewRecordCount = (TextView) findViewById(R.id.textViewRecordCount);
        textViewRecordCount.setText(recordCount + " records found.");
    }

    public void countRecords1() {
        int recordCount = new TableControllerCart(this).count(userId);
        TextView textViewRecordCount = (TextView) findViewById(R.id.textViewRecordCount);
        textViewRecordCount.setText(recordCount + " records found.");
    }

    public void countRecords2() {
        int recordCount = new TableControllerPurchased(this).count(userId);
        TextView textViewRecordCount = (TextView) findViewById(R.id.textViewRecordCount);
        textViewRecordCount.setText(recordCount + " records found.");
    }

    public void readRecords() {

        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
        linearLayoutRecords.removeAllViews();


        List<ObjectProduct> productss = new TableControllerProduct(this).readBuyedItems(userId);

        if (productss.size() > 0) {

            for (ObjectProduct obj : productss) {

                View view = new View(UserPageActivity.this);
                final Context context = view.getRootView().getContext();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                assert inflater != null;
                final View productView = inflater.inflate(R.layout.product, null, false);


                int id = obj.id;
                String productN = obj.name;
                String productP = obj.price.toString();
                String productC = obj.count.toString();


                final TextView productName =  productView.findViewById(R.id.from_name);
                //productName.setPadding(50, 10, 0, 10);
                productName.setText(productN);
                productName.setTag(Integer.toString(id));
                productName.setOnLongClickListener(new OnLongClickListenerStudentRecord());

                final TextView productPrice =  productView.findViewById(R.id.plist_price_text);
                //productName.setPadding(50, 10, 0, 10);
                productPrice.setText(productP + "$");
                productPrice.setTag(Integer.toString(id));
                productPrice.setOnLongClickListener(new OnLongClickListenerStudentRecord());

                final TextView productCount =  productView.findViewById(R.id.cart_product_quantity_tv);
                //productName.setPadding(50, 10, 0, 10);
                productCount.setText(productC);
                productCount.setTag(Integer.toString(id));
                productCount.setOnLongClickListener(new OnLongClickListenerStudentRecord());


                /*TextView textViewStudentItem= new TextView(this);
                textViewStudentItem.setPadding(50, 10, 0, 10);
                textViewStudentItem.setText(textViewContents);
                textViewStudentItem.setTag(Integer.toString(id));
                textViewStudentItem.setOnLongClickListener(new OnLongClickListenerStudentRecord());*/


                linearLayoutRecords.addView(productView);

                /*ImageView img = new ImageView(this);
                img.setImageResource(R.drawable.ic_basket);
                img.setPadding(0,10,0,10);
                linearLayoutRecords.addView(img);*/
            }

        }

        else {

            TextView locationItem = new TextView(this);
            locationItem.setPadding(8, 8, 8, 8);
            locationItem.setText("No records yet.");

            linearLayoutRecords.addView(locationItem);
        }

    }

    public void readRecords1() {

        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
        linearLayoutRecords.removeAllViews();


        List<Integer> itemIds = new TableControllerCart(this).read(userId);
        List<ObjectProduct> productss = new TableControllerProduct(this).readSellItems(itemIds);

        if (productss.size() > 0) {

            for (ObjectProduct obj : productss) {

                View view = new View(UserPageActivity.this);
                final Context context = view.getRootView().getContext();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                assert inflater != null;
                final View productView = inflater.inflate(R.layout.product_main, null, false);


                int id = obj.id;
                String productN = obj.name;
                String productP = obj.price.toString();
                final String productC = obj.count.toString();


                final TextView productName =  productView.findViewById(R.id.from_name);
                //productName.setPadding(50, 10, 0, 10);
                productName.setText(productN);
                productName.setTag(Integer.toString(id));
                productName.setOnLongClickListener(new OtherLongClickListener(userId));

                final TextView productPrice =  productView.findViewById(R.id.plist_price_text);
                //productName.setPadding(50, 10, 0, 10);
                productPrice.setText(productP + "$");
                productPrice.setTag(Integer.toString(id));
                productPrice.setOnLongClickListener(new OtherLongClickListener(userId));

                final TextView productCount =  productView.findViewById(R.id.cart_product_quantity_tv);
                //productName.setPadding(50, 10, 0, 10);
                productCount.setText(String.valueOf(new TableControllerCart(this).readCount(userId,id)));
                productCount.setTag(Integer.toString(id));
                productCount.setOnLongClickListener(new OtherLongClickListener(userId));

                final TextView productCount1 =  productView.findViewById(R.id.plist_weight_text);
                //productName.setPadding(50, 10, 0, 10);
                productCount1.setText("Stock " + productC);
                productCount1.setTag(Integer.toString(id));
                productCount1.setOnLongClickListener(new OtherLongClickListener(userId));

                final ImageView plusButton = productView.findViewById(R.id.cart_plus_img);
                plusButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int value = Integer.parseInt(productCount.getText().toString());
                        if(value < Integer.parseInt(productC)) {
                            productCount.setText(String.valueOf(value + 1));
                        }

                    }
                });

                final ImageView minusButton = productView.findViewById(R.id.cart_minus_img);
                minusButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int value = Integer.parseInt(productCount.getText().toString());
                        if (value > 0 ) {
                            productCount.setText(String.valueOf(Integer.parseInt(productCount.getText().toString()) - 1));
                        }

                    }
                });


                /*TextView textViewStudentItem= new TextView(this);
                textViewStudentItem.setPadding(50, 10, 0, 10);
                textViewStudentItem.setText(textViewContents);
                textViewStudentItem.setTag(Integer.toString(id));
                textViewStudentItem.setOnLongClickListener(new OnLongClickListenerStudentRecord());*/


                linearLayoutRecords.addView(productView);

                /*ImageView img = new ImageView(this);
                img.setImageResource(R.drawable.ic_basket);
                img.setPadding(0,10,0,10);
                linearLayoutRecords.addView(img);*/
            }


        }

        else {

            TextView locationItem = new TextView(this);
            locationItem.setPadding(8, 8, 8, 8);
            locationItem.setText("No records yet.");

            linearLayoutRecords.addView(locationItem);
        }

    }

    public void readRecords2() {

        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
        linearLayoutRecords.removeAllViews();


        List<Integer> itemIds = new TableControllerPurchased(this).read(userId);
        List<ObjectProduct> productss = new TableControllerProduct(this).readSellItems(itemIds);

        if (productss.size() > 0) {

            for (ObjectProduct obj : productss) {

                View view = new View(UserPageActivity.this);
                final Context context = view.getRootView().getContext();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                assert inflater != null;
                final View productView = inflater.inflate(R.layout.product_main, null, false);


                int id = obj.id;
                String productN = obj.name;
                String productP = obj.price.toString();
                String productC = obj.count.toString();


                final TextView productName =  productView.findViewById(R.id.from_name);
                //productName.setPadding(50, 10, 0, 10);
                productName.setText(productN);
                productName.setTag(Integer.toString(id));
                productName.setOnLongClickListener(new PurchasedLongClickListener(userId));

                final TextView productPrice =  productView.findViewById(R.id.plist_price_text);
                //productName.setPadding(50, 10, 0, 10);
                productPrice.setText(productP + "$");
                productPrice.setTag(Integer.toString(id));
                productPrice.setOnLongClickListener(new PurchasedLongClickListener(userId));

                final TextView productCount =  productView.findViewById(R.id.cart_product_quantity_tv);
                //productName.setPadding(50, 10, 0, 10);
                productCount.setText(String.valueOf(new TableControllerPurchased(this).readCount(userId,id)));
                productCount.setTag(Integer.toString(id));
                productCount.setOnLongClickListener(new PurchasedLongClickListener(userId));


                /*TextView textViewStudentItem= new TextView(this);
                textViewStudentItem.setPadding(50, 10, 0, 10);
                textViewStudentItem.setText(textViewContents);
                textViewStudentItem.setTag(Integer.toString(id));
                textViewStudentItem.setOnLongClickListener(new OnLongClickListenerStudentRecord());*/


                linearLayoutRecords.addView(productView);

                /*ImageView img = new ImageView(this);
                img.setImageResource(R.drawable.ic_basket);
                img.setPadding(0,10,0,10);
                linearLayoutRecords.addView(img);*/
            }

        }

        else {

            TextView locationItem = new TextView(this);
            locationItem.setPadding(8, 8, 8, 8);
            locationItem.setText("No records yet.");

            linearLayoutRecords.addView(locationItem);
        }

    }

    public void purchased(){

        List<Integer> itemIds = new TableControllerCart(this).read(userId);
        List<ObjectProduct> productss = new TableControllerProduct(this).readSellItems(itemIds);

        for(int i = 0; i < productss.size(); i++){
            ObjectPurchased obj = new ObjectPurchased();
            obj.itemId = productss.get(i).id;
            obj.userId = userId;
            obj.count = new TableControllerCart(this).readCount(userId,productss.get(i).id);
            boolean delStatus = new TableControllerCart(this).delete(productss.get(i).id, userId);
            Toast.makeText(this, String.valueOf(obj.count), Toast.LENGTH_SHORT).show();
            boolean addStatus = new TableControllerPurchased(this).create(obj);

            ObjectProduct tmp = new TableControllerProduct(this).readSingleRecord(obj.itemId);
            ObjectProduct obj1= new ObjectProduct();
            obj1.id = obj.itemId;
            obj1.count = tmp.count-obj.count;
            obj1.sellerId = tmp.sellerId;
            obj1.price = tmp.price;
            obj1.image = tmp.image;
            obj1.name = tmp.name;
            boolean updateStatus = new TableControllerProduct(this).update(obj1);
        }

        countRecords1();
        readRecords1();

    }


    public void dellAll(){
        List<Integer> itemIds = new TableControllerPurchased(this).read(userId);
        List<ObjectProduct> productss = new TableControllerProduct(this).readSellItems(itemIds);

        for(int i = 0; i < productss.size(); i++){
            boolean delStatus = new TableControllerPurchased(this).delete(productss.get(i).id, userId);
        }

        countRecords2();
        readRecords2();

    }

}
