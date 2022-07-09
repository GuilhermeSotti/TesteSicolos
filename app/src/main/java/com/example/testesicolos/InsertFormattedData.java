package com.example.testesicolos;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;

enum ExampleData {

    // 3 Tabela de Dados fictícios
    NAME("Fernando Barros", "Ligia Melo", "Jefferson Amaral", "Emerson Giovaneli"),
    EMAIL("fernando.barros@sicolos.com.br", "ligia.melo@sicolos.com.br",
                  "jefferson.amaral@sicolos.com.br", "emerson.giovaneli@sicolos.com.br"),
    RESULT("Rejeitado","Aprovado","Rejeitado","Rejeitado");

    private static ArrayList<String> list;

    ExampleData(@NonNull String... list) {
        for (String value: list){
            if(value.isEmpty()){
                list = "NULL"; // Completando o resultado final caso o dado esteja nulo
            }
        }
        getList().addAll(Arrays.asList(list)); // Transforma cada tabela eu um ArrayList<String>()
    }

    public ArrayList<String> getList(){
        return list; // Retorna ArrayList da tabela desejada
    }
}
public class InsertFormattedData {

    public static void main(String[] args) {

        ArrayList<String> result = new ArrayList<>();
        int index = 0;
        // Iniciando valores para o Loop
        
        for (String list: getData("NAME")){ // Separando cada dado da tabela (Cada vetor do Array("NAME") fará o loop separadamente)
            result.add(list + ";");
            result.add(getData("EMAIL").get(index) + ";");
            result.add(getData("RESULT").get(index) + "||"); // Fazendo o insert de todos dados com o mesmo id do vetor e adicionando os separadores ";" e "||"
            index++;
        }
        Log.println(Log.INFO, "InsertFormattedData", "Result List: " + result); // print
    }

    private static ArrayList<String> getData(String data){
        return ExampleData.valueOf(data).getList(); // função que seleciona a tabela dada no parâmetro e retorna em ArrayList<String>()
    }
}
