import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Ticket {
    private char row;// Row of the seat
    private int seat;// Seat number
    private double price;// Price of the ticket
    private Person person; // Person associated with the ticket

    // Constructor
    public Ticket(char row, int seat, double price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    // Getters and setters
    public char getRow() {
        return row;
    }

    public void setRow(char row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }



    // Method to return string representation of ticket information
    public String toString() {
        return "Ticket Information:\n" +
                "Row: " + row + "\n" +
                "Seat: " + seat + "\n" +
                "Price: £" + price + "\n" +
                "\nPerson Information:\n" + getPerson().getName() + " " + getPerson().getSurname() + "\n" +
                "Email: " + getPerson().getEmail();
    }

    // Method to save ticket information to a file
    public void save() {
        String fileName = row + String.valueOf(seat) + ".txt"; // Create file name
        File file = new File(fileName); // Create file object

        try (FileWriter writer = new FileWriter(file)) { // Open file writer

            // Write ticket information to the file
            writer.write("Ticket Information:\n");
            writer.write("Row: " + row + "\n");
            writer.write("Seat: " + seat + "\n");
            writer.write("Price: £" + price + "\n");
            writer.write("\nPerson Information:\n" + person.getName() + " " + person.getSurname() + "\n");
            writer.write("Email: " + person.getEmail() + "\n");
        } catch (IOException e) {// Handle IOException
            System.out.println("Error occurred while saving ticket information to file: " + e.getMessage());
        }
    }
}
