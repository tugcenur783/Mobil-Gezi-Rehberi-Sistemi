package com.example.sayefreyadh.androidmapsnearby;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class Giris extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEECH_İNPUT = 1000;
    private TextToSpeech mTTS;

    private TextView text1;
    private Button mbutton;
    ImageButton imageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        text1 = findViewById(R.id.text1);

        text1.setText("otobüs durağı" );
        mbutton   = findViewById(R.id.mbutton);
        imageButton = findViewById(R.id.imageButton);

        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak2();
            }
        });


        //speech to text
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak1();
            }
        });

        //text to speech
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){

                    int result = mTTS.setLanguage(new Locale("tr","TR"));

                    if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported "); //dil desteklenmiyor
                    } else {
                        mbutton.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "İnitalization failed ");  //başlatma başarısız
                }
            }
        });

        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(new String(text1.getText().toString()).equals("")){
                    speak3();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    speak1();
                }
                else{
                    if(new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("hastane")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("cafe")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("müze")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("resimgalerisi")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("kilise")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("cami")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("banka")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("okul")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("hayvanatbahçesi")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("üniversite")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("süpermarket")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("mağaza")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("taksidurağı")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("postane")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("restoran")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("kütüphane")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("havalimanı")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("fırın")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("bar")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("güzelliksalonu")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("kitapçı")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("bowling")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("otobüsdurağı")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("kampalanı")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("mezarlık")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("belediye")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("giyimmağazası")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("adliye")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("dişhekimi")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("eczane")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("itfaiye")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("çiçekçi")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("kuaför")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("kuyumcu")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("tekel")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("gecekulübü")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("otopark")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("petshop")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("fizyoterapist")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("tesisatçı")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("ilkokul")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("emlakçı")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("ayakkabımağazası")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("alışverişmerkezi")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("spa")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("stadyum")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("metroistasyonu")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("trenistasyonu")||new String(text1.getText().toString().replace(" ","")).equalsIgnoreCase("veteriner"))
                    {

                        String txt=text1.getText().toString();
                        Intent intent = new Intent(Giris.this, MapsActivity.class);
                        intent.putExtra("arama",txt);
                        startActivity(intent);

                    }
                    else
                        {
                            mTTS.speak("Tanımlanamadı",TextToSpeech.QUEUE_FLUSH,null);
                        }

                }
            }
        });
    }
    //speech to text
    private void speak1() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi speak something");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_İNPUT);
        }
        catch (Exception E) {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_SPEECH_İNPUT: {
                if(resultCode == RESULT_OK && null!=data){
                    ArrayList<String> result1=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    text1.setText((result1.get(0)));
                }
                break;
            }
        }
    }

    //text to speech
    private void speak2(){
        String text = text1.getText().toString();
        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void speak3(){
        mTTS.speak("Lütfen bir şey söyleyiniz", TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {
        if(mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();

        }
        super.onDestroy();
    }
}
