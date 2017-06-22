package com.example.sa.students_android.MyUtilMethods;

/**
 * Created by sa on 22.06.17.
 */

public class StringStuff {

    public static String transliterate(String input) {

        char[] abcCyr   = { 'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й',
                            'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф',
                            'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я',
                            'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й',
                            'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф',
                            'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я' };

        String[] abcLat = { "a", "b", "v", "g", "d", "e", "ye", "zh", "z", "i", "y",
                            "k", "l", "m", "n", "o", "p", "r", "s", "t", "u", "f",
                            "h", "c", "ch", "sh", "sch", "", "i", "", "e", "yu", "ya",
                            "A", "B", "V", "G", "D", "E", "Ye", "Zh", "Z", "I", "Y",
                            "K", "L", "M", "N", "O", "P", "R", "S", "T", "U", "F",
                            "H", "C", "Ch", "Sh", "Sch", "", "I", "", "E", "Yu", "Ya"};

        StringBuilder result = new StringBuilder();

        outer:
        for(int i = 0; i < input.length(); i++)
            for(int j = 0; j < abcCyr.length; j++)
                if(input.charAt(i) == abcCyr[j]) {
                    result.append(abcLat[j]);
                    continue outer;
                }

        return result.toString();
    }
}
