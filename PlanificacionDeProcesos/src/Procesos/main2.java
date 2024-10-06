package Procesos;

import java.util.Scanner;

public class main2 {
    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);

        int memTotal, n, quantum;
        int idProces;
        int contadorTotal = 0;

        Queue aux = new Queue();
        Queue procesosListos = new Queue();
        Queue RAM = new Queue();
        Queue CPU = new Queue();
        
        planificador RoundRobin = new planificador();

        System.out.println("\t---------Simulador de Planificación de Procesos RoundRobin---------\n");
        System.out.print("Ingrese la memoria RAM total disponible: ");
        memTotal = scanf.nextInt();
        System.out.print("Ingrese el Quantum del Procesador: ");
        quantum = scanf.nextInt();
        System.out.print("Ingrese el numero de procesos: ");
        n = scanf.nextInt();
        int ref = n; 
        scanf.nextLine();

        for(int i=0; i<n; i++){
            idProces = i;
            System.out.print("Ingrese el nombre del Proceso "+ (i+1) +": ");
            String nombre = scanf.nextLine();
            System.out.print("Ingrese el tiempo de ejecución (Rafaga) del Proceso "+ (i+1) +": ");
            int time = scanf.nextInt();
            System.out.print("Ingrese el tiempo de llegada del Proceso "+ (i+1) +": ");
            int tLlegada = scanf.nextInt();
            System.out.print("Ingrese el espacio en memoria que ocupa el Proceso "+ (i+1) +": ");
            int memory = scanf.nextInt();
            scanf.nextLine();
            
            Node nuevo = new Node(nombre, memory, tLlegada, time, 0, idProces, false);
            aux.priorEnqueue(nuevo);
        }
        RAM.setramTotal(memTotal);

        System.out.println("\nQuantum = "+ quantum + "\nMemoria RAM disponible: "+ memTotal);
        System.out.print("Procesos ingresados: ");
        aux.display(); 
        while(!aux.isEmpty() || !procesosListos.isEmpty() || !RAM.isEmpty() || !CPU.isEmpty()){
            System.out.println("\n============Tiempo del sistema: "+contadorTotal+"[ms]============");
            RoundRobin.moverAuxListo(aux, procesosListos, contadorTotal);
            if (!procesosListos.isEmpty()) {
                RoundRobin.moverListoRam(procesosListos, RAM);
            }
            if (!RoundRobin.cpuVacio(RAM) || !RoundRobin.cpuVacio(CPU)) {
                if (RoundRobin.cpuVacio(CPU)) {
                    RoundRobin.moverRamCpu(RAM, CPU, contadorTotal);
                    int x = RoundRobin.CPUproces(CPU, procesosListos, quantum, RAM, contadorTotal);
                    if (x==1) {
                        CPU.dequeue();
                    }
                }else{
                    int x = RoundRobin.CPUproces(CPU, procesosListos, quantum, RAM, contadorTotal);
                    if (x==1 && !RAM.isEmpty()) {
                        RoundRobin.moverRamCpu(RAM, CPU, contadorTotal);
                        RoundRobin.CPUproces2(CPU, procesosListos, quantum, RAM, contadorTotal);
                    }
                }
            }
            System.out.print("\n ColaListos: ");
            procesosListos.display();
            System.out.print(" RAM: ");
            RAM.display();
            System.out.print("\n"+ RAM.getramTotal() + "[mb] libres en RAM");
            contadorTotal =contadorTotal + 1;
        }
        float tEjec = (RoundRobin.getTejecución()/ref);
        float tEsp = (RoundRobin.getTespera()/ref);
        float tResp = (RoundRobin.getTrespuesta()/ref);

        System.out.println("\n\tTejecución = "+ tEjec +"[ms]");
        System.out.println("\tTespera = "+ tEsp +"[ms]");
        System.out.println("\tTrespuesta = "+ tResp +"[ms]");
    }
}

