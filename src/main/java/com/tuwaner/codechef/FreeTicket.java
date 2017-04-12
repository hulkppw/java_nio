package com.tuwaner.codechef;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by wanglingyun on 2017/4/6.
 */
public class FreeTicket {

    public static void main(String[] args) throws IOException {
        findRoute();
    }

    private static void findRoute(){
        int city = 0;
        int flight = 0;
        int x = 0;
        int y = 0;
        int price = 0;
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(System.in);
            BufferedReader buf = new BufferedReader(inputStreamReader);
            String str = buf.readLine();
            city = Integer.parseInt(str.split(" ")[0]);
            flight = Integer.parseInt(str.split(" ")[1]);
            int[][] a = new int[city][city];

            for (int i = 0; i < flight; i++) {
                String str2 = buf.readLine();
                x = Integer.parseInt(str2.split(" ")[0]);
                y = Integer.parseInt(str2.split(" ")[1]);
                price = Integer.parseInt(str2.split(" ")[2]);
                a[x - 1][y - 1] = price;
                a[y - 1][x - 1] = price;
            }

            for (int k = 0; k < city; k++) {
                for (int i = 0; i < city; i++) {
                    for (int j = 0; j < city; j++) {
                        if (i != j && (a[i][j] > a[i][k] + a[k][j] || a[i][j] == 0) && a[i][k] > 0
                            && a[k][j] > 0) {
                            a[i][j] = a[i][k] + a[k][j];
                        }
                    }
                }
            }
            System.out.println(getMax(city, a));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static long getMax(int city, int[][] a){
        long max = 0;
        for (int i = 0; i < city; i++) {
            for (int j = i + 1; j < city; j++) {
                if (a[i][j] > max) {
                    max = a[i][j];
                }
            }
        }
        return max;
    }
}
