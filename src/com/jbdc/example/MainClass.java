package com.jbdc.example;

import java.util.ArrayList;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Model se = new Model();
        //se.selectOne(sc.next());
        //se.insertOne(sc.nextInt(),sc.next(),sc.next(),sc.next());
        //se.updateOne(sc.nextInt(),sc.next(),sc.next());
        //se.deleteOne(sc.nextInt());
        int i =1;
        ArrayList<EmployeeVO> list = se.selectTwo();
        for(EmployeeVO vo : list){
            System.out.println(i++ +". "+ vo.toString());
        }
    }
}
