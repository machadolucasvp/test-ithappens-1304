package com.ithappens.interview.enums;


public enum Tipo {
    ENTRADA, SAIDA;

    public static String getValueOf(Tipo tipo){
        if(tipo==ENTRADA){
            return "entrada";
        }else if(tipo==SAIDA){
            return "saida";
        }
        return "";
    }
}
