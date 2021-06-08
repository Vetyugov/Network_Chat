package Level_3_Lesson_6;

import java.util.ArrayList;

public class Methods {

    public static Integer[] lastFour (int[] array) throws RuntimeException{
        int lastIndex = -1;
        if (array.length == 0) {
            ArrayList<Integer> myArray = new ArrayList<>();
            return myArray.toArray(new Integer[0]);
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 4){
                lastIndex = i;
            }
        }
        if (lastIndex == -1){
            throw new RuntimeException("Исключение");
        }
        ArrayList<Integer> myArray = new ArrayList<>();
        for (int i = lastIndex+1; i <array.length; i++) {
            myArray.add(array[i]);
        }
        return myArray.toArray(new Integer[0]);
    }

    public static boolean foundNum(int[] array){
        boolean isFour = false;
        boolean isOne = false;
        for (int i = 0; i < array.length; i++) {
            if ((array[i] != 1)&&(array[i] != 4)){
                return false;
            }
            if (array[i] == 4){
                isFour = true;
            }
            if (array[i] == 1){
                isOne = true;
            }
        }
        if (isFour && isOne) {
            return true;
        } else return false;
    }
}
