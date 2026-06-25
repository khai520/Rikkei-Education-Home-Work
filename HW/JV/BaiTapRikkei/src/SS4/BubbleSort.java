package SS4;

public class BubbleSort {
    public static void bubbleSortGiam(int[] arr){
        int n = arr.length;
        for (int i = 0 ; i < n - 1 ; i++){
            boolean swpper = false;
            for (int j = 0 ; j < n - 1 - i; j++){
                if (arr[j] < arr[ j + 1]){
                    int temp = arr[j];
                    arr[j] = arr[ j + 1];
                    arr[j + 1 ] = temp;
                    swpper = true;
                }
            }
            if(!swpper) break;
        }
    }
    public static void bubbleSortTang(int[] arr){
        int n = arr.length;
        for (int i = 0 ; i < n - 1 ; i++){
            boolean swpper = false;
            for (int j = 0 ; j < n - 1 - i; j++){
                if (arr[j] > arr[ j + 1]){
                    int temp = arr[j];
                    arr[j] = arr[ j + 1];
                    arr[j + 1 ] = temp;
                    swpper = true;
                }
            }
            if(!swpper) break;
        }
    }

}
