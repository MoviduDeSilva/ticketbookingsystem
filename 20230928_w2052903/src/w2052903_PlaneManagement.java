import java.util.Scanner;
import java.util.InputMismatchException;

public class w2052903_PlaneManagement {
    static int[][] seats = new int[4][]; // 2D array to represent seats
    static Ticket[] tickets = new Ticket[52]; // Assuming you can sell up to 52 tickets
    static int ticketCount = 0;

    public static void main(String[] args) {
        initializeSeats(); // Initialize seats
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                 -----------------------------------------------------------------------

                    Welcome to the Plane Management Application

                 ------------------------------------------------------------------------
                """);
        int choice = -1;
        do {
            // Display menu
            System.out.println("\n**********************************************************************");
            System.out.println("*                         MENU OPTIONS                               *");
            System.out.println("**********************************************************************");
            System.out.println("        1) Buy a seat");
            System.out.println("        2) Cancel a seat");
            System.out.println("        3) Find first seat available");
            System.out.println("        4) Show seating plan");
            System.out.println("        5) Print tickets information and total sales");
            System.out.println("        6) Search ticket");
            System.out.println("        0) quit");
            System.out.println("************************************************************************");


        boolean isValidInput = false;
        while(!isValidInput) {
            try {
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();

                if (choice >= 0 && choice <= 6) {
                    isValidInput = true;
                } else {
                    System.out.println("Invalid option please try again !");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for the choice.");
                scanner.nextLine(); // Consume the invalid input to prevent an infinite loop
            }
        }

            switch (choice) {
                case 1:
                    buySeat();
                    break;
                case 2:
                    cancelSeat();
                    break;
                case 3:
                    findFirstAvailable();
                    break;
                case 4:
                    showSeatingPlan();
                    break;
                case 5:
                    printTicketsInformation();
                    break;
                case 6:
                    searchTicket();
                    break;
                case 0:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 0);
    }

    // Method to initialize seats
    private static void initializeSeats() {
        seats[0] = new int[14];
        seats[1] = new int[12];
        seats[2] = new int[12];
        seats[3] = new int[14];
    }

    // Method to buy a seat
    private static void buySeat() {
        Scanner scanner = new Scanner(System.in);
        char row;
        int seatNum;

        while (true) {
            Scanner scanner1 = new Scanner(System.in);
            System.out.print("Enter row letter (A-D): ");
            String input = scanner1.next().toUpperCase();


            if (input.length() == 1 && input.charAt(0) >= 'A' && input.charAt(0) <= 'D') {
                row = input.charAt(0);
                break;
            } else {
                System.out.println("Invalid row letter. Please enter A, B, C, or D.");
            }
        }

        int rowIdx;
        while (true) {
            Scanner scanner2 = new Scanner(System.in);
            System.out.print("Enter seat number: ");

            try {
                seatNum = scanner2.nextInt();
                scanner2.nextLine();
                rowIdx = row - 'A';

                if (rowIdx < 0 || rowIdx >= seats.length || seatNum < 1 || seatNum > seats[rowIdx].length) {
                    System.out.println("Invalid seat number.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for the seat number.");
                scanner2.nextLine(); // Consume the invalid input to prevent an infinite loop
            }
        }


        if (seats[rowIdx][seatNum - 1] == 1) {
            System.out.println("Seat already sold.");

        } else {
            System.out.print("Enter passenger's first name: ");
            String name = scanner.nextLine();

            System.out.print("Enter passenger's surname: ");
            String surname = scanner.nextLine();


            String email;

            boolean validEmail;
            do {
                System.out.print("Enter passenger's email: ");
                email = scanner.nextLine();

                validEmail = email.contains("@") && email.contains(".");
                if (!validEmail) {
                    System.out.println("Invalid email format. Please enter a valid email.");
                }
            } while (!validEmail);


            Person person = new Person(name, surname, email); // Replace 'Person' with Person class
            person.setName(name);
            person.setSurname(surname);
            person.setEmail(email);

            double price = calculateTicketPrice(seatNum);
            String formattedPrice = String.format("%.0f", price);

            System.out.println("\nTicket price is " + formattedPrice + " pounds.");
            seats[rowIdx][seatNum - 1] = 1;

            Ticket ticket = new Ticket(row, seatNum, price, person); // Replace 'Ticket' with Ticket class
            ticket.setRow(row);
            ticket.setSeat(seatNum);
            ticket.setPrice(price);
            ticket.setPerson(person);

            ticket.save();
            tickets[ticketCount++] = ticket; // Update ticketCount and add the ticket
            System.out.print("Seat successfully sold for");
            person.printInfo();
        }
    }

    //method to calculate ticket price
    private static double calculateTicketPrice(int seatNum) {

        if (seatNum <= 5) {
            return 200;
        } else if (seatNum <= 9) {
            return 150;
        } else {
            return 180;
        }

    }

    // Method to cancel a seat
    private static void cancelSeat() {
        Scanner scanner = new Scanner(System.in);
        char row;
        int seatNum;

        //  prompt the user to enter the row letter (A-D)
        while (true) {
            System.out.print("Enter row letter (A-D): ");
            String input = scanner.next().toUpperCase();
            if (input.length() == 1 && input.charAt(0) >= 'A' && input.charAt(0) <= 'D') {
                row = input.charAt(0);
                break;
            } else {
                System.out.println("Invalid row letter. Please enter A, B, C, or D.");
            }
        }

        //  determine the row index based on the entered row letter
        int rowIdx = row - 'A';


        // Prompt the user to enter the seat number
        while (true) {
            System.out.print("Enter seat number: ");
            seatNum = scanner.nextInt();
            if (seatNum < 1 || seatNum > seats[rowIdx].length) {
                System.out.println("Invalid seat number.");
            } else {
                break;
            }
        }


        // Check if the seat is already available or sold
        if (seats[rowIdx][seatNum - 1] == 0) {
            System.out.println("Seat already available.");
        } else {
            boolean ticketFound = false;
            for (int i = 0; i < ticketCount; i++) {
                if (tickets[i] != null && tickets[i].getRow() == row && tickets[i].getSeat() == seatNum) {
                    tickets[i] = null;
                    seats[rowIdx][seatNum - 1] = 0; // Mark the seat as available
                    ticketFound = true;
                    System.out.println("Seat successfully cancelled.");
                    break;
                }
            }

            // Display a message if the ticket is not found for the given seat
            if (!ticketFound) {
                System.out.println("Ticket not found for the given seat.");
            }
        }
    }


    // Method to find first available seat
    private static void findFirstAvailable() {

        // Iterate through the seats array to find the first available seat
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j] == 0) {
                    System.out.println("First available seat: Row " + (char) ('A' + i) + ", Seat " + (j + 1));
                    return;
                }
            }
        }
        // Display a message if no available seats are found
        System.out.println("No available seats.");
    }


    // Method to show seating plan
    private static void showSeatingPlan() {
        char rowLabel = 'A';
        // Iterate through the seats array to display the seating plan

        for (int[] row : seats) {
            System.out.print(rowLabel + " : ");
            for (int element : row) {
                // Display 'O' for available seats and 'X' for sold seats
                if (element == 0) {
                    System.out.print("O ");
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println();
            rowLabel++;// Increment row label for the next row
        }
    }

    // Method to print tickets information and total sales
    private static void printTicketsInformation() {
        int totalSales = 0;// Initialize total sales variable

        // Iterate through the tickets array to print ticket information
        for (int i = 0; i < ticketCount; i++) {
            if (tickets[i] != null) {
                System.out.println(tickets[i].toString());// Print ticket details
                totalSales += (int) tickets[i].getPrice(); // Add ticket price to total sales
//              Add a line between each ticket for readability
                System.out.println("**********************************************************");
            }
        }
        // Print total sales amount
        System.out.println("Total sales: " + totalSales + " pounds");
    }

    // Method to search for a ticket
    private static void searchTicket() {
        Scanner scanner = new Scanner(System.in);

        char row;
        // Prompt the user to enter the row letter (A-D)
        while (true) {
            System.out.print("Enter row letter (A-D): ");
            String input = scanner.next().toUpperCase();
            if (input.length() == 1 && input.charAt(0) >= 'A' && input.charAt(0) <= 'D') {
                row = input.charAt(0);
                break;
            } else {
                System.out.println("Invalid row letter. Please enter A, B, C, or D.");
            }
        }

        // Determine the row index based on the entered row letter
        int rowIdx = row - 'A';
        int seatNum;
        while (true) {

            // Prompt the user to enter the seat number
            System.out.print("Enter seat number: ");
            try {
                seatNum = scanner.nextInt();
                if (seatNum < 1 || seatNum > seats[rowIdx].length) {
                    System.out.println("Invalid seat number.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for the seat number.");
                scanner.nextLine(); // Consume the invalid input to prevent an infinite loop
            }
        }

        // Check if the seat is already booked or available
        if (seats[rowIdx][seatNum - 1] == 1) {
            boolean ticketFound = false;
            // Iterate through the tickets array to find the ticket corresponding

            for (int i = 0; i < ticketCount; i++) {
                if (tickets[i] != null && tickets[i].getRow() == row && tickets[i].getSeat() == seatNum) {
                    System.out.println("This seat is already booked.");
                    System.out.println(tickets[i]); // Print the ticket information
                    ticketFound = true;
                    break;
                }
            }

            // Display a message if the ticket is not found for the given seat
            if (!ticketFound) {
                System.out.println("Ticket not found for the given seat.");
            }
        } else {
            System.out.println("This seat is available.");
        }
    }
}