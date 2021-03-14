import java.awt.*;

public class MyOctagonal extends MyDrawing {
    
    /*
     * コンストラクタ
     */
    
    public MyOctagonal(int xpt, int ypt) {
        super(xpt, ypt);
    }
    
    public MyOctagonal(int xpt, int ypt, int width, int height) {
        super(xpt, ypt, width, height);
    }
    
    public MyOctagonal(int xpt, int ypt, Color lineColor, Color fillColor) {
        super(xpt, ypt, lineColor, fillColor);
    }
    
    public MyOctagonal(int xpt, int ypt, int width, int height, Color lineColor, Color fillColor) {
        super(xpt, ypt, width, height, lineColor, fillColor);
    }
    
    /*
     * 描画のためのメソッド
     */
    
    @Override
    public void draw(Graphics g) {
        int x = getX();
        int y = getY();
        int w = getW();
        int h = getH();
        
        if (w != 0 && h != 0) {
            if (Math.abs(w) > Math.abs(h)) {
                w = (w / Math.abs(w)) * Math.abs(h);
            } else {
                h = (h / Math.abs(h)) * Math.abs(w);
            }
        } else {
            return;
        }
        
        // 高さや横幅が負のときのための処理
        if (w < 0) {
            x += w;
            w *= -1;
        }
        if (h < 0) {
            y += h;
            h *= -1;
        }
        
        setSize(w, h);
        
        int[] x_array = new int[9];
        int[] y_array = new int[9];
        int[] x_array_shadow = new int[9];
        int[] y_array_shadow = new int[9];
        
        double r = (double) w / 2.0;
        
        for (int i = 0; i <= 8; i++) {
            double cosine = Math.cos((double) i * 2.0 * Math.PI / 8.0);
            double sine = Math.sin((double) i * 2.0 * Math.PI / 8.0);
            x_array[i] = x + (int) (r + (r * cosine));
            x_array_shadow[i] = x_array[i] + 5;
            y_array[i] = y + (int) (r + (r * sine));
            y_array_shadow[i] = y_array[i] + 5;
        }
        
        Graphics2D g2 = (Graphics2D) g;
        
        // 線種の変更
        if (getDashed()) {
            g2.setStroke(new MyDashStroke(getLineWidth()));
        } else {
            g2.setStroke(new BasicStroke(getLineWidth()));
        }
        
        // 影をつける
        if (getShadowed()) {
            g2.setColor(Color.black);
            g2.fillPolygon(x_array_shadow, y_array_shadow, 8);
        }
        
        g2.setColor(getFillColor());
        g2.fillPolygon(x_array, y_array, 8);
        g2.setColor(getLineColor());
        g2.drawPolygon(x_array, y_array, 8);
        
        // super.draw(g); とは違う実装をする必要があったので，ここに直接書きました．
        // 選択状態を表す四角形を描く
        if (getSelected()) {
            int SIZE = getSIZE();
            if (w != 0 && h != 0) {
                if (Math.abs(w) > Math.abs(h)) {
                    w = (w / Math.abs(w)) * Math.abs(h);
                } else {
                    h = (h / Math.abs(h)) * Math.abs(w);
                }
            }
            System.out.println(x + ", " + y + ", " + w + ", " + h);
            g.setColor(Color.white);
            g.fillRect(x + w / 2 - SIZE / 2, y - SIZE / 2, SIZE, SIZE);
            g.fillRect(x - SIZE / 2, y + h / 2 - SIZE / 2, SIZE, SIZE);
            g.fillRect(x + w / 2 - SIZE / 2, y + h - SIZE / 2, SIZE, SIZE);
            g.fillRect(x + w - SIZE / 2, y + h / 2 - SIZE / 2, SIZE, SIZE);
            g.fillRect(x - SIZE / 2, y - SIZE / 2, SIZE, SIZE);
            g.fillRect(x + w - SIZE / 2, y - SIZE / 2, SIZE, SIZE);
            g.fillRect(x - SIZE / 2, y + h - SIZE / 2, SIZE, SIZE);
            g.fillRect(x + w - SIZE / 2, y + h - SIZE / 2, SIZE, SIZE);
            g.setColor(Color.black);
            g.drawRect(x + w / 2 - SIZE / 2, y - SIZE / 2, SIZE, SIZE);
            g.drawRect(x - SIZE / 2, y + h / 2 - SIZE / 2, SIZE, SIZE);
            g.drawRect(x + w / 2 - SIZE / 2, y + h - SIZE / 2, SIZE, SIZE);
            g.drawRect(x + w - SIZE / 2, y + h / 2 - SIZE / 2, SIZE, SIZE);
            g.drawRect(x - SIZE / 2, y - SIZE / 2, SIZE, SIZE);
            g.drawRect(x + w - SIZE / 2, y - SIZE / 2, SIZE, SIZE);
            g.drawRect(x - SIZE / 2, y + h - SIZE / 2, SIZE, SIZE);
            g.drawRect(x + w - SIZE / 2, y + h - SIZE / 2, SIZE, SIZE);
        }
    }
    
}