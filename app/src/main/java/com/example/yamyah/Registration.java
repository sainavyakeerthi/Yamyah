package com.example.yamyah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class Registration extends AppCompatActivity {

    private EditText usname,pwd,email,type,shopid;
    private Button reg;
    private TextView login;
    private FirebaseAuth firebaseAuth;
    DatabaseReference dbreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setUIViews();
        dbreference= FirebaseDatabase.getInstance().getReference("users");
        firebaseAuth=FirebaseAuth.getInstance();
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate())
                {
                    //register to data base
                    final String mail=email.getText().toString().trim();
                    String pass=pwd.getText().toString().trim();
                    final String name=usname.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {

                                String id=dbreference.push().getKey();
                                String id1=firebaseAuth.getUid();
                                String typ=type.getText().toString().trim();
                                String shpid=shopid.getText().toString().trim();
                                User u=new User(id,id1,name,mail,typ,shpid);
                                dbreference.child(id).setValue(u);
                                Toast.makeText(Registration.this,"Registration Successfull",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Registration.this,MainActivity.class));
                            }
                            else
                            {
                                Toast.makeText(Registration.this,"Registration Failed ",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this,MainActivity.class));
            }
        });
    }

    //initialising widgets
    private void setUIViews(){
        usname=(EditText)findViewById(R.id.reguser);
        pwd=(EditText)findViewById(R.id.regpwd);
        email=(EditText)findViewById(R.id.regemail);
        reg=(Button)findViewById(R.id.register);
        type=(EditText)findViewById(R.id.type);
        shopid=(EditText)findViewById(R.id.shopid);
        login=(TextView)findViewById(R.id.alreadysignedin);
    }
    private boolean validate()
    {
        String name=usname.getText().toString();
        String pass=pwd.getText().toString();
        String mail=email.getText().toString();
        boolean result=false;
        if(name.isEmpty() || pass.isEmpty() || mail.isEmpty())
        {
            Toast.makeText(this,"Enter valid details",Toast.LENGTH_SHORT).show();
        }
        else
        {
            result= true;
        }
        return result;
    }
}
