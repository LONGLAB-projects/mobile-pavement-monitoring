/*The author of this file: Guan Jing*/
//Contact functionality implementation, user can type any text information and send it back to us.
package org.tensorflow.dot;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Contact extends AppCompatActivity {

    private EditText et1;
    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);
        button1 = findViewById(R.id.button2);
        et1 = findViewById(R.id.editText);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!et1.getText().toString().equals("")){
                    final DatabaseReference fbRef = FirebaseDatabase.getInstance().getReference("feedback");
                    Date date=new Date();
                    SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
                    sfd.format(new Date());
                    fbRef.child(date.toString()).setValue(et1.getText().toString());

                    Toast.makeText(Contact.this,"Thanks! We have received.",Toast.LENGTH_SHORT).show();
                    et1.setText("");
                }else{
                    Toast.makeText(Contact.this,"Please write down your advice or question.",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

}
