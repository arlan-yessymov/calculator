import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String input = processString();
        String s = calc(input);
        System.out.println(s);
    }

    static String processString() {
        StringBuilder stringOutput = new StringBuilder();
        Scanner sc = new Scanner(System.in);
        String stringInput = sc.nextLine();
        String[] strings = stringInput.strip().split(" ");
        if (strings.length < 3) {
            stringOutput = new StringBuilder("error //т.к. строка не является математической операцией");
            return stringOutput.toString().strip();
        } else if (strings.length == 3) {
            int romanDigit = 0;
            for (String string:strings) {
                if (isRomanNumber(string)) {
                    RomanNumber romanNumber;
                    romanNumber = RomanNumber.valueOf(string);
                    int number = romanNumber.getNumber();
                    if (number > 10 || number < 1){
                        stringOutput = new StringBuilder("error //т.к. числа могут быть в диапозоне от I до X включительно");
                        return stringOutput.toString().strip();
                    } else {
                        string = Integer.toString(number);
                        stringOutput.append(" ").append(string);
                        romanDigit++;
                    }
                } else if (isDigit(string)) {
                    int digit = Integer.parseInt(string);
                    if (digit > 10 || digit < 1){
                        stringOutput = new StringBuilder("error //т.к. числа могут быть в диапозоне от 1 до 10 включительно");
                        return stringOutput.toString().strip();
                    } else {
                        stringOutput.append(" ").append(string);
                    }
                } else if (isMathematicalOperation(string)) {
                    stringOutput.append(" ").append(string);
                } else if (isFloat(string)) {
                    stringOutput = new StringBuilder("error //т.к. можно использовать только целые числа");
                    return stringOutput.toString().strip();
                }
                else {
                    stringOutput = new StringBuilder("error //т.к. строка не является математической операцией");
                    return stringOutput.toString().strip();
                }
            }
            if (romanDigit == 1) {
                stringOutput = new StringBuilder("error //т.к. используются одновременно разные системы счисления");
                return stringOutput.toString().strip();
            } else if (romanDigit == 2) {
                stringOutput = new StringBuilder(stringOutput + " ;isRoman");
            } else {
                stringOutput = new StringBuilder(stringOutput);
            }
        } else {
            stringOutput = new StringBuilder("error //т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
            return stringOutput.toString().strip();
        }
        return stringOutput.toString().strip();
    }

    public static String calc(String input) {
        String s;
        boolean isRomanNumber = false;
        if (input.endsWith("isRoman")) {
            s = input.split(";")[0];
            isRomanNumber = true;
        } else {
            s = input;
        }
        if (input.startsWith("error")) {
            return input;
        }
        int result = 0;
        String[] string = s.split(" ");
        int first = Integer.parseInt(string[0]);
        int second = Integer.parseInt(string[2]);
        String operation = string[1];
        switch (operation) {
            case "+" -> result = first + second;
            case "-" -> result = first - second;
            case "*" -> result = first * second;
            case "/" -> result = first / second;
        }
        String resultString = "";
        if (isRomanNumber) {
            if (result <= 0) {
                switch (result) {
                    case 0 -> resultString = "error //т.к. результат не может быть равен 0";
                    default -> resultString = "error //т.к. в римской системе нет отрицательных чисел";
                }
                return resultString;
            } else {
                resultString = intToRoman(result);
            }
        } else {
            resultString = String.valueOf(result);
        }
        return resultString;
    }

    static boolean isRomanNumber(String string) {
        try {
            RomanNumber.valueOf(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static boolean isDigit(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static boolean isFloat(String string) {
        try {
            Float.parseFloat(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static boolean isMathematicalOperation(String string) {
        return string.equals("+") || string.equals("-") || string.equals("/") || string.equals("*");
    }

    static String intToRoman(int number)
    {
        String[] keys = new String[] { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
        int[] vales = new int[] { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
        StringBuilder ret = new StringBuilder();
        int ind = 0;
        while(ind < keys.length)
        {
            while(number >= vales[ind])
            {
                var d = number / vales[ind];
                number = number % vales[ind];
                ret.append(keys[ind].repeat(d));
            }
            ind++;
        }
        return ret.toString();
    }
}