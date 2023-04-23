package lexical.analyze;

//
class Pars {
    static final int
            spABS    = 1,
            spMAX    = 2,
            spMIN    = 3,
            spDEC    = 4,
            spODD    = 5,
            spHALT   = 6,
            spINC    = 7,
            spInOpen = 8,
            spInInt  = 9,
            spOutInt = 10,
            spOutLn  = 11;

    static void Check(int L, String M) {
        if (Scanner.Lex != L) {
            Error.Expected(M);
        } else {
            Scanner.NextLex();
        }
    }

    //
    static int ConstExpr() {
        int v = 0;
        Obj X;
        int Op;

        Op = Scanner.lexPlus;
        if (Scanner.Lex == Scanner.lexPlus || Scanner.Lex == Scanner.lexMinus) {
            Op = Scanner.Lex;
            Scanner.NextLex();
        }
        if (Scanner.Lex == Scanner.lexNum) {
            v = Scanner.Num;
            Scanner.NextLex();
        } else if (Scanner.Lex == Scanner.lexName) {
            X = Table.Find(Scanner.Name);
            if (X.Cat == Table.catGuard) {
                Error.Message("ЌҐ«м§п ®ЇаҐ¤Ґ«пвм Є®­бв ­вг зҐаҐ§ бҐЎп");
            } else if (X.Cat != Table.catConst) {
                Error.Expected("Ё¬п Є®­бв ­вл");
            } else {
                v = X.Val;
                Scanner.NextLex();
            }
        } else {
            Error.Expected("Є®­бв ­в­®Ґ ўла ¦Ґ­ЁҐ");
        }
        if (Op == Scanner.lexMinus) {
            return -v;
        }
        return v;
    }

    //
    static void ConstDecl() {
        Obj ConstRef; //

        ConstRef = Table.NewName(Scanner.Name, Table.catGuard);
        Scanner.NextLex();
        Check(Scanner.lexEQ, "\"=\"");
        ConstRef.Val = ConstExpr();
        ConstRef.Typ = Table.typInt; //
        ConstRef.Cat = Table.catConst;
    }

    static void ParseType() {
        Obj TypeRef;

        if (Scanner.Lex != Scanner.lexName) {
            Error.Expected("Ё¬п");
        } else {
            TypeRef = Table.Find(Scanner.Name);
            if (TypeRef.Cat != Table.catType) {
                Error.Expected("Ё¬п вЁЇ ");
            } else if (TypeRef.Typ != Table.typInt) {
                Error.Expected("жҐ«л© вЁЇ");
            }
            Scanner.NextLex();
        }
    }

    //
    static void VarDecl() {
        Obj NameRef;

        if (Scanner.Lex != Scanner.lexName) {
            Error.Expected("Ё¬п");
        } else {
            NameRef = Table.NewName(Scanner.Name, Table.catVar);
            NameRef.Typ = Table.typInt;
            Scanner.NextLex();
        }
        while (Scanner.Lex == Scanner.lexComma) {
            Scanner.NextLex();
            if (Scanner.Lex != Scanner.lexName) {
                Error.Expected("Ё¬п");
            } else {
                NameRef = Table.NewName(Scanner.Name, Table.catVar);
                NameRef.Typ = Table.typInt;
                Scanner.NextLex();
            }
        }
        Check(Scanner.lexColon, "\":\"");
        ParseType();
    }

    //
    static void DeclSeq() {
        while (Scanner.Lex == Scanner.lexCONST || Scanner.Lex == Scanner.lexVAR) {
            if (Scanner.Lex == Scanner.lexCONST) {
                Scanner.NextLex();
                while (Scanner.Lex == Scanner.lexName) {
                    ConstDecl(); //
                    Check(Scanner.lexSemi, "\";\"");
                }
            } else {
                Scanner.NextLex(); // VAR
                while (Scanner.Lex == Scanner.lexName) {
                    VarDecl();   //
                    Check(Scanner.lexSemi, "\";\"");
                }
            }
        }
    }

    static void IntExpression() {
        if (Expression() != Table.typInt) {
            Error.Expected("ўла ¦Ґ­ЁҐ жҐ«®Ј® вЁЇ ");
        }
    }

    static int StFunc(int F) {
        switch (F) {
            case spABS:
                IntExpression();
                Gen.Abs();
                return Table.typInt;
            case spMAX:
                ParseType();
                Gen.Cmd(Integer.MAX_VALUE);
                return Table.typInt;
            case spMIN:
                ParseType();
                Gen.Min();
                return Table.typInt;
            case spODD:
                IntExpression();
                Gen.Odd();
                return Table.typBool;
        }
        return Table.typNone; //
    }

