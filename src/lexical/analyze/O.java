package lexical.analyze;

public class O {

    static void Init() {
        Text.Reset();
        if (!Text.Ok) {
            Error.Message(Text.Message);
        }
        Scanner.Init();
        Gen.Init();
    }

    static void Done() {
        Text.Close();
    }

    public static void main(String[] args) {
        System.out.println("\nЉ®¬ЇЁ«пв®а п§лЄ  Ћ");
        if (args.length == 0) {
            Location.Path = null;
        } else {
            Location.Path = args[0];
        }
        Init();         //
        Pars.Compile(); //
        OVM.Run();      //
        Done();         //
    }
}