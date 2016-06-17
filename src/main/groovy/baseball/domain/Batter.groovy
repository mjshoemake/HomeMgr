package baseball.domain

//import org.apache.log4j.Logger
import org.bson.types.ObjectId

class Batter
{
    //def gameLog = Logger.getLogger('gamelog')
    ObjectId _id
    String name
    String teamName
    String year
    String position
    int atBats = 0
    int walks = 0
    int strikeouts = 0
    int hits = 0
    int doubles = 0
    int triples = 0
    int homers = 0
    int hitByPitch = 0
    int stolenBases = 0
    int caughtStealing = 0
    SimBatter simBatter

    public def getBattingAvg() {
        BigDecimal.valueOf(hits / atBats)
    }

    public def getPlateAppearances() {
        atBats + walks + hitByPitch
    }

    public def getRate(int num) {
        if (plateAppearances == 0) {
            BigDecimal.valueOf(0)
        } else {
            BigDecimal.valueOf(num / plateAppearances)
        }
    }

    public def getRate(int num, int divisor) throws Exception {
        if (divisor == 0) {
            BigDecimal.valueOf(0)
        } else {
            BigDecimal.valueOf(num / divisor)
        }
    }

}
