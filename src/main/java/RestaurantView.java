import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;


public class RestaurantView {
    private JFrame jf; // 메인 프레임
    private JTabbedPane tabpane; // 탭 패널
    private JTextField searchField; // 검색 필드
    private JButton searchButton; // 검색 버튼
    private CustomTimeLabel timeLabel; // 현재 시간 레이블

    private CategoryPanel koreanCategoryPanel;
    private CategoryPanel chineseCategoryPanel;
    private CategoryPanel japaneseCategoryPanel;
    private CategoryPanel snackCategoryPanel;
    private CategoryPanel cafeCategoryPanel;
    private CategoryPanel otherCategoryPanel;

    public RestaurantView(String title) {
        jf = new JFrame(title); // 프레임 타이틀 설정
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프레임 종료 설정

        tabpane = new JTabbedPane(); // 탭 패널 초기화

        searchField = new JTextField(20); // 검색 필드 초기화

        // 검색 아이콘 로드 및 크기 조정
        ImageIcon searchIcon = new ImageIcon("C:\\Users\\SAMSUNG\\eclipse-workspace\\Java\\src\\image\\search2.png");
        Image searchImage = searchIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // 원하는 크기로 변경
        ImageIcon resizedSearchIcon = new ImageIcon(searchImage);
        searchButton = new JButton(resizedSearchIcon); // 크기 조정된 아이콘 설정

        searchButton.addActionListener(e -> {
            String query = searchField.getText();
            // 검색 로직 구현
        });

        JPanel searchPanel = new JPanel(new BorderLayout());

        JPanel leftSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftSearchPanel.add(searchField);
        leftSearchPanel.add(searchButton);

        // 현재 시간 표시 레이블 설정
        timeLabel = new CustomTimeLabel();
        updateTime();
        JPanel rightTimePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightTimePanel.add(timeLabel);

        searchPanel.add(leftSearchPanel, BorderLayout.WEST);
        searchPanel.add(rightTimePanel, BorderLayout.EAST);

        // 타이머 설정: 1초마다 현재 시간 갱신
        Timer timer = new Timer(1000, e -> updateTime());
        timer.start();

        // 각 카테고리 패널 생성 및 탭 추가
        koreanCategoryPanel = new CategoryPanel("한식");
        chineseCategoryPanel = new CategoryPanel("중식");
        japaneseCategoryPanel = new CategoryPanel("일식");
        snackCategoryPanel = new CategoryPanel("분식");
        cafeCategoryPanel = new CategoryPanel("빵/카페");
        otherCategoryPanel = new CategoryPanel("기타");

        tabpane.addTab("한식", koreanCategoryPanel.getPanel());
        tabpane.addTab("중식", chineseCategoryPanel.getPanel());
        tabpane.addTab("일식", japaneseCategoryPanel.getPanel());
        tabpane.addTab("분식", snackCategoryPanel.getPanel());
        tabpane.addTab("빵/카페", cafeCategoryPanel.getPanel());
        tabpane.addTab("기타", otherCategoryPanel.getPanel());

        jf.getContentPane().setLayout(new BorderLayout());
        jf.getContentPane().add(searchPanel, BorderLayout.NORTH); // 검색 패널을 프레임 상단에 추가
        jf.getContentPane().add(tabpane, BorderLayout.CENTER); // 탭 패널을 프레임 중앙에 추가

        jf.setSize(800, 600); // 프레임 크기 설정
        jf.setVisible(true); // 프레임 보이도록 설정
    }

    public void loadData(RestaurantController controller) {
        List<Restaurant> allRestaurants = controller.loadRestaurantsFromDatabase();

        // 장르에 따라 패널에 식당 추가
        for (Restaurant restaurant : allRestaurants) {
            switch (restaurant.getGenre()) {
                case "한식":
                    koreanCategoryPanel.addRestaurant(restaurant);
                    break;
                case "중식":
                    chineseCategoryPanel.addRestaurant(restaurant);
                    break;
                case "일식":
                    japaneseCategoryPanel.addRestaurant(restaurant);
                    break;
                case "분식":
                    snackCategoryPanel.addRestaurant(restaurant);
                    break;
                case "빵/카페":
                    cafeCategoryPanel.addRestaurant(restaurant);
                    break;
                case "기타":
                    otherCategoryPanel.addRestaurant(restaurant);
                    break;
            }
        }
    }

    // 현재 시간 갱신 메서드
    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(new Date());
        timeLabel.setText(currentTime);
    }

    public void setVisible(boolean visible) {
        jf.setVisible(visible);
    }
}
