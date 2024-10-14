import java.util.Random;

public class Coche implements Runnable {
    private String nombre;
    private int distanciaRecorrida;
    private static final int DISTANCIA_META = 100;

    public Coche(String nombre) {
        this.nombre = nombre;
        this.distanciaRecorrida = 0;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (distanciaRecorrida < DISTANCIA_META) {
            // Avance aleatorio entre 1 y 10 unidades
            int avance = random.nextInt(10) + 1;
            distanciaRecorrida += avance;

            System.out.println(nombre + " ha recorrido " + distanciaRecorrida + " unidades.");

            // Simula el tiempo que tarda en avanzar
            try {
                Thread.sleep(500); // 500ms de pausa entre movimientos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (distanciaRecorrida >= DISTANCIA_META) {
                System.out.println(nombre + " ha cruzado la meta!");
                break;
            }
        }
    }

    public int getDistanciaRecorrida() {
        return distanciaRecorrida;
    }
}

