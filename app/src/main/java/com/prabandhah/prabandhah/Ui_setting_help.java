package com.prabandhah.prabandhah;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.net.URI;

public class Ui_setting_help extends AppCompatActivity {
    LinearLayout layout_call_email,layout_sub_email;
    Button btn_email,btn_send,bckbtn,btn_attach;
    EditText edit_subject,edit_email;
    private static final int PICK_FROM_GALLERY = 101;
    int columnIndex;
    String attachmentFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_setting_help);
        btn_email = findViewById(R.id.btn_email);
        btn_send = findViewById(R.id.btn_send);
        edit_subject = findViewById(R.id.edit_subject);
        edit_email = findViewById(R.id.edit_email);
        btn_attach = findViewById(R.id.attach);

        layout_call_email = findViewById(R.id.layout_call_email);
        layout_sub_email = findViewById(R.id.layout_sub_email);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_sub_email.setVisibility(View.GONE);


                String to = "typrojectcpi@gmail.com";
                String subject = edit_subject.getText().toString();
                String compose_email = edit_email.getText().toString();

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.putExtra(Intent.EXTRA_EMAIL, to);
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, edit_subject.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT, edit_email.getText().toString());
                intent.setData(Uri.parse("mailto:typrojectcpi@gmail.com"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Intent.EXTRA_TEXT, compose_email);

                startActivity(Intent.createChooser(intent, "choose an email client :"));
            }
        });


        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_sub_email.setVisibility(View.VISIBLE);



            }
        });

        btn_attach.setOnClickListener(new View.OnClickListener() {
            public Object URI = null;

            @Override
            public void onClick(View v) {
                openFolder();
            }
            protected void onActivityResult(int requestCode, int resultCode, Intent data)
            {
                if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    attachmentFile = cursor.getString(columnIndex);
                    Log.e("Attachment Path:", attachmentFile);
                    URI = Uri.parse("file://" + attachmentFile);
                    cursor.close();
                }
            }


            public void openFolder() {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.putExtra("return-data", true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_GALLERY);
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Ui_setting.class);

        startActivity(intent);
        finish();
    };
}






