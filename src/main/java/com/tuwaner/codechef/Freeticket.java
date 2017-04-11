package com.tuwaner.codechef;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by wanglingyun on 2017/4/6.
 */
public class Freeticket {

    public static void main(String[] args) throws IOException {
        int c = 0;
        int f = 0;
        int x = 0;
        int y = 0;
        int l = 0;
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader buf = new BufferedReader(inputStreamReader);
        String str = buf.readLine();

        c = Integer.parseInt(str.split(" ")[0]);
        f = Integer.parseInt(str.split(" ")[1]);
        int[][] a = new int[c][c];

        for (int i = 0; i < f; i++) {
            String str2 = buf.readLine();
            x = Integer.parseInt(str2.split(" ")[0]);
            y = Integer.parseInt(str2.split(" ")[1]);
            l = Integer.parseInt(str2.split(" ")[2]);
            a[x - 1][y - 1] = l;
            a[y - 1][x - 1] = l;
        }

        for (int k = 0; k < c; k++) {
            for (int i = 0; i < c; i++) {
                for (int j = 0; j < c; j++) {
                    if (i != j && (a[i][j] > a[i][k] + a[k][j] || a[i][j] == 0) && a[i][k] > 0
                        && a[k][j] > 0) {
                        a[i][j] = a[i][k] + a[k][j];
                    }
                }
            }
        }

        long max = 0;
        for (int i = 0; i < c; i++) {
            for (int j = i + 1; j < c; j++) {
                if (a[i][j] > max) {
                    max = a[i][j];
                }
            }
        }
        System.out.println(max);
    }
}
