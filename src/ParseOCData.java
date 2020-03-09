

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;


//надо убрать перегонные модули!!!

public class ParseOCData {

    //Списки с данными. В [0] ячейку ничего не записывается для более простого понимания{null}.
    //Например mapOut[1] - это все ccmi_id="1", mapOutOut[2] - это все ccmi_id="1"
    //структура коллекции - (ccmi_id + HashMap(adr, name))  В HasMap может быть множество значений для 1 ccmi_id
    private HashMap<Integer, HashMap<Integer, String>> mapOut = new HashMap<>();
    private HashMap<Integer, HashMap<Integer, String>> mapIn = new HashMap<>();

    public HashMap<Integer, HashMap<Integer, String>> getMapOut() {
        return mapOut;
    }

    public HashMap<Integer, HashMap<Integer, String>> getMapIn() {
        return mapIn;
    }

    void parseOCD() throws IOException {
//надо позже путь указать к файлу нрмальный
        Files.lines(Paths.get("C:\\test\\OCData.xml"), StandardCharsets.UTF_8).forEach((String s) -> {
            doFilterOut(s);
            doFilterIn(s);
        });
        //test
//       dotestOut();
//        dotestIn();
    }

    //ищем в файле выходы
    private void doFilterOut(String s) {
        HashMap<Integer, String> map;
        int id;
        int adr;
        String name;

        if (s.contains("<out ccmi_id=")) {
            String[] table = s.split("\"");
            id = Integer.parseInt(table[1]);
            adr = Integer.parseInt(table[3]);
            name = table[5];

// добавляем результат в коллекцию
            map = mapOut.get(id);
            if (map == null) map = new HashMap<>();
            //записываем в мапу значения (или добавляем)
            map.put(adr, name);
            //и сохраняем
            mapOut.put(id, map);
        }
    }

    //ищем в файле входы
    private void doFilterIn(String s) {
        int id = 0;
        int adr = 0;
        String name = null;
        HashMap<Integer, String> map;

        if (s.contains("<in type=\"") | s.contains("register type=\"blink\"") || s.contains("<register type=\"output\"")) {
            String[] table = s.split("\"");
            id = Integer.parseInt(table[3]);
            adr = Integer.parseInt(table[5]);
            name = table[7];

        // добавляем результат в коллекцию
        map = mapIn.get(id);
        if (map == null) map = new HashMap<>();
        //записываем в мапу значения (или добавляем)
        map.put(adr, name);
        mapIn.put(id, map);
        }
    }

    //тестовый метод
    private void dotestOut() {
        mapOut.forEach((k, v) -> {
            int finalI = k;
            v.forEach((a, b) -> {

                System.out.print("id = " + finalI);
                System.out.println("   adr = " + a + "   name = " + b);
            });
        });
    }

    private void dotestIn() {
        mapIn.forEach((k, v) -> {
            int finalI = k;
            v.forEach((a, b) -> {

                System.out.print("id = " + finalI);
                System.out.println("   adr = " + a + "   name = " + b);
            });
        });
    }
}
