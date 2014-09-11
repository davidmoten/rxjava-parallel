import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;


public class Generator {

    public void run() throws IOException {
        InputStream is = Generator.class.getResourceAsStream("/signatures.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = br.readLine())!=null) {
            System.out.println(line);
        }
        br.close();
    }
    
    public static void main(String[] args) throws IOException {
        new Generator().run();
    }
    
    private static class Sig {
        String typeParameters;
        String methodReturnType;
        String methodName;
        List<Parameter> parameters;
    }
    
    private static class Parameter {
        String type;
        String name;
    }
    
}
