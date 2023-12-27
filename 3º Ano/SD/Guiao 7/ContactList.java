import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ContactList extends ArrayList<Contact> {

    public void serialize(DataOutputStream out) throws IOException {
        out.writeInt(size());
        for (Contact contact : this) {
            contact.serialize(out);
        }
    }

    public static ContactList deserialize(DataInputStream in) throws IOException {
        ContactList contactList = new ContactList();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            Contact contact = Contact.deserialize(in);
            contactList.add(contact);
        }
        return contactList;
    }
}