    static int Factor() {
        Obj X;
        int T = 0; //

        if (Scanner.Lex == Scanner.lexName) {
            if ((X = Table.Find(Scanner.Name)).Cat == Table.catVar) {
                Gen.Addr(X);    //
                Gen.Cmd(OVM.cmLoad);
                Scanner.NextLex();
                return X.Typ;
            } else if (X.Cat == Table.catConst) {
                Gen.Const(X.Val);
                Scanner.NextLex();
                return X.Typ;
            } else if (X.Cat == Table.catStProc && X.Typ != Table.typNone) {
                Scanner.NextLex();
                Check(Scanner.lexLPar, "\"(\"");
                T = StFunc(X.Val);
                Check(Scanner.lexRPar, "\")\"");
            } else {
                Error.Expected("ЇҐаҐ¬Ґ­­ п, Є®­бв ­в  Ё«Ё Їа®жҐ¤га -дг­ЄжЁЁ");
            }
        } else if (Scanner.Lex == Scanner.lexNum) {
            Gen.Const(Scanner.Num);
            Scanner.NextLex();
            return Table.typInt;
        } else if (Scanner.Lex == Scanner.lexLPar) {
            Scanner.NextLex();
            T = Expression();
            Check(Scanner.lexRPar, "\")\"");
        } else {
            Error.Expected("Ё¬п, зЁб«® Ё«Ё \"(\"");
        }
        return T;
    }

    static int Term() {
        int Op;
        int T = Factor();

        if (Scanner.Lex == Scanner.lexMult || Scanner.Lex == Scanner.lexDIV || Scanner.Lex == Scanner.lexMOD) {
            if (T != Table.typInt) {
                Error.Message("ЌҐб®®вўҐвбвўЁҐ ®ЇҐа жЁЁ вЁЇг ®ЇҐа ­¤ ");
            }
            do {
                Op = Scanner.Lex;
                Scanner.NextLex();
                if( (T = Factor()) != Table.typInt )
                    Error.Expected("ўла ¦Ґ­ЁҐ жҐ«®Ј® вЁЇ ");
                switch( Op ) {
                    case Scanner.lexMult: Gen.Cmd(OVM.cmMult); break;
                    case Scanner.lexDIV:  Gen.Cmd(OVM.cmDiv); break;
                    case Scanner.lexMOD:  Gen.Cmd(OVM.cmMod); break;
                }
            } while (Scanner.Lex == Scanner.lexMult || Scanner.Lex == Scanner.lexDIV ||
                Scanner.Lex == Scanner.lexMOD );
        }
        return T;
    }

    //
    static int SimpleExpr() {
        int T;
        int Op;

        if (Scanner.Lex == Scanner.lexPlus || Scanner.Lex == Scanner.lexMinus) {
            Op = Scanner.Lex;
            Scanner.NextLex();
            if ((T = Term()) != Table.typInt) {
                Error.Expected("ўла ¦Ґ­ЁҐ жҐ«®Ј® вЁЇ ");
            }
            if (Op == Scanner.lexMinus) {
                Gen.Cmd(OVM.cmNeg);
            }
        } else {
            T = Term();
        }
        if (Scanner.Lex == Scanner.lexPlus || Scanner.Lex == Scanner.lexMinus) {
            if (T != Table.typInt) {
                Error.Message("ЌҐб®®вўҐвбвўЁҐ ®ЇҐа жЁЁ вЁЇг ®ЇҐа ­¤ ");
            }
            do {
                Op = Scanner.Lex;
                Scanner.NextLex();
                if ((T = Term()) != Table.typInt) {
                    Error.Expected("ўла ¦Ґ­ЁҐ жҐ«®Ј® вЁЇ ");
                }
                switch (Op) {
                    case Scanner.lexPlus:  Gen.Cmd(OVM.cmAdd); break;
                    case Scanner.lexMinus: Gen.Cmd(OVM.cmSub); break;
                }
            } while (Scanner.Lex == Scanner.lexPlus || Scanner.Lex == Scanner.lexMinus );
        }
        return T;
    }

    // Џа®бв®Ґ‚ла ¦ [Ћв­®иҐ­ЁҐ Џа®бв®Ґ‚ла ¦]
    static int Expression() {
        int Op;
        int T = SimpleExpr();

        if (Scanner.Lex == Scanner.lexEQ || Scanner.Lex == Scanner.lexNE ||
                Scanner.Lex == Scanner.lexGT || Scanner.Lex == Scanner.lexGE ||
                Scanner.Lex == Scanner.lexLT || Scanner.Lex == Scanner.lexLE )
        {
            Op = Scanner.Lex;
            if (T != Table.typInt) {
                Error.Message("ЌҐб®®вўҐвбвўЁҐ ®ЇҐа жЁЁ вЁЇг ®ЇҐа ­¤ ");
            }
            Scanner.NextLex();
            if ((T = SimpleExpr()) != Table.typInt) {
                Error.Expected("ўла ¦Ґ­ЁҐ жҐ«®Ј® вЁЇ ");
            }
            Gen.Comp(Op);   //
            T = Table.typBool;
        }
        return T;
    }

