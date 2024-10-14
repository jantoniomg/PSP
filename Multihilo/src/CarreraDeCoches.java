public class CarreraDeCoches {
    public static void main(String[] args) {
        // Crear e iniciar los hilos de los coches
        Thread coche1 = new Thread(new Coche("Coche 1"));
        Thread coche2 = new Thread(new Coche("Coche 2"));
        Thread coche3 = new Thread(new Coche("Coche 3"));
        Thread coche4 = new Thread(new Coche("Coche 4"));
        Thread coche5 = new Thread(new Coche("Coche 5"));

        coche1.start();
        coche2.start();
        coche3.start();
        coche4.start();
        coche5.start();

        // Esperar a que todos los hilos terminen
        try {
            coche1.join();
            coche2.join();
            coche3.join();
            coche4.join();
            coche5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("La carrera ha terminado!");
    }
}

