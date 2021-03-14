import java.awt.*;

public class MyTriangle extends MyDrawing {
    /*
     * コンストラクタ
     */
    
    public MyTriangle(int xpt, int ypt) {
        super(xpt, ypt);
    }
    
    public MyTriangle(int xpt, int ypt, int width, int height) {
        super(xpt, ypt, width, height);
    }
    
    public MyTriangle(int xpt, int ypt, Color lineColor, Color fillColor) {
        super(xpt, ypt, lineColor, fillColor);
    }
    
    public MyTriangle(int xpt, int ypt, int width, int height, Color lineColor, Color fillColor) {
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
        
        int xPoints[] = { x, x + w, x + (int) (w / 2.0) };
        int yPoints[] = { y + h, y + h, y };
        
        int xPoints_shadow[] = { x + 5, x + w + 5, x + (int) (w / 2.0) + 5 };
        int yPoints_shadow[] = { y + h + 5, y + h + 5, y + 5 };
        
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
            g2.fillPolygon(xPoints_shadow, yPoints_shadow, 3);
        }
        
        g2.setColor(getFillColor());
        g2.fillPolygon(xPoints, yPoints, 3);
        g2.setColor(getLineColor());
        g2.drawPolygon(xPoints, yPoints, 3);
        
        super.draw(g);
    }
}