import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RestaurantController {
    private RestaurantView view;
    private List<Restaurant> allRestaurants;

    public RestaurantController(RestaurantView view) {
        this.view = view;
        this.allRestaurants = loadRestaurantsFromDatabase();

        this.view.getShowButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Restaurant> italianRestaurants = filterRestaurantsByGenre("한식");
                view.displayRestaurants(italianRestaurants);
            }
        });
    }

    private List<Restaurant> loadRestaurantsFromDatabase() {
        List<Restaurant> restaurants = new ArrayList<>();
        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/oop_project";
        String username = "root";
        String password = "Beethoven!77";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM restaurant_data"; // 적절한 쿼리로 변경
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String name = resultSet.getString("식당 이름");
                String genre = resultSet.getString("장르");
                String address = resultSet.getString("주소");

                String[] hours = new String[] {
                    resultSet.getString("월"),
                    resultSet.getString("화"),
                    resultSet.getString("수"),
                    resultSet.getString("목"),
                    resultSet.getString("금"),
                    resultSet.getString("토"),
                    resultSet.getString("일")
                };

                String breakTime = resultSet.getString("break time");
                String phoneNumber = resultSet.getString("전화번호");
                String menuName = resultSet.getString("대표메뉴");
                int menuPrice = resultSet.getInt("메뉴 가격");

                int[] ratings = new int[] {
                    resultSet.getInt("별점(강지윤)"),
                    resultSet.getInt("별점(김찬미)"),
                    resultSet.getInt("별점(유현지)")
                };

                restaurants.add(new Restaurant(name, genre, address, hours, breakTime, phoneNumber, menuName, menuPrice, ratings));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restaurants;
    }

    private List<Restaurant> filterRestaurantsByGenre(String genre) {
        List<Restaurant> filtered = new ArrayList<>();
        for (Restaurant restaurant : allRestaurants) {
            if (restaurant.getGenre().equals(genre)) {
                filtered.add(restaurant);
            }
        }
        return filtered;
    }
}
