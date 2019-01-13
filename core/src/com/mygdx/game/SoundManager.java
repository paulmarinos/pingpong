package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.Random;

public class SoundManager {

    Sound bounceSound1;
    Sound bounceSound2;
    Sound bounceSound3;
    Sound loseBallSound;


    SoundManager(){
        bounceSound1 = Gdx.audio.newSound(Gdx.files.internal("bounce1.ogg"));
        bounceSound2 = Gdx.audio.newSound(Gdx.files.internal("bounce2.ogg"));
        bounceSound3 = Gdx.audio.newSound(Gdx.files.internal("bounce3.ogg"));
        loseBallSound = Gdx.audio.newSound(Gdx.files.internal("lose_ball.ogg"));
    }


    public void dispose () {
        bounceSound1.dispose();
        bounceSound2.dispose();
        bounceSound3.dispose();
        loseBallSound.dispose();
    }

    public void playRandomBounceSound(){
        Random random = new Random();
        switch(random.nextInt(3)){
            case 0: bounceSound1.play();
                break;
            case 1: bounceSound2.play();
                break;
            case 2:	bounceSound3.play();
        }
    }

}
