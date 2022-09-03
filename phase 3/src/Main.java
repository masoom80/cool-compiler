import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        String inputCoolFilePath = "C:/Users/asus/IdeaProjects/phase1compiler/test/in/07_methods.cool";
        String outputFilePath = "C:/Users/asus/IdeaProjects/phase1compiler/src/out.s";
        String tablePath = "C:/Users/asus/IdeaProjects/phase1compiler/src/table.npt";
//        if (args.length >= 6)
//            for (int i = 0; i < args.length; i++) {
//                if (args[i].equals("--input"))
//                    inputCoolFilePath = args[i + 1];
//                if (args[i].equals("--output"))
//                    outputFilePath = args[i + 1];
//                if (args[i].equals("--table"))
//                    tablePath = args[i + 1];
//            }
//        else return;

        Scanner scanner = new Scanner(new FileReader(inputCoolFilePath));
        CodeGeneratorImpl codeGenerator = new CodeGeneratorImpl(scanner);
        Parser parser = new Parser(scanner, codeGenerator, tablePath);
        FileWriter fileWriter = new FileWriter(outputFilePath);
        try {
            parser.parse();
            CodeGeneratorImpl.generate(fileWriter);
            System.out.println("Syntax is correct!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        fileWriter.flush();
        fileWriter.close();
    }


}
