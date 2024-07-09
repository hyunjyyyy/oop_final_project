import javax.swing.*;
import java.awt.*;

public class RestaurantPanel extends JPanel {
    public RestaurantPanel(Restaurant restaurant) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(255, 255, 204)); // 배경색 설정
        setPreferredSize(new Dimension(330, 230)); // 패널 크기 고정

        JLabel nameLabel = new JLabel(restaurant.getName());
        JLabel ratingLabel = new JLabel("★★★"); // 별점은 예시로 고정
        JLabel menuLabel = new JLabel(restaurant.getMenuName());
        JLabel hoursLabel = new JLabel("Today's Hours: " + restaurant.getTodayHours());

        nameLabel.setFont(new Font("", Font.BOLD, 25)); // 폰트 설정
        ratingLabel.setFont(new Font("", Font.PLAIN, 20));
        menuLabel.setFont(new Font("", Font.PLAIN, 20));
        hoursLabel.setFont(new Font("", Font.PLAIN, 20));

        add(nameLabel);
        add(ratingLabel);
        add(menuLabel);
        add(hoursLabel);
    }

    public RestaurantPanel(JPanel restaurantPanel) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(255, 255, 204)); // 배경색 설정
        setPreferredSize(new Dimension(330, 230)); // 패널 크기 고정

        for (Component comp : restaurantPanel.getComponents()) {
            if (comp instanceof JLabel) {
                add(new JLabel(((JLabel) comp).getText()));
            }
        }

        JLabel mapLabel = new JLabel(new ImageIcon("path/to/map.png")); // 지도 이미지 경로 설정
        add(mapLabel);
    }
}
