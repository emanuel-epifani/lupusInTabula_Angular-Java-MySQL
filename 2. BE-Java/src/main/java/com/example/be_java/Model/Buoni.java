package com.example.be_java.Model;

public class Buoni extends  Personaggio{
    private static int countBuoni;

    public Buoni(String nome, boolean isAlive, boolean isProtected) {
        super(nome, isAlive, isProtected);
        countBuoni++;
    }

    public static int getCountBuoni() {
        return countBuoni;
    }

    public static void setCountBuoni(int countBuoni) {
        Buoni.countBuoni = countBuoni;
    }
}