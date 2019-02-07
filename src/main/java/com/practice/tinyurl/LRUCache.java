package com.practice.tinyurl;

import com.practice.tinyurl.model.TinyUrl;

import java.util.HashMap;

public class LRUCache {

    private int capacity = 0;
    private HashMap<String, Node> map;
    private Node head = null;
    private Node current = null;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
    }

    private void removeNode(Node node) {
        if (node == this.head) {
            if (this.current == this.head) {
                this.current = this.head.next;
            }
            this.head = this.head.next;
        } else {

            if (this.current == node) {
                this.current = node.prev;
                node.prev.next = null;
                return;
            }
            node.prev.next = node.next;
            if (node.next != null)
                node.next.prev = node.prev;
        }
        return;
    }

    private Node addNode(String key, TinyUrl value) {
        Node node = new Node(key, value);
        if (this.head == null) {
            this.head = node;
            this.current = node;
        } else {
            if (this.current != null)
                this.current.next = node;
            node.prev = this.current;
            this.current = node;
        }
        return node;
    }

    private void removeHead() {
        if (this.head.next == null) {
            this.head = null;
            this.current = null;
            return;
        }
        Node node = this.head.next;
        node.prev = null;
        this.head = node;
        return;
    }

    public TinyUrl get(String key) {
        //get from hashmap the value
        //use value to fetch node from linked list
        //remove node from linked list
        //add node to end of linked list
        //update hashmap entry with the latest index;
        if (!map.containsKey(key))
            return null;
        Node node = map.get(key);
        TinyUrl cachedValue = node.value;
        removeNode(node);
        this.map.put(key, addNode(key, cachedValue));
        return cachedValue;
    }

    public void remove(String key) {
        //get from hashmap the value
        //use value to fetch node from linked list
        //remove node from linked list
        //add node to end of linked list
        //update hashmap entry with the latest index;
        if (!map.containsKey(key))
            return;
        Node node = map.get(key);
        removeNode(node);
        return;
    }

    public void put(String key, TinyUrl value) {
        //check for size of hashmap.size() <= capacity
        //add value to the end of ll
        //add entry to hashmap
        if (map.containsKey(key)) {
            removeNode(map.get(key));
            this.map.put(key, addNode(key, value));
            return;
        }

        if (map.size() == this.capacity) {
            //remove from map
            this.map.remove(this.head.key);
            //remove the head element
            removeHead();
        }
        this.map.put(key, addNode(key, value));
        return;
    }

    class Node {
        public String key;
        public TinyUrl value;
        public Node prev;
        public Node next;

        public Node(String key, TinyUrl value) {
            this.key = key;
            this.value = value;
            this.next = null;
            this.prev = null;
        }
    }
}

