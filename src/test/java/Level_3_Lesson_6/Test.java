package Level_3_Lesson_6;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

public class Test {
    private static Methods methods;

    @BeforeAll
    public static void init(){
        methods = new Methods();
    }
//2. Написать метод, которому в качестве аргумента передается не пустой одномерный целочисленный массив.
//    Метод должен вернуть новый массив, который получен путем вытаскивания из исходного массива элементов,
//    идущих после последней четверки. Входной массив должен содержать хотя бы одну четверку, иначе в методе
//    необходимо выбросить RuntimeException. Написать набор тестов для этого метода (по 3-4 варианта входных данных).
//    Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].
//              ----ТЕСТЫ ДЛЯ ЗАДАНИЯ 2----
    @org.junit.jupiter.api.Test
    public void testLastFour1(){
        Integer[] trueTest = {5, 6, 7};
        Assertions.assertArrayEquals(trueTest, methods.lastFour(new int[]{2, 3, 4, 5, 6, 7}));
    }

    @org.junit.jupiter.api.Test
    public void testLastFour2(){
        Integer[] trueTest = {};
        Assertions.assertArrayEquals(trueTest, methods.lastFour(new int[]{2, 3, 5, 6, 7, 4}));
    }

    @org.junit.jupiter.api.Test
    public void testLastFour3(){
        Integer[] trueTest = {};
        Assertions.assertArrayEquals(trueTest, methods.lastFour(new int[]{}));
    }

    @org.junit.jupiter.api.Test
    public void testException(){
        Assertions.assertThrows(RuntimeException.class, () ->{
            methods.lastFour(new int[]{2, 3, 5, 6, 7});
        });
    }

//    3. Написать метод, который проверяет состав массива из чисел 1 и 4. Если в нем нет хоть одной четверки или единицы,
//    то метод вернет false; Написать набор тестов для этого метода (по 3-4 варианта входных данных).
    //              ----ТЕСТЫ ДЛЯ ЗАДАНИЯ 3----

    @org.junit.jupiter.api.Test
    public void testTask3_1(){
        Assertions.assertTrue(methods.foundNum(new int[]{ 1, 1, 1, 4, 4, 1, 4, 4}));
    }

    @org.junit.jupiter.api.Test
    public void testTask3_2(){
        Assertions.assertFalse(methods.foundNum(new int[]{ 1, 1, 1, 1, 1, 1}));
    }

    @org.junit.jupiter.api.Test
    public void testTask3_3(){
        Assertions.assertFalse(methods.foundNum(new int[]{ 4, 4, 4, 4}));
    }

    @org.junit.jupiter.api.Test
    public void testTask3_4(){
        Assertions.assertFalse(methods.foundNum(new int[]{ 1, 4, 4, 1, 1, 4, 3}));
    }
}
