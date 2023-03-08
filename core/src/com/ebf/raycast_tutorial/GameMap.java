package com.ebf.raycast_tutorial;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.GridPoint2;

public class GameMap {
    // @formatter:off
    private static final int[][] miniMap = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,},
        {1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1,},
        {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1,},
        {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1,},
        {1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1,},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,},
        {1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1,},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},
    };
    // @formatter:on

    private final Map<GridPoint2, Integer> wordMap;

    public GameMap(GameMain game) {
        this.wordMap = new HashMap<GridPoint2, Integer>();
        initializeWorldMap();
    }

    private void initializeWorldMap() {
        for (int i = 0; i < miniMap.length; i++) {
            int[] mapRow = miniMap[i];
            for (int j = 0; j < mapRow.length; j++) {
                if (mapRow[j] != 0) {
                    wordMap.put(new GridPoint2(j, i), mapRow[j]);
                }
            }
        }
    }

    public void render(ShapeRenderer renderer) {
        renderer.setColor(Color.BLACK);
        // FIXME: how to get the size of viewport / inner screen
        renderer.rect(0, 0, 10000, 10000);
        renderer.setColor(Color.DARK_GRAY);
        for (Map.Entry<GridPoint2, Integer> entry : wordMap.entrySet()) {
            // TODO: line thickness
            renderer.rect(entry.getKey().x * Constants.CELL_SIZE, entry.getKey().y * Constants.CELL_SIZE,
                    Constants.CELL_SIZE, Constants.CELL_SIZE);
        }
    }

    public boolean collidesWall(float x, float y) {
        return collidesWall((int) x, (int) y);
    }

    public boolean collidesWall(int col, int row) {
        if (row < 0 || col < 0 || row >= miniMap.length) {
            return true;
        }
        int[] mapRow = miniMap[row];
        if (col >= mapRow.length) {
            return true;
        }
        return mapRow[col] != 0;
    }
}
