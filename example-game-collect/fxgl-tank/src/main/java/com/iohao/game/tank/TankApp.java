package com.iohao.game.tank;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.time.TimerAction;
import com.iohao.game.tank.component.PlayerViewComponent;
import com.iohao.game.tank.net.FxlgTankConfig;
import com.iohao.game.tank.net.TankWebsocketClient;
import com.iohao.game.tank.ui.GameLoadingScene;
import com.iohao.game.tank.ui.GameMenu;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author 渔民小镇
 * @date 2022-03-06
 */
@Slf4j
public class TankApp extends GameApplication {

    private int hasGeneratedEnemy;
    private Entity player;
    private PlayerViewComponent playerView;
    private final SecureRandom random = new SecureRandom();
    private final int[] spawnX = {30, 295 + 30, 589 + 20, 130 + 30, 424 + 30};


    /**
     * 定时刷新敌军坦克
     */
    private TimerAction spawnEnemyTimerAction;


    public static void main(String[] args) {
        // TODO: 2022/3/6 注意，这个游戏示例需要先启动 GameOne.java (游戏服务器)
        if (args != null && args.length > 0) {
            FxlgTankConfig.externalPort += 1;
            log.info("args: {} - {}", args.length, Arrays.toString(args));
        }

        log.info("externalPort:{}", FxlgTankConfig.externalPort);
        // 启动游戏
        launch(args);


    }

    @Override
    protected void onUpdate(double tpf) {

    }

