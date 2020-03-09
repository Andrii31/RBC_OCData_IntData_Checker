import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException {
        ParseOCData parseOCData = new ParseOCData();
        ParserIntData parserIntData = new ParserIntData();
        UserProperties userProperties = new UserProperties();
        parseOCData.parseOCD();
        parserIntData.parseIntData();
        userProperties.writeProperties(parserIntData.getModulIN(),
                parserIntData.getModulOut(),
                parseOCData.getMapIn(),
                parseOCData.getMapOut());

    }

}
