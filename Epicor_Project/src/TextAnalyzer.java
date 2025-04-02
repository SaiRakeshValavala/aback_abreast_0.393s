import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @Author Sai Rakesh Valavala
 * Text Analyzer class to analyze the given text file and provide the total number of
 * words and the 5 most frequently used words and 50 alphabetically sorted unique words
 */
public class TextAnalyzer {

    // List of Excluded words as defined in the question
    private static final List<String> EXCLUSIONS = Arrays.asList("in", "on", "at", "he", "she", "it", "and", "or", "but", "the", "a", "an", "is", "was");
    public static void main(String[] args) {
        // Start time
        long startTime = System.currentTimeMillis();

        // Path of the sample file
        String path = "C:\\RakeshPersonalData\\Epicor\\RakeshDemo\\src\\moby.txt";

        try {
            // Read the data in the file
            String data = new String(Files.readAllBytes(Paths.get(path)));

            // Convert the data to lowercase for case insensitive comparison
            data = data.toLowerCase();

            // List all the words in the data with non word characters regex as delimiter
            String words[] = data.split("\\W+");

            // Map to store words and their occurrences
            Map<String, Integer> wordCountMap = new HashMap<>();

            // Find all the valid words with their counts
            for(String word : words) {
                if(!word.isEmpty() && !EXCLUSIONS.contains(word) && word.matches("[a-zA-Z]+")) {
                    if (word.endsWith("'s")) {
                        word = word.substring(0, word.length() - 2); // Remove the "'s" suffix
                    }

                    int count = wordCountMap.containsKey(word) ? wordCountMap.get(word) + 1 : 1;
                    wordCountMap.put(word, count);
                }
            }

            int totalWordCount = 0;
            for (int count : wordCountMap.values()) {
                totalWordCount += count;
            }

            // Print valid words count
            System.out.println("Total Words count after excluding the given words is: "+totalWordCount);

            // Convert entries of the wordCountMap to a set view and then typecast it to List for sorting
            List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(wordCountMap.entrySet());

            // Sort entries by value (frequency) in descending order
            sortedEntries.sort((entry1, entry2) -> entry2.getValue() - entry1.getValue());

            System.out.println("\nTop 5 most frequently used words:");
            System.out.println("Word : Count");
            for (int i = 0; i < Math.min(5, sortedEntries.size()); i++) {
                Map.Entry<String, Integer> entry = sortedEntries.get(i);
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }

            System.out.println("\nTop 50 Alphabetically sorted list of all unique words");
            List<String> uniqueWords = new ArrayList<>(wordCountMap.keySet());
            Collections.sort(uniqueWords); // Sort the unique words in alphabetical order

            for (int i = 0; i < Math.min(50, uniqueWords.size()); i++) {
                System.out.println(uniqueWords.get(i));
            }



        } catch (IOException e) {
            System.out.println("\nException while reading the file");
            e.printStackTrace();
        } catch(Exception e) {
            System.out.println("\nException while analyzing the file");
            e.printStackTrace();
        }

        // End time
        long endTime = System.currentTimeMillis();
        double processingTime = (endTime - startTime) / 1000.0; // Convert to seconds
        System.out.println("\nProcessing time: " + processingTime + "s");
    }
}
