import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class UserProperties {


    void writeProperties(HashMap<String, HashMap<Integer, String>> inputs,
                         HashMap<String, HashMap<Integer, String>> outputs,
                         HashMap<Integer, HashMap<Integer, String>> idInputs,
                         HashMap<Integer, HashMap<Integer, String>> idOutputs) {
        checkFile();
        StringBuilder sb = new StringBuilder();

        sb.append("*********ПРОСТАВЬТЕ ЗНАЧЕНИЕ id ДЛЯ ДОНТРОЛЛЕРОВ ВВОДА/ВЫВОДА*********").append('\n');
        sb.append("*********ВХОДЫ*********").append('\n');
        inputs.forEach((k, v) -> sb.append(k).append("=").append('\n'));
        sb.append("*********ВЫХОДЫ*********").append('\n');
        outputs.forEach((k, v) -> sb.append(k).append("=").append('\n'));

        sb.append("***************************************************").append('\n');
        sb.append("*********ДОПУСТИМЫЕ ЗНАЧЕНИЯ id ДЛЯ ВХОДОВ*********").append('\n');
        idInputs.forEach((k, v) -> sb.append(k).append('\n'));
        sb.append("*********ДОПУСТИМЫЕ ЗНАЧЕНИЯ id ДЛЯ ВЫХОДОВ*********").append('\n');
        idOutputs.forEach((k, v) -> sb.append(k).append('\n'));
        sb.append("***************************************************");

        addToFile(sb.toString());

    }

    private void addToFile(String k) {


        File file = new File("C:\\test\\Properties.txt");


        try (PrintWriter out = new PrintWriter(file.getAbsoluteFile())) {

            out.write(k);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void checkFile() {
        File file = new File("C:\\test\\Properties.txt");
        try {
            //проверяем, что если файл не существует то создаем его
            if (file.exists()) {
                file.delete();
            }
            if (!file.exists()) {
                file.createNewFile();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}


