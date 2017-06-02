package com.github.abhijitpparate.android_tts;

import android.hardware.Sensor;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private AppCompatEditText editText;
    private Button speakButton;

    private TextToSpeech tts;
    private boolean ttsEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (AppCompatEditText) findViewById(R.id.edtTextToSpeak);
        speakButton = (Button) findViewById(R.id.btnSpeak);

        tts = new TextToSpeech(getApplicationContext(), this);

        tts.setSpeechRate(1.5f);

        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString();
                speak(message);
            }
        });

    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS){
            int result = tts.setLanguage(Locale.getDefault());
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(this, "This language is not supported", Toast.LENGTH_SHORT).show();
            } else {
                ttsEnabled = true;
            }
        } else {
            Toast.makeText(this, "TTS initialization failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void speak(String message){
        if (ttsEnabled){
            tts.speak(message, TextToSpeech.QUEUE_FLUSH, null, "speech");
        } else {
            Toast.makeText(this, "TTS initialization failed", Toast.LENGTH_SHORT).show();
        }
    }
}
