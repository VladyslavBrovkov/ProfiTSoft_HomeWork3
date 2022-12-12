package homework.third.Task1;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import homework.third.Task1.exception.EmptyFolderException;
import homework.third.Task1.model.Violation;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;

import static java.util.stream.Collectors.toList;

/**
 * Utility class for violation's statistic calculation
 */
public class ViolationStatisticUtil {

    /**
     * method for calculating and writing Statistic to file without uploading whole files to memory
     *
     * @param folderIn        - folder's path with all violation files in
     * @param fileOut         - statistic file's path
     * @param numberOfThreads - number of Threads for parallel calculating
     */
    public static void writeFineStatisticToFile(File folderIn, File fileOut, Integer numberOfThreads) throws IOException, ExecutionException, InterruptedException {
        List<File> fileList = Arrays.stream(Objects.requireNonNull(folderIn.listFiles()))
                .filter(f -> f.isFile() && f.getName().endsWith("_Violations.json"))
                .collect(toList());
        if (fileList.isEmpty()) {
            throw new EmptyFolderException("Folder has no appropriate files");
        }
        XmlMapper xmlMapper = JacksonXmlMapper.getXmlMapper();
        try (Writer writer = new FileWriter(fileOut)) {
            writer.write(xmlMapper.writer().withRootName("TotalFine")
                    .writeValueAsString(calculateFineParallel(fileList, numberOfThreads)));
        }
    }

    /**
     * method for calculating total Statistic with Parallel Threads
     *
     * @param fileList - list of violation files
     */
    private static List<Violation> calculateFineParallel(List<File> fileList, Integer numberOfThreads) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(numberOfThreads);
        Map<String, BigDecimal> allFilesResult = new ConcurrentHashMap<>();
        var futuresArray = fileList.stream()
                .map((file) -> createFutureForOneFile(file, threadPool, allFilesResult))
                .toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futuresArray).get();
        threadPool.shutdown();
        return allFilesResult.entrySet().stream()
                .map((m) -> new Violation(m.getKey(), m.getValue()))
                .sorted(Comparator.comparing(Violation::getFineAmount).reversed())
                .collect(toList());
    }


    /**
     * method for creating CompletableFuture for each File
     *
     * @param file           - one JSON file with violation data
     * @param threadPool     - custom threadPool
     * @param allFilesResult - map for total result
     */
    private static CompletableFuture<Void> createFutureForOneFile(File file, ExecutorService threadPool, Map<String, BigDecimal> allFilesResult) {
        return CompletableFuture.supplyAsync(() -> {
                    try {
                        return calculateSingleJsonFile(file);
                    } catch (IOException e) {
                        threadPool.shutdown();
                        throw new RuntimeException(e);
                    }
                }, threadPool)
                .thenAccept((oneFileResult) ->
                        oneFileResult.forEach((key, val) -> allFilesResult.merge(key, val, BigDecimal::add)));
    }


    /**
     * method for parsing and calculating statistic from one File
     *
     * @param file - one JSON file with violation data
     */
    private static Map<String, BigDecimal> calculateSingleJsonFile(File file) throws IOException {
        Map<String, BigDecimal> oneFileResult = new HashMap<>();
        JsonFactory jsonFactory = new JsonFactory();
        try (InputStream is = new FileInputStream(file);
             JsonParser jsonParser = jsonFactory.createParser(is)) {
            if (jsonParser.nextToken() != JsonToken.START_ARRAY) {
                throw new IllegalStateException("Expected content must be an array");
            }
            while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                Violation violation = calculateSingleViolation(jsonParser);
                String type = violation.getType();
                BigDecimal fineAmount = violation.getFineAmount();
                if (!oneFileResult.containsKey(type)) {
                    oneFileResult.put(type, fineAmount);
                } else {
                    oneFileResult.put(type, oneFileResult.get(type).add(fineAmount));
                }
            }
        }
        return oneFileResult;
    }

    /**
     * method for parsing one Violation object in one JSON file
     *
     * @param jsonParser - JSON parser object
     */
    private static Violation calculateSingleViolation(JsonParser jsonParser) throws IOException {
        if (jsonParser.currentToken() != JsonToken.START_OBJECT) {
            throw new IllegalStateException("Expected content must be an object");
        }
        Violation violation = new Violation();
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String property = jsonParser.getCurrentName();
            jsonParser.nextToken();
            switch (property) {
                case "type" -> violation.setType(jsonParser.getText());
                case "fine_amount" -> violation.setFineAmount(jsonParser.getDecimalValue());
            }
        }
        return violation;
    }

}