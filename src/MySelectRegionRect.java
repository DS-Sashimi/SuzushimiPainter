import java.awt.*;

public class MySelectRegionRect extends MyDrawing {
    
    BasicStroke selectLineStroke = new BasicStroke(1);
    Color selectLineColor = new Color(30, 114, 255, 200);
    Color selectFillColor = new Color(30, 114, 255, 60);
    
    // コンストラクタ
    public MySelectRegionRect(int xpt, int ypt, int width, int height) {
        super(xpt, ypt, width, height);
    }
    
    // 描画
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
        g2.setStroke(selectLineStroke);
        g2.setColor(selectFillColor);
        g2.fillRect(x, y, w, h);
        g2.setColor(selectLineColor);
        g2.drawRect(x, y, w, h);
    }
    
}