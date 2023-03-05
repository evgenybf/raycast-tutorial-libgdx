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
	private final Vector2 dir;

	public Player(GameMain game) {
		this.game = game;
		pos = new Vector2(Constants.PLAYER_POS_X, Constants.PLAYER_POS_Y);
		angle = Constants.PLAYER_ANGLE;
		dir = new Vector2();
	}

	public void update(float delta) {
		float speed = Constants.PLAYER_SPEED * delta;

		dir.setZero();

		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			dir.x += 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			dir.x -= 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			dir.y -= 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			dir.y += 1;
		}

		dir.rotateRad(angle).scl(speed);

		if (collidesWall(pos.x + dir.x, pos.y)) {
			dir.x = 0;
		}
		if (collidesWall(pos.x, pos.y + dir.y)) {
			dir.y = 0;
		}

		pos.add(dir);

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			angle -= Constants.PLAYER_ROT_SPEED * delta;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			angle += Constants.PLAYER_ROT_SPEED * delta;
		}

		angle %= MathUtils.PI2;
	}

	private boolean collidesWall(float x, float y) {
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
