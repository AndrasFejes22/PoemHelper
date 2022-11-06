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
        //System.out.println("haladás: "+extractVowelKeyFromWord2("haladás"));
        System.out.println("---------------------------------------------");
        System.out.println("Advanced poemhelper:");
        String text = "képeslap";
        System.out.println("substring: "+text.substring(text.length()-3));
        advancedPoemHelper(text);
        advancedPoemHelper("kikövez");

    }

    private void run() {
        // 1. lépés: szavak file betöltése
        //List<String> words = loadWordsFromFile("hungarian_words/magyar_szavak_listaja_hosszabb.txt");
        List<String> words = loadWordsFromFile("C:/Users/Andris/IdeaProjects/PoemHelper/res/words/magyar_szavak_listaja_hosszabb.txt");

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
        //System.out.println("Az összes magyar betű: " + totalNumberOfLetters);
        Set<Entry<Character, Integer>> entrySet = letterStatistics.entrySet();
        for (Entry<Character, Integer> entry : entrySet) {
            //System.out.println(entry.getKey() + " --> " + entry.getValue() + " db");
        }
        System.out.println("-----------------------------------------------------");
        System.out.println("A betűk %-os előfordulása: ");
        Map<Character, Double> letterPercentages = new HashMap<>();

        for (Entry<Character, Integer> entry : letterStatistics.entrySet()) {
            letterPercentages.put(entry.getKey(), (((double)entry.getValue()) / totalNumberOfLetters) * 100);

        }
        for (Entry<Character, Double> entry : letterPercentages.entrySet()) {

            //System.out.printf("%s -> %6.4f%%%n" , entry.getKey() , (((double)entry.getValue()) / totalNumberOfLetters) * 100);
        }

        //érték alapján kiiratva
        System.out.println("-----------------------------------------------------");
        System.out.println("A betűk %-os előfordulása csökkenő sorrendben: ");
        Set<Entry<Character, Double>> entrySet2 = letterPercentages.entrySet();
        List<Entry<Character, Double>> entryList = new ArrayList<>(entrySet2);
        Collections.sort(entryList, new PercentageDescendingComparator()); //de lehet streamm()-el is
        for (Entry<Character, Double> entry : entryList) {
            //System.out.printf("%s -> %6.4f%%%n" , entry.getKey() , entry.getValue());
        }

        //3.: multimap létrehozása, kulcs: szavak magánhangzóit tartalmazó String, érték a szavak listája

        Set<Character> wovels = Set.of('a', 'á', 'e', 'é', 'i', 'í', 'o', 'ó', 'ö', 'ő', 'u', 'ú', 'ü', 'ű');
        //"aao" -> [asztalos, maszatos, stb]
        Map<String, List<String>> wordsWithSameWowels = new TreeMap<>();
        for (String word : words){
            String key = extractVowelKeyFromWord(word, wovels); //asztalos -> "aao"
            if(wordsWithSameWowels.containsKey(key)){
                List<String> listOfWords = wordsWithSameWowels.get(key);
                listOfWords.add(word);
            } else {
                List<String> listOfWords = new ArrayList<>();
                listOfWords.add(word);
                wordsWithSameWowels.put(key, listOfWords);
            }
        }
        System.out.println();
        System.out.println("-----------------------------------------------------");
        System.out.println("poemhelper: ");
        for (Entry<String, List<String>> entry : wordsWithSameWowels.entrySet()) {
            //System.out.println(entry.getKey() + " -> " + entry.getValue());
        }



    } //run vége

    private static String extractVowelKeyFromWord(String word, Set<Character> wovels) {
        StringBuilder vowelKey = new StringBuilder();
        char[] charArray = word.toCharArray();
        for(char letter : charArray){
            if(wovels.contains(letter)){
                vowelKey.append(letter);
            }
        }
        return vowelKey.toString();//asztalos -> "aao"
    }

    private static List<String> loadWordsFromFile(String fileName) {
        List<String> words = new ArrayList<>();
        try(Scanner scanner = new Scanner(new File(fileName), "UTF-8")){ //"UTF-8" :  hungarian
            //List<String> words = new ArrayList<>();
            while(scanner.hasNext()){
                String word = scanner.nextLine();
                //System.out.println(word);
                words.add(word); //az összes szót belerakjuk a listába
            }
            //System.out.println(words.size() +" szó található a(z) '" + fileName + "' fájlban.");
            //System.out.println("HashSet.size(): "+new HashSet<>(words).size());//ugyanakkora mint a lista: nincs benne duplikáció
            return words;
        } catch (FileNotFoundException e) {
            System.out.println("A rendszer nem találja a megadott fájlt");
            //e.printStackTrace();
            //e.getMessage();
        }
        return Collections.emptyList();
    }

    private static String extractVowelKeyFromWord2(String word) {
        Set<Character> wovels = Set.of('a', 'á', 'e', 'é', 'i', 'í', 'o', 'ó', 'ö', 'ő', 'u', 'ú', 'ü', 'ű');
        StringBuilder vowelKey = new StringBuilder();
        char[] charArray = word.toCharArray();
        for(char letter : charArray){
            if(wovels.contains(letter)){
                vowelKey.append(letter);
            }
        }
        return vowelKey.toString();//asztalos -> "aao"
    }

    private static void advancedPoemHelper(String text){
        List<String> words = loadWordsFromFile("C:/Users/Andris/IdeaProjects/PoemHelper/res/words/magyar_szavak_listaja_hosszabb.txt");
        Set<Character> wovels = Set.of('a', 'á', 'e', 'é', 'i', 'í', 'o', 'ó', 'ö', 'ő', 'u', 'ú', 'ü', 'ű');
        String extracted = extractVowelKeyFromWord2(text); //asztalos -> "aao"
        List<String> listOfWords = new ArrayList<>();
        List<String> listOfWords2 = new ArrayList<>();
        Map<String, List<String>> wordsWithSameWowels = new TreeMap<>();
        for (String word : words){
            String key = extractVowelKeyFromWord(word, wovels); //asztalos -> "aao"
            if(extracted.contentEquals(key)) {
                listOfWords.add(word);
            }
            if(extracted.contentEquals(key) && text.substring(text.length()-3).equals(word.substring(word.length()-3))) {
                listOfWords2.add(word);
            }

        }
        System.out.println("Azonos hangrendű szavak a '" + text + "' szóval: ");
        System.out.println();
        System.out.println(listOfWords);
        System.out.println();
        System.out.println("Szavak amik rímelnek a '" + text + "' szóra: ");
        System.out.println();
        System.out.println(listOfWords2);
    }
}
