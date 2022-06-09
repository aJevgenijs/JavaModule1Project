import java.io.*;
import java.util.*;

/**
 * Шифруем, расшифровываем и ломаем Цезаря.
 */

public class Solution {
    static String fileName = "";
    static int key = 0;

    private static final char[] ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '};

    public static void main(String[] args) {
        System.out.println("Введите путь к файлу, src/text");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            fileName = reader.readLine();
            //
            // Проверка Файла на название
            //
            System.out.println("Введите ключ");
            key = Integer.parseInt(reader.readLine());
            reader.close();



        } catch (IOException e) {

        }
        cipher(fileName, key);
        deCipher("src/cipherResult",key);
        bruteForce("src/cipherResult");

    }

    // Шифруем строку.
    public static void cipher(String fileName, int aKey) {
        char[] inputText;
        StringBuilder outputText = new StringBuilder();
        boolean found = true;
        try {
            BufferedReader readingFile = new BufferedReader(new FileReader(fileName));
            BufferedWriter writeFile = new BufferedWriter(new FileWriter("src/cipherResult"));
            while (readingFile.ready()) {
                outputText.setLength(0);
                inputText = readingFile.readLine().toCharArray();
                for (char character :inputText) {
                    found = false;
                    for (int i = 0; i < ALPHABET.length; i++) {
                        if (ALPHABET[i] == character) {
                            int index = i + aKey;
                            if (index >= 40) {
                                index -= 40;
                            }
                            outputText.append(ALPHABET[index]);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        outputText.append(character);
                    }
                }
                writeFile.write(outputText + "\n");
                writeFile.flush();
            }
            writeFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound cipher");
        } catch (IOException e) {
            System.out.println("IOException cipher");
        }
    }


    // Расшифровываем строку.
    public static void deCipher(String fileName, int aKey) {
        char[] inputText;
        StringBuilder outputText = new StringBuilder();
        boolean found = true;

        try {
            BufferedReader readingFile = new BufferedReader(new FileReader(fileName));
            BufferedWriter writeFile = new BufferedWriter(new FileWriter("src/deCipherResult"));
            while (readingFile.ready()) {
                outputText.setLength(0);
                inputText = readingFile.readLine().toCharArray();
                for (char character :inputText) {
                    found = false;
                    for (int i = 0; i < ALPHABET.length; i++) {
                        if (ALPHABET[i] == character) {
                            int index = i - aKey;
                            if (index < 0) {
                                index += 40;
                            }
                            outputText.append(ALPHABET[index]);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        outputText.append(character);
                    }
                }
                writeFile.write(outputText + "\n");
                writeFile.flush();
            }
            writeFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound deCipher");
        } catch (IOException e) {
            System.out.println("IOException deCipher");
        }
    }

    public static void bruteForce(String fileName) {
        int maxCount = 0;
        int currentCount;
        int index = 0;
        char[] inputText;
        StringBuilder outputText = new StringBuilder();
        for (int i = 0; i < ALPHABET.length; i++) {
            deCipher(fileName, i);
            currentCount = 0;
            try {
                BufferedReader readingFile = new BufferedReader(new FileReader("src/deCipherResult"));
                while (readingFile.ready()) {
                    inputText = readingFile.readLine().toCharArray();
                    for (char character: inputText) {
                        if (character == 'о' || character == ' ') {
                            currentCount++;
                        }
                    }
                }
                if (maxCount < currentCount) {
                    maxCount = currentCount;
                    index = i;
                }
            } catch (FileNotFoundException e) {
                System.out.println("FileNotFound");
            } catch (IOException e) {
                System.out.println("IOException");
            }
        }
        System.out.println(index);
        deCipher(fileName, index);
    }




    //конец Solution
}