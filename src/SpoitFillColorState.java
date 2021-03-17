import java.awt.*;

public class SpoitFillColorState implements State {
    
    StateManager stateManager;
    Robot robot;
    Color fillColor;
    
    public SpoitFillColorState(StateManager stateManager) {
        this.stateManager = stateManager;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void mouseDown(int x, int y) {
        fillColor = robot.getPixelColor(MouseInfo.getPointerInfo().getLocation().x,
                MouseInfo.getPointerInfo().getLocation().y);
        stateManager.canvas.getMediator().setFillColor(fillColor);
        
    }
    
    @Override
    public void mouseDrag(int x, int y) {
        fillColor = robot.getPixelColor(MouseInfo.getPointerInfo().getLocation().x,
                MouseInfo.getPointerInfo().getLocation().y);
        stateManager.canvas.getMediator().setFillColor(fillColor);
    }
    
    @Override
    public void mouseUp(int x, int y) {
        fillColor = robot.getPixelColor(MouseInfo.getPointerInfo().getLocation().x,
                MouseInfo.getPointerInfo().getLocation().y);
        stateManager.canvas.getMediator().setFillColor(fillColor);
        stateManager.setState(new SelectState(stateManager), State.SELECT_STATE);
    }
    
}