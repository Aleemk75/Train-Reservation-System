import java.util.*;

class Passenger {
    private String name;
    private int age;
    private String pnr;

    public Passenger(String name, int age, String pnr) {
        this.name = name;
        this.age = age;
        this.pnr = pnr;
    }

    public String getName() {
        return name;
    }

    public String getPNR() {
        return pnr;
    }

    @Override
    public String toString() {
        return "PNR: " + pnr + ", Name: " + name + ", Age: " + age;
    }
}

public class TrainReservationSystem {
    private Stack<Integer> seatStack = new Stack<>();
    private List<Passenger> passengerList = new ArrayList<>();
    private int maxSeats;

    public TrainReservationSystem(int maxSeats) {
        this.maxSeats = maxSeats;
        for (int i = maxSeats; i >= 1; i--) {
            seatStack.push(i); // Initialize seats
        }
    }

    public void bookTicket(String name, int age) {
        if (seatStack.isEmpty()) {
            System.out.println("No seats available!");
            return;
        }
        int seatNumber = seatStack.pop();
        String pnr = generatePNR(seatNumber);
        Passenger passenger = new Passenger(name, age, pnr);
        passengerList.add(passenger);
        System.out.println("Ticket booked! " + passenger);
    }

    public void cancelTicket(String pnr) {
        Passenger passenger = searchByPNR(pnr);
        if (passenger != null) {
            passengerList.remove(passenger);
            int seatNumber = Integer.parseInt(pnr.substring(3));
            seatStack.push(seatNumber);
            System.out.println("Ticket with PNR " + pnr + " has been cancelled.");
        } else {
            System.out.println("Invalid PNR. No booking found.");
        }
    }

    public Passenger searchByPNR(String pnr) {
        for (Passenger p : passengerList) {
            if (p.getPNR().equals(pnr)) {
                return p;
            }
        }
        return null;
    }

    public void displayPassengers() {
        if (passengerList.isEmpty()) {
            System.out.println("No passengers booked.");
        } else {
            System.out.println("Booked Passengers:");
            passengerList.sort(Comparator.comparing(Passenger::getName));
            for (Passenger p : passengerList) {
                System.out.println(p);
            }
        }
    }

    private String generatePNR(int seatNumber) {
        return "PNR" + seatNumber;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TrainReservationSystem system = new TrainReservationSystem(5); // Example with 5 seats

        while (true) {
            System.out.println("\n--- Train Reservation System ---");
            System.out.println("1. Book Ticket");
            System.out.println("2. Cancel Ticket");
            System.out.println("3. Search by PNR");
            System.out.println("4. Display Passengers");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the buffer

            switch (choice) {
                case 1:
                    System.out.print("Enter Passenger Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Age: ");
                    int age = scanner.nextInt();
                    system.bookTicket(name, age);
                    break;

                case 2:
                    System.out.print("Enter PNR to cancel: ");
                    String pnr = scanner.nextLine();
                    system.cancelTicket(pnr);
                    break;

                case 3:
                    System.out.print("Enter PNR to search: ");
                    String searchPNR = scanner.nextLine();
                    Passenger passenger = system.searchByPNR(searchPNR);
                    if (passenger != null) {
                        System.out.println("Booking Found: " + passenger);
                    } else {
                        System.out.println("No booking found with PNR " + searchPNR);
                    }
                    break;

                case 4:
                    system.displayPassengers();
                    break;

                case 5:
                    System.out.println("Exiting... Thank you for using the system.");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
