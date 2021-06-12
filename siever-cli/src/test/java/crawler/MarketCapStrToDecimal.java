package crawler;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MarketCapStrToDecimal {
    private final String str;

    public MarketCapStrToDecimal(String str) {
        this.str = str;
    }

    public BigDecimal translate() {
        if (str != null && str.length() > 1) {
            String num = str.substring(0, str.length() - 1);
            long l;
            String unit = str.substring(str.length() - 1);
            switch (unit) {
                case "M":
                    l = 1_000_000L;
                    break;
                case "B":
                    l = 1_000_000_000L;
                    break;
                case "T":
                    l = 1_000_000_000_000L;
                    break;
                default:
                    l = 0;
            }
            double d = Double.parseDouble(num);
            BigDecimal bigDecimal = new BigDecimal(d);
            BigDecimal multiply = bigDecimal.multiply(new BigDecimal(l));
            return multiply.divide(new BigDecimal(1_000_000_000), 4, RoundingMode.HALF_UP);
        }
        return new BigDecimal(0);
    }
}
