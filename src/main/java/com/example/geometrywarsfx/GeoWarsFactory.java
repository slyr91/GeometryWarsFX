package com.example.geometrywarsfx;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.LiftComponent;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.dsl.components.RandomMoveComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class GeoWarsFactory implements EntityFactory {

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        return FXGL.entityBuilder()
                .from(data)
                .type(GeoWars.EntityType.PLAYER)
                .viewWithBBox(new Rectangle(30, 30, Color.BLUE))
                .collidable()
                .build();
    }

    @Spawns("bullet")
    public Entity newBullet(SpawnData data) {
        Entity player = FXGL.getGameWorld().getSingleton(GeoWars.EntityType.PLAYER);
        Point2D direction = FXGL.getInput().getMousePositionWorld()
                .subtract(player.getCenter());

        return FXGL.entityBuilder()
                .from(data)
                .type(GeoWars.EntityType.BULLET)
                .viewWithBBox(new Rectangle(10, 2, Color.BLACK))
                .collidable()
                .with(new ProjectileComponent(direction, 1000))
                .with(new OffscreenCleanComponent())
                .build();
    }

    @Spawns("enemy")
    public Entity newEnemy(SpawnData data) {
        Circle circle = new Circle(20, 20, 20, Color.RED);
        circle.setStroke(Color.BROWN);
        circle.setStrokeWidth(2.0);

        return FXGL.entityBuilder()
                .from(data)
                .type(GeoWars.EntityType.ENEMY)
                .viewWithBBox(circle)
                .collidable()
                .with(new RandomMoveComponent(new Rectangle2D(0, 0, FXGL.getAppWidth(), FXGL.getAppHeight()), 50))
                .build();
    }

    @Spawns("sneakySquare")
    public Entity sneakySquareEnemy(SpawnData data) {
        Rectangle rectangle = new Rectangle(20, 20, Color.DARKBLUE);
        rectangle.setStroke(Color.BROWN);
        rectangle.setStrokeWidth(2.0);

        return FXGL.entityBuilder(data)
                .type(GeoWars.EntityType.ENEMY)
                .viewWithBBox(rectangle)
                .collidable()
                .with(new LiftComponent().xAxisSpeedDuration(-500.0, Duration.seconds(30.0)))
                .with(new OffscreenCleanComponent())
                .build();
    }
}
