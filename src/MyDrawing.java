import java.awt.*;
import java.io.Serializable;

public class MyDrawing implements Cloneable, Serializable {
    private int x, y, w, h; // x 座標，y 座標，幅，高さ
    private Color lineColor, fillColor; // 線の色，塗り色
    private int lineWidth; // 線の太さ
    
    private boolean isDashed = false; // 破線であるかどうか
    private boolean isShadowed = false; // 影付きであるかどうか
    
    private boolean isSelected; // 選択されているかどうか
    private Shape region; // 包含判定用の範囲
    private final int SIZE = 7; // 選択表示矩形に付く四角形の大きさ
    final float[] dash = { 3, 3 }; // 選択図形の枠の破線のパターン
    
    /*
     * コンストラクタ
     */
    
    public MyDrawing() {
        x = y = 0;
        w = h = 40;
        lineColor = Color.black;
        fillColor = Color.white;
        setLineWidth(1);
    }
    
    public MyDrawing(int xpt, int ypt) {
        this();
        setLocation(xpt, ypt);
    }
    
    public MyDrawing(int xpt, int ypt, int width, int height) {
        this(xpt, ypt);
        setSize(width, height);
    }
    
    public MyDrawing(int xpt, int ypt, Color lineColor, Color fillColor) {
        this(xpt, ypt);
        setLineColor(lineColor);
        setFillColor(fillColor);
    }
    
    public MyDrawing(int xpt, int ypt, int width, int height, Color lineColor, Color fillColor) {
        this(xpt, ypt, width, height);
        setLineColor(lineColor);
        setFillColor(fillColor);
    }
    
    /*
     * 共通する部分の描画
     */
    
    public void draw(Graphics g) {
        // 選択状態を表す四角形を描く
        if (isSelected) {
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
    
    /*
     * 各種メソッド
     */
    
    // オブジェクトを移動する
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
        setRegion();
    }
    
    // オブジェクトの位置を変更する
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
        setRegion();
    }
    
    // オブジェクトの大きさを変更する
    public void setSize(int w, int h) {
        this.w = w;
        this.h = h;
        setRegion();
    }
    
    // 包含判定用のメソッド
    public boolean contains(int x, int y) {
        return region.contains(x, y);
    }
    
    // clone メソッドのオーバーライド
    @Override
    public MyDrawing clone() {
        MyDrawing b = null;
        try {
            b = (MyDrawing) super.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }
    
    /*
     * ゲッター・セッター
     */
    
    public int getX() {
        return x;
    }
    
    // public void setX(int x) { this.x = x; }
    
    public int getY() {
        return y;
    }
    
    // public void setY(int y) { this.y = y; }
    
    public int getW() {
        return w;
    }
    
    // public void setW(int w) { this.w = w; }
    
    public int getH() {
        return h;
    }
    
    // public void setH(int h) { this.h = h; }
    
    public Color getLineColor() {
        return lineColor;
    }
    
    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }
    
    public Color getFillColor() {
        return fillColor;
    }
    
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }
    
    public int getLineWidth() {
        return lineWidth;
    }
    
    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }
    
    public boolean getDashed() {
        return isDashed;
    }
    
    public void setDashed(boolean b) {
        isDashed = b;
    }
    
    public boolean getShadowed() {
        return isShadowed;
    }
    
    public void setShadowed(boolean b) {
        isShadowed = b;
    }
    
    public boolean getSelected() {
        return isSelected;
    }
    
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
    
    public Shape getRegion() {
        return region;
    }
    
    public void setRegion() {
        int regionX = getX();
        int regionY = getY();
        int regionW = getW();
        int regionH = getH();
        
        // 高さや横幅が符のときのための処理
        if (regionW < 0) {
            regionX += regionW;
            regionW *= -1;
        }
        if (regionH < 0) {
            regionY += regionH;
            regionH *= -1;
        }
        
        region = new Rectangle(regionX, regionY, regionW, regionH);
    }
    
    public int getSIZE() {
        return SIZE;
    }
    
}