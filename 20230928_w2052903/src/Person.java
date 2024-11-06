public class Person {
    private String name;// Name of the person
    private String surname;// Surname of the person
    private String email;// Email address of the person

    // Constructor
    public Person(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    // Method to print person information
    public void printInfo() {
        System.out.println(" "+ name + " " + surname + " ( " + email + " ) ");

    }
}
