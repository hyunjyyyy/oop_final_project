import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;

public class CategoryPanel {
    private String category; // 카테고리 필드 추가
    private JPanel panel;
    private DefaultListModel<JPanel> listModel;
    private List<Restaurant> restaurants;

    public CategoryPanel(String category) {
        this.category = category; // 카테고리 초기화
        panel = new JPanel(new BorderLayout());
        listModel = new DefaultListModel<>();
        restaurants = new ArrayList<>();

        JPanel panelContainer = new JPanel(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane(panelContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // 스크롤바 항상 표시

        panel.add(scrollPane, BorderLayout.CENTER);
    }

    public JPanel getPanel() {
        return panel;
    }

    public String getCategory() {
        return category; // 카테고리 이름 반환
    }

    public void addRestaurant(Restaurant restaurant) {
        restaurants.add(restaurant);

        RestaurantPanel restaurantPanel = new RestaurantPanel(restaurant);

        restaurantPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                showRestaurantDetails(restaurantPanel); // 식당 상세 정보 표시
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = listModel.getSize() % 2; // 2열 배치
        gbc.gridy = listModel.getSize() / 2;

        JPanel panelContainer = (JPanel) ((JScrollPane) panel.getComponent(0)).getViewport().getView();
        panelContainer.add(restaurantPanel, gbc);
        listModel.addElement(restaurantPanel);
        panelContainer.revalidate();
        panelContainer.repaint();
    }

    public void clear() {
        JPanel panelContainer = (JPanel) ((JScrollPane) panel.getComponent(0)).getViewport().getView();
        panelContainer.removeAll();
        listModel.clear();
        panelContainer.revalidate();
        panelContainer.repaint();
    }

    private void showRestaurantDetails(JPanel restaurantPanel) {
        JFrame detailsFrame = new JFrame("Restaurant Details");
        detailsFrame.setSize(400, 500);
        detailsFrame.setLayout(new BorderLayout());

        RestaurantPanel infoPanel = new RestaurantPanel(restaurantPanel);

        JPanel buttonPanel = new JPanel();
        JButton goodButton = new JButton("good");
        goodButton.setBackground(new Color(255, 182, 193)); // 연한 빨간색으로 설정 (라이트 핑크)
        goodButton.setOpaque(true);
        goodButton.setBorderPainted(false);
        JButton badButton = new JButton("bad");
        badButton.setBackground(new Color(0, 191, 255)); // 연한 파란색으로 설정 (앨리스 블루)
        badButton.setOpaque(true);
        badButton.setBorderPainted(false);

        goodButton.addActionListener(e -> {
            // 식당을 리스트 상단으로 이동
            moveRestaurant(restaurantPanel, 0);
            detailsFrame.dispose();
        });

        badButton.addActionListener(e -> {
            // 식당을 리스트 하단으로 이동
            moveRestaurant(restaurantPanel, listModel.getSize() - 1);
            detailsFrame.dispose();
        });

        buttonPanel.add(goodButton);
        buttonPanel.add(badButton);

        detailsFrame.add(infoPanel, BorderLayout.CENTER);
        detailsFrame.add(buttonPanel, BorderLayout.SOUTH);
        detailsFrame.setVisible(true);
    }

    private void moveRestaurant(JPanel restaurantPanel, int newIndex) {
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
}
