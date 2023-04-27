package lexical.analyze;

//
class Pars {
    static void Compile() {
        Scanner.NextLex();

        while (Scanner.Lex != Scanner.lexEOT) {
            if (Scanner.Lex != Scanner.lexNone) {
                Counter.Add();
            }
        }

        System.out.println("\nКомпиляция задачи А завершена.\n");

        Counter.Info();
    }
}