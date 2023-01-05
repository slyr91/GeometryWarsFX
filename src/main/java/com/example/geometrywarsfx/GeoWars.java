package com.example.geometrywarsfx;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.util.Duration;

public class GeoWars extends GameApplication {

    private final GeoWarsFactory geoWarsFactory = new GeoWarsFactory();
    private Entity player;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Geometry Wars");
    }

    public enum EntityType {
        PLAYER, BULLET, ENEMY
    }

    @Override
    protected void initInput() {
        FXGL.onKey(KeyCode.W, () -> player.translateY(-5));
        FXGL.onKey(KeyCode.S, () -> player.translateY(5));
        FXGL.onKey(KeyCode.A, () -> player.translateX(-5));
        FXGL.onKey(KeyCode.D, () -> player.translateX(5));
        FXGL.onBtnDown(MouseButton.PRIMARY, () -> FXGL.spawn("bullet", player.getCenter()));

    }

    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(geoWarsFactory);

        player = FXGL.spawn("player", FXGL.getAppWidth() / 2 - 15, FXGL.getAppWidth() / 2 - 15);

        FXGL.run(() -> FXGL.spawn("enemy"), Duration.seconds(1.0));
    }

    @Override
    protected void initPhysics() {
        FXGL.onCollisionBegin(EntityType.BULLET, EntityType.ENEMY, (bullet, enemy) -> {
            bullet.removeFromWorld();
            enemy.removeFromWorld();
        });

        FXGL.onCollisionBegin(EntityType.ENEMY, EntityType.PLAYER, (enemy, player) -> {
            FXGL.showMessage("You Died!", () -> {
                FXGL.getGameController().startNewGame();
            });
        });



    }
}
