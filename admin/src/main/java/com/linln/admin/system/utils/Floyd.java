package com.linln.admin.system.utils;


import java.util.*;

public class Floyd {

    //矩阵阶数
    static int matrixOrder = 6;

    //无穷距离
    static int MD = 999;

    //邻接矩阵
    static int[][] arcs = {
            {0,       4,     6,     MD,    MD,     MD},
            {4,      0,      2,     2,     10,     MD},
            {6,      2,      0,     MD,    2,     MD},
            {MD,      2,     MD,      0,    1,     3},
            {MD,      10,     2,     1,     0,     7},
            {MD,      MD,     MD,      3,    7,     0 }} ;

    //路径记录
    static int[][] path = new int[matrixOrder][matrixOrder];

    static void floyd(){
        //初始化path
        for(int i = 0; i < matrixOrder; i++){
            for(int j = 0; j < matrixOrder; j++){
                if(arcs[i][j] != MD)
                    path[i][j] = j;
                else
                    path[i][j] = -1;
            }
        }
        //算法开始
        for(int k = 0; k < matrixOrder; k++){
            for(int i = 0; i< matrixOrder; i++){
                for (int j = 0; j < matrixOrder; j++){
                    if(arcs[i][k] >= MD || arcs[k][j] >= MD)
                        continue;
                    if(arcs[i][k] + arcs[k][j] < arcs[i][j]){
                        arcs[i][j] = arcs[i][k] + arcs[k][j];
                        path[i][j] = path[i][k];
                    }
                }
            }
        }
    }
    public static List<int[]> getResult() {
        floyd();
        System.out.println("Arcs:");
        List<int[]> strList = new ArrayList<>();
        List<int[]> pathList = new ArrayList<>();
        Map<String,List<int[]>> map = new HashMap<>();
        for(int i = 0; i < matrixOrder; i++){
            int str[]=new int[matrixOrder];
            for(int j = 0; j < matrixOrder; j++){
                str[j] = arcs[i][j];
                System.out.print(arcs[i][j]+"\t");
            }
            strList.add(str);
            System.out.println();
        }
        System.out.println("Path:");
        for(int i = 0; i < matrixOrder; i++){
            int patharr[] = new int[matrixOrder];
            for(int j = 0; j < matrixOrder; j++){
                patharr[j] = path[i][j];
                //System.out.print(path[i][j]+"\t");
            }
            pathList.add(patharr);
            System.out.println();
        }
        return strList;
    }



}
