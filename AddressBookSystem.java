import java.util.*;
import java.util.regex.Pattern;
import java.io.*;

class Contact implements Comparable<Contact> {
    public String firstName;
    public String lastName;
    public String address;
    public String city;
    public String state;
    public String zip;
    public String phoneNumber;
    public String email;

    public Contact(String firstName, String lastName, String address, String city, String state, String zip,
            String phoneNumber, String email) {
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

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Contact contact = (Contact) obj;
        return firstName.equalsIgnoreCase(contact.firstName) &&
                lastName.equalsIgnoreCase(contact.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName.toLowerCase(), lastName.toLowerCase());
    }

    @Override
    public int compareTo(Contact other) {
        String fullName = this.firstName + " " + this.lastName;
        String otherFullName = other.firstName + " " + other.lastName;
        return fullName.compareToIgnoreCase(otherFullName);
    }

}

class AddressBook {
    public List<Contact> contacts;

    public AddressBook() {
        this.contacts = new ArrayList<>();
    }

    public boolean addContact(Contact contact) {
        if (!contacts.contains(contact)) {
            contacts.add(contact);
            return true;
        }
        return false;
    }

    public boolean isDuplicate(Contact newContact) {
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

    public List<Contact> getContactsByCity(String city) {
        List<Contact> contactsInCity = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.getCity().equalsIgnoreCase(city)) {
                contactsInCity.add(contact);
            }
        }
        return contactsInCity;
    }

    public List<Contact> getContactsByState(String state) {
        List<Contact> contactsInState = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.getState().equalsIgnoreCase(state)) {
                contactsInState.add(contact);
            }
        }
        return contactsInState;
    }

    public void sortContactsByName() {
        Collections.sort(contacts);
    }

    public void sortContactsByCity() {
        Collections.sort(contacts, Comparator.comparing(Contact::getCity));
    }

    public void sortContactsByState() {
        Collections.sort(contacts, Comparator.comparing(Contact::getState));
    }

    public void sortContactsByZip() {
        Collections.sort(contacts, Comparator.comparing(Contact::getZip));
    }

}

class AddressBookManager {
    public Map<String, AddressBook> addressBookMap;
    public Map<String, List<Contact>> cityToContactsMap;
    public Map<String, List<Contact>> stateToContactsMap;
    private static final String FILE_PATH = "address_book.txt";

    public AddressBookManager() {
        addressBookMap = new HashMap<>();
        cityToContactsMap = new HashMap<>();
        stateToContactsMap = new HashMap<>();
    }

    public void addAddressBook(String name) {
        if (!addressBookMap.containsKey(name)) {
            addressBookMap.put(name, new AddressBook());
            System.out.println("Address Book '" + name + "' added successfully.");
        } else {
            System.out.println("An Address Book with this name already exists.");
        }
    }

    public AddressBook getAddressBook(String name) {
        return addressBookMap.get(name);
    }

    public void listAddressBooks() {
        if (addressBookMap.isEmpty()) {
            System.out.println("No Address Books available.");
        } else {
            System.out.println("Available Address Books:");
            for (String name : addressBookMap.keySet()) {
                System.out.println("- " + name);
            }
        }
    }

    public List<Contact> searchByCity(String city) {
        List<Contact> foundContacts = new ArrayList<>();
        for (AddressBook addressBook : addressBookMap.values()) {
            foundContacts.addAll(addressBook.getContactsByCity(city));
        }
        return foundContacts;
    }

    public List<Contact> searchByState(String state) {
        List<Contact> foundContacts = new ArrayList<>();
        for (AddressBook addressBook : addressBookMap.values()) {
            foundContacts.addAll(addressBook.getContactsByState(state));
        }
        return foundContacts;
    }

    public void viewPersonsByCity(String city) {
        List<Contact> contacts = cityToContactsMap.getOrDefault(city, Collections.emptyList());
        if (contacts.isEmpty()) {
            System.out.println("No contacts found in " + city);
        } else {
            for (Contact contact : contacts) {
                System.out.println(contact);
            }
        }
    }

