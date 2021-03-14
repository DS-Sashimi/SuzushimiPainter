import java.awt.Color;

public class StateManager {
    MyCanvas canvas;
    State state; // = new SelectState(this); とかにすれば，下の if (state != null) が不要になるのかな．
    int stateNumber;
    // これから描画する図形の属性
    boolean isDashed = false;
    boolean isShadowed = false;
    Color lineColor = Color.black;
    Color fillColor = Color.white;
    int lineWidth = 1;
    
    // コンストラクタ
    public StateManager(MyCanvas canvas) {
        this.canvas = canvas;
    }
    
    public void setState(State state) {
        this.state = state;
    }
    
    public void setState(State state, int stateNumber) {
        this.state = state;
        this.stateNumber = stateNumber;
    }
    
    public void setStateNumber(int stateNumber) {
        this.stateNumber = stateNumber;
    }
    
    public void addDrawing(MyDrawing d) {
        d.setDashed(isDashed);
        d.setShadowed(isShadowed);
        d.setLineColor(lineColor);
        d.setFillColor(fillColor);
        d.setLineWidth(lineWidth);
        canvas.getMediator().addDrawing(d);
        canvas.repaint();
    }
    
    public void mouseDown(int x, int y) {
        if (state != null) {
            state.mouseDown(x, y);
            canvas.repaint();
        }
    }
    
    public void mouseDrag(int x, int y) {
        if (state != null) {
            state.mouseDrag(x, y);
            canvas.repaint(); // 不要？
        }
    }
    
    public void mouseUp(int x, int y) {
        if (state != null) {
            state.mouseUp(x, y);
        }
    }
    
    public void setDashed(boolean b) {
        isDashed = b;
        canvas.getMediator().setDashed(isDashed);
    }
    
    public void setShadowed(boolean b) {
        isShadowed = b;
        canvas.getMediator().setShadowed(isShadowed);
    }
    
    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
        canvas.getMediator().setLineColor(lineColor);
		/*
		if (canvas.getMediator().selectedDrawings.size() > 0) {
			for (MyDrawing d : canvas.getMediator().selectedDrawings) {
				d.setLineColor(lineColor);
			}
		}
		*/
    }
    
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
        canvas.getMediator().setFillColor(fillColor);
    }
    
    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
        canvas.getMediator().setLineWidth(lineWidth);
    }
    
}