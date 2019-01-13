package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Paddle {

    int x;
    int y = 10;
    Texture texture;

    public Paddle(){
        texture = new Texture("paddle.bmp");
    }

    void draw(SpriteBatch batch){
        batch.draw(texture, x, y);

    }

    public void move() {
        if (Gdx.input.isTouched()) {
            x = Gdx.input.getX() - texture.getWidth() / 2;

            //prevent paddle from moving past left edge of screen
            if (x < 0) {
                x = 0;
            }
            //prevent paddle from moving past right edge of screen
            if (x + texture.getWidth() > Gdx.graphics.getWidth()) {
                x = Gdx.graphics.getWidth() - texture.getWidth();
            }
        }
    }

    public void center(){
        x = (Gdx.graphics.getWidth() - texture.getWidth()) / 2;
    }

    void dispose(){
        texture.dispose();
    }




}
