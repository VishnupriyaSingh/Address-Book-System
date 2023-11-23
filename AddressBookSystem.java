import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Contact {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;
    private String email;

    public Contact(String firstName, String lastName, String address, String city, String state, String zip, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

class AddressBook {
    private List<Contact> contacts;

    public AddressBook() {
        this.contacts = new ArrayList<>();
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void editContact(String firstName, Contact updatedContact) {
        boolean found = false;
        for (int i = 0; i < contacts.size(); i++) {
            Contact c = contacts.get(i);
            if (c.getFirstName().equalsIgnoreCase(firstName)) {
                contacts.set(i, updatedContact);
                System.out.println("Contact updated successfully!");
                found = true;
                return;
            }
        }
        if(!found)
        {
            System.out.println("Contact not found.");
        }
    }

    public void viewContacts() {
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }
}

public class AddressBookSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AddressBook addressBook = new AddressBook();

        System.out.println("Welcome to Address Book Program");

        while (true) {
            System.out.println("Choose an option: \n1. Add Contact \n2. Edit Contact \n3. View Contacts \n4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.println("Enter First Name:");
                String firstName = scanner.nextLine();

                System.out.println("Enter Last Name:");
                String lastName = scanner.nextLine();

                System.out.println("Enter Address:");
                String address = scanner.nextLine();

                System.out.println("Enter City:");
                String city = scanner.nextLine();

                System.out.println("Enter State:");
                String state = scanner.nextLine();

                System.out.println("Enter Zip:");
                String zip = scanner.nextLine();

                System.out.println("Enter Phone Number:");
                String phoneNumber = scanner.nextLine();

                System.out.println("Enter Email:");
                String email = scanner.nextLine();

                Contact newContact = new Contact(firstName, lastName, address, city, state, zip, phoneNumber, email);
                addressBook.addContact(newContact);
            } else if (choice == 2) {
                System.out.println("Enter the first name of the contact to edit:");
                String name = scanner.nextLine();

                System.out.println("Enter new details for the contact:");

                System.out.println("Enter First Name:");
                String firstName = scanner.nextLine();

                System.out.println("Enter Last Name:");
                String lastName = scanner.nextLine();

                System.out.println("Enter Address:");
                String address = scanner.nextLine();

                System.out.println("Enter City:");
                String city = scanner.nextLine();

                System.out.println("Enter State:");
                String state = scanner.nextLine();

                System.out.println("Enter Zip:");
                String zip = scanner.nextLine();

                System.out.println("Enter Phone Number:");
                String phoneNumber = scanner.nextLine();

                System.out.println("Enter Email:");
                String email = scanner.nextLine();

                Contact updatedContact = new Contact(firstName, lastName, address, city, state, zip, phoneNumber, email);
                addressBook.editContact(name, updatedContact);
            } else if (choice == 3) {
                addressBook.viewContacts();
            } else if (choice == 4) {
                break;
            } else {
                System.out.println("Invalid option! Please choose again.");
            }
        }

        scanner.close();
    }
}