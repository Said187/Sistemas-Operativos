package Procesos;


class Node {
    String name;
    int memory;
    int tLlegada;
    int tServicio;
    int rafaga;
    int idProces;
    int tRestante;
    int counter;
    int ultVezCPU;
    boolean responde;
    int primVezCPU;

    Node next;

    public Node(){
        this.next = null;
    }

    public Node(String name, int memory, int tLlegada, int tServicio, int rafaga, int idProces, boolean responde) {
        this.name = name;
        this.memory = memory;
        this.tLlegada = tLlegada;
        this.tServicio = tServicio;
        this.rafaga = rafaga;  
        this.idProces = idProces;
        this.counter = 0;
        this.ultVezCPU = 0;
        this.responde = responde;
        this.primVezCPU = 0;
        this.next = null;
    }

    public Node clone() {
        return new Node(this.name, this.memory, this.tLlegada, this.tServicio, this.rafaga, this.idProces, this.responde);
    }

    public int getLlegada(){
        return tLlegada;
    }

    public int getTServ(){
        return tServicio;
    }

    public int getRafaga(){
        return rafaga;
    }

    public void setRafaga(int i){
        this.rafaga = i;
    }

    public int getMem(){
        return memory;
    }

    public String getName(){
        return name;
    }

    public void setCounter(int i){
        this.counter = i;
    }

    public int getCounter(){
        return counter; 
    }

    public void setUltVez(int i){
        this.ultVezCPU = i;
    }

    public int getUltVez(){
        return ultVezCPU;
    }

    void setResponde(boolean x){
        this.responde = x;
    }

    public boolean getResponde(){
        return responde;
    }

    public void setPrimVezCPU(int i){
        this.primVezCPU = i;
    }

    public int getPrimVezCPU(){
        return primVezCPU;
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
