package com.ebf.raycast_tutorial;

import com.badlogic.gdx.math.MathUtils;

public class Constants {
    private Constants() {
    }

    public static final int WIDTH = 1600;
    public static final int HEIGHT = 900;
    public static final int CELL_SIZE = 100;
    public static final int MINIMAP_SCALE = 3;

    public static final int HALF_WIDTH = WIDTH / 2;
    public static final int HALF_HEIGHT = HEIGHT / 2;
    public static final int FPS = 0;

    public static final float PLAYER_POS_X = 1.5f;
    public static final float PLAYER_POS_Y = 5.0f;

    public static final float PLAYER_ANGLE = 0;
    public static final float PLAYER_SPEED = 0.004f;
    public static final float PLAYER_ROT_SPEED = 0.002f;

    public static final float FOV = MathUtils.HALF_PI;
    public static final float HALF_FOV = FOV / 2;

    public static final int NUM_RAYS = WIDTH / 2;
    public static final int HALF_NUM_RAYS = NUM_RAYS / 2;

    public static final float DELTA_ANGLE = FOV / NUM_RAYS;
    public static final int MAX_DEPTH = 20;

    public static final float SCREEN_DIST = HALF_WIDTH / (float) Math.tan(HALF_FOV);
    public static final int SCALE = WIDTH / NUM_RAYS;
}
