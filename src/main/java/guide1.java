import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ArrayList;

public class guide1 {
    JFrame jf; // 메인 프레임
    JTabbedPane tabpane; // 탭 패널
    JTextField searchField; // 검색 필드
    JButton searchButton; // 검색 버튼
    CustomTimeLabel timeLabel; // 현재 시간 레이블

    DefaultListModel<JPanel> koreanListModel; // 한식 리스트 모델
    DefaultListModel<JPanel> chineseListModel; // 중식 리스트 모델

    JPanel koreanPanelContainer; // 한식 패널 컨테이너
    JPanel chinesePanelContainer; // 중식 패널 컨테이너

    JScrollPane koreanScrollPane; // 한식 스크롤 패널
    JScrollPane chineseScrollPane; // 중식 스크롤 패널

    List<Restaurant> koreanRestaurants; // 한식 식당 리스트
    List<Restaurant> chineseRestaurants; // 중식 식당 리스트

    public guide1(String title) {
        jf = new JFrame(title); // 프레임 타이틀 설정
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프레임 종료 설정

        tabpane = new JTabbedPane(); // 탭 패널 초기화

        searchField = new JTextField(20); // 검색 필드 초기화

        // 검색 아이콘 로드 및 크기 조정
        ImageIcon searchIcon = new ImageIcon("./photo/search2.png");
        Image searchImage = searchIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // 원하는 크기로 변경
        ImageIcon resizedSearchIcon = new ImageIcon(searchImage);
        searchButton = new JButton(resizedSearchIcon); // 크기 조정된 아이콘 설정

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText();
                // 검색 로직 구현
            }
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
        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
        timer.start();

        JPanel koreanPanel = createCategoryPanel(); // 한식 패널 생성
        JPanel chinesePanel = createCategoryPanel(); // 중식 패널 생성
        JPanel westernPanel = createCategoryPanel(); // 양식 패널 생성
        JPanel snackPanel = createCategoryPanel(); // 분식 패널 생성
        JPanel japanesePanel = createCategoryPanel(); // 일식 패널 생성
        JPanel breadPanel = createCategoryPanel(); // 빵 패널 생성
        JPanel cafePanel = createCategoryPanel(); // 카페 패널 생성
        JPanel otherPanel = createCategoryPanel(); // 기타 패널 생성

        tabpane.addTab("한식", koreanPanel); // 한식 탭 추가
        tabpane.addTab("중식", chinesePanel); // 중식 탭 추가
        tabpane.addTab("양식", westernPanel); // 양식 탭 추가
        tabpane.addTab("분식", snackPanel); // 분식 탭 추가
        tabpane.addTab("일식", japanesePanel); // 일식 탭 추가
        tabpane.addTab("빵", breadPanel); // 빵 탭 추가
        tabpane.addTab("카페", cafePanel); // 카페 탭 추가
        tabpane.addTab("기타", otherPanel); // 기타 탭 추가

        koreanListModel = new DefaultListModel<>(); // 한식 리스트 모델 초기화
        chineseListModel = new DefaultListModel<>(); // 중식 리스트 모델 초기화

        koreanPanelContainer = new JPanel(new GridBagLayout()); // GridBagLayout 사용
        koreanScrollPane = new JScrollPane(koreanPanelContainer);
        koreanScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // 스크롤바 항상 표시

        koreanPanel.setLayout(new BorderLayout());
        koreanPanel.add(koreanScrollPane, BorderLayout.CENTER);

        chinesePanelContainer = new JPanel(new GridBagLayout()); // GridBagLayout 사용
        chineseScrollPane = new JScrollPane(chinesePanelContainer);
        chineseScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // 스크롤바 항상 표시

        chinesePanel.setLayout(new BorderLayout());
        chinesePanel.add(chineseScrollPane, BorderLayout.CENTER);

