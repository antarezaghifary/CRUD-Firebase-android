package id.azer.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private  String UserID;
    EditText user, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = findViewById(R.id.username);
        email = findViewById(R.id.email);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("DataUser");

        UserID = databaseReference.push().getKey();

    }

    public void addUser(String username, String email){
        User user = new User(username,email);
        databaseReference.child("Users").child(UserID).setValue(user);
    }

    public void updateUser(String username, String email){
        databaseReference.child("Users").child(UserID).child("username").setValue(username);
        databaseReference.child("Users").child(UserID).child("email").setValue(email);
    }

    public void insertData(View view){
        if(user.getText().toString().equalsIgnoreCase("") || email.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this, "Input",
                    Toast.LENGTH_LONG).show();
        }else{
            addUser(user.getText().toString().trim(), email.getText().toString().trim());
        }
    }
    public void updateData(View view) {
        if (user.getText().toString().equalsIgnoreCase("") || email.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Input",
                    Toast.LENGTH_LONG).show();
        } else {
            updateUser(user.getText().toString().trim(), email.getText().toString().trim());
        }
    }

    public void readData(View view){
        databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshots : dataSnapshot.getChildren() ){
                    for (DataSnapshot ds:dataSnapshot.getChildren()){
                        String dbUser = ds.child("username").getValue(String.class);
                        String dbEmail = ds.child("email").getValue(String.class);
                        Log.d("TAG","Konfirm : "+dbUser +" "+ dbEmail);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}