import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Genome {

    public static void main(String[] args) {
        String firstParentGen = GetString();
        String secondParentGen = GetString();

        Set<String> motherSet = GenSet(firstParentGen);
        Set<String> fatherSet = GenSet(secondParentGen);
        List<String> progeny = Progeny(motherSet, fatherSet);
        percentageRatio(progeny);
    }

    /* Метод GenSet принимает строку с генотипом существа в виде последовательности аллелей ("AaBbCC" etc)
        и возвращает Set из всех возможных уникальных комбинаций генов.
     */
    public static Set<String> GenSet(String gen) {
        int genAmount = gen.length()/2; //число известных генов в генотипе
        int allelCombinations = (int) Math.pow(2, genAmount); //число возможных сочетаний генов равно 2^(число генов)

        String[][] genArray = new String[2][genAmount]; //двухмерный массив с аллелями генов

        //наполняем массив значениями
        int c = 0;
        for (int y = 0; y < genAmount; y++) {
            for (int x = 0; x < 2; x++) {
                genArray[x][y] = String.valueOf(gen.charAt(c));
                c++;
            }
        }
        String[] genCombs = new String[allelCombinations]; //массив со всеми возможными комбинациями аллелей у родителя
        int count = 0;
        int y = 0;
        //делаем все строки пустыми, но не null
        for (int i = 0; i < allelCombinations/Math.pow(2, y+1); i++) {
            for (int x = 0; x < 2; x++) {
                    genCombs[count] = "";
                    count++;
            }
        }
        //каждой строке присваиваем аллели во всех сочетаниях
        for (y = 0; y < genAmount; y++) {
            count = 0;
            for (int i = 0; i < allelCombinations / Math.pow(2, y + 1); i++) {
                for (int x = 0; x < 2; x++) {
                    for (int z = 0; z < Math.pow(2, y); z++) {
                        genCombs[count] = genCombs[count] + genArray[x][y];
                        count++;
                    }
                }
            }
        }
        //фильтруем повторы путем помещения в HashSet
        Set<String> genSet = new HashSet<>();
        for (String s : genCombs) {
            genSet.add(s);
        }
        return genSet;
    }

    public static List<String> Progeny(Set<String> m, Set<String> f) {
        List<String> progenyList = new ArrayList<>();
        String[] mother = m.toArray(new String[m.size()]);
        String[] father = f.toArray(new String[f.size()]);

        for (int i = 0; i < father.length; i++) {
            for (String s : mother) {
                progenyList.add(charSort(s + father[i]));
            }
        }
        return progenyList;
    }

    //сортировочка
    public static String charSort(String s) {
        List<Character> sampleList = Arrays.asList('A','a','B','b','C','c','D','d','E','e','F','f','G','g','H','h','I','i','J','j','K','k','L','l','M','m','N','n','O','o','P','p','Q','q','R','r','S','s','T','t','U','u','V','v','W','w','X','x','Y','y','Z','z');
        char[] charArray = s.toCharArray();

        for (int c = 0; c < s.length(); c++) {
            for (int i = 0; i < s.length() - 1; i++) {
                if (sampleList.indexOf(charArray[i]) > sampleList.indexOf(charArray[i+1])) {
                    char temp = charArray[i];
                    charArray[i] = charArray[i + 1];
                    charArray[i + 1] = temp;
                }
            }
        }
        return String.valueOf(charArray);
    }

    public static void percentageRatio(List<String> progeny) {
        Map<String, Integer> genMap = new TreeMap<>();
        int n = 0;
        for (String s : progeny) {
            if (progeny.get(0).equals(s)) {
                n++;
            }
            genMap.put(s, n);
        }

        int sum = 0;
        for (int i : genMap.values()) {
            sum+=i;
        }

        System.out.println("Вероятное распределение генов в потомстве:");
        for (Map.Entry<String, Integer> entry : genMap.entrySet()) {
            float percentage = (float) entry.getValue()/sum*100;
            System.out.println(percentage + "% " + entry.getKey());
        }
    }

    public static String GetString() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Укажите генотип родителя в формате \"AaBbCc\"");
        String s = "";
        try {
            s = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
}
