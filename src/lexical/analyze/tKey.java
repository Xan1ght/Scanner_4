package lexical.analyze;

import static lexical.analyze.Scanner.MAX_LENGTH_KEY;

public class tKey {
    public String name;

    public tKey(String name) {
        if (name.length() > MAX_LENGTH_KEY) {
            this.name = name.substring(0, MAX_LENGTH_KEY);
        } else {
            this.name = name;
        }
    }

    public String getKey() {
        return name;
    }
}
