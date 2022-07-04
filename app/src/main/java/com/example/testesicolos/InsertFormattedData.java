package com.example.testesicolos;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;

enum ExampleData {

    NAME("Fernando Barros", "Ligia Melo", "Jefferson Amaral", "Emerson Giovaneli"),
    EMAIL("fernando.barros@sicolos.com.br", "ligia.melo@sicolos.com.br",
                  "jefferson.amaral@sicolos.com.br", "emerson.giovaneli@sicolos.com.br"),
    RESULT("Rejeitado","Aprovado","Rejeitado","Rejeitado");

    private static ArrayList<String> list;

    ExampleData(@NonNull String... list) {
        getList().addAll(Arrays.asList(list));
    }

    public ArrayList<String> getList(){
        return list;
    }
}
public class InsertFormattedData {

    public static void main(String[] args) {

        ArrayList<String> result = new ArrayList<>();
        int index;
        for (String list: getData("NAME")){
            index = 0;
            if (list.isEmpty())
                list = "NULL";
            result.add(list + ";");
            result.add(getData("EMAIL").get(index) + ";");
            result.add(getData("RESULT").get(index) + "||");
            index++;
        }
        Log.println(Log.INFO, "InsertFormattedData", "Result List: " + result);
    }

    private static ArrayList<String> getData(String data){
        return ExampleData.valueOf(data).getList();
    }
}
