package lexical.analyze;

class Gen {
    static int PC;

    static void Init() {
        PC = 0;
    }

    static void Cmd(int Cmd) {
        if (PC < OVM.MEMSIZE) {
            OVM.M[PC++] = Cmd;
        } else {
            Error.Message("ЌҐ¤®бв в®з­® Ї ¬пвЁ ¤«п Є®¤ ");
        }
    }

    static void Fixup(int A) {
        while (A > 0) {
            int temp = OVM.M[A-2];
            OVM.M[A-2] = PC;
            A = temp;
        }
    }

    static void Abs() {
        Cmd(OVM.cmDup);
        Cmd(0);
        Cmd(PC+3);
        Cmd(OVM.cmIfGE);
        Cmd(OVM.cmNeg);
    }

    static void Min() {
        Cmd(Integer.MAX_VALUE);
        Cmd(OVM.cmNeg);
        Cmd(1);
        Cmd(OVM.cmSub);
    }

    static void Odd() {
        Cmd(2);
        Cmd(OVM.cmMod);
        Cmd(1);
        Cmd(0); //
        Cmd(OVM.cmIfNE);
    }

    static void Const(int C) {
        Cmd(Math.abs(C));
        if (C < 0) {
            Cmd(OVM.cmNeg);
        }
    }

    static void Comp(int Lex) {
        Cmd(0); //
        switch (Lex) {
            case Scanner.lexEQ : Cmd(OVM.cmIfNE); break;
            case Scanner.lexNE : Cmd(OVM.cmIfEQ); break;
            case Scanner.lexLE : Cmd(OVM.cmIfGT); break;
            case Scanner.lexLT : Cmd(OVM.cmIfGE); break;
            case Scanner.lexGE : Cmd(OVM.cmIfLT); break;
            case Scanner.lexGT : Cmd(OVM.cmIfLE); break;
        }
    }

    static void Addr(Obj X) {
        Cmd(X.Val);   //
        X.Val = PC+1; //
    }

    static void AllocateVariables() {
        Obj VRef; //

        VRef = Table.FirstVar(); //
        while (VRef != null) {
            if (VRef.Val == 0) {
                Error.Warning("ЏҐаҐ¬Ґ­­ п " + VRef.Name + " ­Ґ ЁбЇ®«м§гҐвбп");
            } else if (PC < OVM.MEMSIZE) {
                Fixup(VRef.Val);   //
                PC++;
            } else {
                Error.Message("ЌҐ¤®бв в®з­® Ї ¬пвЁ ¤«п ЇҐаҐ¬Ґ­­ле");
            }
            VRef = Table.NextVar(); //
        }
    }
}