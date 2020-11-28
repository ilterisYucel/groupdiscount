package kuneri.ersan.com.groupdiscount;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.kuneri.ersan.com.groupDiscount.MESSAGE";
    Button btnLogin;
    Button btnloginguest;
    Button btnsignup;
    EditText userName;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        userName=(EditText)findViewById(R.id.edtUsername);
        btnloginguest = (Button) findViewById(R.id.btnloginguest);
        password=(EditText)findViewById(R.id.edtPassword);
        btnsignup = (Button) findViewById(R.id.btnsignup);
        btnloginguest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainGuest();
            }
        });
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validate(userName.getText().toString(),password.getText().toString());

            }
        });
    }

    public void validate(String username, String password)
    {
        if(username.equals("admin") && password.equals("admin")){
            startAdminPage();
        }
        else if(new TableControllerUser(this).controlUser(username, password) != -1)
            startMain(username);
        else
            Toast.makeText(this, "User not found.", Toast.LENGTH_LONG).show();
    }

    public void startMain(String username) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_MESSAGE, username);
        startActivity(intent);
        finish();
    }

    public void startAdminPage() {
        Intent intent = new Intent(this, AdminActivity.class);
        //intent.putExtra(EXTRA_MESSAGE, username);
        startActivity(intent);
        finish();
    }

    public void startMainGuest() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_MESSAGE, "Custom User");
        startActivity(intent);
        finish();
    }

    public void signup(){
        View view = new View(LoginActivity.this);
        final Context context = view.getRootView().getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        final View formElementsView = inflater.inflate(R.layout.sign_up, null, false);

        final EditText username =  formElementsView.findViewById(R.id.username);
        final EditText email =  formElementsView.findViewById(R.id.email);
        final EditText password =  formElementsView.findViewById(R.id.password);

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Create User")
                .setPositiveButton("Add",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String uname = username.getText().toString();
                                String uemail = email.getText().toString();
                                String upassword = password.getText().toString();
                                if(new TableControllerUser(context).searchEmail(uemail) != -1 ||
                                        new TableControllerUser(context).searchName(uname) != -1 ){
                                    Toast.makeText(context, "Username or Email in use.", Toast.LENGTH_SHORT).show();

                                    return;
                                }
                                ObjectUser objectUser = new ObjectUser();
                                objectUser.name = uname;
                                objectUser.password = upassword;
                                objectUser.email = uemail;
                                boolean createSuccessful = new TableControllerUser(context).create(objectUser);
                                if(createSuccessful){
                                    Toast.makeText(context, "User information was saved.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Unable to save user information.", Toast.LENGTH_SHORT).show();
                                }

                                dialog.cancel();

                            }

                        }).show();

    }


}


