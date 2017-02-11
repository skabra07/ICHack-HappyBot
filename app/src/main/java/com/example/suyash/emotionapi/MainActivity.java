package com.example.suyash.emotionapi;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.HttpClients;
import cz.msebera.android.httpclient.client.utils.URIBuilder;
import cz.msebera.android.httpclient.util.EntityUtils;


import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private static final int RESULT_LOAD_IMAGE  = 1;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHandler = new DBHandler(this, null, null, 1);

        final ImageView imgPreview = (ImageView) findViewById(R.id.imageView3);
        final Button button = (Button) findViewById(R.id.button);


        ImageButton photoButton = (ImageButton) this.findViewById(R.id.camera);
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();//
            }
        });

        ImageButton photoButton2 = (ImageButton) this.findViewById(R.id.gallery);
        photoButton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        button.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        TextView textView  =(TextView) findViewById(R.id.textView);
                        String emotion;
                        emotion = getEmotion(imgPreview);

                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        String formattedDate = df.format(c.getTime());

                        Emotion emotions = new Emotion(emotion,formattedDate);
                        dbHandler.addEmotion(emotions);
                        textView.setText(emotion);
                        //button.setText("Go");
                        Toast message =  Toast.makeText(getApplicationContext(),"Your emotion is: " + emotion,Toast.LENGTH_LONG);
                        message.show();
                        Intent i = new Intent(getApplicationContext(),SecondPage.class);
                        // sending information to the other activity
                        String getrec=textView.getText().toString();
                        Bundle bundle = new Bundle();
                        bundle.putString("emotion", getrec);
                        i.putExtras(bundle);
                        startActivity(i);
                    }
                }
        );
    }

    public void previousResults(View view) {
        Intent i = new Intent(this,resultsPage.class);
        startActivity(i);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            ImageView imageView = (ImageView) findViewById(R.id.imageView3);
            Bitmap photo = (Bitmap) BitmapFactory.decodeFile(picturePath);
            imageView.setImageBitmap(photo);
        }
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ImageView imgPreview = (ImageView) findViewById(R.id.imageView3);
            imgPreview.setImageBitmap(photo);
        }
    }


    public String getEmotion(ImageView img) {
        HttpClient httpclient = HttpClients.createDefault();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            URIBuilder builder = new URIBuilder("https://westus.api.cognitive.microsoft.com/emotion/v1.0/recognize");

            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/octet-stream");
            request.setHeader("Ocp-Apim-Subscription-Key", "406a73deaec7416a9f40e3a933e285c8");

            // Request body
            request.setEntity(new ByteArrayEntity(toBase64(img)));

            // getting a response and assigning it to the string res
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();
            String res = EntityUtils.toString(entity);

            if (entity == null) {
                return "null";
            }

            // creating two array lists which saves the emotions and their respective scores
            ArrayList<String> allEmotions = new ArrayList<String>();
            int numberOfPeople = StringUtils.countMatches(res, "scores");
            String words;

            if (numberOfPeople > 1) {
                for (int i = 0; i < numberOfPeople - 1; i++) {
                    int start = res.indexOf("scores");
                    int stop = res.indexOf("{\"faceRectangle", start);
                    words = res.substring(start + 9, stop);
                    res = res.substring(stop, res.length());

                    words = words.replaceAll("\"", "");
                    words = words.replaceAll(":", ",");
                    words = words.replace('}', ',');
                     words = words.replaceAll(",,,", "");
                    String[] items = words.split(",");

                    ArrayList<String> emotions = new ArrayList<String>();
                    ArrayList<Double> score = new ArrayList<Double>();
                    int j = 0;
                    for (String word : items) {
                        if (j % 2 == 0) emotions.add(word);
                        else score.add(Double.parseDouble(word));
                        j++;
                    }
                    // finding the maximum score and the emotion respective to the score
                    double max = 0.0;
                    for (double x : score) if (x > max) max = x;
                    allEmotions.add(emotions.get(score.indexOf(max)));
                }
            }
            else {
                int start = res.lastIndexOf('{');
                words = res.substring(start + 1, res.length() - 3);
                words = words.replaceAll("\"", "");
                words = words.replaceAll(":", ",");

                String[] items = words.split(",");

                ArrayList<String> emotions = new ArrayList<String>();
                ArrayList<Double> score = new ArrayList<Double>();
                int j = 0;
                for (String word : items) {
                    if (j % 2 == 0) emotions.add(word);
                    else score.add(Double.parseDouble(word));
                    j++;
                }
                // finding the maximum score and the emotion respective to the score
                double max = 0.0;
                for (double x : score) if (x > max) max = x;
                return emotions.get(score.indexOf(max));
            }

            ArrayList<Integer> frequency = new ArrayList<Integer>();
            ArrayList<String> frequencyWord = new ArrayList<String>();
            for (String y : allEmotions) {
                if (frequencyWord.contains(y)) {
                    int x = frequencyWord.indexOf(y);
                    int z = frequency.get(x);
                    z = z + 1;
                    frequency.add(x, z);
                } else {
                    frequencyWord.add(y);
                    frequency.add(1);
                }
            }
            int max = 0;
            int k = 0;
            for (int i = 0; i < frequency.size(); i++) {
                if (frequency.get(i) > max) {
                    max = frequency.get(i);
                    k = i;
                }
            }
            return frequencyWord.get(k);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "null";
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//the intention of taking the image
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {//+
            startActivityForResult(takePictureIntent, CAMERA_REQUEST);//+

        }
    }
    public byte[] toBase64(ImageView imgPreview /*String pathToFile*/) {

        //Bitmap bm = BitmapFactory.decodeFile(pathToFile);
        Bitmap bm = ((BitmapDrawable) imgPreview.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        return b;
    }


}
