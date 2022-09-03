

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TokenHighlighter {
    private ArrayList<Scanner.Token> Tokens;

    TokenHighlighter(ArrayList<Scanner.Token> Tokens) {
        this.Tokens = Tokens;
    }

    public void tokensToHtml() throws IOException {
        File f = new File("highlighted.html");
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        bw.write("\t<html>\n \t\t<body style=\"background-color:#1e2a2a\"> \n \t \t \t<div>\n");
        int currentLine = 0, currentcolumn = 0;
        bw.write("<span style=\"color:#FFFFFF\">" + (currentLine + 1) + " </span>");

        for (Scanner.Token currentToken : this.Tokens) {
            while (currentToken.line > currentLine) {
                bw.write("<div> </div>" + "<span style=\"color:#FFFFFF\">" + (currentLine + 2) + " </span>");
                currentLine++;
                currentcolumn = 0;
            }
            while (currentcolumn < currentToken.column) {
                bw.write("&nbsp;");
                currentcolumn++;
            }

            switch (currentToken.type) {
                case 0:
                    bw.write("<span style=\"color:#B667F1\">" + currentToken.token + " </span>");
                    break;
                case 1:
                    bw.write("<span style=\"color:#FFFFFF\">" + currentToken.token + " </span>");
                    break;
                case 2:
                    bw.write("<span style=\"color:#FFC300\">" + currentToken.token + " </span>");
                    break;
                case 3:
                    bw.write("<span style=\"color:#FFC300\"><em>" + currentToken.token + " </em></span>");
                    break;
                case 4:
                    bw.write("<span style=\"color:#00C897\">" + currentToken.token + " </span>");
                    break;
                case 5:
                    bw.write("<span style=\"color:#B8FFF9\"> <em>" + currentToken.token + " </em></span>");
                    break;
                case 6:
                    bw.write("<span style=\"color:#69676C\">" + currentToken.token + " </span>");
                    break;
                case 7:
                    bw.write("<span style=\"color:#90E0EF\">" + currentToken.token + " </span>");
                    break;
                default:
                    bw.write("<span style=\"color:#FF1818\">" + currentToken.token + " </span>");
                    break;
            }
            currentcolumn+=currentToken.token.length();
        }
        bw.write("\n \t \t \t</div> \n \t \t</body> \n \t</html>");
        bw.close();
    }
}
