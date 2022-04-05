import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static AtomicInteger counter =new AtomicInteger(0);

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            var local_counter = counter;

            while (local_counter.get() < 10) {
                if (local_counter != counter) {
                    System.out.println("[T1] Local counter is changed ");
                    local_counter = counter;
                }
            }
        });

        Thread t2 = new Thread(() -> {
           var local_counter = counter;

            while (local_counter.get() < 10) {
                System.out.println("[T2] Incremented counter to " + (local_counter.addAndGet(1) ));
                int newValue =  local_counter.getAndIncrement();
                counter.set(newValue);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
    }
}
