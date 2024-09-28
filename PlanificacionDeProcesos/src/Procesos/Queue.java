package Procesos;

import java.util.NoSuchElementException;


public class Queue {
    private Node front, rear;
    private int size;

    public Queue() {
        this.front = this.rear = null;
        this.size = 0;
    }

    public void enqueue(Node newNode) {
        if (this.rear == null) {
            this.front = this.rear = newNode;
        } else {
            this.rear.next = newNode;
            this.rear = newNode;
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
        System.out.println("Procesos listos para ejecución");
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


