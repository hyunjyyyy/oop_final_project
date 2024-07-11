import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class RestaurantPanel extends JPanel {
    private String restaurantName;

    public RestaurantPanel(Restaurant restaurant) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(240, 248, 255)); // 배경색 설정
        setPreferredSize(new Dimension(400, 300)); // 패널 크기 고정
        
        this.restaurantName = restaurant.getName();

        JLabel nameLabel = new JLabel(restaurant.getName());
        JLabel ratingLabel = new JLabel(restaurant.getAverageRating());
        JLabel menuLabel = new JLabel(restaurant.getMenuName());
        JLabel hoursLabel = new JLabel("영업시간 : " + restaurant.getTodayHours());

        nameLabel.setFont(new Font("", Font.BOLD, 25)); // 폰트 설정
        ratingLabel.setFont(new Font("", Font.PLAIN, 20));
        menuLabel.setFont(new Font("", Font.PLAIN, 20));
        hoursLabel.setFont(new Font("", Font.PLAIN, 20));

        add(nameLabel);
        add(ratingLabel);
        add(menuLabel);
        add(hoursLabel);

        addMapImage(restaurant);
    }

    private void addMapImage(Restaurant restaurant) {
        // 이미지 경로 설정
        String imagePath = "C:/oop24/final_project24/oop_finalproject/src/main/java/maps/" + this.restaurantName + ".jpeg";
        
        // 이미지 레이블 생성 및 추가
        JLabel mapLabel = new JLabel(new ImageIcon(imagePath));
        mapLabel.addMouseListener(new MouseAdapter() {
        	//@override
        	public void mouseClicked(MouseEvent e) {
        		openWebpage(restaurant.getURL());
        	}
        });
        add(mapLabel);
    }
    private void addMapImage() {
        // 이미지 경로 설정
        String imagePath = "C:/oop24/final_project24/oop_finalproject/src/main/java/maps/" + this.restaurantName + ".jpeg";
        
        // 이미지 레이블 생성 및 추가
        JLabel mapLabel = new JLabel(new ImageIcon(imagePath));
        add(mapLabel);
    }
    
    public static void openWebpage(String url) {
    	try {
    		URI uri = new URI(url);
    		Desktop desktop = Desktop.getDesktop();
    		if (desktop.isSupported(Desktop.Action.BROWSE)) {
    			desktop.browse(uri);
    		}
    		else {
    			System.err.println("브라우저 열기 기능이 지원되지 않습니다.");
    		}
    	} catch (IOException | URISyntaxException e) {
    			e.printStackTrace();
    		}
    }

    public RestaurantPanel(JPanel restaurantPanel) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(240, 248, 255)); // 배경색 설정
        setPreferredSize(new Dimension(330, 230)); // 패널 크기 고정

        // restaurantPanel에서 첫 번째 JLabel의 텍스트를 가져와서 식당 이름으로 설정
        Component[] components = restaurantPanel.getComponents();
        if (components.length > 0 && components[0] instanceof JLabel) {
            this.restaurantName = ((JLabel) components[0]).getText();
        }

        for (Component comp : components) {
            if (comp instanceof JLabel) {
                add(new JLabel(((JLabel) comp).getText()));
            }
        }
        addMapImage();
    }
}
