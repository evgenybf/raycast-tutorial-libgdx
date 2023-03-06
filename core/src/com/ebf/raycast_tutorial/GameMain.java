package com.ebf.raycast_tutorial;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameMain extends ApplicationAdapter {

    private final FPSLogger fpsLogger;

    private GameMap map;
    private Player player;
    private RayCasting rayCasting;

    private FitViewport viewport;
    private ShapeRenderer renderer;

    private boolean map2dDisplayed = true;
    private boolean map3dDisplayed = true;

    public GameMain() {
        fpsLogger = new FPSLogger();
    }

    @Override
    public void create() {
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(true, Constants.WIDTH, Constants.HEIGHT);

        viewport = new FitViewport(Constants.WIDTH, Constants.HEIGHT, camera);

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
//		Gdx.gl.glClearColor(0, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.begin(ShapeType.Filled);

        if (map2dDisplayed) {
            map.render(renderer);
        }

        rayCasting.render(renderer);

        if (map2dDisplayed) {
            player.render(renderer);
        }

        renderer.end();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (map2dDisplayed && map3dDisplayed) {
                map3dDisplayed = false;
            } else if (map2dDisplayed) {
                map2dDisplayed = false;
                map3dDisplayed = true;
            } else {
                map2dDisplayed = true;
                map3dDisplayed = true;
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.setAutoShapeType(true);
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

    public boolean isMap3dDisplayed() {
        return map3dDisplayed;
    }

    public boolean isMap2dDisplayed() {
        return map2dDisplayed;
    }
}
