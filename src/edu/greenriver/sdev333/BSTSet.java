package edu.greenriver.sdev333;

/**
 * Binary Search Tree Set Class
 *
 *  * Author: Dee Brecke
 *  * This class uses a binary search tree set to process data and compare two sets
 *  * using math methods of intersect, union and difference
 *  * The iterator uses a queue to store data
 *
 * @param <KeyType> comparable data type to be compared
 */
public class BSTSet<KeyType extends Comparable<KeyType>> implements MathSet<KeyType>{

    private Node root;

    private class Node{
        private KeyType key;
        private Node left;
        private Node right;
        private int count;

        public Node(KeyType key, int count){
            this.key = key;
            this.count = count;
        }
    }

    /**
     * Puts the specified key into the set.
     *
     * @param key key to be added into the set
     */
    @Override
    public void add(KeyType key) {
        root = put(root, key);
    }

    private Node put(Node current, KeyType key){
        if(current==null){
            return new Node(key, 1);
        }
        int compare = key.compareTo(current.key);
        if(compare < 0){
            current.left = put(current.left, key);
        } else if (compare > 0) {
            current.right = put(current.right, key);
        }else{//this is unnecessary code but a good reminder that if the key is there, no need to do anything
            //current.key = key;
            //do nothing
        }
        current.count = size(current.left) + size(current.right) + 1;
        return current;
    }

    /**
     * Is the key in the set?
     *
     * @param key key to check
     * @return true if key is in the set, false otherwise
     *
     */
    @Override
    public boolean contains(KeyType key) {
        return get(key) !=null;
    }

    /**
     * getter used in other methods to check if a given key is in the set
     * @param key
     * @return key if it is in the set
     */
    public KeyType get(KeyType key) {
    return get(root, key);
    }

    //private helper method for getter
     private KeyType get(Node current, KeyType key){
        if(current==null){
            return null;
        }
         int compare = key.compareTo(current.key);
         if(compare < 0){
             //go left
             return get(current.left, key);
         }else if(compare > 0){
             return get(current.right, key);
         }else {
             return current.key;
         }
     }

    /**
     * Is the set empty?
     *
     * @return true if the set is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Number of keys in the set
     *
     * @return number of keys in the set.
     */
    @Override
    public int size() {
        return size(root);
    }

    //helper method
    private int size(Node current){
        if(current == null){
            return 0;
        }else {
            return current.count;
        }
    }

    /**
     * Determine the union of this set with another specified set.
     * Returns A union B, where A is this set, B is other set.
     * A union B = {key | A.contains(key) OR B.contains(key)}.
     * Does not change the contents of this set or the other set.
     *
     * @param other specified set to union
     * @return the union of this set with other
     */
    @Override
    public MathSet<KeyType> union(MathSet<KeyType> other) {
        MathSet<KeyType> result = new BSTSet<KeyType>();
        //walk through this and put in result
        for (KeyType currentKey: this.keys()) {
            result.add(currentKey);
        }
        //then walk through other and put in result
        //because it's a set, it won't add duplicates
        for(KeyType currentKey : other.keys()){
            result.add(currentKey);
        }
        return result;
    }

    /**
     * Determine the intersection of this set with another specified set.
     * Returns A intersect B, where A is this set, B is other set.
     * A intersect B = {key | A.contains(key) AND B.contains(key)}.
     * Does not change the contents of this set or the other set.
     *
     * @param other specified set to intersect
     * @return the intersection of this set with other
     */
    @Override
    public MathSet<KeyType> intersection(MathSet<KeyType> other) {
        MathSet<KeyType> result = new BSTSet<KeyType>();
        //walk through this and see if they are in other if so put in result
        for (KeyType currentKey: this.keys()) {
            if(other.contains(currentKey)){
                result.add(currentKey);
            }
        }
        return result;
    }

    /**
     * Determine the difference of this set with another specified set.
     * Returns A difference B, where A is this set, B is other set.
     * A difference B = {key | A.contains(key) AND !B.contains(key)}.
     * Does not change the contents of this set or the other set.
     *
     * @param other specified set to difference
     * @return the difference of this set with other
     */
    @Override
    public MathSet<KeyType> difference(MathSet<KeyType> other) {
        MathSet<KeyType> result = new BSTSet<KeyType>();
        //walk through this and see if they are in other if not put in result
        for (KeyType currentKey: this.keys()) {
            if(!other.contains(currentKey)){
                result.add(currentKey);
            }
        }
        return result;
    }

    /**
     * Retrieves a collection of all the keys in this set.
     *
     * @return a collection of all keys in this set
     *
     * carry over from SymbolTables/BST.java
     */
    @Override
    public Iterable<KeyType> keys() {
        //empty queue to hold results
        Queue<KeyType> queue = new Queue<>();
        //start the recursion collecting results in teh queue
        inOrder(root, queue);
        return queue;
    }

    //helper method for iterator
    private void inOrder(Node current, Queue<KeyType> q){
        if(current == null){
            //do nothing -- just exit
            return;
        }
        inOrder(current.left, q);
        q.enqueue(current.key);
        inOrder(current.right, q);
    }

    /**
     * Method to print out results as a string instead of an address
     * useful for testing
     * @return string version of data in set
     */
    public String toString(){
        String output ="";
        for(KeyType key: keys()){
            output += key + ", ";
        }
        return output;
    }
}
