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

//Register functionality implementation, which not be used temporarily.
package org.tensorflow.dot;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    Button button;
    ImageView profile;
    EditText emailField, usernameField, passwordField,confirmationField;
    Uri imageUri;
    Bitmap bitmap;
    int TAKE_IMAGE_CODE = 10001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        button = (Button) findViewById(R.id.button_register2);
        usernameField = (EditText) findViewById(R.id.editText4);
        passwordField = (EditText) findViewById(R.id.editText2);
        confirmationField = (EditText) findViewById(R.id.editText3);
        emailField = (EditText) findViewById(R.id.editText5);
        profile = (ImageView) findViewById(R.id.imageView);


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
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
        if (requestCode == 10 && resultCode == RESULT_OK && data!=null) {
            try {
                imageUri = data.getData();
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                profile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void signup(){
        final String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        String confirmation = confirmationField.getText().toString();
        final String email = emailField.getText().toString();

        if (!password.equals(confirmation)){
            Toast.makeText(RegisterActivity.this, "The password did not match the confirmed password.", Toast.LENGTH_SHORT).show();
        }else if(password.isEmpty()){
            Toast.makeText(RegisterActivity.this, "The password cannot be empty.", Toast.LENGTH_SHORT).show();
        }else{
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100,baos);
                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        final StorageReference reference = FirebaseStorage.getInstance().getReference()
                                .child("users")
                                .child(uid+".jpeg");
                        reference.putBytes(baos.toByteArray())
                                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                        if(task.isSuccessful()){
                                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                    UserProfileChangeRequest profileUpdates1 = new UserProfileChangeRequest.Builder()
                                                            .setDisplayName(username)
                                                            .build();
                                                    UserProfileChangeRequest profileUpdates2 = new UserProfileChangeRequest.Builder()
                                                            .setPhotoUri(uri)
                                                            .build();
                                                    user.updateProfile(profileUpdates1);
                                                    user.updateProfile(profileUpdates2);
                                                }
                                            });
                                        }
                                    }
                                });
                        Toast.makeText(RegisterActivity.this,"Register Successfully",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setClass(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);



                    }else {
                        Toast.makeText(RegisterActivity.this,"Connect Error",Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
    }

}
