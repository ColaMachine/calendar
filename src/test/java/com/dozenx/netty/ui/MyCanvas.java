package com.dozenx.netty.ui;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by dozen.zhang on 2017/11/14.
 */
public class MyCanvas  extends Canvas {

    public int mouseStartX;
    public int mouseStartY;
    public int mouseEndX;
    public int mouseEndY;
    public int imgheight;
    public int imgwidth;
    Image image;
    private GraphicsContext gc;

    public MyCanvas(double width, double height) {
        super(width, height);


        gc = getGraphicsContext2D();

        // 在用户拖动鼠标时擦除部分内容
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        gc.clearRect(0, 0, 600, 600);
                        gc.drawImage(image, 0, 0, 600, 600);
                        //gc.save();
                        mouseEndX = (int) e.getX();
                        mouseEndY = (int) e.getY();
                        gc.strokeRect(mouseStartX, mouseStartY, mouseEndX - mouseStartX, mouseEndY - mouseStartY);
                        //gc.restore();
                        MainFrame.message.offer(" input swipe "+mouseStartX+" "+mouseStartY+"  "+mouseEndX+" "+mouseEndY);
                    }
                });
        this.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        mouseStartX = (int) e.getX();
                        mouseStartY = (int) e.getY();
                        MainFrame.message.offer(" input tap "+mouseStartX+" "+mouseStartY+"  ");
                     //  MainFrame.message.offer("input keyevent 7");

                    }
                });

    }

    public void drawImage(File file) {
        try {

            image = new Image(new FileInputStream(file));
            imgheight = (int) image.getWidth();
            imgwidth = (int) image.getHeight();
            gc.drawImage(image, 0, 0, 600, 600);
           /* this.setWidth(width);

            this.setHeight(height);
            this.resize(width,height);*/


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }


    public void drawImage(BufferedImage bf) {
        try {

            gc.clearRect(0, 0, this.getWidth(), this.getHeight());

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(bf, "png", out);
            InputStream inputStream = new ByteArrayInputStream(out.toByteArray());

            image = new Image(inputStream);
            imgheight = (int) image.getWidth();
            imgwidth = (int) image.getHeight();
            gc.drawImage(image, 0, 0, 600, 600);
            /*this.setWidth(width);

            this.setHeight(height);
            this.resize(width,height);*/


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
