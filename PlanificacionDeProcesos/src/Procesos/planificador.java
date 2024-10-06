package Procesos;

import java.util.NoSuchElementException;

public class planificador {
    public float tFinalEjec; 
    public float tFinalResp;
    public float tFinalEsp;
    
    public planificador(){
        this.tFinalEjec = 0;
        this.tFinalResp = 0;
        this.tFinalEsp = 0;
    }
    
    void moverAuxListo(Queue aux, Queue listos, int time) {
        while (!aux.isEmpty() && aux.peek().getLlegada() <= time) {
            Node proceso = aux.dequeue();
            listos.enqueue(proceso.clone());
            System.out.print("---" + proceso.getName() + " llegó en tiempo " + time + "ms a ColaListos: ");
            listos.display();
        }
    }
    
    public int moverListoRam(Queue listos, Queue ram) {
        if (listos.isEmpty()) {
            return 0;
        }
    
        Node proceso = listos.dequeue();
    
        if (proceso.getMem() <= ram.getramTotal()) {
            ram.enqueue(proceso.clone());
            ram.setramTotal(ram.getramTotal() - proceso.getMem());
            System.out.printf("\n ---" + proceso.getName() +"("+proceso.getMem()+"[mb]) subió a la RAM: ");
            ram.display();
            System.out.printf(ram.getramTotal()+ "[mb] restantes en RAM.");
            return 0;
        } else {
            listos.enqueue(proceso.clone());
            System.out.printf("\n ===|No hay RAM suficiente para subir a %s (%dMB)\n", proceso.getName(), proceso.getMem());
            return 1;
        }
    }

    public void moverRamCpu(Queue ram, Queue cpu, int time){ 
        Node copia = ram.peek();
        int subeCPU = copia.getMem();
        System.out.print("---" + copia.getName() + " sale de la RAM");
        ram.dequeue(); 
        ram.setramTotal(ram.getramTotal()+copia.getMem());
        ram.display();
        System.out.printf(ram.getramTotal()+ "[mb] restantes en RAM.");
        cpu.enqueue(copia.clone());
        System.out.print("---" + cpu.peek().getName() + " sube a la CPU: ");
        cpu.display();
    }

    public void moverCpuListo(Queue cpu, Queue listos){
        Node saleDeCPU = cpu.peek();
        System.out.print("---" + saleDeCPU.getName() + " se forma en ColaListos: " );       
        listos.enqueue(saleDeCPU.clone());   
        listos.display();
        cpu.dequeue();
    }    
    
    public int CPUproces(Queue CPU, Queue listos, int quantum, Queue ram, int time){
        Node ejecutando = CPU.peek();

        if(ejecutando.getCounter() == 0){
            ejecutando.setUltVez(time);
        } 
        
        if(!ejecutando.getResponde()){
            ejecutando.setResponde(true);
            ejecutando.setPrimVezCPU(time);
            this.tFinalResp += (ejecutando.getPrimVezCPU() - ejecutando.getLlegada());
        }
        if(ejecutando.getRafaga() == ejecutando.getTServ()){ 
            int sum = (time - ejecutando.getUltVez()); 
            this.setTespera(this.getTespera()+ (((time - sum) - (ejecutando.getRafaga() - sum)) - ejecutando.getLlegada()));
            System.out.print("CPU Esta ejecutando a ");
            CPU.display();
            System.out.print(ejecutando.getName() + "Termino su ejecucion en CPU \n");  
            this.setTejecución((time - ejecutando.getLlegada()) + this.getTejecución());
            CPU.dequeue();
            return 1;
        }else{
            System.out.print("\n  CPU: ");
            CPU.display();
            
            if(ejecutando.getCounter() == quantum){
                System.out.println(ejecutando.getName() + " Finalizo su Quantum y sale de la CPU");   
                CPU.setCon(0); 
                this.moverCpuListo(CPU, listos);
                this.moverListoRam(listos, ram);
                return 1;
            }else{
                int rafagaAcum = ejecutando.getRafaga() + 1;
                ejecutando.setRafaga(rafagaAcum);
                ejecutando.setCounter(ejecutando.getCounter() + 1);     
            }
        }
        return 0;
    }
    
    public int CPUproces2(Queue CPU, Queue listos, int quantum, Queue ram, int time){
        Node ejecutando = CPU.peek();

        if(ejecutando.getCounter() == 0){
            ejecutando.setUltVez(time);
        }
        if(!ejecutando.getResponde()){
            ejecutando.setResponde(true);
            ejecutando.setPrimVezCPU(time);
            this.tFinalResp += (ejecutando.getPrimVezCPU() - ejecutando.getLlegada());
        }
        if(ejecutando.getRafaga() == ejecutando.getTServ()){ 
            CPU.dequeue(); 
            return 1;
        }else{
            if(ejecutando.getCounter() == quantum){                 
                CPU.setCon(0); 
                this.moverCpuListo(CPU, listos);
                this.moverListoRam(listos, ram);
                return 1;
            }else{
                int rafagaAcum = ejecutando.getRafaga() + 1;
                ejecutando.setRafaga(rafagaAcum);
                ejecutando.setCounter(ejecutando.getCounter() + 1);
            }
        }
        return 0;
    }    
    
    public boolean endRR(Queue aux, Queue listos, Queue ram, Queue CPU) {
        try {
            Node a = aux.peek();
            Node b = listos.peek();
            Node c = ram.peek();
            Node d = CPU.peek();
            return a == null && b == null && c == null && d == null;
        } catch (NoSuchElementException e) {
            return true;
        }
    }   
    
    public void setTrespuesta(float i){
        this.tFinalResp = i;
    }
    
    public float getTrespuesta(){
        return tFinalResp;
    }
    
    public void setTejecución(float i){
        this.tFinalEjec = i;
    }
    
    public float getTejecución(){
        return tFinalEjec;
    }
    
    public void setTespera(float i){
        this.tFinalEsp = i;
    }
    
    public float getTespera(){
        return tFinalEsp;
    }
    
    
    public boolean cpuVacio(Queue listaCPU){
        return listaCPU.isEmpty();
    }

    public boolean ramVacio(Queue listaRAM){
        return listaRAM.isEmpty();
    }
    
    public void resetCola(Queue lista){
        lista.enqueue(null);
    }
}
