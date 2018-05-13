
public class HashLinkedList {
	HashLinkedList nextHash;
    String key;
    String value;
         
    HashLinkedList(String key, String value) 
    {
        this.nextHash = null;
        this.key = key;
        this.value = value;
    }
}
