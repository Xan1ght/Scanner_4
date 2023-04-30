package lexical.analyze;

public class CPlusPlus {

    static void Init() {
        Text.Reset();
        if (!Text.Ok) {
            Error.Message(Text.Message);
        }
        Scanner.Init();
    }

    static void Done() {
        Text.Close();
    }

    public static void main(String[] args) {
        System.out.println("\nКомпилятор С++");
        if (args.length == 0) {
            Location.Path = null;
        } else {
            Location.Path = args[0];
        }
        Init();             // Инициализация текста и сканнера
        Pars.Compile();     // Компиляция по заданию А
        Done();             // Завершение работы
    }
}