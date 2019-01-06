package graph;
import javax.sound.midi.Soundbank;
import java.util.Iterator;
import java.util.LinkedList;

/** Custom implementation of a hash table using open hashing, separate chaining.
 *  Each key is a String (name of the city), each value is an integer (node id). */
public class HashTable {

	 // FILL IN CODE: add variables and methods

    int num;
    double count;
    LinkedList<HashNode>[] table;
    HashNode[] tempTable;
    public HashTable(int num)
    {
        this.num = num;
        table  = new LinkedList[num];

        for (int i = 0; i < num; i++)
            table[i] = new LinkedList<HashNode>();

    }


    public void insert(String key,int ID) {
        HashNode node = new HashNode(key, ID);

        int hashindex = hashCode(key);

        int modIndex = hashindex % num;
        table[modIndex].addFirst(node);

        //count++;
        ///////
        double num1 = num;
        table[modIndex].addFirst(node);



    }
        public static int hashCode(String s) {
            int i;
            int r = 0;
            char c;
            int a =33;

            for (i = 0; i < s.length(); i++)
            {
                c = s.charAt(i);
                r += (int)c * (a ^ (s.length() - i));


            }
            return r;
        }
    public int find(String key)
    {

        int hashindex = hashCode(key);
        int modIndex = hashindex%num;
        Iterator<HashNode> it = table[modIndex].iterator();
        while (it.hasNext()) {
            HashNode elem = it.next();
            //int key = elem.key();
            if (elem.city.equals(key))
            {
                return elem.index;
            }
            // System.out.println(elem);
        }
        return -1;
    }






}