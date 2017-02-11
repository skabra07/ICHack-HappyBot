package com.example.suyash.emotionapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class resultsPage extends AppCompatActivity {

    DBHandler dbHandler;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_page);
        text = (TextView) findViewById(R.id.results);
        dbHandler = new DBHandler(this, null, null, 1);
        printDatabase();
    }

    public void printDatabase(){
        String dbString = dbHandler.databaseToString();
        text.setText(dbString);
    }

    public void backHome(View view){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

}
