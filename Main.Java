import java.io.*;
import java.util.*;

class Train {
    private int trainNo;
    private String trainName;
    private int seatsAvailable;

    public Train(int trainNo, String trainName, int seatsAvailable) {
        this.trainNo = trainNo;
        this.trainName = trainName;
        this.seatsAvailable = seatsAvailable;
    }

    public int getTrainNo() { return trainNo; }
    public String getTrainName() { return trainName; }
    public int getSeatsAvailable() { return seatsAvailable; }

    public void bookSeat(int count) {
        seatsAvailable -= count;
    }

    @Override
    public String toString() {
        return trainNo + " - " + trainName + " (Seats: " + seatsAvailable + ")";
    }
}


class Passenger {
    private String name;
    private int age;

    public Passenger(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
}


class Booking {
    private Passenger passenger;
    private Train train;
    private int seats;

    public Booking(Passenger passenger, Train train, int seats) {
        this.passenger = passenger;
        this.train = train;
        this.seats = seats;
    }

    @Override
    public String toString() {
        return "Passenger: " + passenger.getName() + " (" + passenger.getAge() + " yrs)" +
                " | Train: " + train.getTrainName() +
                " | Seats Booked: " + seats;
    }
}


public class Main {
    private static List<Train> trains = new ArrayList<>();
    private static List<Booking> bookings = new ArrayList<>();
    private static File file = new File("bookings.txt");

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        // Predefined trains
        trains.add(new Train(101, "Express A", 50));
        trains.add(new Train(102, "Express B", 40));
        trains.add(new Train(103, "Express C", 30));


        loadBookings();

        while (true) {
            System.out.println("\n--- Train Ticket Booking System ---");
            System.out.println("1. View Trains");
            System.out.println("2. Book Ticket");
            System.out.println("3. View Bookings");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("\nAvailable Trains:");
                    for (Train t : trains) {
                        System.out.println(t);
                    }
                    break;

                case 2:
                    System.out.print("Enter your name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter age: ");
                    int age = sc.nextInt();

                    System.out.println("Choose Train No:");
                    for (Train t : trains) {
                        System.out.println(t);
                    }
                    int trainNo = sc.nextInt();
                    System.out.print("Enter number of seats: ");
                    int seatCount = sc.nextInt();

                    Train selected = null;
                    for (Train t : trains) {
                        if (t.getTrainNo() == trainNo) {
                            selected = t;
                            break;
                        }
                    }

                    if (selected != null && seatCount <= selected.getSeatsAvailable()) {
                        Passenger p = new Passenger(name, age);
                        Booking b = new Booking(p, selected, seatCount);
                        bookings.add(b);
                        selected.bookSeat(seatCount);
                        saveBooking(b);
                        System.out.println("Booking Successful!");
                    } else {
                        System.out.println("Booking Failed! Not enough seats or invalid train.");
                    }
                    break;

                case 3:
                    System.out.println("\nAll Bookings:");
                    if (bookings.isEmpty()) {
                        System.out.println("No bookings yet.");
                    } else {
                        for (Booking b : bookings) {
                            System.out.println(b);
                        }
                    }
                    break;

                case 4:
                    System.out.println("Exiting... Thank you!");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }


    private static void saveBooking(Booking b) throws IOException {
        FileWriter fw = new FileWriter(file, true);
        fw.write(b.toString() + "\n");
        fw.close();
    }


    private static void loadBookings() throws IOException {
        if (!file.exists()) return;
        Scanner fr = new Scanner(file);
        System.out.println("\nPrevious Bookings from file:");
        while (fr.hasNextLine()) {
            System.out.println(fr.nextLine());
        }
        fr.close();
    }
}
