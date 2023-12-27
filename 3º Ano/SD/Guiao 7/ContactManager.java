import java.util.HashMap;

public class ContactManager {
    private HashMap<String, Contact> contacts = new HashMap<>();

    public void update(Contact c) {
        synchronized (contacts) {
            contacts.put(c.name(), c);
        }
    }

    public ContactList getContacts() {
        ContactList contactList = new ContactList();
        synchronized (contacts) {
            contactList.addAll(contacts.values());
        }
        return contactList;
    }
}
