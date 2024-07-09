import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                RestaurantView view = new RestaurantView("Restaurant Guide");
                RestaurantController controller = new RestaurantController(view);
                view.setVisible(true);
            }
        });
    }
}
