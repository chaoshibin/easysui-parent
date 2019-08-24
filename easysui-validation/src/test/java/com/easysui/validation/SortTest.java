package com.easysui.validation;

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
        int[] src = {43, 435, 532, 4, 53, 7456, 2342, 33, 231};
        System.out.println("bubbleSort = " + Arrays.toString(bubbleSort(src)));
        System.out.println("selectionSort = " + Arrays.toString(selectionSort(src)));
        System.out.println("insertSort = " + Arrays.toString(insertSort(src)));
        System.out.println("quickSort = " + Arrays.toString(quickSort(src, 0, src.length - 1)));
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

    public static int[] quickSort(int[] arr, int left, int rigth) {
        //比较左边找的
        int ll = left;
        //标记右边找的位置
        int rr = rigth;

        int base = arr[left];

        while (ll < rr) {
            //从序列最后后面往前面找，找到第一个比基准数少的那个数，进行交换
            while (ll < rr && arr[rr] >= base) {
                rr--;
            }
            if (ll < rr) {
                int temp = arr[rr];
                arr[rr] = arr[ll];
                arr[ll] = temp;
                ll++;
            }
            //从序列的最前面往后面找，找到第一个比基准数大的那个进行交换
            while (ll < rr && arr[ll] <= base) {
                ll++;
            }
            if (ll < rr) {
                int temp = arr[rr];
                arr[rr] = arr[ll];
                arr[ll] = temp;
                rr--;
            }
            //打印排序过程
            print(arr);
            //发现以45位中心 左边的都比45小，右边的都比45大。到此我们就完成了一遍排序。接下来只要对45的左边和右边做同样的操作 即可
            if (ll > left) {
                quickSort(arr, left, ll - 1);
            }
            if (rr < rigth) {
                quickSort(arr, ll + 1, rigth);
            }
        }
        return arr;
    }

    public static void print(int[] data) {
        System.out.println("排序的过程：");
        for (int datum : data) {
            System.out.print(datum + " ");
        }
        System.out.println();
    }
}