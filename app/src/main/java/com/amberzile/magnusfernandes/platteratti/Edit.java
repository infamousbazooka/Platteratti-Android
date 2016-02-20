package com.amberzile.magnusfernandes.platteratti;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class Edit extends AppCompatActivity {
    Spinner itemTypeInput, itemInput;
    public static EditText descriptionEditText, priceEditText;
    public static ImageView imageInput;
    String itemTypeText, itemText, descriptionText, priceText;
    BackgroundTask backgroundTask;
    public static byte[] byteArray;
    Button imageButton;
    private static final int SELECTED_PICTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        itemTypeInput = (Spinner)findViewById(R.id.itemTypeSpinner);
        String[] foods = {"BREAKFAST", "APPETIZERS", "SNACKS", "LUNCH", "DINNER", "DESSERTS", "BEVERAGES"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, foods);
        itemTypeInput.setAdapter(adapter);
        itemTypeInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemTypeText = itemTypeInput.getSelectedItem().toString();
                getItem(itemTypeText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        itemInput = (Spinner)findViewById(R.id.itemSpinner);
        itemInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemTypeText = itemTypeInput.getSelectedItem().toString();
                itemText = itemInput.getSelectedItem().toString();
                getDetails(itemTypeText, itemText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        descriptionEditText = (EditText)findViewById(R.id.displayDescription);
        priceEditText = (EditText)findViewById(R.id.displayPrice);
        imageInput = (ImageView)findViewById(R.id.displayImage);
        imageButton = (Button)findViewById(R.id.displayImageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, SELECTED_PICTURE);
            }
        });

    }
    public void getDetails(String type, String item){
        backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("getDetails", type, item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==SELECTED_PICTURE && resultCode==RESULT_OK && data!=null){
            Uri uri = data.getData();
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(projection[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
            imageInput.setImageBitmap(yourSelectedImage);
        }
    }
    public void getItem(String item){
        backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("selectItem", item);
    }
    public void goToSettingsClicked(View view){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
    public void deleteItemClicked(View view){
        itemTypeText = itemTypeInput.getSelectedItem().toString();
        itemText = itemInput.getSelectedItem().toString();
        backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("getId", itemTypeText, itemText);
        Toast.makeText(this, "ITEM " + itemText + " WAS DELETED SUCCESSFULLY!", Toast.LENGTH_LONG).show();
        getItem(itemTypeText);
    }
    public void updateItemClicked(View view){
        itemTypeText = itemTypeInput.getSelectedItem().toString();
        itemText = itemInput.getSelectedItem().toString();
        descriptionText = descriptionEditText.getText().toString();
        priceText = priceEditText.getText().toString();
        imageInput.setDrawingCacheEnabled(true);
        imageInput.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable)imageInput.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byteArray = stream.toByteArray();
        backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("update",itemTypeText,itemText,descriptionText,priceText);
    }
}