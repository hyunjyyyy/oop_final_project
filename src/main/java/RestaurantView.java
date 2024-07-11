import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;
import java.text.SimpleDateFormat;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.Date;


public class RestaurantView {
    private JFrame jf; // 메인 프레임
    private JTabbedPane tabpane; // 탭 패널
    private JTextField searchField; // 검색 필드
    private JButton searchButton; // 검색 버튼
    private CustomTimeLabel timeLabel; // 현재 시간 레이블

    private CategoryPanel koreanCategoryPanel;
    private CategoryPanel chineseCategoryPanel;
    private CategoryPanel japaneseCategoryPanel;
    private CategoryPanel westernCategoryPanel;
    private CategoryPanel snackCategoryPanel;
    private CategoryPanel cafeCategoryPanel;
    private CategoryPanel otherCategoryPanel;
    private List<CategoryPanel> categoryPanels; // 카테고리 패널 리스트

    private List<Restaurant> allRestaurants = new ArrayList<>();
    private Set<String> selectedDays = new HashSet<>(); // 선택된 요일 저장하는 변수

    public RestaurantView(String title) {
        jf = new JFrame(title); // 프레임 타이틀 설정
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프레임 종료 설정

        tabpane = new JTabbedPane(); // 탭 패널 초기화
        
        createMenuBar();

        searchField = new JTextField(20); // 검색 필드 초기화

        // 검색 아이콘 로드 및 크기 조정
        ImageIcon searchIcon = new ImageIcon("C:/oop24/final_project24/oop_finalproject/src/main/java/photo/search.png");
        Image searchImage = searchIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // 원하는 크기로 변경
        ImageIcon resizedSearchIcon = new ImageIcon(searchImage);
        searchButton = new JButton(resizedSearchIcon); // 크기 조정된 아이콘 설정

        searchButton.addActionListener(e -> {
            String query = searchField.getText().trim();
            if (!query.isEmpty()) {
                List<Restaurant> searchResults = searchRestaurants(query);
                displaySearchResults(searchResults);
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
        javax.swing.Timer timer = new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
        timer.start();


        // 각 카테고리 패널 생성 및 탭 추가
        koreanCategoryPanel = new CategoryPanel("한식");
        chineseCategoryPanel = new CategoryPanel("중식");
        japaneseCategoryPanel = new CategoryPanel("일식");
        westernCategoryPanel = new CategoryPanel("양식");
        snackCategoryPanel = new CategoryPanel("분식");
        cafeCategoryPanel = new CategoryPanel("빵/카페");
        otherCategoryPanel = new CategoryPanel("기타");

        categoryPanels = new ArrayList<>(); // 카테고리 패널 리스트 초기화
        categoryPanels.add(koreanCategoryPanel);
        categoryPanels.add(chineseCategoryPanel);
        categoryPanels.add(japaneseCategoryPanel);
        categoryPanels.add(westernCategoryPanel);
        categoryPanels.add(snackCategoryPanel);
        categoryPanels.add(cafeCategoryPanel);
        categoryPanels.add(otherCategoryPanel);

        tabpane.addTab("한식", koreanCategoryPanel.getPanel());
        tabpane.addTab("중식", chineseCategoryPanel.getPanel());
        tabpane.addTab("일식", japaneseCategoryPanel.getPanel());
        tabpane.addTab("양식", westernCategoryPanel.getPanel());
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
        allRestaurants = controller.loadRestaurantsFromDatabase();
        updateCategoryPanels(allRestaurants); // allRestaurants를 업데이트합니다.
    }

    private List<Restaurant> searchRestaurants(String query) {
        List<Restaurant> searchResults = new ArrayList<>();
        
        for (Restaurant restaurant : allRestaurants) {
            if (restaurant.getName().contains(query)) { // 부분 검색이 가능하도록 contains로 수정
                searchResults.add(restaurant);
            }
            else if (restaurant.getMenuName().contains(query)) {
            	searchResults.add(restaurant);
            }
        }
        return searchResults;
    }

    private void displaySearchResults(List<Restaurant> searchResults) {
        JFrame searchResultsFrame = new JFrame("검색 결과");
        searchResultsFrame.setSize(600, 400);
        searchResultsFrame.setLayout(new BorderLayout());

        JPanel resultsPanel = new JPanel(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // 스크롤바 항상 표시

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        int row = 0;

        for (Restaurant restaurant : searchResults) {
            RestaurantPanel restaurantPanel = new RestaurantPanel(restaurant);
            gbc.gridx = 0;
            gbc.gridy = row++;
            resultsPanel.add(restaurantPanel, gbc);
        }

        searchResultsFrame.add(scrollPane, BorderLayout.CENTER);
        searchResultsFrame.setVisible(true);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu filterMenu = new JMenu("Filter");
        menuBar.add(filterMenu);
        JMenu daysMenu = new JMenu("요일별");
        filterMenu.add(daysMenu);

        String[] days = {"월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일"};
        for (String day : days) {
            JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(day);
            menuItem.addActionListener(e -> {
                if (menuItem.isSelected()) {
                    selectedDays.add(day);
                } else {
                    selectedDays.remove(day);
                }
                filterBySelectedDays();
            });
            daysMenu.add(menuItem);
        }

        jf.setJMenuBar(menuBar);
    }

    private void filterBySelectedDays() {
        List<Restaurant> filteredRestaurants = new ArrayList<>();
        for (Restaurant restaurant : allRestaurants) {
            String[] hours = restaurant.getHours();
            for (String day : selectedDays) {
                int dayIndex = getDayIndex(day);
                if (hours != null && hours.length > dayIndex && hours[dayIndex] != null) {
                    filteredRestaurants.add(restaurant);
                    break; // 한 요일이라도 열려 있으면 추가
                }
            }
        }
        updateCategoryPanels(filteredRestaurants);
    }

    private int getDayIndex(String day) {
        switch (day) {
            case "월요일": return 0;
            case "화요일": return 1;
            case "수요일": return 2;
            case "목요일": return 3;
            case "금요일": return 4;
            case "토요일": return 5;
            case "일요일": return 6;
            default: throw new IllegalArgumentException("Invalid day: " + day);
        }
    }

    private void updateCategoryPanels(List<Restaurant> filteredRestaurants) {
        clearCategoryPanels();
        for (Restaurant restaurant : filteredRestaurants) {
            for (CategoryPanel panel : categoryPanels) {
                if (restaurant.getGenre().equals(panel.getCategory())) {
                    panel.addRestaurant(restaurant);
                }
            }
        }
    }

    private void clearCategoryPanels() {
        for (CategoryPanel panel : categoryPanels) {
            panel.clear();
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

    public void setSize(int x, int y) {
        jf.setSize(x, y);
    }
}
