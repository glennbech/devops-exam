package devops.org.eksamen.cards;

import java.util.Arrays;
import java.util.List;

public class CardsService {
    public static List<String> getCards() {
        String allCards = "001-monster.svg\n" +
                "002-monster.svg\n" +
                "003-cyclops.svg\n" +
                "004-monster.svg\n" +
                "005-monster.svg\n" +
                "006-monster.svg\n" +
                "007-dragon.svg\n" +
                "008-monster.svg\n" +
                "009-monster.svg\n" +
                "010-cyclops.svg\n" +
                "011-cyclops.svg\n" +
                "012-monster.svg\n" +
                "013-monster.svg\n" +
                "014-monster.svg\n" +
                "015-monster.svg\n" +
                "016-cyclops.svg\n" +
                "017-monster.svg\n" +
                "018-cyclops.svg\n" +
                "019-monster.svg\n" +
                "020-monster.svg\n" +
                "021-monster.svg\n" +
                "022-monster.svg\n" +
                "023-monster.svg\n" +
                "024-monster.svg\n" +
                "025-monster.svg\n" +
                "026-monster.svg\n" +
                "027-monster.svg\n" +
                "028-monster.svg\n" +
                "029-monster.svg\n" +
                "030-monster.svg\n" +
                "031-cyclops.svg\n" +
                "032-monster.svg\n" +
                "033-monster.svg\n" +
                "034-monster.svg\n" +
                "035-monster.svg\n" +
                "036-monster.svg\n" +
                "037-cyclops.svg\n" +
                "038-monster.svg\n" +
                "039-monster.svg\n" +
                "040-monster.svg\n" +
                "041-monster.svg\n" +
                "042-dragon.svg\n" +
                "043-cyclops.svg\n" +
                "044-monster.svg\n" +
                "045-monster.svg\n" +
                "046-cyclops.svg\n" +
                "047-monster.svg\n" +
                "048-monster.svg\n" +
                "049-monster.svg\n" +
                "050-monster.svg\n" +
                "051-monster.svg\n" +
                "052-snake.svg\n" +
                "053-monster.svg\n" +
                "054-monster.svg\n" +
                "055-monster.svg\n" +
                "056-monster.svg\n" +
                "057-monster.svg\n" +
                "058-monster.svg\n" +
                "059-monster.svg\n" +
                "060-monster.svg\n" +
                "061-monster.svg\n" +
                "062-monster.svg\n" +
                "063-monster.svg\n" +
                "064-monster.svg\n" +
                "065-monster.svg\n" +
                "066-monster.svg\n" +
                "067-monster.svg\n" +
                "068-cyclops.svg\n" +
                "069-monster.svg\n" +
                "070-monster.svg\n" +
                "071-monster.svg\n" +
                "072-dragon.svg\n" +
                "073-monster.svg\n" +
                "074-monster.svg\n" +
                "075-monster.svg\n" +
                "076-monster.svg\n" +
                "077-monster.svg\n" +
                "078-monster.svg\n" +
                "079-monster.svg\n" +
                "080-cyclops.svg\n" +
                "081-monster.svg\n" +
                "082-monster.svg\n" +
                "083-monster.svg\n" +
                "084-monster.svg\n" +
                "085-monster.svg\n" +
                "086-monster.svg\n" +
                "087-monster.svg\n" +
                "088-monster.svg\n" +
                "089-monster.svg\n" +
                "090-monster.svg\n" +
                "091-monster.svg\n" +
                "092-cyclops.svg\n" +
                "093-monster.svg\n" +
                "094-monster.svg\n" +
                "095-monster.svg\n" +
                "096-monster.svg\n" +
                "097-monster.svg\n" +
                "098-monster.svg\n" +
                "099-monster.svg\n" +
                "100-monster.svg\n";
        return Arrays.asList(allCards.split("\n").clone());
    }

    public static String getRandomCard(){
        return getCards().get((int) (Math.random() * getCards().size()));
    }
}
