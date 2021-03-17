import java.awt.*;

public class MyDashStroke extends BasicStroke {
    
    private static final float[] pattern = {10, 15};
    
    public MyDashStroke(float lineWidth) {
        super(lineWidth, CAP_BUTT, JOIN_BEVEL, 1.0f, pattern, 0);
    }
    
    public MyDashStroke(float lineWidth, float[] dash) {
        super(lineWidth, CAP_BUTT, JOIN_BEVEL, 1.0f, dash, 0);
    }
}