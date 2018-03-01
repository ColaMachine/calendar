package com.dozenx.netty.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by dozen.zhang on 2017/11/14.
 */
public class MainFrame extends Application {
    Stage primaryStage;
    public static BlockingQueue<String> message = new ArrayBlockingQueue<String>(20);

    public static Queue<BufferedImage> images = new ConcurrentLinkedQueue<BufferedImage>();
    public static void main(String[] args) {
        launch(args);
    }

    TabPane root;

    @Override
    public void start(final Stage primaryStage) throws Exception {
        root = new TabPane();
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);
        this.primaryStage = primaryStage;
        ScrollPane sp = new ScrollPane();
        Scene scene = new Scene(sp, 800, 800);
        add(new ImagePanel(primaryStage));
        sp.setContent(root);
        primaryStage.setTitle("FlowPane Layout Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void add(Tab tab){
        root.getTabs().add(tab);
    }
}