        jf.getContentPane().setLayout(new BorderLayout());
        jf.getContentPane().add(searchPanel, BorderLayout.NORTH); // 검색 패널을 프레임 상단에 추가
        jf.getContentPane().add(tabpane, BorderLayout.CENTER); // 탭 패널을 프레임 중앙에 추가

        jf.setSize(800, 600); // 프레임 크기 설정
        jf.setVisible(true); // 프레임 보이도록 설정

        koreanRestaurants = new ArrayList<>();
        chineseRestaurants = new ArrayList<>();

        // 샘플 데이터 추가
        addRestaurant(new Restaurant("식당이름1", "한식", "서울시", new String[]{"09:00-22:00", "09:00-22:00", "09:00-22:00", "09:00-22:00", "09:00-22:00", "09:00-22:00", "09:00-22:00"}, "14:00-15:00", "02-1234-5678", "대표메뉴1", 10000, new int[]{4, 5, 3}), koreanPanelContainer, koreanListModel, koreanRestaurants);
        addRestaurant(new Restaurant("식당이름2", "한식", "서울시", new String[]{"09:00-22:00", "09:00-22:00", "09:00-22:00", "09:00-22:00", "09:00-22:00", "09:00-22:00", "09:00-22:00"}, "14:00-15:00", "02-1234-5678", "대표메뉴2", 20000, new int[]{4, 5, 3}), koreanPanelContainer, koreanListModel, koreanRestaurants);
        addRestaurant(new Restaurant("식당이름3", "중식", "서울시", new String[]{"09:00-22:00", "09:00-22:00", "09:00-22:00", "09:00-22:00", "09:00-22:00", "09:00-22:00", "09:00-22:00"}, "14:00-15:00", "02-1234-5678", "대표메뉴3", 15000, new int[]{4, 5, 3}), chinesePanelContainer, chineseListModel, chineseRestaurants);
        addRestaurant(new Restaurant("식당이름4", "중식", "서울시", new String[]{"09:00-22:00", "09:00-22:00", "09:00-22:00", "09:00-22:00", "09:00-22:00", "09:00-22:00", "09:00-22:00"}, "14:00-15:00", "02-1234-5678", "대표메뉴4", 12000, new int[]{4, 5, 3}), chinesePanelContainer, chineseListModel, chineseRestaurants);
    }

    // 카테고리 패널 생성 메서드
    private JPanel createCategoryPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.white); // 배경색 설정
        return panel;
    }

    // 식당 추가 메서드
    private void addRestaurant(Restaurant restaurant, JPanel panelContainer, DefaultListModel<JPanel> listModel, List<Restaurant> restaurantList) {
        restaurantList.add(restaurant);

        RoundedPanel restaurantPanel = new RoundedPanel(); // 둥근 모서리 패널 생성
        restaurantPanel.setLayout(new BoxLayout(restaurantPanel, BoxLayout.Y_AXIS));
        restaurantPanel.setBackground(new Color(255, 255, 204)); // 배경색 설정
        restaurantPanel.setPreferredSize(new Dimension(330,230)); // 패널 크기 고정

        JLabel nameLabel = new JLabel(restaurant.getName());
        JLabel ratingLabel = new JLabel("★★★"); // 별점은 예시로 고정
        JLabel menuLabel = new JLabel(restaurant.getMenuName());
        JLabel hoursLabel = new JLabel("Today's Hours: " + restaurant.getTodayHours());

        nameLabel.setFont(new Font("", Font.BOLD, 25)); // 폰트 설정
        ratingLabel.setFont(new Font("", Font.PLAIN, 20));
        menuLabel.setFont(new Font("", Font.PLAIN, 20));
        hoursLabel.setFont(new Font("", Font.PLAIN, 20));

        restaurantPanel.add(nameLabel);
        restaurantPanel.add(ratingLabel);
        restaurantPanel.add(menuLabel);
        restaurantPanel.add(hoursLabel);

        restaurantPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                showRestaurantDetails(restaurantPanel, listModel); // 식당 상세 정보 표시
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = listModel.getSize() % 2; // 2열 배치
        gbc.gridy = listModel.getSize() / 2;

        panelContainer.add(restaurantPanel, gbc);
        listModel.addElement(restaurantPanel);
        panelContainer.revalidate();
        panelContainer.repaint();
    }

    // 식당 상세 정보 표시 메서드
    private void showRestaurantDetails(JPanel restaurantPanel, DefaultListModel<JPanel> listModel) {
        JFrame detailsFrame = new JFrame("Restaurant Details");
        detailsFrame.setSize(400, 300);
        detailsFrame.setLayout(new BorderLayout());

        RoundedPanel infoPanel = new RoundedPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(255, 255, 204)); // 배경색 설정
        infoPanel.setPreferredSize(new Dimension(330, 230)); // 패널 크기 고정

        // 원본 패널의 라벨 복사
        for (Component comp : restaurantPanel.getComponents()) {
            if (comp instanceof JLabel) {
                infoPanel.add(new JLabel(((JLabel) comp).getText()));
            }
        }

        JLabel mapLabel = new JLabel(new ImageIcon("path/to/map.png")); // 지도 이미지 경로 설정
        infoPanel.add(mapLabel);

        JPanel buttonPanel = new JPanel();
        JButton goodButton = new JButton("good");
        goodButton.setBackground(new Color(255, 182, 193)); // 연한 빨간색으로 설정 (라이트 핑크)
        goodButton.setOpaque(true);
        goodButton.setBorderPainted(false);
        JButton badButton = new JButton("bad");
        badButton.setBackground(new Color(0, 191, 255)); // 연한 파란색으로 설정 (앨리스 블루)
        badButton.setOpaque(true);
        badButton.setBorderPainted(false);

        goodButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 식당을 리스트 상단으로 이동
                moveRestaurant(restaurantPanel, listModel, 0);
                detailsFrame.dispose();
            }
        });

        badButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 식당을 리스트 하단으로 이동
                moveRestaurant(restaurantPanel, listModel, listModel.getSize() - 1);
                detailsFrame.dispose();
            }
        });

        buttonPanel.add(goodButton);
        buttonPanel.add(badButton);

        detailsFrame.add(infoPanel, BorderLayout.CENTER);
        detailsFrame.add(buttonPanel, BorderLayout.SOUTH);
        detailsFrame.setVisible(true);
    }

    // 식당 이동 메서드
    private void moveRestaurant(JPanel restaurantPanel, DefaultListModel<JPanel> listModel, int newIndex) {
        int currentIndex = listModel.indexOf(restaurantPanel);
        if (currentIndex >= 0) {
            listModel.remove(currentIndex);
            listModel.add(newIndex, restaurantPanel);

            // 패널 컨테이너 갱신
            JPanel parentContainer = (JPanel) restaurantPanel.getParent();
            parentContainer.removeAll();

            for (int i = 0; i < listModel.size(); i++) {
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.BOTH;
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.gridx = i % 2; // 2열 배치
                gbc.gridy = i / 2;
                parentContainer.add(listModel.get(i), gbc);
            }

            parentContainer.revalidate();
            parentContainer.repaint();
        }
    }

    // 현재 시간 갱신 메서드
    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(new Date());
        timeLabel.setText(currentTime);
    }

    public static void main(String[] args) {
        new guide1("Restaurant Guide");
    }

    // 둥근 모서리 패널 클래스
    class RoundedPanel extends JPanel {
        private Color backgroundColor;
        private int cornerRadius = 15;

        public RoundedPanel() {
            super();
            setOpaque(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension arcs = new Dimension(cornerRadius, cornerRadius);
            int width = getWidth();
            int height = getHeight();
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 배경색 설정
            graphics.setColor(getBackground());
            graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);

            // 테두리 설정
            graphics.setColor(getForeground());
            graphics.drawRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
        }
    }

    // 시간 레이블 클래스
    class CustomTimeLabel extends JLabel {
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
}
