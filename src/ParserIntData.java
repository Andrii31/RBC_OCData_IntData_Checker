import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class ParserIntData {

    private HashMap<String, HashMap<Integer, String>> modulIN = new HashMap<>();
    private HashMap<String, HashMap<Integer, String>> modulOut = new HashMap<>();

    public HashMap<String, HashMap<Integer, String>> getModulIN() {
        return modulIN;
    }

    public HashMap<String, HashMap<Integer, String>> getModulOut() {
        return modulOut;
    }

    private String modulName = null;

    void parseIntData() throws IOException {
        //   doFilterOut(s);
        Files.lines(Paths.get("C:\\test\\IntData.xml"), StandardCharsets.UTF_8).forEach(this::checkXmlPart);

//        dotestIn();
//        dotestOut();
    }

    private void checkXmlPart(String s) {

        if (s.contains("<obj objtype=\"Модуль_ввода\"") || s.contains("<obj objtype=\"Модуль_вывода\"")) {
            modulName = s.split("\"")[3];
        }

        if (!s.contains("</obj>")) {
            if (s.contains("<attributes jsonValue=\"{&quot;Out")) doFilterOut(s);
            doFilterIn(s);
        } else {
            modulName = null;
        }
    }

    private void doFilterOut(String s) {
        int out;
        String nameOutModule;
        HashMap<Integer, String> map;

        String[] listOfOuts = s.split("\"")[1]
                .replaceAll("\\\\w", "")
                .replaceAll("[[{}&qo;Out]]", "")
                .split(",");

        for (String a : listOfOuts
        ) {
//            System.out.println(a);
//            System.out.println(a.length());

            if (a.indexOf(":") < a.length() - 1) {
                out = Integer.parseInt(a.split(":")[0]);
                nameOutModule = a.split(":")[1];
                map = modulOut.get(modulName);
                if (map == null) map = new HashMap<>();

                map.put(out, nameOutModule);
                modulOut.put(modulName, map);
            }
        }
    }

    private void doFilterIn(@NotNull String s) {
        int inp;
        String nameInModule;
        HashMap<Integer, String> map;

        if (s.contains("<in type=\"inp")) {

            inp = Integer.parseInt(s.split("\"")[1].replaceAll("[^0-9]", ""));
            nameInModule = s.split("\"")[3];

            map = modulIN.get(modulName);

            if (map == null) map = new HashMap<>();
            map.put(inp, nameInModule);
            modulIN.put(modulName, map);

        }


    }


    //test method for In
    private void dotestIn() {
        modulIN.forEach((k, v) -> v.forEach((a, b) -> {
            System.out.print("Модуль ввода = " + k);
            System.out.println("   inp = " + a + "   name = " + b);
        }));
    }
    //test method for Out
    private void dotestOut() {
        modulOut.forEach((k, v) -> v.forEach((a, b) -> {
            System.out.print("Модуль вывода = " + k);
            System.out.println("   inp = " + a + "   name = " + b);
        }));
    }

}
