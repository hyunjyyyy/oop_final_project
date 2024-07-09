import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                RestaurantView view = new RestaurantView();
                RestaurantController controller = new RestaurantController(view);
                view.setVisible(true);
            }
        });
    }
}