    @Override
    protected void initSettings(GameSettings settings) {
        //26cell * 24px
        settings.setWidth(28 * 24);
        settings.setHeight(28 * 24);
        settings.setFontUI("airstrikeacad.ttf");
        settings.setTitle("90 Tank");
        settings.setAppIcon("icon.png");
        settings.setVersion("Version 0.2");
        settings.setMainMenuEnabled(true);
        settings.setGameMenuEnabled(true);

        settings.setSceneFactory(new SceneFactory() {
            @Override
            public FXGLMenu newMainMenu() {
                return new GameMenu();
            }

            @Override
            public LoadingScene newLoadingScene() {
                return new GameLoadingScene();
            }
        });

        //开发模式.这样可以输出较多的日志异常追踪
        settings.setApplicationMode(ApplicationMode.DEVELOPER);

        try {
            TankWebsocketClient.me().start();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("level", 1);
        vars.put("playerBulletLevel", 0);
        vars.put("armedHelmet", true);
        vars.put("hasSpade", false);
        vars.put("destroyedEnemy", 0);
    }

    @Override
    protected void initInput() {
        Input input = FXGL.getInput();
        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                playerView.up();
            }
        }, KeyCode.W);
        input.addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                playerView.down();
            }
        }, KeyCode.S);
        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                playerView.left();
            }
        }, KeyCode.A);
        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                playerView.right();
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Shoot") {
            @Override
            protected void onAction() {
                playerView.shoot();
            }
        }, KeyCode.SPACE);

        input.addAction(new UserAction("Shoot N") {
            @Override
            protected void onAction() {
                playerView.shoot(1000);
            }
        }, KeyCode.N);

        input.addAction(new UserAction("Shoot O") {
            @Override
            protected void onAction() {
                playerView.testShoot(1);
            }
        }, KeyCode.O);
    }

    @Override
    protected void initGame() {

        FXGL.getGameScene().setBackgroundColor(Color.BLACK);
        FXGL.getGameWorld().addEntityFactory(new GameEntityFactory());
        buildAndStartLevel();
    }

    private void buildAndStartLevel() {
        //1. 清理上一个关卡的残留(这里主要是清理声音残留)
        clearEntitiesByTpe(GameType.BULLET, GameType.ENEMY, GameType.PLAYER);

        //2. 开场动画
        Rectangle rect1 = new Rectangle(FXGL.getAppWidth(), FXGL.getAppHeight() / 2.0, Config.BG_GARY);
        Rectangle rect2 = new Rectangle(FXGL.getAppWidth(), FXGL.getAppHeight() / 2.0, Config.BG_GARY);
        rect2.setLayoutY(FXGL.getAppHeight() / 2.0);
        Text text = new Text("STAGE " + FXGL.geti("level"));
        text.setFont(new Font(35));
        text.setLayoutX(FXGL.getAppWidth() / 2.0 - 80);
        text.setLayoutY(FXGL.getAppHeight() / 2.0 - 5);
        Pane p1 = new Pane(rect1, rect2, text);

        FXGL.addUINode(p1);

        Timeline tl = new Timeline(
                new KeyFrame(Duration.seconds(1.2),
                        new KeyValue(rect1.translateYProperty(), -FXGL.getAppHeight() / 2.0),
                        new KeyValue(rect2.translateYProperty(), FXGL.getAppHeight() / 2.0)
                ));
        tl.setOnFinished(e -> FXGL.removeUINode(p1));

        PauseTransition pt = new PauseTransition(Duration.seconds(1.5));
        pt.setOnFinished(e -> {
            text.setVisible(false);
            tl.play();
            //3. 开始新关卡
            startLevel();
        });
        pt.play();
    }

    private void clearEntitiesByTpe(GameType... types) {
        List<Entity> entities = FXGL.getGameWorld().getEntitiesByType(types);
        for (Entity entity : entities) {
            entity.removeFromWorld();
        }
    }

    private void startLevel() {
        if (spawnEnemyTimerAction != null) {
            spawnEnemyTimerAction.expire();
            spawnEnemyTimerAction = null;
        }
        FXGL.set("gameOver", false);
        //每局开始都有无敌保护时间
        FXGL.set("armedHelmet", true);
        //清除上一关残留的道具影响
        FXGL.set("freezingEnemy", false);
        //恢复消灭敌军数量
        FXGL.set("destroyedEnemy", 0);

        hasGeneratedEnemy = 0;

        FXGL.play("start.wav");
        player = null;
        player = FXGL.spawn("player", 9 * 24, 25 * 24);
        playerView = player.getComponent(PlayerViewComponent.class);

        //首先产生几个
//        for (int i = 0; i < spawnX.length; i++) {
//            FXGL.spawn("enemy",
//                    new SpawnData(spawnX[i], 30).put("assentName", "tank/E" + FXGLMath.random(1, 12) + "U.png"));
//            hasGeneratedEnemy++;
//        }
//        spawnEnemy();
    }

    private void spawnEnemy() {
        if (spawnEnemyTimerAction != null) {
            spawnEnemyTimerAction.expire();
            spawnEnemyTimerAction = null;
        }
        Entity rectEntity = FXGL.spawn("empty", new SpawnData(-100, -100));
        spawnEnemyTimerAction = FXGL.run(() -> {
            //尝试次数
            int testTimes = random.nextInt(2) + 2;
            for (int i = 0; i < testTimes; i++) {
                if (hasGeneratedEnemy < Config.MAX_ENEMY_NUM) {
                    boolean canGenerate = true;
                    int x = spawnX[random.nextInt(3)];
                    int y = 30;
                    rectEntity.setPosition(x, y);
                    List<Entity> tankList = FXGL.getGameWorld().getEntitiesByType(GameType.ENEMY, GameType.PLAYER);
                    BoundingBoxComponent emptyBox = rectEntity.getBoundingBoxComponent();
                    for (Entity tank : tankList) {
                        if (tank.isActive() && emptyBox.isCollidingWith(tank.getBoundingBoxComponent())) {
                            canGenerate = false;
                            break;
                        }
                    }
                    if (canGenerate) {
                        hasGeneratedEnemy++;
                        FXGL.spawn("enemy",
                                new SpawnData(x, y).put("assentName", "tank/E" + FXGLMath.random(1, 12) + "U.png"));
                    }
                    rectEntity.setPosition(-100, -100);

                } else {
                    if (spawnEnemyTimerAction != null) {
                        spawnEnemyTimerAction.expire();
                    }
                }
            }
        }, Config.GENERATE_ENEMY_TIME);
    }

    @Override
    protected void initPhysics() {
        bulletHitEnemy();
        bulletHitPlayer();
        bulletHitBullet();
    }

    private void bulletHitBullet() {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.BULLET, GameType.BULLET) {
            @Override
            protected void onCollisionBegin(Entity b1, Entity b2) {
                Entity owner1 = b1.getObject("owner");
                Serializable type1 = owner1.getType();

                Entity owner2 = b2.getObject("owner");
                Serializable type2 = owner2.getType();
                if (type1 != type2) {
                    b1.removeFromWorld();
                    b2.removeFromWorld();
                }
            }
        });
    }

    private void bulletHitPlayer() {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.BULLET, GameType.PLAYER) {
            @Override
            protected void onCollisionBegin(Entity bullet, Entity player) {
                FXGL.play("normalBomb.wav");
                FXGL.spawn("bomb", bullet.getCenter().getX() - 25, bullet.getCenter().getY() - 20);
                bullet.removeFromWorld();
            }
        });
    }

    private void bulletHitEnemy() {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.BULLET, GameType.ENEMY) {
            @Override
            protected void onCollisionBegin(Entity bullet, Entity enemy) {
                FXGL.play("normalBomb.wav");
                FXGL.spawn("bomb", enemy.getCenter().getX() - 25, enemy.getCenter().getY() - 20);
                bullet.removeFromWorld();
            }
        });
    }


}