    public void viewPersonsByState(String state) {
        List<Contact> contacts = stateToContactsMap.getOrDefault(state, Collections.emptyList());
        if (contacts.isEmpty()) {
            System.out.println("No contacts found in " + state);
        } else {
            for (Contact contact : contacts) {
                System.out.println(contact);
            }
        }
    }

    public void addContactToCityAndStateMaps(Contact contact) {
        cityToContactsMap.computeIfAbsent(contact.getCity(), k -> new ArrayList<>()).add(contact);
        stateToContactsMap.computeIfAbsent(contact.getState(), k -> new ArrayList<>()).add(contact);
    }

    public int getContactCountByCity(String city) {
        return cityToContactsMap.getOrDefault(city, Collections.emptyList()).size();
    }

    public int getContactCountByState(String state) {
        return stateToContactsMap.getOrDefault(state, Collections.emptyList()).size();
    }

    public void saveContactsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (AddressBook addressBook : addressBookMap.values()) {
                for (Contact contact : addressBook.contacts) {
                    writer.println(contactToFileString(contact));
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public void loadContactsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Contact contact = fileStringToContact(line);
                AddressBook defaultBook = addressBookMap.getOrDefault("default", new AddressBook());
                defaultBook.addContact(contact);
                addressBookMap.putIfAbsent("default", defaultBook);
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
    }

    private String contactToFileString(Contact contact) {
        return contact.firstName + "," + contact.lastName + "," + contact.address + "," +
               contact.city + "," + contact.state + "," + contact.zip + "," +
               contact.phoneNumber + "," + contact.email;
    }

    private Contact fileStringToContact(String line) {
        String[] data = line.split(",");
        return new Contact(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7]);
    }
}

public class AddressBookSystem {
    public static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String PHONE_REGEX = "^[0-9]{10}$";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AddressBookManager manager = new AddressBookManager();

        manager.loadContactsFromFile(); 
        System.out.println("Welcome to Address Book Program");

