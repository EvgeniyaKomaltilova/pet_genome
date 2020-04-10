public class Genome {

    public static void main(String[] args) {
        String motherGen = "AaBbCcDdEeFfGgRrPp";
        String fatherGen = "aabb";

        GenCombo(motherGen);
        //GenCombo(fatherGen);
    }

    public static void GenCombo(String gen) {
        int genAmount = gen.length()/2; //число известных генов в генотипе
        int allelCombinations = (int) Math.pow(2, genAmount); //число возможных сочетаний генов равно 2^(число генов)

        String[][] genArray = new String[2][genAmount]; //двухмерный массив с аллелями генов

        //наполняев массив значениями
        int c = 0;
        for (int y = 0; y < genAmount; y++) {
            for (int x = 0; x < 2; x++) {
                genArray[x][y] = String.valueOf(gen.charAt(c));
                c++;
                //System.out.print(genArray[x][y]);
            }
            //System.out.println("");
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

        // выводим в консоль список всех сочетаний аллелей
        for (String s : genCombs) {
            System.out.println(s);
        }
    }

}
