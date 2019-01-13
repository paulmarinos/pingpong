package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Ball {

    Texture texture;
    int x;
    int y;
    final int INITIAL_VELOCITY = 7;
    int velocityX = INITIAL_VELOCITY;
    int velocityY = INITIAL_VELOCITY;
    int ballStartFrameCounter;
    int ballFlyFrameCounter;
    final int FRAMES_TO_WAIT_BEFORE_SPEEDING_UP_BALL = 100;
    final int FRAMES_TO_WAIT_BEFORE_BALL_START = 60;

    public Ball() {
        texture = new Texture("ball_small.png");

    }

    void draw(SpriteBatch batch){
        batch.draw(texture, x, y);
    }

    public void move(Paddle paddle) {
        ballStartFrameCounter++;

        //update ball coordinates to make it fly
        if (ballStartFrameCounter >= FRAMES_TO_WAIT_BEFORE_BALL_START){
            x += velocityX;
            y += velocityY;
            ballFlyFrameCounter++;
            speedUp();
        }

        else {
            //ball follows paddle before it flies
            x = paddle.x + paddle.texture.getWidth() / 2 - texture.getWidth() / 2;
        }
    }

    private void speedUp() {
        if (ballFlyFrameCounter % FRAMES_TO_WAIT_BEFORE_SPEEDING_UP_BALL == 0){
            if (velocityX>=0){
                velocityX++;
            }
            else{
                velocityX--;
            }

            if (velocityY>=0){
                velocityY++;
            }
            else{
                velocityY--;
            }

        }

    }

    public void restart(Paddle paddle) {
        ballStartFrameCounter = 0;
        velocityY = Math.abs(velocityY);
        x = paddle.x + paddle.texture.getWidth() / 2 - texture.getWidth() / 2;
        y = paddle.y + paddle.texture.getHeight();
    }

    void dispose(){
        texture.dispose();
    }

}
