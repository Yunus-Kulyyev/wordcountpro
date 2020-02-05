package com.graspery.www.wordcountpro;

import java.util.ArrayList;

public class Word {
    private String word;
    private ArrayList<String> synonyms;
    private ArrayList<String> antonyms;

    public Word(String word){
        this.word = word;
        synonyms = new ArrayList<>();
        antonyms = new ArrayList<>();
    }

    public boolean hasSynonyms() {
        if(synonyms.size() == 0) {
            return false;
        }

        return true;
    }

    public String getWord() {
        return word;
    }

    public void pushSynonym(String syn) {
        if(!hasSynonyms()) {
            synonyms.add(word);
        }
        synonyms.add(syn);
    }

    public ArrayList<String> getSynonyms() {
        return synonyms;
    }

    public String getExpandSyn() {
        String resp = "";
        for(int i = 0; i < synonyms.size(); i++) {
            resp = resp + synonyms.get(i) + ", ";
        }
        return  resp;
    }

    public void pushAntonym(String ant) {
        antonyms.add(ant);
    }

    public ArrayList<String> getAntonyms() {
        return antonyms;
    }
}
