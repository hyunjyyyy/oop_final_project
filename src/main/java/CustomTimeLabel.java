import javax.swing.*;
import java.awt.*;

public class CustomTimeLabel extends JLabel {
    private int borderRadius = 15;

    public CustomTimeLabel() {
        super();
        setOpaque(false);
        setFont(new Font("Serif", Font.BOLD, 18));
        setHorizontalAlignment(SwingConstants.CENTER);
        setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // 배경색과 테두리 색상 설정
        g2.setColor(new Color(240, 240, 240));
        g2.fillRoundRect(0, 0, width, height, borderRadius, borderRadius);
        g2.setColor(Color.GRAY);
        g2.drawRoundRect(0, 0, width - 1, height - 1, borderRadius, borderRadius);

        g2.dispose();
        super.paintComponent(g);
    }
}
