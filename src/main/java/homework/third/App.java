package homework.third;

import homework.third.Task1.ViolationStatisticUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class App {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        //Task1
        int numberOfThreads = 8;
        ViolationStatisticUtil.writeFineStatisticToFile(new File("src/main/resources/Task1Files/ViolationFiles/"),
                new File("src/main/resources/Task1Files/violationStatParallel.xml"), numberOfThreads);
    }
//    Program Results Data:
//    Without parallel Threads | Program Time: 9.616
//    Threads Number: 2 | Program Time: 6.994
//    Threads Number: 4 | Program Time: 5.297
//    Threads Number: 8 | Program Time: 4.843
}
