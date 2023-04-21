package lexical.analyze;

public class Text {
    public char Ch;     // Очередной символ
    public int Line;    // Номер строки
    public int Pos;     // Номер символа в строке

    public Scanner uFile = null;

    public String readFile(String f) {
        try {
            uFile = new Scanner(new java.io.File(f));
            while (uFile.hasNext()) {

            }
        }
    }
}
