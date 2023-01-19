import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;


public class Main {
    public static List<Integer> generateCoefficients(int k) {
        List<Integer> coefficients = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i <= k; i++) {
            coefficients.add(r.nextInt(101));
        }
        return coefficients;
    }

    public static String getstring(List<Integer> coefficients) {
        int xpower;
        String result = coefficients.get(0) + "x" + (coefficients.size() - 1);
        for (int i = 1; i < coefficients.size(); i++) {
            xpower = coefficients.size() - 1 - i;
            result = result + "+" + coefficients.get(i) + "x" + xpower;
        }
        result = result.replace("x0", "").replace("x1+", "x+") + "=0";
        return result;
    }

    public static void writeFile(String filename, String line) {
        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            writer.println(line);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String @NotNull [] getPolinomialSArr(String l) {
        l = l.replace("-", "+-").replace("=0","");
        String[] result = l.split("\\+");
//        System.out.println(Arrays.toString(result));
        return result;
    }

    public static String readLineFromFile(String fileName) {
        String line = null;
        try {
            File file = new File(fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            line = bufferedReader.readLine();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    public static Map<String, Integer> getMap(String[] list) {
        Map<String, Integer> map = new HashMap<>();


        for (String s : list) {
            String[] split = s.split("x");
//            Проверяем есть ли х вообще
            if (s.contains("x")) {
                if (split.length == 2) {
                    int value = Integer.parseInt(split[0]);
                    String key = "x" + split[1];
//                    System.out.println(key);
                    map.put(key, value);
                }
                if (split.length < 2) {
                    map.put("x", Integer.parseInt(split[0]));
                }
            } else {
                map.put("wx", Integer.parseInt(s));
            }
        }
        return map;
    }

    public static Map<String, Integer> mergeMaps(Map<String, Integer> data1, Map<String, Integer> data2) {
        Map<String, Integer> result = new HashMap<>();
        for (Map.Entry<String, Integer> entry : data1.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if (data2.containsKey(key)) {
                value += data2.get(key);
            }
            result.put(key, value);
        }
        for (Map.Entry<String, Integer> entry : data2.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if (!data1.containsKey(key)) {
                result.put(key, value);
            }
        }
        return result;
    }

    public static String getStringfromMap(Map<String, Integer> map){
        String result = null;
        for (String key : map.keySet()) {
//            System.out.println(key);
//            System.out.println(map.get(key));
            if(key == "wx") {
                result = result + map.get(key);
            }
            else {
                result = result + map.get(key)+key;
            }
            result += "+";
        }
        result = result.substring(0, result.length() - 1);
        result += "=0";
        return result.replace("null", "");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the degree of the polynomial: ");
        int degree = sc.nextInt();
        if (degree >= 1) {
            List<Integer> coefficients = generateCoefficients(degree);
            System.out.println(coefficients);
            String outLine = getstring(coefficients);
            System.out.println(outLine);
            System.out.print("Enter file name: ");
            String filename = sc.next();
            writeFile(filename, outLine);
        }
//        getPolinomialSArr("23x2-76x+78=0");
        System.out.print("Enter first file name: ");
        String file_first= sc.next();
        System.out.print("Enter second file name: ");
        String file_second= sc.next();

//        String str1, str2;
//        str1 = readLineFromFile(file_first);
//        str2 = readLineFromFile(file_second);

        String[] arr_first = getPolinomialSArr(readLineFromFile(file_first));
        String[] arr_second = getPolinomialSArr(readLineFromFile(file_second));
        Map<String, Integer> res1 = getMap(arr_first);
        Map<String, Integer> res2 = getMap(arr_second);
//        System.out.println(res1);
//        System.out.println(res2);
        Map<String, Integer> result = mergeMaps(res1, res2);
//        System.out.println(result);
        System.out.println(getStringfromMap(result));
        writeFile("output.txt", getStringfromMap(result));
    }
}
