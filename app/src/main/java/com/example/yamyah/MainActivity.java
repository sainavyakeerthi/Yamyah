package com.example.yamyah;




import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText mail,pwd;
    private Button login;
    private TextView userRegistration,info;
    FirebaseAuth firebaseAuth;
    private int counter=5;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialization();
        info.setText("Number of attempts remaining: 5");
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        progressDialog=new ProgressDialog(this);
        if(user!=null)
        {
            finish();
            startActivity(new Intent(MainActivity.this,Screen.class));
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validate();

            }
        });

        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Registration.class));
            }
        });

    }
    private void initialization()
    {
        mail=(EditText)findViewById(R.id.lemail);
        pwd=(EditText)findViewById(R.id.lpassword);
        login=(Button)findViewById(R.id.llogin);
        userRegistration=(TextView)findViewById(R.id.lregister);
        info=(TextView)findViewById(R.id.no_of_attempts);

    }
    private void validate()
    {
        //boolean res=false;
        final String log,pwd1;
        log=mail.getText().toString();
        pwd1=pwd.getText().toString();

        progressDialog.setMessage("Loading..");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(log,pwd1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this,"Login success",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,Screen.class));
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                    counter--;
                    info.setText("Number of attempts remaining: "+counter);
                    if(counter==0)
                    {
                        login.setEnabled(false);
                    }
                }
            }
        });

    }
}
