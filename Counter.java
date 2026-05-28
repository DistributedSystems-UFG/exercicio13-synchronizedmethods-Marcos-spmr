// Versão sem sincronização 
class Counter {
    private int c = 0;

    public void increment() {
        c++;
    }

    public void decrement() {
        c--;
    }

    public int value() {
        return c;
    }
}

// Versão com sincronização
class SynchronizedCounter {
    private int c = 0;

    public synchronized void increment() {
        c++;
    }

    public synchronized void decrement() {
        c--;
    }

    public synchronized int value() {
        return c;
    }
}

public class CounterTest {

    public static void main(String[] args) throws InterruptedException {
        final int ITERATIONS = 100000;

        System.out.println("--- Teste 1: Counter SEM sincronização ---");
        Counter unsafeCounter = new Counter();
        runTestUnsafe(unsafeCounter, ITERATIONS);

        System.out.println("\n--- Teste 2: Counter COM sincronização ---");
        SynchronizedCounter safeCounter = new SynchronizedCounter();
        runTestSafe(safeCounter, ITERATIONS);
    }

    // Método para testar a classe não segura
    private static void runTestUnsafe(Counter counter, int iterations) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < iterations; i++) counter.increment();
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < iterations; i++) counter.decrement();
        });

        t1.start();
        t2.start();
        
        t1.join();
        t2.join();

        // O valor esperado é 0, pois incrementamos e decrementamos a mesma quantidade de vezes
        System.out.println("Valor final (Esperado 0): " + counter.value());
    }

    // Método para testar a classe segura
    private static void runTestSafe(SynchronizedCounter counter, int iterations) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < iterations; i++) counter.increment();
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < iterations; i++) counter.decrement();
        });

        t1.start();
        t2.start();
        
        t1.join();
        t2.join();

        System.out.println("Valor final (Esperado 0): " + counter.value());
    }
}