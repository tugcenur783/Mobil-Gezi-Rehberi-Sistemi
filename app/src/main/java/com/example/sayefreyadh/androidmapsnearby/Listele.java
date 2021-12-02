package com.example.sayefreyadh.androidmapsnearby;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class Listele extends AppCompatActivity {

    private TextToSpeech mTTS;
    ListView liste;
    Context context = this;
    Dialog mydialog;
    int sayac = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listele);

        liste = findViewById(R.id.liste);
        mydialog = new Dialog(this);

        Intent intent = getIntent();
        String url = intent.getStringExtra("konumUrl");

       final MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
        try {
            String[] arrayOfString = myAsyncTasks.execute(url).get().split("£");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_list_item_1,android.R.id.text1,arrayOfString);
            liste.setAdapter(adapter);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(sayac==0){
                    if(id>=0)
                    {
                        String item = (String) liste.getAdapter().getItem(position);
                        mTTS.speak(item, TextToSpeech.QUEUE_FLUSH,null);
                        sayac = 1;
                    }
                }
                else{
                    TextView txtclose;
                    Button konuma_git;
                    Button konum_bilgisi;
                    mydialog.setContentView(R.layout.custompopup);
                    txtclose = (TextView) mydialog.findViewById(R.id.text_close);
                    konuma_git = (Button) mydialog.findViewById(R.id.konuma_git);
                    konum_bilgisi = (Button) mydialog.findViewById(R.id.konum_bilgisi);

                    if(id>=0)
                    {
                        String item = (String) liste.getAdapter().getItem(position);
                        if(item.equals("Atatürk Havalimanı")){
                            konum_bilgisi.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mTTS.speak("Atatürk Havalimanı, eski adıyla Yeşilköy Havaalanı, 1912 yılında Türkiye'de ilk defa sivil hava ulaşımının başlatıldığı yerdir. 1953 yılında da uluslar arası hava trafiğine açılmıştır. 1985 yılında adı, modern Türkiye'nin kurucusu Atatürk'ün adı verilerek, Atatürk Havalimanı olarak değiştirilmiştir. Atatürk Havalimanı, İstanbul şehir merkezine 24 km uzaklıkta, Batı istikametinde kurulmuş olup, meydana ulaşım; otobüs, taksi, metro ile sağlanmaktadır. Atatürk Havalimanı, Uluslararası Havacılık Teşkilatının yaptığı sınıflandırmaya göre CAT II niteliklerine sahip olup, meteorolojik koşulların kötü olduğu zamanlarda bileuçak iniş-kalkışına imkan verebilecek düzeydedir.",TextToSpeech.QUEUE_FLUSH,null);
                                }
                            });
                        }

                    }
                    txtclose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mydialog.dismiss();
                        }
                    });
                    mydialog.show();
                    sayac = 0;
                }
            }
        });
        //text to speech
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){

                    int result = mTTS.setLanguage(new Locale("tr","TR"));

                    if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported ");
                    } else {
                        liste.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "İnitalization failed ");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if(mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }

    public class MyAsyncTasks extends AsyncTask<String, String, String> {
        String konumlar = "";
        @Override
        protected String doInBackground(String... param1VarArgs) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(((HttpURLConnection)(new URL(param1VarArgs[0])).openConnection()).getInputStream()));
                for (JsonElement jsonElement : (new JsonParser()).parse(bufferedReader).getAsJsonObject().getAsJsonArray("results"))
                    this.konumlar += jsonElement.getAsJsonObject().get("name").getAsString() + "£";
                return this.konumlar;
            } catch (Exception exception) {
                return  "Lütfen bir yer belirleyip tekrar deneyiniz!";

            }
        }
    }

}

