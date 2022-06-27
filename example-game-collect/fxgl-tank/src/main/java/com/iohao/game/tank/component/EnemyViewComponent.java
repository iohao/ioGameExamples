package com.iohao.game.tank.component;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityGroup;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.time.LocalTimer;
import com.iohao.game.tank.Config;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.security.SecureRandom;
import java.util.List;

import static com.iohao.game.tank.GameType.ENEMY;
import static com.iohao.game.tank.GameType.PLAYER;

/**
 * @author 渔民小镇
 * @date 2022-03-06
 */
public class EnemyViewComponent extends Component {
    private BoundingBoxComponent bbox;
    private ViewComponent view;
    private Texture texture;

    private double frameWidth;
    private double frameHeight;

    private LocalTimer shootTimer = FXGL.newLocalTimer();
    private SecureRandom random = new SecureRandom();
    private double speed = 1;
    private double speedFactor = FXGLMath.random(1.1, 1.35);
    private Dir moveDir;
    private LazyValue<EntityGroup> blocks = new LazyValue<>(() -> entity.getWorld().getGroup(ENEMY, PLAYER));

    /**
     * 变成坦克前3秒不能动
     */
    private boolean canMove;
    private static AnimationChannel ac = new AnimationChannel(FXGL.image("tank/spawnTank.png"), Duration.seconds(0.4), 4);

    private static Dir[] dirs = Dir.values();

    @Override
    public void onUpdate(double tpf) {
        speed = tpf * 60;
        if (FXGL.getb("freezingEnemy") || !canMove) {
            return;
        }
        if (moveDir == Dir.UP && random.nextInt(1000) > 880) {
            moveDir = dirs[random.nextInt(4)];
        } else if (random.nextInt(1000) > 980) {
            moveDir = dirs[random.nextInt(4)];
        }
        setMoveDir(moveDir);
        if (random.nextInt(1000) > 980) {
            shoot();
        }
    }

    public void shoot() {
        if (!shootTimer.elapsed(Config.ENEMY_SHOOT_DELAY)) {
            return;
        }
        FXGL.spawn("bullet", new SpawnData(getEntity().getCenter().getX() - 4, getEntity().getCenter().getY() - 4)
                .put("direction", angleToVector())
                .put("owner", entity)
        );

        shootTimer.capture();
    }

    private Point2D angleToVector() {
        double angle = getEntity().getRotation();
        if (angle == 0) {
            return new Point2D(0, -1);
        } else if (angle == 90) {
            return new Point2D(1, 0);
        } else if (angle == 180) {
            return new Point2D(0, 1);
        } else {    // 270
            return new Point2D(-1, 0);
        }
    }

    public void setMoveDir(Dir moveDir) {
        this.moveDir = moveDir;
        switch (moveDir) {
            case UP:
                up();
                break;
            case DOWN:
                down();
                break;
            case LEFT:
                left();
                break;
            case RIGHT:
                right();
                break;
            default:
                break;
        }
    }

    private void right() {
        getEntity().setRotation(90);
        move(speedFactor * speed, 0);
    }

    private void left() {
        getEntity().setRotation(270);
        move(-speedFactor * speed, 0);

    }

    private void down() {
        getEntity().setRotation(180);
        move(0, speedFactor * speed);

    }

    private void up() {
        getEntity().setRotation(0);
        move(0, -speedFactor * speed);

    }

    @Override
    public void onAdded() {
        moveDir = Dir.DOWN;
        Texture texture = FXGL.texture(entity.getString("assentName"));

        AnimatedTexture animatedTexture = new AnimatedTexture(ac).loop();
        entity.getViewComponent().addChild(animatedTexture);
        FXGL.runOnce(() -> {
            if (entity != null && entity.isActive()) {
                entity.getViewComponent().addChild(texture);
                entity.getViewComponent().removeChild(animatedTexture);
                canMove = true;
            }

        }, Duration.seconds(1));
    }

    private Vec2 velocity = new Vec2();

    private void move(double dx, double dy) {
        if (!getEntity().isActive()) {
            return;
        }
        velocity.set((float) dx, (float) dy);
        int length = Math.round(velocity.length());
        velocity.normalizeLocal();

        List<Entity> blockList = blocks.get().getEntitiesCopy();
        for (int i = 0; i < length; i++) {
            entity.translate(velocity.x, velocity.y);
            boolean collision = false;
            Entity entityTemp = null;
            for (int j = 0; j < blockList.size(); j++) {
                entityTemp = blockList.get(j);
                if (entityTemp == entity) {
                    continue;
                }
                if (entityTemp.getBoundingBoxComponent().isCollidingWith(bbox)) {
                    collision = true;
                    break;
                }
            }
            if (collision) {
                entity.translate(-velocity.x, -velocity.y);
                //碰撞后增加开火几率; Increase the chance of firing after a collision
                if (random.nextInt(10) > 6) {
                    shoot();
                }
                //碰撞后增加改变方向的几率;Increase the chance of changing direction after collision;
                if (random.nextInt(10) > 3) {
                    setMoveDir(Dir.values()[random.nextInt(4)]);
                }

                break;
            }
        }
    }

}
