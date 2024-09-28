package Procesos;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanf = new Scanner(System.in);
        int memTotal, memRest;
        int idProces;
        boolean cont = true;

        priorQueue procesosListos = new priorQueue();
        Queue procesosListosEjec = new Queue();

        System.out.println("\t---------Simulador de Planificación de Procesos RoundRobin---------\n");
        System.out.print("Ingrese la memoria RAM total disponible: ");
        memTotal = scanf.nextInt(); 
        System.out.print("Ingrese el Quantum del Procesador: ");
        int quantum = scanf.nextInt();
        System.out.print("Ingrese el numero de procesos: ");
        int n = scanf.nextInt();
        scanf.nextLine();

        for(int i=0; i<n; i++){
            idProces = i;
            System.out.print("Ingrese el nombre del Proceso "+ (i+1) +": ");
            String nombre = scanf.nextLine();
            System.out.print("Ingrese el tiempo de ejecución del Proceso "+ (i+1) +": ");
            int time = scanf.nextInt();
            System.out.print("Ingrese el tiempo de llegada del Proceso "+ (i+1) +": ");
            int tLlegada = scanf.nextInt();
            System.out.print("Ingrese el espacio en memoria que ocupa el Proceso "+ (i+1) +": ");
            int memory = scanf.nextInt();
            scanf.nextLine();
            
            Node nuevo = new Node(nombre, memory, tLlegada, time, idProces);
            procesosListos.enqueue(nuevo);
            procesosListos.display();
        }
        System.out.println("Cola de Procesos Listos(Mediano Plazo)");
        procesosListos.display();

        while(cont){
            Node actual = new Node();
            actual = procesosListos.dequeue();
            procesosListos.display();
            int tamProceso = actual.tamMem();
            if(tamProceso <= memTotal){
                procesosListosEjec.enqueue(actual.clone());
                memTotal -= tamProceso;
                procesosListosEjec.display();
            }else{
                System.out.println("No hay memoria suficiente para el Proceso "+ actual.nameProces());
                procesosListos.enqueue(actual);
                cont = false;
            }
        }
        System.out.println("Cola de Procesos Listos para ejecución(Corto Plazo)");
        procesosListosEjec.display();

    /*    Node enCPU = new Node();
        enCPU = procesosListosEjec.dequeue();
 */
        
        //procesosListosEjec.display();
        procesosListos.display();
    }
}
