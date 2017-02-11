package com.example.suyash.emotionapi;

import java.security.Timestamp;
import java.util.Date;

/**
 * Created by Suyash on 09-Feb-17.
 */

public class Emotion {


    private int _id;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    private String _emotion;
    private String _timestamp;

    public Emotion(){
    }

    public Emotion(String emotion, String timestamp){
        _emotion = emotion;
        _timestamp=timestamp;
    }

    public String get_emotion() {
        return _emotion;
    }

    public void set_emotion(String _emotion) {
        this._emotion = _emotion;
    }

    public String get_timestamp() {
        return _timestamp;
    }

    public void set_timestamp(String _timestamp) {
        this._timestamp = _timestamp;
    }
}
