package domaine.portfolio;

import domaine.portfolio.model.Position;
import org.apache.commons.lang3.StringUtils;

public class PositionVerifier {

    private PositionVerifier() {
        // private constructor to prevent instantiation
    }

    public static boolean isPositionValid(Position position) {
        return position != null
                && validSymbol(position.getSymbol())
                && validSize(position.getSize());
    }

    private static boolean validSize(Double size) {
        return size != null && size != 0;
    }

    private static boolean validSymbol(String symbol) {
        return StringUtils.isNotEmpty(symbol);
    }

}
