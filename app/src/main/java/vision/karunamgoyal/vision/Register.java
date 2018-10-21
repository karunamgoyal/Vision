package vision.karunamgoyal.vision;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {
    Button b3;
    TextView name;
    TextView username;
    TextView password;
    TextView mail;
    TextView phno;
    TextView rollno;
    Spinner userType;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        b3=(Button)findViewById(R.id.button4);
        b3.setOnClickListener(new OnClickListener(){
            public void onClick(final View view){

                try {
                    name = (TextView) findViewById(R.id.name);
                    userType=findViewById(R.id.spinner);
                    username = (TextView) findViewById(R.id.username);
                    password = (TextView) findViewById(R.id.password);
                    mail = (TextView) findViewById(R.id.email);
                    phno = (TextView) findViewById(R.id.phnumber);
                    final String name1 = name.getText().toString();
                    final String u = username.getText().toString();
                    final String passTemp = password.getText().toString();
                    final String mail1 = mail.getText().toString();
                    final String phno1 = phno.getText().toString();
                    final String userTypeValue=userType.getSelectedItem().toString();
                    Trippledes Des=new Trippledes();
                    final String pass=Des.encrypt(passTemp);
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("adminusers").child(u);
                    final DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("registeradminuser");
                    RegisterAdminUser ruser = new RegisterAdminUser(name1, mail1, phno1,userTypeValue);

                    reference.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Snackbar.make(view, "Username Already Exists Choose Other username", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                            } else {
                                RegisterAdminUser ruser = new RegisterAdminUser(name1, mail1, phno1,userTypeValue);
                                User user = new User(pass);
                                reference.setValue(user);
                                reference1.child(u).setValue(ruser);
                                Snackbar.make(view, "Succesfully Registered press back", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                sendmain();
                                finish();
                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value

                        }
                    });
                }
                catch (Exception e){
                    Snackbar.make(view, "Please Fill these values properly or there maybe connection error", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                sendmain();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void sendmain(){
        Intent in=new Intent(this,MainActivity.class);

        startActivity(in);
    }

}
