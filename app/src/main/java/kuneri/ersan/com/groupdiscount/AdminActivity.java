package kuneri.ersan.com.groupdiscount;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AdminActivity extends AppCompatActivity {
    public static String name;
    static int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
        //Button buttonCreateStudent = findViewById(R.id.buttonCreateStudent);
        //buttonCreateStudent.setOnClickListener(new OnClickListenerCreateStudent());

        String username = "admin";
        if (username != null) {
            AdminActivity.name = username;
        }

        TextView textView = findViewById(R.id.sellerName);
        textView.setText(name.toString());

        userId = new TableControllerUser(this).searchName(AdminActivity.name);

        Button searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new SearchButtonListener(userId));


        Spinner dropdown = findViewById(R.id.spinner1);

        String[] items = new String[]{"recent","price", "name"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdminSpinnerListener());

        Spinner dropdown1 = findViewById(R.id.spinner2);

        String[] items1 = new String[]{"newest","name", "email"};

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);

        dropdown1.setAdapter(adapter1);
        dropdown1.setOnItemSelectedListener(new AdminSpinnerListener1());



        //sS.userId = new TableControllerUser(context).search(username);
        Button sellingButton = findViewById(R.id.sellingItems);
        sellingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                countRecords();
                readRecords();
            }
        });

        Button backButton = findViewById(R.id.loginButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnHome();
            }
        });

        Button buyedButton = findViewById(R.id.buyedItems);
        buyedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                readRecords1("newest");
            }
        });

    }

    public void returnHome(){
        Intent intent = new Intent(this , LoginActivity.class);
        startActivity(intent);
    }

    public void countRecords() {
        int recordCount = new TableControllerProduct(this).count();
        TextView textViewRecordCount = (TextView) findViewById(R.id.textViewRecordCount);
        textViewRecordCount.setText(recordCount + " records found.");
    }


    public void readRecords() {

        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
        linearLayoutRecords.removeAllViews();


        List<ObjectProduct> productss = new TableControllerProduct(this).read();

        if (productss.size() > 0) {

            for (ObjectProduct obj : productss) {

                View view = new View(AdminActivity.this);
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
                productName.setOnLongClickListener(new AdminLongClickListener());

                final TextView productPrice =  productView.findViewById(R.id.plist_price_text);
                //productName.setPadding(50, 10, 0, 10);
                productPrice.setText(productP + "$");
                productPrice.setTag(Integer.toString(id));
                productPrice.setOnLongClickListener(new AdminLongClickListener());

                //final TextView productCount =  productView.findViewById(R.id.cart_product_quantity_tv);
                final TextView productCount =  productView.findViewById(R.id.cart_product_quantity_tv);
                //productName.setPadding(50, 10, 0, 10);
                productCount.setText(productC);
                productCount.setTag(Integer.toString(id));
                productCount.setOnLongClickListener(new AdminLongClickListener());

                final TextView productCount1 =  productView.findViewById(R.id.plist_weight_text);
                //productName.setPadding(50, 10, 0, 10);
                productCount1.setText("Stock " + productC);
                productCount1.setTag(Integer.toString(id));
                productCount1.setOnLongClickListener(new AdminLongClickListener());


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

    public void readRecords1(String sortBy) {

        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
        linearLayoutRecords.removeAllViews();


        List<ObjectUser> productss = new TableControllerUser(this).read(sortBy);

        if (productss.size() > 0) {

            for (ObjectUser obj : productss) {

                View view = new View(AdminActivity.this);
                final Context context = view.getRootView().getContext();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                assert inflater != null;
                final View productView = inflater.inflate(R.layout.settings1, null, false);


                int id = obj.id;
                String productN = obj.name;
                String productP = obj.email.toString();
                String productC = obj.password.toString();


                final TextView productName =  productView.findViewById(R.id.edtUsername);
                //productName.setPadding(50, 10, 0, 10);
                productName.setText(productN);
                productName.setTag(Integer.toString(id));
                productName.setOnLongClickListener(new AdminLongClickListener1());

                final TextView productPrice =  productView.findViewById(R.id.edtPassword);
                //productName.setPadding(50, 10, 0, 10);
                productPrice.setText(productP + "$");
                productPrice.setTag(Integer.toString(id));
                productPrice.setOnLongClickListener(new AdminLongClickListener1());

                //final TextView productCount =  productView.findViewById(R.id.cart_product_quantity_tv);
                final TextView productCount =  productView.findViewById(R.id.edtEmail);
                //productName.setPadding(50, 10, 0, 10);
                productCount.setText(productC);
                productCount.setTag(Integer.toString(id));
                productCount.setOnLongClickListener(new AdminLongClickListener1());



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


    public void readRecords(String sortBy) {
        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
        linearLayoutRecords.removeAllViews();

        List<ObjectProduct> productss = new TableControllerProduct(this).read(sortBy);

        if (productss.size() > 0) {
            for (ObjectProduct obj : productss) {

                View view = new View(AdminActivity.this);
                final Context context = view.getRootView().getContext();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                assert inflater != null;
                final View productView = inflater.inflate(R.layout.product_main, null, false);


                int id = obj.id;
                String productN = obj.name;
                String productP = obj.price.toString();
                final String productC = obj.count.toString();

                final AdminLongClickListener currentListener = new AdminLongClickListener();


                final TextView productName =  productView.findViewById(R.id.from_name);
                //productName.setPadding(50, 10, 0, 10);
                productName.setText(productN);
                productName.setTag(Integer.toString(id));
                productName.setOnLongClickListener(currentListener);

                final TextView productPrice =  productView.findViewById(R.id.plist_price_text);
                //productName.setPadding(50, 10, 0, 10);
                productPrice.setText(productP + "$");
                productPrice.setTag(Integer.toString(id));
                productPrice.setOnLongClickListener(currentListener);

                final TextView productCount =  productView.findViewById(R.id.cart_product_quantity_tv);
                //productName.setPadding(50, 10, 0, 10);
                productCount.setText("0");
                productCount.setTag(Integer.toString(id));
                productCount.setOnLongClickListener(currentListener);

                final TextView productCount1 =  productView.findViewById(R.id.plist_weight_text);
                //productName.setPadding(50, 10, 0, 10);
                productCount1.setText("Stock " + productC);
                productCount1.setTag(Integer.toString(id));
                productCount1.setOnLongClickListener(currentListener);

                final ImageView plusButton = productView.findViewById(R.id.cart_plus_img);
                plusButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int value = Integer.parseInt(productCount.getText().toString());
                        if(value < Integer.parseInt(productC)) {
                            productCount.setText(String.valueOf(value + 1));
                            currentListener.count = value+1;
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
                            currentListener.count = value-1;
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

}
