package poemHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class PoemHelper {

    public static void main(String[] args) {
        new PoemHelper().run();
    }

    private void run() {
        // 1. lépés: szavak file betöltése
        //List<String> words = loadWordsFromFile("hungarian_words/magyar_szavak_listaja_hosszabb.txt");
        List<String> words = loadWordsFromFile("hungarian_words/magyar_szavak_listaja.txt");

        // 2.: A magyar nyelvre jellemző betűstatisztika elkészítése
        Map<Character, Integer> letterStatistics = new TreeMap<>(); //treemap hogy a kulcsok alapján rendezve legyen
        int totalNumberOfLetters = 0;
        for (String word : words) { //words--->lista
            char[] lettersOfWord = word.toCharArray();
            for (char letter : lettersOfWord) {//char tömb
                totalNumberOfLetters++;
                if(letterStatistics.containsKey(letter)) {
                    Integer count = letterStatistics.get(letter);
                    count++;
                    letterStatistics.put(letter, count);
                } else {
                    letterStatistics.put(letter, 1);
                }

            }
        }
        System.out.println("Az összes magyar betű: " + totalNumberOfLetters);
        Set<Entry<Character, Integer>> entrySet = letterStatistics.entrySet();
        for (Entry<Character, Integer> entry : entrySet) {
            System.out.println(entry.getKey() + " --> " + entry.getValue() + " db");
        }
        System.out.println("-----------------------------------------------------");
        System.out.println("A betűk %-os előfordulása: ");
        Map<Character, Double> letterPercentages = new HashMap<>();

        for (Entry<Character, Integer> entry : letterStatistics.entrySet()) {
            letterPercentages.put(entry.getKey(), (((double)entry.getValue()) / totalNumberOfLetters) * 100);

        }
        for (Entry<Character, Double> entry : letterPercentages.entrySet()) {

            System.out.printf("%s -> %6.4f%%%n" , entry.getKey() , (((double)entry.getValue()) / totalNumberOfLetters) * 100);
        }

        //érték alapján kiiratva
        System.out.println("-----------------------------------------------------");
        System.out.println("A betűk %-os előfordulása csökkenő sorrendben: ");
        Set<Entry<Character, Double>> entrySet2 = letterPercentages.entrySet();
        List<Entry<Character, Double>> entryList = new ArrayList<>(entrySet2);
        Collections.sort(entryList, new PercentageDescendingComparator()); //de lehet streamm()-el is
        for (Entry<Character, Double> entry : entryList) {
            System.out.printf("%s -> %6.4f%%%n" , entry.getKey() , entry.getValue());
        }
    }

    private List<String> loadWordsFromFile(String fileName) {
        List<String> words = new ArrayList<>();
        try(Scanner scanner = new Scanner(new File(fileName), "UTF-8")){ //"UTF-8" :  hungarian
            //List<String> words = new ArrayList<>();
            while(scanner.hasNext()){
                String word = scanner.nextLine();
                //System.out.println(word);
                words.add(word); //az összes szót belerakjuk a listába
            }
            System.out.println(words.size() +" szó található a(z) '" + fileName + "' fájlban.");
            //System.out.println("HashSet.size(): "+new HashSet<>(words).size());//ugyanakkora mint a lista: nincs benne duplikáció
            return words;
        } catch (FileNotFoundException e) {
            System.out.println("A rendszer nem találja a megadott fájlt");
            //e.printStackTrace();
            //e.getMessage();
        }
        return Collections.emptyList();
    }
}
