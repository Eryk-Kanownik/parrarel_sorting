import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        String JSON_FILE_CONTENT = "";

        // Get data from file and save to variable
        String stringToSort = "gffdgfdngbnvnbvnv";
        System.out.print("Do you want to load text from file? (y/n): ");
        Scanner ch = new Scanner(System.in);
        String response = ch.next();
        switch (response) {
            case "y": {
                stringToSort = Helpers.loadFromFile();
                break;
            }
        }
        if (stringToSort.length() == 0) {
            System.out.println(
                    "Your text is length of 0. You cannot do the test. Change data.txt or write something to stringToSort variable");
            System.exit(-1);
        }

        char[] chars = stringToSort.toCharArray();
        int strLen = chars.length;
        System.out.println("Loaded string of length: " + strLen);

        // Get thread number

        System.out.print("Up to how many threads you want to use?: ");
        Scanner howManyThreads = new Scanner(System.in);
        int threadNumber = howManyThreads.nextInt();
        if (threadNumber <= 0) {
            System.out.println("Thread number cannot be equal or lesser than 0");
            System.exit(-1);
        }
        JSON_FILE_CONTENT += "{ \"threads_up_to\": " + threadNumber + ", \"characters\": " + strLen
                + ", \"tests\":[";

        // split string chars to separate lists
        for (int test = 1; test <= threadNumber; test++) {
            List<Integer> integerList = new ArrayList<>();
            integerList.add(0);
            for (int j = 1; j <= test; j++) {
                if (j == test) {
                    integerList.add(strLen / test + integerList.get(j - 1)
                            + strLen % test);
                } else {
                    integerList.add(strLen / test + integerList.get(j - 1));
                }
            }

            List<List<Character>> listsOfChars = new ArrayList<>();
            for (int i = 0; i < integerList.size(); i++) {
                List<Character> newList = new ArrayList<>();
                for (int j = integerList.get(i); j < integerList.get(i + 1); j++) {
                    newList.add(chars[j]);
                }
                listsOfChars.add(newList);
                if (i + 1 == integerList.size() - 1) {
                    break;
                }
            }

            // split to threads
            List<Thread> threadList = new ArrayList<>();
            List<SortingThread> sortingThreadList = new ArrayList<>();

            for (int j = 0; j < test; j++) {
                String charsForThread = "";
                for (int k = integerList.get(j); k < integerList.get(j + 1); k++) {
                    charsForThread += chars[k];
                }
                SortingThread st = new SortingThread(j, charsForThread.toCharArray());
                sortingThreadList.add(st);
                Thread th = new Thread(st);
                threadList.add(th);
            }

            // run threads and count time
            double start = System.nanoTime();
            List<char[]> lChar = new ArrayList<>();

            for (int j = 0; j < threadList.size(); j++) {
                threadList.get(j).start();
            }

            for (int j = 0; j < threadList.size(); j++) {
                threadList.get(j).join();
            }

            for (int j = 0; j < sortingThreadList.size(); j++) {
                lChar.add(sortingThreadList.get(j).getChars());
            }

            String result = Helpers.Sort(lChar);
            double end = System.nanoTime();

            double seconds = (end - start);

            JSON_FILE_CONTENT += "{\"threads\":" + test + ",\"time\":" + seconds + "},";
            System.out.println("Threads:" + test + ", Time: " + seconds + ", Result: " + result);
        }
        char[] array = JSON_FILE_CONTENT.toCharArray();
        array[array.length - 1] = ']';
        JSON_FILE_CONTENT = "";
        for (int i = 0; i < array.length; i++) {
            JSON_FILE_CONTENT += array[i];
        }

        JSON_FILE_CONTENT += "}";

        BufferedWriter ow = null;
        ow = new BufferedWriter(new FileWriter("src\\results.json"));
        ow.append(JSON_FILE_CONTENT);
        ow.newLine();
        ow.flush();
        ow.close();

        /*
         * //show indexes
         * System.out.println("Indexes that divides:");
         * for (int i = 0; i < integerList.size(); i++){
         * System.out.print(integerList.get(i) + ", ");
         * }
         * System.out.print("\n");
         * //show listsOfChars
         * System.out.println("Divided Arrays3" +
         * ":");
         * for (int i = 0; i < listsOfChars.size(); i++){
         * String str = "";
         * for (int j = 0; j < listsOfChars.get(i).size(); j++){
         * str += listsOfChars.get(i).get(j);
         * }
         * System.out.println(str);
         * }
         * 
         */
    }
}