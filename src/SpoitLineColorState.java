import java.awt.*;

public class SpoitLineColorState implements State {
    StateManager stateManager;
    Robot robot;
    Color lineColor;
    
    public SpoitLineColorState(StateManager stateManager) {
        this.stateManager = stateManager;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void mouseDown(int x, int y) {
        lineColor = robot.getPixelColor(MouseInfo.getPointerInfo().getLocation().x,
                MouseInfo.getPointerInfo().getLocation().y);
        stateManager.canvas.getMediator().setLineColor(lineColor);
    }
    
    @Override
    public void mouseDrag(int x, int y) {
        lineColor = robot.getPixelColor(MouseInfo.getPointerInfo().getLocation().x,
                MouseInfo.getPointerInfo().getLocation().y);
        stateManager.canvas.getMediator().setLineColor(lineColor);
    }
    
    @Override
    public void mouseUp(int x, int y) {
        lineColor = robot.getPixelColor(MouseInfo.getPointerInfo().getLocation().x,
                MouseInfo.getPointerInfo().getLocation().y);
        stateManager.canvas.getMediator().setLineColor(lineColor);
        stateManager.setState(new SelectState(stateManager), State.SELECT_STATE);
    }
    
}