package com.example.testesicolos;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchUserExcel {

    private static List<String> v_cpf = new ArrayList<>();
    private static List<String> v_rg = new ArrayList<>();
    // Inicializando variáveis do resultado 
    
    public static void main(String[] args) {

        List<String> cpf = getV_cpf(); // Lendo cpf (Apenas fictício, no caso pode ser um chamada de um REST, por exemplo)
        for (String eachCpf: getV_cpf()) {
            if (!havePatternCPF(eachCpf)) {
                cpf.clear();
                Log.e("SearchUserExcel", "O cpf " + eachCpf + " não está de acordo com o regras" +
                        " de regex da receita federal."); // Checando se o (os) CPF é válido
            }
        }

        CaptureAgencyAccount captureAgencyAccount;
        if (!getV_cpf().isEmpty()) {
            captureAgencyAccount = new CaptureAgencyAccount(
                    new User(cpf,
                            2,
                            new HashMap<>().put(2, 3),
                            new ArrayList<>()
                    )); // Criando instância do objeto User, serve para facilitar a procura dele no banco de dados
            
            v_cpf = captureAgencyAccount.searchCPF(); // Chamando função que procura o CPF
            v_rg = captureAgencyAccount.searchRg(); // Chamando função que procura o RG

            Log.println(Log.INFO, "SearchUserExcel", "CPF: " + v_cpf + " RG: " + v_rg); // print
        }
    }

    public static List<String> getV_cpf() {
        return v_cpf;
    }

    private static boolean havePatternCPF(String cpf) {
        return cpf != null && cpf.matches("([0-9]{2}[.]?[0-9]{3}[.]?[0-9]{3}[/]?[0-9]{4}" +
                "[-]?[0-9]{2})|([0-9]{3}[.]?[0-9]{3}[.]?[0-9]{3}[-]?[0-9]{2})"); // REGEX para validar CPF
    }

    static class User { // Objeto

        private final List<String> v_data;
        private Integer v_line;
        private Object v_cell;
        private final List<String> v_agency_account;

        User(List<String> v_data, Integer v_line, Object v_cell, List<String> v_agency_account) {
            this.v_data = v_data;
            this.v_line = v_line;
            this.v_cell = v_cell;
            this.v_agency_account = v_agency_account;
        }

        public List<String> getV_data() {
            return v_data;
        }

        public List<String> getV_agency_account() {
            return v_agency_account;
        }

        public Integer getV_line() {
            return v_line;
        }

        public void setV_line(Integer v_line) {
            this.v_line = v_line;
        }

        public Object getV_cell() {
            return v_cell;
        }

        public void setV_cell(Object v_cell) {
            this.v_cell = v_cell;
        }
    }
}

// TODOS: os HashMaps usados são para exemplificar o (CapturaCelula), pois naturalmente tem quase a mesma função
class CaptureAgencyAccount {

    private final SearchUserExcel.User user;
    private boolean nunException = true; // boolean para se o CPF não existir

    public CaptureAgencyAccount(SearchUserExcel.User user) {
        this.user = user; // construtor
    }

    protected List<String> searchCPF(){

        for (String cpf: user.getV_data()) { // garante que todos os cpf passados serão checados no banco de dados
            cpf = new HashMap<>().put(user.getV_line(), 6) + "/"
                    + new HashMap<>().put(user.getV_line(), 7); 

            while (user.getV_cell() != "") {
                if (!user.getV_cell().equals(user.getV_agency_account())) {
                    user.getV_agency_account().add(cpf);
                    nunException = false; // se existir o cpf
                }
                user.setV_line(user.getV_line() + 1);
                user.setV_cell(new HashMap<>().put(user.getV_line(), 3));
            }
            if (nunException) Log.println(Log.INFO, "SearchUserExcel", "Não Existe este CPF" + cpf);
        }

        return user.getV_agency_account();
    }

    protected List<String> searchRg() {
        
        for (String rg : user.getV_data()) { // garante que todos os cpf passados serão checados no banco de dados
            rg = (String) new HashMap<>().put(user.getV_line(), 4);

            while (user.getV_cell() != "") {
                if (!user.getV_cell().equals(user.getV_agency_account())) {
                    user.getV_agency_account().add(rg);
                    nunException = false; // se não existir rg
                }
                user.setV_line(user.getV_line() + 1);
                user.setV_cell(new HashMap<>().put(user.getV_line(), 3));
            }
            if (nunException) Log.println(Log.INFO, "SearchUserExcel", "Não Existe este RG" + rg);
        }

        return user.getV_agency_account();
    }
}
