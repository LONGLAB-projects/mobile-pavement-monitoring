/*
 * Copyright 2020 Guan Jing. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//Profile Functionality implementation, which not be used temporarily.
package org.tensorflow.dot;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Profile extends AppCompatActivity {

    private TextView tv1;
    private EditText et1,et2;
    private ImageView iv1;
    private Button button1;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personinfo);
        tv1 = findViewById(R.id.TextView5);
        et1 = findViewById(R.id.editText4);
        et2 = findViewById(R.id.editText2);
        iv1 = findViewById(R.id.imageView);
        button1 = findViewById(R.id.button_update);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            tv1.setText(user.getEmail());
            et1.setText(user.getDisplayName());
            if(user.getPhotoUrl()!=null){
                Glide.with(this)
                        .load(user.getPhotoUrl())
                        .into(iv1);
            }
        }

        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = et2.getText().toString();
                if(!pwd.equals("")){
                    UserProfileChangeRequest updateusername = new UserProfileChangeRequest.Builder()
                            .setDisplayName(et1.getText().toString())
                            .build();
                    user.updatePassword(pwd);
                    UserProfileChangeRequest updateimg = new UserProfileChangeRequest.Builder()
                            .setPhotoUri(imageUri)
                            .build();
                    user.updateProfile(updateusername);
                    user.updateProfile(updateimg);
                    Intent intent = new Intent();
                    intent.setClass(Profile.this, LoginActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(Profile.this, "Password cannot be null.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void upload(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK) {
            iv1.setImageURI(data.getData());
            imageUri = data.getData();
        }
    }

}