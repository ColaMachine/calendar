package applet;

import java.applet.Applet;
import java.awt.*;

/**
 * Created by dozen.zhang on 2016/3/11.
 */

    public class TestApplet extends Applet {
        @Override
        public void print(Graphics g) {
            g.drawRect(0, 0, 499, 149);
            g.drawString("Hello World!", 5, 70);
        }
    }

