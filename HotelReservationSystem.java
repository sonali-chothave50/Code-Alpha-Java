import java.io.*;
import java.util.*;

class Room implements Serializable {
    int roomNumber;
    String category;
    boolean isAvailable;
    double price;

    Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.isAvailable = true;
    }
}

class Booking implements Serializable {
    String customerName;
    int roomNumber;
    double amountPaid;

    Booking(String customerName, int roomNumber, double amountPaid) {
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.amountPaid = amountPaid;
    }
}

class Hotel {
    List<Room> rooms = new ArrayList<>();
    List<Booking> bookings = new ArrayList<>();

    // Load bookings from file
    @SuppressWarnings("unchecked")
    public void loadBookings() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("bookings.dat"))) {
            bookings = (List<Booking>) ois.readObject();
            for (Booking b : bookings) {
                for (Room r : rooms) {
                    if (r.roomNumber == b.roomNumber) {
                        r.isAvailable = false;
                    }
                }
            }
        } catch (Exception e) {
            // First run: file may not exist
            bookings = new ArrayList<>();
        }
    }

    // Save bookings to file
    public void saveBookings() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("bookings.dat"))) {
            oos.writeObject(bookings);
        } catch (IOException e) {
            System.out.println("Error saving bookings.");
        }
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void displayAvailableRooms(String category) {
        System.out.println("\n--- Available " + category + " Rooms ---");
        boolean found = false;
        for (Room room : rooms) {
            if (room.category.equalsIgnoreCase(category) && room.isAvailable) {
                System.out.println("Room #" + room.roomNumber + " | Price: ₹" + room.price);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No available rooms in this category.");
        }
    }

    public Room findAvailableRoom(String category) {
        for (Room room : rooms) {
            if (room.category.equalsIgnoreCase(category) && room.isAvailable) {
                return room;
            }
        }
        return null;
    }

    public void makeBooking(String customerName, String category) {
        Room room = findAvailableRoom(category);
        if (room != null) {
            System.out.println("Processing payment of ₹" + room.price + "...");
            // Simulate payment
            System.out.println("Payment successful!");

            room.isAvailable = false;
            Booking booking = new Booking(customerName, room.roomNumber, room.price);
            bookings.add(booking);

            saveBookings();

            System.out.println("Booking confirmed for " + customerName + " in Room #" + room.roomNumber);
        } else {
            System.out.println("No available room in this category.");
        }
    }

    public void cancelBooking(String customerName) {
        Iterator<Booking> iterator = bookings.iterator();
        boolean found = false;
        while (iterator.hasNext()) {
            Booking b = iterator.next();
            if (b.customerName.equalsIgnoreCase(customerName)) {
                iterator.remove();
                for (Room r : rooms) {
                    if (r.roomNumber == b.roomNumber) {
                        r.isAvailable = true;
                    }
                }
                found = true;
                System.out.println("Booking for " + customerName + " canceled successfully.");
                saveBookings();
                break;
            }
        }
        if (!found) {
            System.out.println("No booking found for " + customerName + ".");
        }
    }

    public void viewBookings() {
        System.out.println("\n--- Current Bookings ---");
        if (bookings.isEmpty()) {
            System.out.println("No active bookings.");
            return;
        }
        for (Booking b : bookings) {
            System.out.println("Customer: " + b.customerName + " | Room #" + b.roomNumber + " | Paid: ₹" + b.amountPaid);
        }
    }
}

public class HotelReservationSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Hotel hotel = new Hotel();
        // Add sample rooms
        hotel.addRoom(new Room(101, "Standard", 2000));
        hotel.addRoom(new Room(102, "Standard", 2000));
        hotel.addRoom(new Room(201, "Deluxe", 3500));
        hotel.addRoom(new Room(202, "Deluxe", 3500));
        hotel.addRoom(new Room(301, "Suite", 5000));
        hotel.addRoom(new Room(302, "Suite", 5000));

        hotel.loadBookings();

        int choice;
        do {
            System.out.println("\n=== Hotel Reservation System ===");
            System.out.println("1. Search Available Rooms");
            System.out.println("2. Make a Booking");
            System.out.println("3. Cancel a Booking");
            System.out.println("4. View Booking Details");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    sc.nextLine(); // consume newline
                    System.out.print("Enter room category (Standard / Deluxe / Suite): ");
                    String category = sc.nextLine();
                    hotel.displayAvailableRooms(category);
                    break;

                case 2:
                    sc.nextLine(); // consume newline
                    System.out.print("Enter your name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter room category (Standard / Deluxe / Suite): ");
                    String bookCategory = sc.nextLine();
                    hotel.makeBooking(name, bookCategory);
                    break;

                case 3:
                    sc.nextLine(); // consume newline
                    System.out.print("Enter your name to cancel booking: ");
                    String cancelName = sc.nextLine();
                    hotel.cancelBooking(cancelName);
                    break;

                case 4:
                    hotel.viewBookings();
                    break;

                case 5:
                    System.out.println("Thank you for using Hotel Reservation System!");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        } while (choice != 5);

        sc.close();
    }
}
