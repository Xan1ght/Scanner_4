package lexical.analyze;

import lexical.analyze.Scanner.tItem;
import static lexical.analyze.Scanner.N;

public class tChainHash {
    public tItem[] items;

    tChainHash(int index) {
        this.items = new tItem[index];
    }

    void init() {
        for (int i = 0; i < N; i++)
            items[i] = null;
    }
}
