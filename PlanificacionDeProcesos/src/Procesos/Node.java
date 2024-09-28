package Procesos;


class Node {
    String name;
    int memory;
    int tLlegada;
    int tServicio;
    //int priority;
    int idProces;
    int tRestante;

    Node next;

    public Node(){
        this.next = null;
    }

    public Node(String name, int memory, int tLlegada, int tServicio, /*int priority,*/ int idProces) {
        this.name = name;
        this.memory = memory;
        this.tLlegada = tLlegada;
        this.tServicio = tServicio;
        //this.priority = priority;  
        this.idProces = idProces;
        this.next = null;
    }

    public Node clone() {
        return new Node(this.name, this.memory, this.tLlegada, this.tServicio, this.idProces);
    }

    public int tamMem(){
        return memory;
    }

    public String nameProces(){
        return name;
    }

    public void actTServicio(int Quantum){
        this.tServicio = calcTServicio(Quantum);
    }

    private int calcTServicio(int Quantum){
        if(ejecutarQuantum(Quantum) == true){
            return 0;
        }else{
            return tServicio - Quantum;
        }
    
    }

    public boolean ejecutarQuantum(int quantum) {
        tServicio -= quantum;
        if (tServicio <= 0) {
            tServicio = 0;
            return true;
        } else {
            return false; 
        }
    }
}