    //
    static void Variable() {
        Obj X;

        if (Scanner.Lex != Scanner.lexName) {
            Error.Expected("Ё¬п");
        } else {
            if ((X = Table.Find(Scanner.Name)).Cat != Table.catVar) {
                Error.Expected("Ё¬п ЇҐаҐ¬Ґ­­®©");
            }
            Gen.Addr(X);
            Scanner.NextLex();
        }
    }

    static void StProc(int P) {
        switch (P) {
            case spDEC:
                Variable();
                Gen.Cmd(OVM.cmDup);
                Gen.Cmd(OVM.cmLoad);
                if (Scanner.Lex == Scanner.lexComma) {
                    Scanner.NextLex();
                    IntExpression();
                } else {
                    Gen.Cmd(1);
                }
                Gen.Cmd(OVM.cmSub);
                Gen.Cmd(OVM.cmSave);
                return;
            case spINC:
                Variable();
                Gen.Cmd(OVM.cmDup);
                Gen.Cmd(OVM.cmLoad);
                if (Scanner.Lex == Scanner.lexComma) {
                    Scanner.NextLex();
                    IntExpression();
                } else {
                    Gen.Cmd(1);
                }
                Gen.Cmd(OVM.cmAdd);
                Gen.Cmd(OVM.cmSave);
                return;
            case spInOpen:
                //
                return;
            case spInInt:
                Variable();
                Gen.Cmd(OVM.cmIn);
                Gen.Cmd(OVM.cmSave);
                return;
            case spOutInt:
                IntExpression();
                Check(Scanner.lexComma , "\",\"");
                IntExpression();
                Gen.Cmd(OVM.cmOut);
                return;
            case spOutLn:
                Gen.Cmd(OVM.cmOutLn);
                return;
            case spHALT:
                Gen.Const(ConstExpr());
                Gen.Cmd(OVM.cmStop);
                return;
        }
    }

    static void BoolExpression() {
        if (Expression() != Table.typBool) {
            Error.Expected("«®ЈЁзҐбЄ®Ґ ўла ¦Ґ­ЁҐ");
        }
    }

    // ЏҐаҐ¬Ґ­­ п "=" ‚ла ¦
    static void AssStatement() {
        Variable();
        if (Scanner.Lex == Scanner.lexAss) {
            Scanner.NextLex();
            IntExpression();
            Gen.Cmd(OVM.cmSave);
        } else {
            Error.Expected("\":=\"");
        }
    }

    //
    static void CallStatement(int sp) {
        Check(Scanner.lexName, "Ё¬п Їа®жҐ¤гал");
        if (Scanner.Lex == Scanner.lexLPar) {
            Scanner.NextLex();
            StProc(sp);
            Check( Scanner.lexRPar, "\")\"" );
        } else if (sp == spOutLn || sp == spInOpen) {
            StProc(sp);
        } else {
            Error.Expected("\"(\"");
        }
    }

    static void IfStatement() {
        int CondPC;
        int LastGOTO;

        Check(Scanner.lexIF, "IF");
        LastGOTO = 0;      //
        BoolExpression();
        CondPC = Gen.PC;        //
        Check(Scanner.lexTHEN, "THEN");
        StatSeq();

        while (Scanner.Lex == Scanner.lexELSIF) {
            Gen.Cmd(LastGOTO);   //
            Gen.Cmd(OVM.cmGOTO); //
            LastGOTO = Gen.PC;   //
            Scanner.NextLex();
            Gen.Fixup(CondPC);   //
            BoolExpression();
            CondPC = Gen.PC;     //
            Check(Scanner.lexTHEN, "THEN");
            StatSeq();
        }
        if (Scanner.Lex == Scanner.lexELSE) {
            Gen.Cmd(LastGOTO);   //
            Gen.Cmd(OVM.cmGOTO); //
            LastGOTO = Gen.PC;   //
            Scanner.NextLex();
            Gen.Fixup(CondPC);   //
            StatSeq();
        } else {
            Gen.Fixup(CondPC);    //
        }
        Check( Scanner.lexEND, "END" );
        Gen.Fixup(LastGOTO);     //
    }

    static void WhileStatement() {
        int WhilePC = Gen.PC;

        Check(Scanner.lexWHILE, "WHILE");
        BoolExpression();

        int CondPC = Gen.PC;

        Check(Scanner.lexDO, "DO");
        StatSeq();
        Check(Scanner.lexEND, "END");
        Gen.Cmd(WhilePC);
        Gen.Cmd(OVM.cmGOTO);
        Gen.Fixup(CondPC);
    }

