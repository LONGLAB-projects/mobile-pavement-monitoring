/* The authors are Guan Jing & Ran Ran */
package org.tensorflow.dot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

//create the buttons in the Main Page
public class MainPage extends AppCompatActivity {

    private Button button1, button2, button3, button4, button5, button6;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);
        button1 = (Button) findViewById(R.id.carBtn);
//        button2 = (Button) findViewById(R.id.personBtn);
// the person function we will not use temporarily.
        button3 = (Button) findViewById(R.id.contactBtn);
        button4 = (Button) findViewById(R.id.privacyBtn);
        button5 = (Button) findViewById(R.id.purposeBtn);
        button6 = (Button) findViewById(R.id.viewImagesBtn);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainPage.this, DetectorActivity.class);
                startActivity(intent);
            }
        });

//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(MainPage.this, Profile.class);
//                startActivity(intent);
//            }
//        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainPage.this, Contact.class);
                startActivity(intent);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainPage.this, Privacy.class);
                startActivity(intent);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainPage.this, Purpose.class);
                startActivity(intent);
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainPage.this, viewImages.class);
                startActivity(intent);
            }
        });
    }

}
