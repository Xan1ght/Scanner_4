package lexical.analyze;

class Obj {         //
    String Name;    //
    int Cat;        //
    int Typ;        //
    int Val;        //
    Obj Prev;       //
}

//
class Table {

    //
    static final int
        catConst  = 1, catVar    = 2,
        catType   = 3, catStProc = 4,
        catModule = 5, catGuard  = 6;

    //
    static final int
        typNone = 0,
        typInt = 1,
        typBool = 2;

    private static Obj Top;     //
    private static Obj Bottom;  //
    private static Obj CurrObj; //

    //
    static void Init() {
        Top = null;
    }

    //
    static void Enter(String N, int C, int T, int V) {
        Obj P = new Obj();
        P.Name = new String(N);
        P.Cat = C;
        P.Typ = T;
        P.Val = V;
        P.Prev = Top;
        Top = P;
    }

    static void OpenScope() {
        Enter("", catGuard, typNone, 0);
        if (Top.Prev == null) {
            Bottom = Top;
        }
    }

    static void CloseScope() {
        while(Top.Cat != catGuard) {
            Top = Top.Prev;
        }
        Top = Top.Prev;
    }

    static Obj NewName(String Name, int Cat) {
        Obj obj = Top;
        while (obj.Cat != catGuard && obj.Name.compareTo(Name) != 0) {
            obj = obj.Prev;
        }
        if (obj.Cat == catGuard) {
            obj = new Obj();
            obj.Name = new String(Name);
            obj.Cat = Cat;
            obj.Val = 0;
            obj.Prev = Top;
            Top = obj;
        } else {
            Error.Message("Џ®ўв®а­®Ґ ®Ўкпў«Ґ­ЁҐ Ё¬Ґ­Ё");
        }
        return obj;
    }

    static Obj Find(String Name) {
        Obj obj;

        Bottom.Name = new String(Name);
        for (obj = Top; obj.Name.compareTo(Name) != 0; obj = obj.Prev);
        if (obj == Bottom) {
            Error.Message("ЌҐ®Ўкпў«Ґ­­®Ґ Ё¬п");
        }
        return obj;
    }

    static Obj FirstVar() {
        CurrObj = Top;
        return NextVar();
    }

    static Obj NextVar() {
        Obj VRef;

        while (CurrObj != Bottom && CurrObj.Cat != catVar) {
            CurrObj = CurrObj.Prev;
        }
        if (CurrObj == Bottom) {
            return null;
        } else {
            VRef = CurrObj;
            CurrObj = CurrObj.Prev;
            return VRef;
        }
    }

}