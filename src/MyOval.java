import java.awt.*;

public class MyOval extends MyDrawing {
    
    /*
     * コンストラクタ
     */
    
    public MyOval(int xpt, int ypt) {
        super(xpt, ypt);
    }
    
    public MyOval(int xpt, int ypt, int width, int height) {
        super(xpt, ypt, width, height);
    }
    
    public MyOval(int xpt, int ypt, Color lineColor, Color fillColor) {
        super(xpt, ypt, lineColor, fillColor);
    }
    
    public MyOval(int xpt, int ypt, int width, int height, Color lineColor, Color fillColor) {
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
        
        // 高さや横幅が負のときのための処理
        if (w < 0) {
            x += w;
            w *= -1;
        }
        if (h < 0) {
            y += h;
            h *= -1;
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
            g2.fillOval(x + 5, y + 5, w, h);
        }
        
        g2.setColor(getFillColor());
        g2.fillOval(x, y, w, h);
        g2.setColor(getLineColor());
        g2.drawOval(x, y, w, h);
        
        super.draw(g);
    }
}