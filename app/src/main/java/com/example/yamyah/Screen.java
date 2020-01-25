package com.example.yamyah;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Screen extends AppCompatActivity {

    private TextView name,type;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbref;
    FirebaseAuth firebaseAuth;
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        name=(TextView)findViewById(R.id.luser1);
        type=(TextView)findViewById(R.id.ltype);
        logout=(Button)findViewById(R.id.logout);
        firebaseAuth= FirebaseAuth.getInstance();
        dbref= FirebaseDatabase.getInstance().getReference("users");
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Screen.this,MainActivity.class));
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        dbref.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                String name= Objects.requireNonNull(user).getDisplayName();
                for(DataSnapshot userSnapshot : dataSnapshot.getChildren())
                {
                    User usr=userSnapshot.getValue(User.class);
                    if(!usr.getId1().equals(firebaseAuth.getUid()))
                    {

                        type.setText(usr.type);
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
