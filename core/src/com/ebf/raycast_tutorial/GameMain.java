package com.ebf.raycast_tutorial;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameMain extends ApplicationAdapter {

    private final FPSLogger fpsLogger;

    private GameMap map;
    private Player player;
    private RayCasting rayCasting;

    private Viewport viewport;
    private OrthographicCamera camera;
    private Viewport minimapViewport;

    private ShapeRenderer renderer;

    private boolean mapDisplayed = true;

    public GameMain() {
        fpsLogger = new FPSLogger();
    }

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(true, Constants.WIDTH, Constants.HEIGHT);

        viewport = new FillViewport(Constants.WIDTH, Constants.HEIGHT, camera);

        minimapViewport = new FitViewport(Constants.WIDTH, Constants.HEIGHT, camera);

        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
        renderer.setProjectionMatrix(viewport.getCamera().combined);

        map = new GameMap(this);
        player = new Player(this);
        rayCasting = new RayCasting(this);
    }

    @Override
    public void render() {
        fpsLogger.log();

        float delta = Gdx.graphics.getDeltaTime() * 1000;
        player.update(delta);

        ScreenUtils.clear(0, 0, 0, delta);

        // Draw the 3d maze
        viewport.apply();
        renderer.setProjectionMatrix(viewport.getCamera().combined);

        renderer.begin(ShapeType.Filled);
        rayCasting.render(renderer, false);
        renderer.end();

        // Draw minimap
        // Note, that we do ray casting twice

        minimapViewport.apply();
        renderer.setProjectionMatrix(minimapViewport.getCamera().combined);

        renderer.begin(ShapeType.Filled);

        if (mapDisplayed) {
            map.render(renderer);
            player.render(renderer);
            rayCasting.render(renderer, true);
        }

        renderer.end();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            mapDisplayed = !mapDisplayed;
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        // Set minimap viewpoint size and position it at the top left corner
        minimapViewport.update(width / Constants.MINIMAP_SCALE, height / Constants.MINIMAP_SCALE, true);
        minimapViewport.setScreenPosition(0, height - minimapViewport.getScreenHeight());
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    public GameMap getMap() {
        return map;
    }

    public Player getPlayer() {
        return player;
    }
}
