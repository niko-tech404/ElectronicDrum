package com.example.electronicdrum;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SoundPool soundPool;

    private int soundKick;
    private int soundSnare;
    private int soundHiHat;
    private int soundCrash;
    private int soundTom1;
    private int soundTom2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setStatusBarColor(android.graphics.Color.parseColor("#121214"));

        setupAudioEngine();
        setupDrumPads();
    }

    private void setupAudioEngine() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(6)
                .setAudioAttributes(audioAttributes)
                .build();

        soundKick = soundPool.load(this, R.raw.kick, 1);
        soundSnare = soundPool.load(this, R.raw.snare, 1);
        soundHiHat = soundPool.load(this, R.raw.hihat, 1);
        soundCrash = soundPool.load(this, R.raw.crash, 1);
        soundTom1 = soundPool.load(this, R.raw.tom1, 1);
        soundTom2 = soundPool.load(this, R.raw.tom2, 1);
    }

    private void setupDrumPads() {
        findViewById(R.id.padCrash).setOnClickListener(v -> hitDrum(soundCrash, v));
        findViewById(R.id.padHiHat).setOnClickListener(v -> hitDrum(soundHiHat, v));
        findViewById(R.id.padTom1).setOnClickListener(v -> hitDrum(soundTom1, v));
        findViewById(R.id.padTom2).setOnClickListener(v -> hitDrum(soundTom2, v));
        findViewById(R.id.padSnare).setOnClickListener(v -> hitDrum(soundSnare, v));
        findViewById(R.id.padKick).setOnClickListener(v -> hitDrum(soundKick, v));
    }

    private void hitDrum(int soundId, View pad) {

        soundPool.play(soundId, 1.0f, 1.0f, 0, 0, 1.0f);

        pad.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);

        pad.animate()
                .scaleX(0.92f).scaleY(0.92f)
                .setDuration(45)
                .withEndAction(() -> pad.animate().scaleX(1.0f).scaleY(1.0f).setDuration(45))
                .start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}