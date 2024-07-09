import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RestaurantView extends JFrame {
    private JButton showButton;

    public RestaurantView() {
        // 메인 창 구성
        setTitle("Restaurant Info");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        showButton = new JButton("Show Italian Restaurants");
        add(showButton);
    }

    public JButton getShowButton() {
        return showButton;
    }

    // 새로운 창에 데이터를 보여주는 메서드
    public void displayRestaurants(List<Restaurant> restaurants) {
        JFrame displayFrame = new JFrame("Italian Restaurants");
        displayFrame.setSize(800, 600);
        displayFrame.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        for (Restaurant restaurant : restaurants) {
            textArea.append("Name: " + restaurant.getName() + "\n");
            textArea.append("Genre: " + restaurant.getGenre() + "\n");
            textArea.append("Address: " + restaurant.getAddress() + "\n");
            textArea.append("Phone: " + restaurant.getPhoneNumber() + "\n");
            textArea.append("Today's Hours: " + restaurant.getTodayHours() + "\n");
            textArea.append("Break Time: " + restaurant.getBreakTime() + "\n");
            textArea.append("Menu: " + restaurant.getMenuName() + " (Price: " + restaurant.getMenuPrice() + ")\n");
            textArea.append("Average Rating: " + restaurant.getAverageRating() + "\n");
            textArea.append("\n");
        }


        displayFrame.add(new JScrollPane(textArea), BorderLayout.CENTER);
        displayFrame.setVisible(true);
    }
}
