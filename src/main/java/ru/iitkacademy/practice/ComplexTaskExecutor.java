package ru.iitkacademy.practice;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class ComplexTaskExecutor {
    private final CyclicBarrier barrier;
    private final ExecutorService executor;
    private final AtomicLong totalResult;

    public ComplexTaskExecutor(int numberOfTasks) {
        this.totalResult = new AtomicLong(0);
        this.barrier = new CyclicBarrier(numberOfTasks, () -> {
            System.out.println("All tasks completed with result "+ totalResult);
        });

        this.executor = Executors.newFixedThreadPool(numberOfTasks);
    }

    public void executeTasks(int numberOfTasks) {
        for (int i = 0; i < numberOfTasks; i++) {
            executor.submit(() -> {
                ComplexTask task = new ComplexTask();
                long taskResult = task.execute();
                totalResult.addAndGet(taskResult);
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }

    public void shutdown() {
        executor.shutdown();
    }
}
