import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Шифруем, расшифровываем и ломаем Цезаря.
 */

public class Solution {
    static String fileNameIn = "";
    static String fileNameOut = "";
    static int key = 0;
    static String userInput = "";
    static boolean theLoop = true;

    private static final char[] ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '};

    public static void main(String[] args) {
        String fileSeparator = System.getProperty("file.separator");
        System.out.println("[1 - Шифрование] [2 - Расшифровка], [3 - Bruteforce], [exit - Выход из программы]");
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            userInput = inputReader.readLine();
            while (theLoop) {
                switch (userInput) {
                    case "exit" -> theLoop = false;
                    case "1" -> {
                        System.out.println("cipher");
                        theLoop = false;
                        System.out.println("Введите путь к файлу с текстом для шифрования. src/text.txt");
                        fileNameIn = inputReader.readLine();
                        File fileIn = new File(fileNameIn);
                        while (!fileIn.exists()) {
                            System.out.println("такого файла не существует, Введите путь к файлу с текстом для шифрования. src/text.txt ");
                            fileNameIn = inputReader.readLine();
                            fileIn = new File(fileNameIn);
                        }
                        System.out.println("Введите путь к файлу для вывода зашифрованного текста. src/cipherResult.txt");
                        fileNameOut = inputReader.readLine();
                        if (fileNameOut.matches("(.*)\\.bash_profile") || fileNameOut.matches("(.*)hosts")) {
                            while (true) {
                                System.out.println("Запрещенное название файла");
                                System.out.println("Введите путь к файлу для вывода текста деШифрования. src/deCipherResult.txt");
                                fileNameOut = inputReader.readLine();
                                if (!fileNameOut.matches("(.*)\\.bash_profile")) {
                                    if (!fileNameOut.matches("(.*)hosts")) {
                                        break;
                                    }
                                }
                            }
                        }

                        System.out.println("Введите ключ");
                        key = Integer.parseInt(inputReader.readLine());
                        cipher(fileNameIn, fileNameOut, key);
                    }
                    case "2" -> {
                        System.out.println("decipher");
                        System.out.println("Введите путь к файлу с зашифрованным текстом. src/cipherResult.txt");
                        fileNameIn = inputReader.readLine();
                        File fileIn = new File(fileNameIn);
                        while (!fileIn.exists()) {
                            System.out.println("такого файла не существует, Введите путь к файлу с зашифрованным текстом. src/cipherResult.txt");
                            fileNameIn = inputReader.readLine();
                            fileIn = new File(fileNameIn);
                        }
                        System.out.println("Введите путь к файлу для вывода текста деШифрования. src/deCipherResult.txt");
                        fileNameOut = inputReader.readLine();
                        if (fileNameOut.matches("(.*)\\.bash_profile") || fileNameOut.matches("(.*)hosts")) {
                            while (true) {
                                System.out.println("Запрещенное название файла");
                                System.out.println("Введите путь к файлу для вывода текста деШифрования. src/deCipherResult.txt");
                                fileNameOut = inputReader.readLine();
                                if (!fileNameOut.matches("(.*)\\.bash_profile")) {
                                    if (!fileNameOut.matches("(.*)hosts")) {
                                        break;
                                    }
                                }
                            }
                        }

                        System.out.println("Введите ключ");
                        key = Integer.parseInt(inputReader.readLine());
                        deCipher(fileNameIn, fileNameOut, key);
                        theLoop = false;
                    }
                    case "3" -> {
                        System.out.println("bruteforce");
                        theLoop = false;
                        System.out.println("Введите путь к файлу с зашифрованным текстом. src/cipherResult.txt");
                        fileNameIn = inputReader.readLine();
                        File fileIn = new File(fileNameIn);
                        while (!fileIn.exists()) {
                            System.out.println("такого файла не существует, Введите путь к файлу с зашифрованным текстом. src/cipherResult.txt");
                            fileNameIn = inputReader.readLine();
                            fileIn = new File(fileNameIn);
                        }
                        System.out.println("Введите путь к файлу для вывода текста деШифрования. src/bfResult.txt");
                        fileNameOut = inputReader.readLine();
                        if (fileNameOut.matches("(.*)\\.bash_profile") || fileNameOut.matches("(.*)hosts")) {
                            while (true) {
                                System.out.println("Запрещенное название файла");
                                System.out.println("Введите путь к файлу для вывода текста деШифрования. src/deCipherResult.txt");
                                fileNameOut = inputReader.readLine();
                                if (!fileNameOut.matches("(.*)\\.bash_profile")) {
                                    if (!fileNameOut.matches("(.*)hosts")) {
                                        break;
                                    }
                                }
                            }
                        }
                        bruteForce(fileNameIn, fileNameOut);
                    }
                    default -> {
                        System.out.println("Ваш ввод не верен: Введите - [1 - Шифрование] [2 - Расшифровка], [3 - Bruteforce], [exit - Выход из программы]");
                        userInput = inputReader.readLine();
                    }
                }

            }
        } catch (IOException e) {
            System.out.println("UserInput + switch loop");
        }
        try {
            inputReader.close();
        } catch (IOException e) {
            System.out.println("inputReader.close()");
        }
        System.out.println("Программа завершена корректно");


