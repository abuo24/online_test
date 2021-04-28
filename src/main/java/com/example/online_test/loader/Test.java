package com.example.online_test.loader;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();

//        int a=scanner.nextInt();
//        int max = a;
//        int min = a;
//        for (int i = 1; i < n; i++) {
//            a = scanner.nextInt();
//            if (a>max){
//                max = a;
//            }
//            if (a<min){
//                min = a;
//            }
//        }
//        System.out.println(max+" "+min);

        int q1=0,q2=0,a=0,min1=0,min2=0;

        for (int i = 1; i <= n; i++) {
            a=scanner.nextInt();
            if (i==1||i==2){
                if (i==1) q2=a; else q1=a;
                min1=q1+q2;
            } else {
                q2=q1;
                q1=a;
                if ((min2=q1+q2)>min1){
                    min1=q1+q2;
                }
            }
        }
        System.out.println(min1);

    }
}
