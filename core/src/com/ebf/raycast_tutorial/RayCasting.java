package com.ebf.raycast_tutorial;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class RayCasting {
    private GameMain game;

    public RayCasting(GameMain game) {
        this.game = game;
    }

    void render(ShapeRenderer renderer, boolean mapMode) {
        Vector2 pos = game.getPlayer().getPos();

        float ox = pos.x;
        float oy = pos.y;

        int col = (int) pos.x;
        int row = (int) pos.y;

        float angle = game.getPlayer().getAngle();

        float rayAngle = angle - Constants.HALF_FOV + 0.0001f;

        for (int ray = 0; ray < Constants.NUM_RAYS; ray++, rayAngle += Constants.DELTA_ANGLE) {
            float sinA = MathUtils.sin(rayAngle);
            float cosA = MathUtils.cos(rayAngle);

            // horizontals
            float yHor;
            float dy;

            if (sinA > 0) {
                yHor = row + 1;
                dy = 1;
            } else {
                yHor = row - 1e-6f;
                dy = -1;
            }

            float depthHor = (yHor - oy) / sinA;
            float xHor = ox + depthHor * cosA;

            float deltaDepth = dy / sinA;
            float dx = deltaDepth * cosA;

            for (int i = 0; i < Constants.MAX_DEPTH; i++) {
                if (game.getMap().collidesWall((int) xHor, (int) yHor)) {
                    break;
                }
                xHor += dx;
                yHor += dy;
                depthHor += deltaDepth;
            }

            // verticals
            float xVert;
            if (cosA > 0) {
                xVert = col + 1;
                dx = 1;
            } else {
                xVert = col - 1e-6f;
                dx = -1;
            }

            float depthVert = (xVert - ox) / cosA;
            float yVert = oy + depthVert * sinA;

            deltaDepth = dx / cosA;
            dy = deltaDepth * sinA;

            for (int i = 0; i < Constants.MAX_DEPTH; i++) {
                if (game.getMap().collidesWall(xVert, yVert)) {
                    break;
                }
                xVert += dx;
                yVert += dy;
                depthVert += deltaDepth;
            }

            // depth
            float depth;
            if (depthVert < depthHor) {
                depth = depthVert;
            } else {
                depth = depthHor;
            }

            if (mapMode) {
                // Draw 2d projection
                renderer.setColor(Color.YELLOW);
                renderer.rectLine(Constants.CELL_SIZE * ox, Constants.CELL_SIZE * oy,
                        Constants.CELL_SIZE * ox + Constants.CELL_SIZE * depth * cosA,
                        Constants.CELL_SIZE * oy + Constants.CELL_SIZE * depth * sinA, 2);
            }

            // Fish eye correction
            depth *= MathUtils.cos(angle - rayAngle);

            // projection
            float proj_height = Constants.SCREEN_DIST / (depth + 0.0001f);

            if (!mapMode) {
                // Draw walls
                float color_comp = 1 / (1 + (float) Math.pow(depth, 5) * 0.0002f);
                renderer.setColor(color_comp, color_comp, color_comp, 1);
                renderer.rect(ray * Constants.SCALE, Constants.HALF_HEIGHT - MathUtils.round(proj_height / 2),
                        Constants.SCALE * 2, proj_height);
            }
        }
    }
}
