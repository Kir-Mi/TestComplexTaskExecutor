import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ComplexTaskExecutor {
    private final ComplexTask task;
    private final CyclicBarrier barrier;

    public ComplexTaskExecutor(int numberOfTasks) {
        this.task = new ComplexTask();
        this.barrier = new CyclicBarrier(numberOfTasks);
    }

    public void executeTasks(int numberOfTasks) {
        ExecutorService executor = Executors.newFixedThreadPool(numberOfTasks);
        for (int i = 0; i < numberOfTasks; i++) {
            executor.execute(() -> {
                task.execute();
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        executor.shutdown();
    }
}
