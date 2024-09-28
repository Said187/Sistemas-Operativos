package Procesos;

import java.util.NoSuchElementException;

public class priorQueue {
    private Node front, rear;
    private int size;

    public priorQueue() {
        this.front = this.rear = null;
        this.size = 0;
    }

    public void enqueue(Node newNode) {
        if (isEmpty()) {
            front = rear = newNode;
        } else if (newNode.tLlegada >= rear.tLlegada) {
            rear.next = newNode;
            rear = newNode;
        } else { 
            Node current = front;
            Node previous = null;
            while (current != null && newNode.tLlegada > current.tLlegada) {
            previous = current;
            current = current.next;
            }
        if (previous == null) { 
            newNode.next = front;
            front = newNode;
        } else { // 
            newNode.next = current;
            previous.next = newNode;
        }
        }
        size++;
    }

    public Node dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        Node temp = this.front;
        this.front = this.front.next;
        if (this.front == null) {
            this.rear = null;
        }
        size--;
        return temp;
    }

    public Node peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return this.front;
    }

    public void display() {
        Node current = front;
        System.out.println("Procesos listos");
        while (current != null) {
            System.out.print("[" + current.name +/* ", "+ current.tServicio +*/"] " /*+ current.memory + ", Llegada: " + current.tLlegada + ", Servicio: " + current.tServicio + ", Prioridad: " + current.priority*/);
            current = current.next;
        }
        System.out.println("");
    }

    public boolean isEmpty() {
        return this.front == null;
    }

    public int size() {
        return size;
    }
}
