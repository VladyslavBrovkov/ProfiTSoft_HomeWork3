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
}
