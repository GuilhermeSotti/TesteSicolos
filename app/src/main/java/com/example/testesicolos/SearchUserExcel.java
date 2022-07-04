package com.example.testesicolos;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchUserExcel {

    private static List<String> v_cpf = new ArrayList<>();
    private static List<String> v_rg = new ArrayList<>();

    public static void main(String[] args) {

        List<String> cpf = getV_cpf();
        for (String eachCpf: getV_cpf()) {
            if (!havePatternCPF(eachCpf)) {
                cpf.clear();
                Log.e("SearchUserExcel", "O cpf " + eachCpf + " não está de acordo com o regras" +
                        " de regex da receita federal.");
            }
        }

        CaptureAgencyAccount captureAgencyAccount;
        if (!getV_cpf().isEmpty()) {
            captureAgencyAccount = new CaptureAgencyAccount(
                    new User(cpf,
                            2,
                            new HashMap<>().put(2, 3),
                            new ArrayList<>()
                    ));
            v_cpf = captureAgencyAccount.searchCPF();
            v_rg = captureAgencyAccount.searchRg();

            Log.println(Log.INFO, "SearchUserExcel", "CPF: " + v_cpf + " RG: " + v_rg);
        }
    }

    public static List<String> getV_cpf() {
        return v_cpf;
    }

    private static boolean havePatternCPF(String cpf) {
        return cpf != null && cpf.matches("([0-9]{2}[.]?[0-9]{3}[.]?[0-9]{3}[/]?[0-9]{4}" +
                "[-]?[0-9]{2})|([0-9]{3}[.]?[0-9]{3}[.]?[0-9]{3}[-]?[0-9]{2})");
    }

    static class User {

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

class CaptureAgencyAccount {

    private final SearchUserExcel.User user;
    private boolean nunException = true;

    public CaptureAgencyAccount(SearchUserExcel.User user) {
        this.user = user;
    }

    protected List<String> searchCPF(){

        for (String cpf: user.getV_data()) {
            cpf = new HashMap<>().put(user.getV_line(), 6) + "/"
                    + new HashMap<>().put(user.getV_line(), 7);

            while (user.getV_cell() != "") {
                if (!user.getV_cell().equals(user.getV_agency_account())) {
                    user.getV_agency_account().add(cpf);
                    nunException = false;
                }
                user.setV_line(user.getV_line() + 1);
                user.setV_cell(new HashMap<>().put(user.getV_line(), 3));
            }
            if (nunException) Log.println(Log.INFO, "SearchUserExcel", "Não Existe este CPF" + cpf);
        }

        return user.getV_agency_account();
    }

    public List<String> searchRg() {
        for (String rg : user.getV_data()) {
            rg = (String) new HashMap<>().put(user.getV_line(), 4);

            while (user.getV_cell() != "") {
                if (!user.getV_cell().equals(user.getV_agency_account())) {
                    user.getV_agency_account().add(rg);
                    nunException = false;
                }
                user.setV_line(user.getV_line() + 1);
                user.setV_cell(new HashMap<>().put(user.getV_line(), 3));
            }
            if (nunException) Log.println(Log.INFO, "SearchUserExcel", "Não Existe este RG" + rg);
        }

        return user.getV_agency_account();
    }
}
