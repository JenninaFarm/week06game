package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class MyGdxGame implements ApplicationListener {
	final int TILES_AMOUNT_WIDTH = 120;
	final int TILES_AMOUNT_HEIGTH = 20;

	final int TILE_WIDTH = 32;
	final int TILE_HEIGTH = 32;

	final int WORLD_WIDTH_PIXELS = TILE_WIDTH * TILES_AMOUNT_WIDTH;
	final int WORLD_HEIGTH_PIXELS = TILE_HEIGTH * TILES_AMOUNT_HEIGTH;

	private SpriteBatch batch;
	private OrthographicCamera camera;


	private Player player;
	private Enemy enemy;
	private TiledMap tiledMap;
	private TiledMapRenderer tiledMapRenderer;

	Texture img;
	float imgWidth;
	float imgHeight;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		//camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 400);

		tiledMap = new TmxMapLoader().load("map.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

		player = new Player(new Texture("swimBeaver.png"));
		//enemy = new Enemy(new Texture("polar.png"));

		moveCamera();
	}

	public void moveCamera() {
		//just move the camera to some direction:
		//camera.translate(1, 0); // moves x++

		//move to certain point
		//camera.position.x = 200;

		//move camera to direction of some sprite
		camera.position.x = player.getX() + 250f;
		camera.position.y = player.getY();

		//update camera movement
		camera.update();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void render () {
		clearScreen();

		batch.setProjectionMatrix(camera.combined);

		// Render all layers to screen
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();

		batch.begin();
		player.draw(batch);
		batch.end();

		movePlayer();
		checkCollectibleCollisions();
		checkIcebergCollisions();

		moveCamera();
	}

	private void clearScreen() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	public void movePlayer() {
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			player.move(1f);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			player.move(-1f);
		}
	}

	public void checkCollectibleCollisions() {
		//Let's get the collectible object layer
		MapLayer flowerObjectLayer = (MapLayer)tiledMap.getLayers().get("kukatHit");

		//All the rectangles of the layer
		MapObjects flowerObjects = flowerObjectLayer.getObjects();

		//Cast it to rectangleObjects array
		Array<RectangleMapObject> rectangleFlowerObjects = flowerObjects.getByType(RectangleMapObject.class);
		
		//Iterate all the rectangles
		for(RectangleMapObject rectangleObject : rectangleFlowerObjects) {
			Rectangle rectangle = rectangleObject.getRectangle();
			
			//SCALE given circle down if using world dimensions!
			if(player.getBoundingRectangle().overlaps(rectangle)) {
				clearCollectible(rectangle.getX(), rectangle.getY());
			}
		}
	}

	private void checkIcebergCollisions() {
		//Let's get the collectible object layer
		MapLayer icebergObjectLayer = (MapLayer)tiledMap.getLayers().get("vuoretHit");
		//All the rectangles of the layer
		MapObjects icebergObjects = icebergObjectLayer.getObjects();
		//Cast it to rectangleObjects array
		Array<RectangleMapObject> rectangleIcebergObjects = icebergObjects.getByType(RectangleMapObject.class);
		//Iterate all the rectangles
		for(RectangleMapObject rectangleObject : rectangleIcebergObjects) {
			Rectangle rectangle = rectangleObject.getRectangle();

			if(player.getBoundingRectangle().overlaps(rectangle)) {
				player.die();
			}
		}
	}

	private void clearCollectible(float xCoord, float yCoord) {
		Gdx.app.log("LOG", "in clearCollectibles");
		int indexX = (int) xCoord / TILE_WIDTH;
		int indexY = (int) yCoord / TILE_HEIGTH;

		TiledMapTileLayer wallCells = (TiledMapTileLayer) tiledMap.getLayers().get("kukat");
		wallCells.setCell(indexX, indexY, null);
	}


	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose () {
		batch.dispose();

		img.dispose();
	}
}
