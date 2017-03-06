package by.it.zeynalov.jd01_06.Level_A;


import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskA {

    //отдельный метод для нахождения в тексте слов начинающихся и заканчивающихся на гласные буквы
    public static void text(String[] words) {
        StringBuilder sb = new StringBuilder(Data.lukomor);
        Pattern pattern = Pattern.compile("[ёйуеыаоэяию]");
        for (String word : words) {
            if (word.charAt(0) == word.charAt(word.length() - 1)) {
                sb.append(word).append(" ");
            }
        }
        System.out.println(sb);
    }

    public static void main(String[] args) {



        StringBuilder sb = new StringBuilder(Data.lukomor);
        Pattern pattern = Pattern.compile("[а-яА-ЯёЁ]{4,}");
        Matcher matcher = pattern.matcher(sb);
        while (matcher.find()) {
            int pos = matcher.start() + 3;
            sb.setCharAt(pos, '#');
            if (matcher.group().length() > 6) {
                pos = matcher.start() + 6;
                sb.setCharAt(pos, '#');
            }
        }
        System.out.println(sb.toString());

        String[] words = Data.lukomor.split("[^а-яА-ЯёЁ]+");
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            int counter = 0;

            if (!word.isEmpty()) {
                for (int j = i + 1; j < words.length; j++) {
                    if (word.equalsIgnoreCase(words[j])) {
                        counter++;
                        words[j] = "";
                    }
                }
                System.out.printf("Слово %s повторяется %d раз\n", word, counter);
            }
        }
    }

}