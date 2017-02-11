package com.example.suyash.emotionapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class SecondPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);
        Button button = (Button) findViewById(R.id.button2);
        TextView textView2 =(TextView) findViewById(R.id.textView2);
        TextView textView3 =(TextView) findViewById(R.id.textView3);
        WebView browser = (WebView) findViewById(R.id.webview);
        Bundle bundle = getIntent().getExtras();
        String emotion = bundle.getString("emotion");


        button.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        Intent i = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(i);
                    }
                }
        );

        String[] advice = getAdvice(emotion);
        textView2.setText(advice[0]);
        textView3.setText(advice[1]);
        if(advice[2].equals("x")) browser.setVisibility(View.INVISIBLE);
        else browser.loadUrl(advice[2]);

    }

    public String[] getAdvice(String emotion) {
        // The aim is to return a message which makes users happier

        // return string array
        // element index 0: emotion
        // element index 1: message
        // element index 2: action (URL)

        String[] output = new String[3];
        int random = Math.abs(new Integer(new Random().nextInt(100)));

        switch (emotion) {
            case "anger":
                output[0] = "Angry";
                switch (random % 3) {
                    case 0:
                        // listen to soothing music
                        output[1] = "How about listen to soothing music?";
                        output[2] = videoURL("calm soothing music");
                        return output;
                    case 1:
                        // watch a movie
                        output[1] = "Go watch a movie\nI would recommend a comedy one. Have you watched Airplane (1980)?";
                        output[2] = "x";
                        return output;

                    case 2:
                        // calming message
                        output[1] = "You’ll never mean the things you say when you’re angry. So try to be calm. You don’t want to end up saying things that’d only serve to produce guilt and regret.";
                        output[2] = "x";
                        return output;
                }
                break;
            case "contempt":
                output[0] = "Contempt";
                switch (random % 2) {
                    case 0:
                        // inspirational quotes
                        output[1] = "Why not take a look at this?";
                        output[2] = websiteURL("inspirational quotes");
                        return output;
                    case 1:
                        // fun jokes
                        output[1] = "Why not read this?";
                        output[2] = websiteURL("fun joke");
                        return output;

                }
                break;
            case "disgust":
                output[0] = "Disgusted";
                switch (random % 1) {
                    case 0:
                        // counsel the user
                        // TODO
                        output[1] = " ";
                        output[2] = "x";
                        return output;
                }
                break;
            case "fear":
                output[0] = "Scared";
                switch (random % 2) {
                    case 0:
                        // relaxing music
                        output[1] = "How about listen to some relaxing music?";
                        output[2] = videoURL("calm relaxing music");
                        return output;
                    case 1:
                        // call your friends
                        // TODO
                        output[1] = "Call a friend. Atleast you can both be scared together";
                        output[2] = "x";
                        return output;
                }
                break;
            case "happiness":
                output[0] = "Happy!!!";
                switch (random % 5) {
                    case 0:
                        // funny videos
                        output[1] = "Want to laugh?";
                        output[2] = videoURL("laugh");
                        return output;
                    case 1:
                        // tell a happy joke
                        output[1] = "Why not read this?";
                        output[2] = websiteURL("happy joke");
                        return output;

                    case 2:
                        // happy memories
                        // TODO
                        output[1] = "A special suprise...";
                        output[2] = "http://trumpdonald.org/";
                        return output;

                    case 3:
                        // plan a meet up with friends
                        // TODO
                        output[1] = "Meet up with a friend. Spread that happiness";
                        output[2] = "x";
                        return output;

                    case 4:
                        output[1] = "Always stay happy!!! It suits you";
                        output[2] = "https://s-media-cache-ak0.pinimg.com/originals/a9/cd/49/a9cd493d65db3d072bfa0c311035fe33.jpg";
                        return output;
                }
                break;
            case "neutral":
                output[0] = "Bored?";
                switch (random % 2) {
                    case 0:
                        output[1] = "How about watch this video?";
                        output[2] = videoURL("fun facts");
                        return output;
                    case 1:
                        // watch a movie
                        // TODO
                        output[1] = "Watch a movie.\nI would recommend a thriller. Have you watched 'The Usual Suspects (1995)'";
                        output[2] = "x";
                        return output;
                }
                break;
            case "sadness":
                output[0] = "Sad";
                switch (random % 4) {
                    case 0:
                        // funny animal videos
                        output[1] = "I hope this will cheer you up...";
                        output[2] = videoURL("funny animals");
                        return output;
                    case 1:
                        // funny jokes
                        output[1] = "Why not read this?";
                        output[2] = websiteURL("funny joke");
                        return output;
                    case 2:
                        // dog/cat pictures
                        // TODO
                        output[1] = "These animals are sure to bring a smile to your face";
                        output[2] = "http://www.animal-photos.org/shuffle/";
                        return output;

                    case 3:
                        // try not to laugh videos
                        output[1] = "I hope this will cheer you up...";
                        output[2] = videoURL("try not to laugh");
                        return output;

                }
                break;
            case "surprise":
                output[0] = "Surprised!";
                switch (random % 1) {
                    case 0:
                        // amazing videos
                        output[1] = "Want to feel more surprised?";
                        output[2] = videoURL("amazing videos");
                        return output;
                }
                break;
            default:
                output[0] = "No emotion";
                output[1] = "You have no emotions like me\nSmile maybe...";
                output[2] = "https://www.grumpycats.com/images/about/tardar.jpg";
                return output;
        }
        return null;
    }

    private String websiteURL(String searchTerm) {
        ArrayList<String> websites = new ArrayList<String>();
        int random;
        switch (searchTerm) {
            case "inspirational quotes":
                websites.add("https://www.brainyquote.com/quotes/topics/topic_inspirational.html");
                websites.add("https://www.brainyquote.com/quotes/topics/topic_motivational.html");
                websites.add("http://www.values.com/inspirational-quotes");
                websites.add("https://uk.pinterest.com/explore/inspirational-quotes/");
                websites.add("http://www.keepinspiring.me/positive-inspirational-life-quotes/");
                websites.add("http://www.briantracy.com/blog/personal-success/26-motivational-quotes-for-success/");
                break;
            case "fun joke":
                websites.add("http://jokes.cc.com/joke-categories");
                websites.add("http://www.rd.com/jokes/");
                websites.add("http://www.short-funny.com/");
                websites.add("http://www.laughfactory.com/jokes");
                websites.add("http://jokes4us.com/miscellaneousjokes/schooljokes/kidjokes.html");
                websites.add("https://www.facebook.com/notes/joke-of-the-day/top-50-funniest-jokes-ever-told/589651657715055/");
                break;
            case "happy joke":
                websites.add("http://www.jokes4us.com/classicadult/makingpeoplehappyjoke.html");
                websites.add("http://jokes.cc.com/funny-school/9q5mlf/happy-butt");
                websites.add("http://academictips.org/funny-jokes/");
                websites.add("http://www.telegraph.co.uk/culture/comedy/9074244/Joke-of-the-year-dwarf-gag-is-Telegraph-readers-favourite-one-liner.html");
                websites.add("http://www.funny-jokes-quotes.com/");
                websites.add("http://www.laughfactory.com/jokes/relationship-jokes");
                break;
            default:
                return null;

        }
        random = new Integer(new Random().nextInt(websites.size()));
        return websites.get(random);
    }

    private String videoURL(String searchTerm) {
        ArrayList<String> websites = new ArrayList<String>();
        int random;
        switch (searchTerm) {
            case "calm soothing music":
                websites.add("https://www.youtube.com/watch?v=QNwHKYtWTU4");
                websites.add("https://www.youtube.com/watch?v=CcsUYu0PVxY");
                websites.add("https://www.youtube.com/watch?v=KevbhpCWXSQ");
                websites.add("https://www.youtube.com/watch?v=FaRrq7cYu84");
                websites.add("https://www.youtube.com/watch?v=4D8ezH0iXh8");
                websites.add("https://www.youtube.com/watch?v=zMWHc2LzGPY");
                break;
            case "calm relaxing music":
                websites.add("https://www.youtube.com/watch?v=CcsUYu0PVxY");
                websites.add("https://www.youtube.com/watch?v=hvOHISBckw8");
                websites.add("https://www.youtube.com/watch?v=9Q634rbsypE");
                websites.add("https://www.youtube.com/watch?v=e4Eod9ljkyA");
                websites.add("https://www.youtube.com/watch?v=rtwjIOQWiJs");
                websites.add("https://www.youtube.com/watch?v=zHjGGrmrfdo");
                break;
            case "laugh":
                websites.add("https://www.youtube.com/watch?v=GeyTu18sm3c");
                websites.add("https://www.youtube.com/watch?v=10kZBrqfo1w");
                websites.add("https://www.youtube.com/watch?v=vQHHL1Rbdbw");
                websites.add("https://www.youtube.com/watch?v=TS3dxL0E1kc");
                websites.add("https://www.youtube.com/watch?v=IBuaBOktNio");
                websites.add("https://www.youtube.com/watch?v=olLhpWkhKCM");
                break;
            case "fun facts":
                websites.add("https://www.youtube.com/watch?v=Tmtyi_BjnqM");
                websites.add("https://www.youtube.com/watch?v=VipvYabCdSQ");
                websites.add("https://www.youtube.com/watch?v=tnc2fG3sQqE");
                websites.add("https://www.youtube.com/watch?v=2Z90zB5-TWc");
                websites.add("https://www.youtube.com/watch?v=6W_lA8JmVLg");
                websites.add("https://www.youtube.com/watch?v=_ojOTtw_AUE");
                break;
            case "funny animals":
                websites.add("https://www.youtube.com/watch?v=vEO4WavlXdA");
                websites.add("https://www.youtube.com/watch?v=VtmffQXma2o");
                websites.add("https://www.youtube.com/watch?v=nXntiXb1fmg");
                websites.add("https://www.youtube.com/watch?v=0jBg49dckXU");
                websites.add("https://www.youtube.com/watch?v=Mq0yEI_xpb8");
                websites.add("https://www.youtube.com/watch?v=IT3Xl3Gezow");
                break;
            case "try not to laugh":
                websites.add("https://www.youtube.com/watch?v=hL8alp7jANk");
                websites.add("https://www.youtube.com/watch?v=4TnQ99ESoKM");
                websites.add("https://www.youtube.com/watch?v=lZvxC1Cw09c");
                websites.add("https://www.youtube.com/watch?v=JiYglThWF7w");
                websites.add("https://www.youtube.com/watch?v=2N0yWIzaVEM");
                websites.add("https://www.youtube.com/watch?v=GeyTu18sm3c");
                break;
            case "amazing videos":
                websites.add("https://www.youtube.com/watch?v=FS72k1Qiobg");
                websites.add("https://www.youtube.com/watch?v=l6CdGUDbOGA");
                websites.add("https://www.youtube.com/watch?v=b7x79OygxX8");
                websites.add("https://www.youtube.com/watch?v=QJxwE7mdGns");
                websites.add("https://www.youtube.com/watch?v=tXUVOjCz83A");
                websites.add("https://www.youtube.com/watch?v=Orrr7PyaZs4");
                break;
            default:
                return null;
        }
        random = new Integer(new Random().nextInt(websites.size()));
        return websites.get(random);
    }


}