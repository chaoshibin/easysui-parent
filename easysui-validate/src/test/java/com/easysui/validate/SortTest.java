package com.easysui.validate;

import java.util.Arrays;

/**
 * <p>
 *
 * </p>
 *
 * @since 2019/7/5
 **/
public class SortTest {
    public static void main(String[] args) {
        System.out.println(~(-1L << 5));
        int[] src = {43, 435, 532, 4, 53, 7456, 2342, 33, 231};
        System.out.println("bubbleSort = " + Arrays.toString(bubbleSort(src)));
        System.out.println("selectionSort = " + Arrays.toString(selectionSort(src)));
        System.out.println("insertSort = " + Arrays.toString(insertSort(src)));
    }

    public static int[] bubbleSort(int[] src) {
        int[] arr = Arrays.copyOf(src, src.length);
        for (int i = 0; i < arr.length; i++) {
            //退出排序标志，当数组基本有序时可快速退出
            boolean flag = true;
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    /*
                     *  a与b替换，不使用临时变量
                     *  a = a + b;
                     *  b = a - b;
                     *  a = a - b;
                     */
                    arr[j] = arr[j] + arr[j + 1];
                    arr[j + 1] = arr[j] - arr[j + 1];
                    arr[j] = arr[j] - arr[j + 1];
                    flag = false;
                }
            }
            if (flag) {
                break;
            }
        }
        return arr;
    }

    public static int[] selectionSort(int[] src) {
        int[] arr = Arrays.copyOf(src, src.length);
        for (int i = 0; i < arr.length - 1; i++) {
            //记录最小值的数组下标
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[min] > arr[j]) {
                    min = j;
                }
            }
            //交换值
            if (min != i) {
                arr[i] = arr[i] + arr[min];
                arr[min] = arr[i] - arr[min];
                arr[i] = arr[i] - arr[min];
            }
        }
        return arr;
    }

    public static int[] insertSort(int[] src) {
        int[] arr = Arrays.copyOf(src, src.length);
        //arr[0]作为有序序列  arr[1]及之后作为待排序序列
        for (int i = 1; i < arr.length; i++) {
            //待插入数据
            int current = arr[i];
            int j = i;
            while (j > 0 && current < arr[j - 1]) {
                arr[j] = arr[j - 1];
                j--;
            }
            if (j != i) {
                arr[j] = current;
            }
        }
        return arr;
    }
}