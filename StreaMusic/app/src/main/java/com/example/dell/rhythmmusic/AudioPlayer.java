package com.example.dell.rhythmmusic;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AudioPlayer extends AppCompatActivity implements MediaPlayer.OnPreparedListener {

    private ImageView iplay,ipause,istop,iforward, ibackward;
    private MediaPlayer mediaPlayer;

    private double startTime = 0;
    private double finalTime = 0;
    private static int count =0;

    private Handler myHandler = new Handler();;
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private SeekBar seekbar;
    private TextView tsong, tv1, tv2;

    public static int oneTimeOnly = 0;

//


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);
        setTitle("");

        iplay = findViewById(R.id.iplay);
        ipause = findViewById(R.id.ipause);
        istop = findViewById(R.id.istop);
        iforward = findViewById(R.id.iforward);
        ibackward = findViewById(R.id.ibackward);
        seekbar = findViewById(R.id.seekBar);
        tsong = findViewById(R.id.tsong);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);



        Intent intent = getIntent();
        String name = intent.getStringExtra("songName");
        final String Url = intent.getStringExtra("songUrl");

        tsong.setText(name);


        mediaPlayer = new MediaPlayer();




        try {
            mediaPlayer.setDataSource(Url);

           /* progressDialog.dismiss();*/


        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare();

        } catch (IOException e) {
            e.printStackTrace();
        }


       /* mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                progressDialog.dismiss();
            }
        });*/
        seekbar = (SeekBar)findViewById(R.id.seekBar);
        seekbar.setClickable(false);
        iplay.setEnabled(true);




       iplay.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {


                    Toast.makeText(getApplicationContext(), "Playing music", Toast.LENGTH_SHORT).show();
                    mediaPlayer.start();


                    finalTime = mediaPlayer.getDuration();
                    startTime = mediaPlayer.getCurrentPosition();


                        seekbar.setMax((int) finalTime);

                    tv1.setText(String.format("%d min, %d sec",
                            TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                            startTime)))
                    );

                    tv2.setText(String.format("%d min, %d sec",
                            TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                            finalTime)))
                    );


                    seekbar.setProgress((int) startTime);
                    myHandler.postDelayed(UpdateSongTime, 100);
                    ipause.setEnabled(true);
                    ibackward.setEnabled(true);
                    istop.setEnabled(true);
                    iplay.setEnabled(false);


                }


        });

        ipause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(AudioPlayer.this, "Pausing music", Toast.LENGTH_SHORT).show();
                mediaPlayer.pause();
                ipause.setEnabled(false);
                ibackward.setEnabled(true);
                if(!iplay.isEnabled()){
                    iplay.setEnabled(true);
                }
                if(!istop.isEnabled()){
                    istop.setEnabled(true);
                }
            }
        });

        istop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer!=null && mediaPlayer.isPlaying()){
//                    mediaPlayer.stop();
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);

                    istop.setEnabled(false);
                    iplay.setEnabled(true);
                }
            }
        });

        iforward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int)startTime;

                if((temp+forwardTime)<=finalTime){
                    startTime = startTime + forwardTime;
                    mediaPlayer.seekTo((int) startTime);
                    Toast.makeText(getApplicationContext(),"You have Jumped forward 5 seconds",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Cannot jump forward 5 seconds",Toast.LENGTH_SHORT).show();
                    if(!iplay.isEnabled()){
                        iplay.setEnabled(true);
                    }
                }
            }
        });


        ibackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int)startTime;

                if((temp-backwardTime)>0){
                    startTime = startTime - backwardTime;
                    mediaPlayer.seekTo((int) startTime);
                    Toast.makeText(getApplicationContext(),"You have Jumped backward 5 seconds",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Cannot jump backward 5 seconds",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private Runnable UpdateSongTime = new Runnable() {
        @SuppressLint("DefaultLocale")
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            tv1.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekbar.setProgress((int)startTime);
            myHandler.postDelayed(this, 100);
        }
    };






        @Override
        public void onPrepared (MediaPlayer mp){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Loading");
            progressDialog.show();
        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
       


    }
}