    static void Statement() {
        Obj X;

        if (Scanner.Lex == Scanner.lexName) {
            if ((X=Table.Find(Scanner.Name)).Cat == Table.catModule) {
                Scanner.NextLex();
                Check(Scanner.lexDot, "\".\"");
                if (Scanner.Lex == Scanner.lexName && X.Name.length() + Scanner.Name.length() <= Scanner.NAMELEN) {
                    X = Table.Find(X.Name + "." + Scanner.Name);
                } else {
                    Error.Expected("Ё¬п Ё§ ¬®¤г«п " + X.Name);
                }
            }
            if (X.Cat == Table.catVar) {
                AssStatement();        //
            } else if( X.Cat == Table.catStProc && X.Typ == Table.typNone) {
                CallStatement(X.Val); //
            } else {
                Error.Expected("®Ў®§­ зҐ­ЁҐ ЇҐаҐ¬Ґ­­®© Ё«Ё Їа®жҐ¤гал");
            }
        } else if (Scanner.Lex == Scanner.lexIF) {
            IfStatement();
        } else if (Scanner.Lex == Scanner.lexWHILE) {
            WhileStatement();
        }
        //
    }

    //
    static void StatSeq() {
        Statement();    //
        while (Scanner.Lex == Scanner.lexSemi) {
            Scanner.NextLex();
            Statement(); //
        }
    }

    static void ImportName() {
        if (Scanner.Lex == Scanner.lexName) {
            Table.NewName(Scanner.Name, Table.catModule);

            if (Scanner.Name.compareTo("In") == 0) {
                Table.Enter("In.Open", Table.catStProc, Table.typNone, spInOpen);
                Table.Enter("In.Int", Table.catStProc, Table.typNone, spInInt);
            } else if (Scanner.Name.compareTo("Out") == 0) {
                Table.Enter("Out.Int", Table.catStProc, Table.typNone, spOutInt);
                Table.Enter("Out.Ln", Table.catStProc, Table.typNone, spOutLn);
            } else
                Error.Message("ЌҐЁ§ўҐбв­л© ¬®¤г«м");
            Scanner.NextLex();
        } else {
            Error.Expected("Ё¬п Ё¬Ї®авЁагҐ¬®Ј® ¬®¤г«п");
        }
    }

    // IMPORT
    static void Import() {
        Check(Scanner.lexIMPORT, "IMPORT");
        ImportName();    //
        while (Scanner.Lex == Scanner.lexComma) {
            Scanner.NextLex();
            ImportName(); //
        }
        Check(Scanner.lexSemi, "\";\"");
    }

    // MODULE
// END
    static void Module() {
        Obj ModRef; //

        Check(Scanner.lexMODULE, "MODULE");
        if (Scanner.Lex != Scanner.lexName) {
            Error.Expected("Ё¬п ¬®¤г«п");
        }
        //
        ModRef = Table.NewName(Scanner.Name, Table.catModule);
        Scanner.NextLex();
        Check(Scanner.lexSemi, "\";\"");
        if (Scanner.Lex == Scanner.lexIMPORT) {
            Import();
        }
        DeclSeq();
        if (Scanner.Lex == Scanner.lexBEGIN) {
            Scanner.NextLex();
            StatSeq();
        }
        Check(Scanner.lexEND, "END");

        //
        if (Scanner.Lex != Scanner.lexName) {
            Error.Expected("Ё¬п ¬®¤г«п");
        } else if (Scanner.Name.compareTo(ModRef.Name) != 0) {
            Error.Expected("Ё¬п ¬®¤г«п \"" + ModRef.Name + "\"");
        } else {
            Scanner.NextLex();
        }
        if (Scanner.Lex != Scanner.lexDot) {
            Error.Expected("\".\"");
        }
        Gen.Cmd(0);              //
        Gen.Cmd(OVM.cmStop);     //
        Gen.AllocateVariables(); //
    }

    static void Compile() {
        Table.Init();
        Table.OpenScope(); //
        Table.Enter("ABS", Table.catStProc, Table.typInt, spABS);
        Table.Enter("MAX", Table.catStProc, Table.typInt, spMAX);
        Table.Enter("MIN", Table.catStProc, Table.typInt, spMIN);
        Table.Enter("DEC", Table.catStProc, Table.typNone, spDEC);
        Table.Enter("ODD", Table.catStProc, Table.typBool, spODD);
        Table.Enter("HALT", Table.catStProc, Table.typNone, spHALT);
        Table.Enter("INC", Table.catStProc, Table.typNone, spINC);
        Table.Enter("INTEGER", Table.catType, Table.typInt, 0);
        Table.OpenScope();  //
        Module();
        Table.CloseScope(); //
        Table.CloseScope(); //
        System.out.println("\nЉ®¬ЇЁ«пжЁп § ўҐаиҐ­ ");
    }

}