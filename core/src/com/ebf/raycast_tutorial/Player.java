package com.ebf.raycast_tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Player {
	private final GameMain game;
	private final Vector2 pos;
	private float angle;

	public Player(GameMain game) {
		this.game = game;
		pos = new Vector2(Constants.PLAYER_POS_X, Constants.PLAYER_POS_Y);
		angle = Constants.PLAYER_ANGLE;
	}

	public void update(float delta) {
		float sinA = MathUtils.sin(angle);
		float cosA = MathUtils.cos(angle);

		float speed = Constants.PLAYER_SPEED * delta;
		float speedSin = speed * sinA;
		float speedCos = speed * cosA;

		float dx = 0;
		float dy = 0;

		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			dx += speedCos;
			dy += speedSin;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			dx += -speedCos;
			dy += -speedSin;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			dx += speedSin;
			dy += -speedCos;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			dx += -speedSin;
			dy += speedCos;
		}

		checkWallCollision(dx, dy);

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			angle -= Constants.PLAYER_ROT_SPEED * delta;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			angle += Constants.PLAYER_ROT_SPEED * delta;
		}
		angle %= MathUtils.PI2;
	}

	private void checkWallCollision(float dx, float dy) {
		if (checkWall(pos.x + dx, pos.y)) {
			dx = 0;
		}
		if (checkWall(pos.x, pos.y + dy)) {
			dy = 0;
		}
		pos.add(dx, dy);
	}

	private boolean checkWall(float x, float y) {
		return game.getMap().collidesWall((int) x, (int) y);
	}

	public void render(ShapeRenderer renderer) {
		renderer.setColor(Color.RED);

		renderer.rectLine(pos.x * Constants.CELL_SIZE, pos.y * Constants.CELL_SIZE,
				pos.x * Constants.CELL_SIZE + Constants.WIDTH * MathUtils.cos(angle),
				pos.y * Constants.CELL_SIZE + Constants.WIDTH * MathUtils.sin(angle), 2);

		renderer.setColor(Color.GREEN);
		renderer.circle(pos.x * Constants.CELL_SIZE, pos.y * Constants.CELL_SIZE, 15);
	}

	public Vector2 getPos() {
		return pos;
	}

	public float getAngle() {
		return angle;
	}
}
