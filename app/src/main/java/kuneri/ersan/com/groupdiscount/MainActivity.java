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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    //public static final String SS = "com.example.kuneri.ersan.com.groupDiscount.SS";
    public static String name;
    static int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Button buttonCreateStudent = findViewById(R.id.buttonCreateStudent);
        //buttonCreateStudent.setOnClickListener(new OnClickListenerCreateStudent());

        String username = getIntent().getStringExtra(LoginActivity.EXTRA_MESSAGE);
        if (username != null) {
            MainActivity.name = username;
        }

        TextView textView = findViewById(R.id.sellerName);
        textView.setText(name.toString());

        userId = new TableControllerUser(this).searchName(MainActivity.name);

        Button searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new SearchButtonListener(userId));

        final StatusSwitch SS = new StatusSwitch();
        SS.mod = 0;
        SS.userId = userId;

        final StatusSwitch SS1 = new StatusSwitch();
        SS1.mod = 1;
        SS1.userId = userId;

        final StatusSwitch SS2 = new StatusSwitch();
        SS2.mod = 2;
        SS2.userId = userId;

        final StatusSwitch SS3 = new StatusSwitch();
        SS3.mod = 3;
        SS3.userId = userId;
        //TextView textView = findViewById(R.id.sellerName);
        //textView.setText(new TableControllerProduct(context).count());

        Spinner dropdown = findViewById(R.id.spinner1);

        String[] items = new String[]{"recent","price", "name"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new SpinnerListener());



        //sS.userId = new TableControllerUser(context).search(username);
        Button sellingButton = findViewById(R.id.sellingItems);
        sellingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SS);
            }
        });

        Button buyedButton = findViewById(R.id.buyedItems);
        buyedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SS1);
            }
        });

        Button cartButton = findViewById(R.id.userCart);
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SS2);
            }
        });

        Button settingsButton = findViewById(R.id.userSettings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SS3);
            }
        });

        Button backButton = findViewById(R.id.loginButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnHome();
            }
        });

        countRecords();
        readRecords();

    }

    public void returnHome(){
        Intent intent = new Intent(this , LoginActivity.class);
        startActivity(intent);
    }

    public void startActivity(StatusSwitch SS){
        Intent intent = new Intent(this , UserPageActivity.class);
        intent.putExtra("SS", SS);
        startActivity(intent);
    }
    public void countRecords() {
        int recordCount = new TableControllerProduct(this).count();
        TextView textViewRecordCount = (TextView) findViewById(R.id.textViewRecordCount);
        textViewRecordCount.setText(recordCount + " records found.");
    }

    public void addOperation(){
        View view = new View(MainActivity.this);
        final Context context = view.getRootView().getContext();
        //final String id = view.getTag().toString();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        final View formElementsView = inflater.inflate(R.layout.product_details, null, false);

        new AlertDialog.Builder(context).setTitle("Product")
                .setView(formElementsView)
                .setTitle("Item Page")
                .setPositiveButton("add to chart", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        ObjectCarts objectCarts = new ObjectCarts();
                        objectCarts.itemId = item;
                        objectCarts.userId = new TableControllerUser(context).searchName(MainActivity.name);
                        boolean createSuccessful = new TableControllerCart(context).create(objectCarts);
                        if(createSuccessful){
                            Toast.makeText(context, "Product added to chart.", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Product can't be added to chart.", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();

                    }
                }).show();


    }

    public void readRecords() {

        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
        linearLayoutRecords.removeAllViews();


        List<ObjectProduct> productss = new TableControllerProduct(this).read();

        if (productss.size() > 0) {

            for (ObjectProduct obj : productss) {

                View view = new View(MainActivity.this);
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
                productName.setOnLongClickListener(new OnLongClickListenerProduct(userId));

                final TextView productPrice =  productView.findViewById(R.id.plist_price_text);
                //productName.setPadding(50, 10, 0, 10);
                productPrice.setText(productP + "$");
                productPrice.setTag(Integer.toString(id));
                productPrice.setOnLongClickListener(new OnLongClickListenerProduct(userId));

                //final TextView productCount =  productView.findViewById(R.id.cart_product_quantity_tv);
                final TextView productCount =  productView.findViewById(R.id.cart_product_quantity_tv);
                //productName.setPadding(50, 10, 0, 10);
                productCount.setText(productC);
                productCount.setTag(Integer.toString(id));
                productCount.setOnLongClickListener(new OnLongClickListenerProduct(userId));

                final TextView productCount1 =  productView.findViewById(R.id.plist_weight_text);
                //productName.setPadding(50, 10, 0, 10);
                productCount1.setText("Stock " + productC);
                productCount1.setTag(Integer.toString(id));
                productCount1.setOnLongClickListener(new OnLongClickListenerProduct(userId));

                linearLayoutRecords.addView(productView);
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

                View view = new View(MainActivity.this);
                final Context context = view.getRootView().getContext();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                assert inflater != null;
                final View productView = inflater.inflate(R.layout.product_main, null, false);


                int id = obj.id;
                String productN = obj.name;
                String productP = obj.price.toString();
                final String productC = obj.count.toString();

                final OnLongClickListenerProduct currentListener = new OnLongClickListenerProduct(userId);


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


                linearLayoutRecords.addView(productView);

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
