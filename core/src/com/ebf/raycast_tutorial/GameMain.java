package com.ebf.raycast_tutorial;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

public class GameMain extends ApplicationAdapter {

    private final FPSLogger fpsLogger;

    private GameMap map;
    private Player player;
    private RayCasting rayCasting;

    private FitViewport viewport;
    private ShapeRenderer renderer;

    private boolean mapDisplayed = true;

    private ScalingViewport viewport2;

    private OrthographicCamera camera;

    public GameMain() {
        fpsLogger = new FPSLogger();
    }

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(true, Constants.WIDTH, Constants.HEIGHT);

        viewport = new FitViewport(Constants.WIDTH, Constants.HEIGHT, camera);
        viewport2 = new ScalingViewport(Scaling.stretch, Constants.WIDTH, Constants.HEIGHT, camera);
        viewport2.setScreenBounds(0, 0, Constants.WIDTH / 5, Constants.HEIGHT / 5);

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

        viewport2.apply();
        renderer.setProjectionMatrix(viewport.getCamera().combined);

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
        // TODO: Try to do something about this.. Doesn't look right
        // FIXME: Move to the top left corner
        viewport2.update(width, height, true);
        viewport2.setScreenBounds(0, 0, Constants.WIDTH / 5, Constants.HEIGHT / 5);
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