        while (true) {
            System.out.println(
                "Choose an option: \n1. Add Address Book \n2. Select Address Book \n3. List Address Books \n4. Search Across Address Books \n5. View Persons by City or State \n6. Get Contact Count by City or State \n7. Sort Address Book \n8. Save Contacts to File \n9. Load Contacts from File \n10. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter Name for new Address Book:");
                    String bookName = scanner.nextLine();
                    manager.addAddressBook(bookName);
                    break;
                case 2:
                    System.out.println("Enter Name of Address Book to select:");
                    bookName = scanner.nextLine();
                    AddressBook selectedBook = manager.getAddressBook(bookName);
                    if (selectedBook != null) {
                        manageAddressBook(scanner, selectedBook);
                    } else {
                        System.out.println("Address Book not found.");
                    }
                    break;
                case 3:
                    manager.listAddressBooks();
                    break;
                case 4:
                    searchAcrossAddressBooks(scanner, manager);
                    break;
                case 5:
                    viewPersonsByCityOrState(scanner, manager);
                    break;
                case 6:
                    getContactCountByCityOrState(scanner, manager);
                    break;
                case 7:
                    System.out.println("Enter Name of Address Book to sort:");
                    String bookName1 = scanner.nextLine();
                    AddressBook selectedBook1 = manager.getAddressBook(bookName1);
                    if (selectedBook1 != null) {
                        sortAddressBook(scanner, selectedBook1);
                    } else {
                        System.out.println("Address Book not found.");
                    }
                    break;
                    case 8:
                        manager.saveContactsToFile();
                        System.out.println("Contacts saved to file successfully.");
                        break;
                    case 9:
                        manager.loadContactsFromFile();
                        System.out.println("Contacts loaded from file successfully.");
                        break;
                    case 10:
                        System.out.println("Exiting Address Book Program. Goodbye!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid option! Please choose again.");
                        break;
            }
        }
    }

    public static void manageAddressBook(Scanner scanner, AddressBook addressBook) {
        while (true) {
            System.out.println(
                    "Choose an option: \n1. Add Contact \n2. Edit Contact \n3. View Contacts \n4. Delete Contact \n5. Return to Main Menu");
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
                    return;
                default:
                    System.out.println("Invalid option! Please choose again.");
                    break;
            }
        }
    }

    public static void addContact(Scanner scanner, AddressBook addressBook) {
        Contact newContact = getContactDetails(scanner);
        if (addressBook.addContact(newContact)) {
            System.out.println("Contact added successfully!");
        } else {
            System.out.println("Duplicate contact found. Contact not added.");
        }
    }

    public static Contact getContactDetails(Scanner scanner) {
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

    public static void searchAcrossAddressBooks(Scanner scanner, AddressBookManager manager) {
        System.out.println("Choose an option: \n1. Search by City \n2. Search by State");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.println("Enter City:");
                String city = scanner.nextLine();
                List<Contact> contactsInCity = manager.searchByCity(city);
                if (contactsInCity.isEmpty()) {
                    System.out.println("No contacts found in " + city);
                } else {
                    System.out.println("Contacts found in " + city + ":");
                    for (Contact contact : contactsInCity) {
                        System.out.println(contact);
                    }
                }
                break;
            case 2:
                System.out.println("Enter State:");
                String state = scanner.nextLine();
                List<Contact> contactsInState = manager.searchByState(state);
                if (contactsInState.isEmpty()) {
                    System.out.println("No contacts found in " + state);
                } else {
                    System.out.println("Contacts found in " + state + ":");
                    for (Contact contact : contactsInState) {
                        System.out.println(contact);
                    }
                }
                break;
            default:
                System.out.println("Invalid option! Please choose again.");
                break;
        }
    }

    public static void addContact(Scanner scanner, AddressBook addressBook, AddressBookManager manager) {
        Contact newContact = getContactDetails(scanner);
        if (addressBook.addContact(newContact)) {
            manager.addContactToCityAndStateMaps(newContact);
            System.out.println("Contact added successfully!");
        } else {
            System.out.println("Duplicate contact found. Contact not added.");
        }
    }

    public static void viewPersonsByCityOrState(Scanner scanner, AddressBookManager manager) {
        System.out.println("Choose an option: \n1. View by City \n2. View by State");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.println("Enter City:");
                String city = scanner.nextLine();
                manager.viewPersonsByCity(city);
                break;
            case 2:
                System.out.println("Enter State:");
                String state = scanner.nextLine();
                manager.viewPersonsByState(state);
                break;
            default:
                System.out.println("Invalid option! Please choose again.");
                break;
        }
    }

    public static void getContactCountByCityOrState(Scanner scanner, AddressBookManager manager) {
        System.out.println("Choose an option: \n1. Count by City \n2. Count by State");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.println("Enter City:");
                String city = scanner.nextLine();
                int cityCount = manager.getContactCountByCity(city);
                System.out.println("Number of contacts in " + city + ": " + cityCount);
                break;
            case 2:
                System.out.println("Enter State:");
                String state = scanner.nextLine();
                int stateCount = manager.getContactCountByState(state);
                System.out.println("Number of contacts in " + state + ": " + stateCount);
                break;
            default:
                System.out.println("Invalid option! Please choose again.");
                break;
        }
    }

    public static void sortAddressBookByName(Scanner scanner, AddressBook addressBook) {
        addressBook.sortContactsByName();
        System.out.println("Address Book sorted by name:");
        addressBook.viewContacts();
    }

    public static void sortAddressBook(Scanner scanner, AddressBook addressBook) {
        System.out.println("Choose an option: \n1. Sort by Name \n2. Sort by City \n3. Sort by State \n4. Sort by Zip");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                addressBook.sortContactsByName();
                break;
            case 2:
                addressBook.sortContactsByCity();
                break;
            case 3:
                addressBook.sortContactsByState();
                break;
            case 4:
                addressBook.sortContactsByZip();
                break;
            default:
                System.out.println("Invalid option! Please choose again.");
                return;
        }
        System.out.println("Sorted Address Book:");
        addressBook.viewContacts();
    }
}