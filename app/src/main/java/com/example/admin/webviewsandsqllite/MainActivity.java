package com.example.admin.webviewsandsqllite;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    //WebView wvPrincipal;
    EditText etName,etNumber,etEmail,etSocial;
    ImageButton ibImage,btnSend;
    Bitmap bitmap;
    String filepath = "";
    String source = "";
    String[] itemName;
    String id = "-1";
    DataBseHelper dataBseHelper =  new DataBseHelper(this);
    public static final int CAMERA_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Contacts");
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        etName = (EditText) findViewById(R.id.etName);
        etNumber = (EditText) findViewById(R.id.etNumber);
        etEmail =(EditText) findViewById(R.id.etEmail);
        etSocial = (EditText) findViewById(R.id.etSocial);
        ibImage = (ImageButton) findViewById(R.id.ibImage);
        btnSend = (ImageButton) findViewById(R.id.btnSend);
//        wvPrincipal = (WebView) findViewById(R.id.wvPrincipal);
//        WebViewClient webViewClient =  new WebViewClient();
//        WebSettings webSettings = wvPrincipal.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        wvPrincipal.setWebViewClient(webViewClient);
//        wvPrincipal.loadUrl("https://developer.android.com/");
        try {
            Intent intent = getIntent();
            id = intent.getStringExtra(getString(R.string.ID));

            ArrayList<Contact> listContact = dataBseHelper.getContacts(id);
            if (listContact.size() > 0) {
                etName.setText(listContact.get(0).getName());
                etEmail.setText(listContact.get(0).getEmail());
                etNumber.setText(listContact.get(0).getNumber());
                etSocial.setText(listContact.get(0).getSocial());
                byte[] b = listContact.get(0).getPhoto();
                bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                ibImage.setImageBitmap(bitmap);
                Log.d(TAG, "onCreate: " + id+ listContact.get(0).getName());
            }

        }catch(Exception ex){}

        ibImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                File file = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(intent, CAMERA_REQUEST);
            }
        });
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(bitmap!=null) {
            outState.putString("img", "uploaded");
            outState.putString("source", source);
            outState.putString("filepath", filepath);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        try {
            if (savedInstanceState.getString("img").equals("uploaded")) {
                filepath = savedInstanceState.getString("filepath");
                File file = new File(filepath);
                source = savedInstanceState.getString("source");
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    //Landscape do some stuff

                    if(!savedInstanceState.getString("source").equals("landscape")){
                        bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 768, 1024);
                        Matrix matrix = new Matrix();
                        matrix.postRotate(90);
                        Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                        ibImage.setImageBitmap(bitmap2);
                        ibImage.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                    else {
                        bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 1024, 768);
                        ibImage.setImageBitmap(bitmap);
                        ibImage.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                } else {
                    //portrait

                    //rotates img
                    if(savedInstanceState.getString("source").equals("portrait")) {
                        bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 768, 1024);
                        Matrix matrix = new Matrix();
                        matrix.postRotate(90);
                        Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                        ibImage.setImageBitmap(bitmap2);
                        ibImage.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                    else {
                        bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 1024 , 768);
                        ibImage.setImageBitmap(bitmap);
                        ibImage.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                }
            }
        }catch(Exception ex){}
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST) {
            //Get our saved file into a bitmap object:
            File file = new File(Environment.getExternalStorageDirectory() + File.separator +
                    "image.jpg");
            filepath = file.getAbsolutePath();
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                //Landscape do some stuff
                source = "landscape";
                bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 768, 1024);
                ibImage.setImageBitmap(bitmap);
                ibImage.setScaleType(ImageView.ScaleType.FIT_XY);
            }
            else{
                bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 1024 , 768);
                source = "portrait";
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                ibImage.setImageBitmap(bitmap2);
                ibImage.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        }
    }
    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight)
    {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;
        if (height > reqHeight)
        {
            inSampleSize = Math.round((float)height / (float)reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth)
        {
            inSampleSize = Math.round((float)width / (float)reqWidth);
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }
    public void saveContact(View view) {
        try {
            byte [] bytes = null;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bytes = bos.toByteArray();
            Contact contact = new Contact("-1",etName.getText().toString(),etNumber.getText().toString(),etEmail.getText().toString(),
                    etSocial.getText().toString(),bytes);
            dataBseHelper.saveNewContact(contact);
            Toast.makeText(this, "Contact Saved .....", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ListContacts.class);
            startActivity(intent);
        }catch (Exception  e){
            Toast.makeText(this, "Error while saving..." + e, Toast.LENGTH_SHORT).show();}
    }

}
