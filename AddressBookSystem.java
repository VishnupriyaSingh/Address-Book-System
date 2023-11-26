import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

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

    public String getLastName() {
        return lastName;
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

    public boolean addContact(Contact contact) {
        if (!isDuplicate(contact)) {
            contacts.add(contact);
            return true;
        }
        return false;
    }

    private boolean isDuplicate(Contact newContact) {
        for (Contact contact : contacts) {
            if (contact.getFirstName().equalsIgnoreCase(newContact.getFirstName()) &&
                contact.getLastName().equalsIgnoreCase(newContact.getLastName())) {
                return true;
            }
        }
        return false;
    }

    public void editContact(String firstName, Contact updatedContact) {
        for (int i = 0; i < contacts.size(); i++) {
            Contact c = contacts.get(i);
            if (c.getFirstName().equals(firstName)) {
                contacts.set(i, updatedContact);
                System.out.println("Contact updated successfully!");
                return;
            }
        }
        System.out.println("Contact not found.");
    }

    public void viewContacts() {
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }

    public void deleteContact(String firstName) {
        for (int i = 0; i < contacts.size(); i++) {
            Contact c = contacts.get(i);
            if (c.getFirstName().equals(firstName)) {
                contacts.remove(i);
                System.out.println("Contact deleted successfully!");
                return;
            }
        }
        System.out.println("Contact not found.");
    }
}

public class AddressBookSystem {
    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final String PHONE_REGEX = "^[0-9]{10}$";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AddressBook addressBook = new AddressBook();

        System.out.println("Welcome to Address Book Program");

        while (true) {
            System.out.println("Choose an option: \n1. Add Contact \n2. Edit Contact \n3. View Contacts \n4. Delete Contact \n5. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addContact(scanner, addressBook);
                    break;
                case 2:
                    System.out.println("Enter the first name of the contact to edit:");
                    String name = scanner.nextLine();

                    Contact updatedContact = getContactDetails(scanner);
                    addressBook.editContact(name, updatedContact);
                    break;
                case 3:
                    addressBook.viewContacts();
                    break;
                case 4:
                    System.out.println("Enter the first name of the contact to delete:");
                    name = scanner.nextLine();
                    addressBook.deleteContact(name);
                    break;
                case 5:
                    System.out.println("Exiting Address Book Program. Goodbye!");
                    scanner.close();
                    return;
            }
        }
    }

    private static void addContact(Scanner scanner, AddressBook addressBook) {
        Contact newContact = getContactDetails(scanner);
        if (addressBook.addContact(newContact)) {
            System.out.println("Contact added successfully!");
        } else {
            System.out.println("Duplicate contact found. Contact not added.");
        }
    }

    private static Contact getContactDetails(Scanner scanner) {
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

        String phoneNumber;
        while (true) {
            System.out.println("Enter Phone Number (10 digits):");
            phoneNumber = scanner.nextLine();
            if (Pattern.matches(PHONE_REGEX, phoneNumber)) {
                break;
            }
            System.out.println("Invalid phone number format. Please try again.");
        }

        String email;
        while (true) {
            System.out.println("Enter Email:");
            email = scanner.nextLine();
            if (Pattern.matches(EMAIL_REGEX, email)) {
                break;
            }
            System.out.println("Invalid email format. Please try again.");
        }

        return new Contact(firstName, lastName, address, city, state, zip, phoneNumber, email);
    }
}