        // конец main.
    }

    // Шифруем строку.
    public static void cipher(String fileName1, String fileName2, int aKey) {
        char[] inputText;
        StringBuilder outputText = new StringBuilder();
        boolean found;
        try {
            BufferedReader readingFile = new BufferedReader(new FileReader(fileName1));
            BufferedWriter writeFile = new BufferedWriter(new FileWriter(fileName2));
            while (readingFile.ready()) {
                outputText.setLength(0);
                inputText = readingFile.readLine().toCharArray();
                for (char character :inputText) {
                    found = false;
                    for (int i = 0; i < ALPHABET.length; i++) {
                        if (ALPHABET[i] == character) {
                            int index = i + aKey;
                            index = index % 40;
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
    public static void deCipher(String fileName1, String fileName2, int aKey) {
        char[] inputText;
        StringBuilder outputText = new StringBuilder();
        boolean found;
        try {
            BufferedReader readingFile = new BufferedReader(new FileReader(fileName1));
            // BufferedWriter writeFile = new BufferedWriter(new FileWriter(fileName2));
            BufferedOutputStream writeFile = new BufferedOutputStream(new FileOutputStream(fileName2));
            while (readingFile.ready()) {
                outputText.setLength(0);
                inputText = readingFile.readLine().toCharArray();
                for (char character :inputText) {
                    found = false;
                    for (int i = 0; i < ALPHABET.length; i++) {
                        if (ALPHABET[i] == character) {
                            int index = i - aKey;
                            index = index % 40;
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
                writeFile.write(outputText.toString().getBytes());
                String next = "\n";
                writeFile.write(next.getBytes());
                writeFile.flush();
            }
            writeFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound deCipher");
        } catch (IOException e) {
            System.out.println("IOException deCipher");
        }
    }

    // Применяем Брут Форс
    public static void bruteForce(String fileName1, String fileName2) {
        int maxCount = 0;
        int currentCount;
        int index = 0;
        char[] inputText;
        StringBuilder outputText = new StringBuilder();
        for (int i = 0; i < ALPHABET.length; i++) {
            deCipher(fileName1, fileName2, i);
            currentCount = 0;
            try {
                BufferedReader readingFile = new BufferedReader(new FileReader(fileName2));
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
                System.out.println("FileNotFound BruteForce");
            } catch (IOException e) {
                System.out.println("IOException BruteForce");
            }
        }
        // Выводим расшифрованный текст в fileName2
        deCipher(fileName1, fileName2, index);
    }




    //конец Solution, постоянно последняя скобка теряется =)
}