import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Helpers {
    public static String Sort(List<char[]> sortedCharsFromThreads){

        List<Character> charList = new ArrayList<>();
        for(int i = 0; i < sortedCharsFromThreads.size();i++) {
            for (int j = 0; j < sortedCharsFromThreads.get(i).length; j++) {
                charList.add(sortedCharsFromThreads.get(i)[j]);
            }
        }

        char[] charArray = new char[charList.size()];
        for(int i = 0; i < charList.size(); i++) {
            charArray[i] = charList.get(i);
        }

        int i, j;
        char temp;
        boolean swapped;
        for (i = 0; i < charArray.length - 1; i++) {
            swapped = false;
            for (j = 0; j < charArray.length - i - 1; j++) {
                if (charArray[j] > charArray[j + 1]) {
                    temp = charArray[j];
                    charArray[j] = charArray[j + 1];
                    charArray[j + 1] = temp;
                    swapped = true;
                }
            }
            if (swapped == false)
                break;
        }

        String str = "";

        for(int k = 0;k < charArray.length; k++){
            str += charArray[k];
        }

        return str;
    }

    public static String loadFromFile() throws IOException {
        File file = new File("src\\data.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String result = "";
        String line;
        while ((line = br.readLine()) != null) {
            result += line;
        }

        return result;
    }
}
