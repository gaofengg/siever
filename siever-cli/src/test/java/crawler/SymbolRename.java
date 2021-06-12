package crawler;

public class SymbolRename {
    private final String str;
    private final String split;

    public SymbolRename(String str, String split) {
        this.str = str;
        this.split = split;
    }

    public String translate() {
        String str1 = str.replace(" ", split);
        String str11 = str1.substring(0, str1.length() - 2);
        String str12 = str1.substring(str1.length() - 1);
        return str11.concat(str12);
    }
}
