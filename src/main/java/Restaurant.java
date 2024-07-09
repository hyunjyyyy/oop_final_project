import java.time.DayOfWeek;
import java.time.LocalDate;

public class Restaurant {
    private String name;
    private String genre;
    private String address;
    private String[] hours;
    private String breakTime;
    private String phoneNumber;
    private String menuName;
    private int menuPrice;
    private int[] ratings;

    public Restaurant(String name, String genre, String address, String[] hours, String breakTime,
                      String phoneNumber, String menuName, int menuPrice, int[] ratings) {
        this.name = name;
        this.genre = genre;
        this.address = address;
        this.hours = hours.clone();
        this.breakTime = breakTime;
        this.phoneNumber = phoneNumber;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.ratings = ratings.clone();
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getAddress() {
        return address;
    }

    public String[] getHours() {
        return hours.clone();
    }

    public String getBreakTime() {
        return breakTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMenuName() {
        return menuName;
    }

    public int getMenuPrice() {
        return menuPrice;
    }

    public int[] getRatings() {
        return ratings.clone();
    }

    // 평균 별점 계산
    public double getAverageRating() {
        int sum = 0;
        int count = 0;
        for (int rating : ratings) {
            if (rating > 0) { // 0 이상의 별점만 계산
                sum += rating;
                count++;
            }
        }
        return count > 0 ? (double) sum / count : 0.0;
    }

    // 오늘 요일의 운영시간 가져오기
    public String getTodayHours() {
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        switch (dayOfWeek) {
            case MONDAY:
                return hours[0];
            case TUESDAY:
                return hours[1];
            case WEDNESDAY:
                return hours[2];
            case THURSDAY:
                return hours[3];
            case FRIDAY:
                return hours[4];
            case SATURDAY:
                return hours[5];
            case SUNDAY:
                return hours[6];
            default:
                return "Closed";
        }
    }
}
