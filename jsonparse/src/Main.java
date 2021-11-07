import com.parse.JSONParse;

public class Main {

    public static void main(String[] args) {
        JSONParse jsonParse = new JSONParse();
        try {
            String input = "{\"name\":\"xiaoming\",\"age\":18,\"like\":[\"sing\",18,{\"tech\":\"code\"}]}";
            System.out.println(jsonParse.fromJSON(input));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
