package Level_3_Lesson_4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(() -> {
            long time = System.nanoTime();
            System.out.println("А");
            System.out.println("B");
            System.out.println("C");
            System.out.println("А");
            System.out.println("B");
            System.out.println("C");
            System.out.println("А");
            System.out.println("B");
            System.out.println("C");
            System.out.println("А");
            System.out.println("B");
            System.out.println("C");
            System.out.println(System.nanoTime()-time);

        });
        executorService.shutdown();
    }

}
