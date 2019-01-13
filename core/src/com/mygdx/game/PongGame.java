package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PongGame extends ApplicationAdapter {
	SpriteBatch batch;
	Ball ball;
	Paddle paddle;
	SoundManager soundManager;
	BitmapFont font;
	int score;
	final int INITIAL_LIVES_COUNT = 3;
	int livesCount = INITIAL_LIVES_COUNT;
	final int CATCH_BALL_BONUS = 100;
	Texture gameOverTexture;
	boolean isGameOver;
	Button closeBtn;
	Button replayBtn;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		soundManager = new SoundManager();
		font = new BitmapFont();
		font.getData().setScale(5);
		ball = new Ball();
		paddle = new Paddle();
		paddle.center();
		gameOverTexture = new Texture("game_over_logo.jpg");
		closeBtn = new Button("close_btn.png");
		closeBtn.x = Gdx.graphics.getWidth() - closeBtn.texture.getWidth();
		closeBtn.y  = 10;
		replayBtn = new Button("replay_btn.png");
		replayBtn.y = 10;

		ball.restart(paddle);

	}


	@Override
	public void render () {
		if (isGameOver){
			if (closeBtn.isClicked()){
				System.exit(0);
			}
			if (replayBtn.isReleased()){
				score = 0;
				livesCount = INITIAL_LIVES_COUNT;
				isGameOver = false;
				ball.velocityX = ball.velocityY = ball.INITIAL_VELOCITY;
				paddle.center();
				ball.restart(paddle);
			}
		}


		if (! isGameOver){
			paddle.move();
			ball.move(paddle);
		}

		collideBall();

		//if we lose the ball
		if(ball.y + ball.texture.getHeight() < 0){
			soundManager.loseBallSound.play();
			ball.restart(paddle);
			livesCount--;
			if(livesCount == 0){
				isGameOver = true;
			}
		}

		draw();
	}

	private void draw() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		paddle.draw(batch);
		ball.draw(batch);
		font.draw(batch, "score: " + score + " lives: " + livesCount, 0, Gdx.graphics.getHeight());

		if (isGameOver){
			batch.draw(gameOverTexture, (Gdx.graphics.getWidth() - gameOverTexture.getWidth())/2,
				(Gdx.graphics.getHeight() - gameOverTexture.getWidth())/2);
			closeBtn.draw(batch);
			replayBtn.draw(batch);
		}
		batch.end();
	}


	@Override
	public void dispose () {
		batch.dispose();
		ball.dispose();
		paddle.dispose();
		paddle.texture.dispose();
		soundManager.dispose();
		font.dispose();
		gameOverTexture.dispose();
		closeBtn.dispose();
		replayBtn.dispose();
	}


	private void collideBall() {
		//the ball collides the right edge of the screen
		if (ball.x + ball.texture.getWidth() >= Gdx.graphics.getWidth()){
			ball.velocityX = -ball.velocityX;
			soundManager.playRandomBounceSound();
		}
		//the ball collides the top edge of the screen
		if (ball.y + ball.texture.getHeight() >= Gdx.graphics.getHeight()){
			ball.velocityY = -ball.velocityY;
			soundManager.playRandomBounceSound();
		}
		//the ball collides the left edge of the screen
		if (ball.x < 0){
			ball.velocityX = -ball.velocityX;
			soundManager.playRandomBounceSound();
		}

		//the ball collides with the top of the paddle
		if (ball.x > paddle.x - ball.texture.getWidth() / 2 && ball.x < paddle.x + paddle.texture.getWidth() - ball.texture.getWidth() / 2){
			if(ball.y < paddle.y + paddle.texture.getHeight()){
				ball.velocityY = -ball.velocityY;
				soundManager.playRandomBounceSound();

				score+=CATCH_BALL_BONUS*Math.abs(ball.velocityX);
			}

		}
		//the ball collides with the left side of the paddle
		if(ball.x > paddle.x - ball.texture.getWidth() && ball.x < paddle.x - ball.texture.getWidth() / 2 + 1){
			if(ball.y < paddle.y + paddle.texture.getHeight()){
				if(ball.velocityX > 0) {
					ball.velocityX = -ball.velocityX;
					soundManager.playRandomBounceSound();
				}
			}
		}
		//the ball collides with the right side of the paddle
		if(ball.x > paddle.x + paddle.texture.getWidth() - ball.texture.getWidth() / 2 - 1
				&& ball.x < paddle.x + paddle.texture.getWidth()){
			if(ball.y < paddle.y + paddle.texture.getHeight()){
				if(ball.velocityX < 0) {
					ball.velocityX = -ball.velocityX;
					soundManager.playRandomBounceSound();
				}
			}
		}
	}


}