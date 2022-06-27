package com.iohao.game.tank.ui;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.view.KeyView;
import com.almasb.fxgl.texture.Texture;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.getUIFactoryService;
import static javafx.scene.input.KeyCode.*;

/**
 * @author 渔民小镇
 * @date 2022-03-06
 */
public class GameMenu extends FXGLMenu {

    private final TranslateTransition tt;

    public GameMenu() {
        super(MenuType.MAIN_MENU);
        Texture texture = texture("logo.png");
        texture.setLayoutX(72);
        texture.setLayoutY(160);
        VBox menuBox = new VBox(
                5,
                new LabelButton("New Game", Color.LIGHTGREEN, this::fireNewGame),
                new LabelButton("Help", Color.LIGHTGREEN, this::instructions),
                new LabelButton("Exit", Color.DARKRED, this::fireExit)
        );
        menuBox.setAlignment(Pos.TOP_CENTER);
        menuBox.setLayoutX(180);
        menuBox.setLayoutY(390);
        menuBox.setVisible(false);

        Texture tankTexture = FXGL.texture("tank/H1U.png");
        tankTexture.setRotate(90);

        tt = new TranslateTransition(Duration.seconds(2), tankTexture);
        tt.setInterpolator(Interpolators.ELASTIC.EASE_OUT());
        tt.setFromX(100);
        tt.setFromY(252);
        tt.setToX(302);
        tt.setToY(252);
        tt.setOnFinished(e->menuBox.setVisible(true));


        Text tip = getUIFactoryService().newText("Powered by FXGL game engine", Color.web("#BC4E40"), 22);
        tip.setLayoutX(136);
        tip.setLayoutY(590);
        //Background is black
        Rectangle bgRect = new Rectangle(getAppWidth(), getAppHeight());
        getContentRoot().getChildren().addAll(bgRect, texture, tankTexture, menuBox, tip);

    }

    @Override
    public void onCreate() {
        FXGL.play("start.wav");
        tt.play();
    }


    private static class LabelButton extends Label {
        LabelButton(String name, Color hoverColor, Runnable action) {
            setText(name);
            setTextFill(Color.WHITE);
            setFont(FXGL.getAssetLoader().loadFont("airstrikeacad.ttf").newFont(50));
            textFillProperty().bind(
                    Bindings.when(hoverProperty())
                            .then(hoverColor)
                            .otherwise(Color.WHITE)
            );
            setOnMouseClicked(e -> {
                FXGL.play("select.wav");
                action.run();
            });
            setOnMouseEntered(e->FXGL.play("mainMenuHover.wav"));
        }
    }

    private void instructions() {
        GridPane pane = new GridPane();
        pane.setHgap(20);
        pane.setVgap(15);
        pane.addRow(0, getUIFactoryService().newText("Movement"), new HBox(4, new KeyView(W), new KeyView(S), new KeyView(A), new KeyView(D)));
        pane.addRow(1, new Region(), getUIFactoryService().newText("Up,Down,Left,Right"));
        pane.addRow(2, getUIFactoryService().newText("Shoot"), new KeyView(SPACE));
        pane.addRow(3, new Region(), getUIFactoryService().newText("Space"));
        getDialogService().showBox("Help", pane, getUIFactoryService().newButton("OK"));
    }

}
