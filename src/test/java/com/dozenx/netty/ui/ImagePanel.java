package com.dozenx.netty.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by dozen.zhang on 2017/11/14.
 */
public class ImagePanel extends Tab {
    private GraphicsContext gc;
    final MyCanvas canvas = new MyCanvas(600, 600);

    public ImagePanel(final Stage primaryStage) {
        GridPane selectGrid = new GridPane();

        this.setContent(selectGrid);
        this.setText("图片资源");

        selectGrid.setVgap(5);
        selectGrid.setPadding(new Insets(5, 5, 5, 5));

        final MyCanvas canvas = new MyCanvas(600, 600);

       /* canvas.widthProperty().bind(root.widthProperty().subtract(20));
        canvas.heightProperty().bind(root.heightProperty().subtract(20));*/

        gc = canvas.getGraphicsContext2D();
        selectGrid.add(canvas, 0, 0, 2, 1);

        Button refreshBtn =new Button("刷新");
        selectGrid.add(refreshBtn, 0, 1, 2, 1);


        refreshBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                MainFrame.message.offer("screenshot");
              if(MainFrame.images.peek()!=null){
                  BufferedImage bufferedImage =MainFrame. images.poll();
                  canvas.drawImage(bufferedImage);
              }
            }
        });
    }
}
