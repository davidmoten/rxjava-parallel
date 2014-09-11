import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Generator {

    public void run() throws IOException {
        InputStream is = Generator.class.getResourceAsStream("/signatures.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
            Sig sig = from(line);
        }
        br.close();
    }

    public static void main(String[] args) throws IOException {
        new Generator().run();
    }

    private static Sig from(String line) {

        AtomicInteger i = new AtomicInteger(0);
        String typeParameter = "";
        String methodReturnType = "";
        String methodName = "";
        String token = "";
        List<Parameter> parameters = new ArrayList<Parameter>();
        while (i.get() < line.length()) {
            char ch = line.charAt(i.get());
            if (ch != ' ') {
                token = readType(i, line);
            }
            i.incrementAndGet();
        }

        return null;
    }

    private static String readToken(AtomicInteger i, String line) {
        int level = 0;
        char ch = line.charAt(i.get());
        StringBuilder s = new StringBuilder();
        while (i.get() < line.length() && (level > 0 || (ch != ' ' && ch != '(' && ch != ')'))) {
            if (ch == '<')
                level++;
            if (ch == '>')
                level--;
            s.append(ch);
            i.incrementAndGet();
            if (i.get() < line.length())
                ch = line.charAt(i.get());
        }
        return s.toString();
    }

    private static class Sig {
        String typeParameters;
        String methodReturnType;
        String methodName;
        List<Parameter> parameters;

        s

        Sig(String typeParameters, String methodReturnType, String methodName,
                List<Parameter> parameters) {
            this.typeParameters = typeParameters;
            this.methodReturnType = methodReturnType;
            this.methodName = methodName;
            this.parameters = parameters;
        }

    }

    private static class Parameter {
        String type;
        String name;

        public Parameter(String type, String name) {
            this.type = type;
            this.name = name;
        }

    }

}
