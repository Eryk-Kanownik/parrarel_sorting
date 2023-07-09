public class SortingThread implements Runnable {

    public int id;
    public char[] chars;

    public SortingThread(int id, char[] chars) {
        this.id = id;
        this.chars = chars;
    }

    public char[] getChars() {
        return  this.chars;
    }

    @Override
    public void run() {
        bubbleSort(this.chars);
        System.out.println(this.chars.length);
    }

    static void bubbleSort(char arr[]) {
        int i, j;
        char temp;
        boolean swapped;
        for (i = 0; i < arr.length - 1; i++) {
            swapped = false;
            for (j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            if (swapped == false)
                break;
        }

    }
}
