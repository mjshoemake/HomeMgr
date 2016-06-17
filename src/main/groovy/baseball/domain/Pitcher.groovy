package baseball.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Transient;


//import javax.persistence.Column;
import javax.persistence.Table


//import org.apache.log4j.Logger
@Entity
@Table(name="pitchers")
class Pitcher
{
    //def gameLog = Logger.getLogger('gamelog')
    @Id
    @GeneratedValue
    int pitcher_pk

    String name
    String teamName
    String year
    String position
    int battersRetired
    int orderPos
    int walks
    int strikeouts
    int hits
    int homers
    int hitByPitch
    double whip
    int balks
    SimPitcher simPitcher

    @Transient
    public def getBattersFaced() {
        battersRetired + walks + hits + hitByPitch
    }

    def getOppBattingAvg() {
        int oppAtBats = battersRetired + hits
        if (oppAtBats == 0) {
            BigDecimal.valueOf(0)
        }  else {
            BigDecimal.valueOf(hits / oppAtBats)
        }
    }

    public def getRate(int num) {
        def rate = BigDecimal.valueOf(num / battersFaced)
        rate
    }

    public def getRate(int num, int divisor) {
        def rate = BigDecimal.valueOf(num / divisor)
        rate
    }

}
