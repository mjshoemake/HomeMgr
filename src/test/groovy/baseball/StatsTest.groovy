package baseball

import baseball.domain.BallGame
import baseball.domain.Batter
import baseball.mongo.MongoManager
import baseball.processing.BravesTeamLoader
import baseball.processing.DatabankTeamLoader
import com.accenture.core.model.DatabaseDriver
import com.accenture.core.utils.ConfigFileLoader
import org.junit.After
import org.junit.Before
import org.junit.Test

class StatsTest {

    MongoManager mongoDB = null

    @Before
    void setUp() {
        mongoDB = new MongoManager()
        mongoDB.open("ballsim")
    }

    @After
    void tearDown() {
        mongoDB.close()
        mongoDB = null
    }

    @Test
    void testStatistics() {
        def bravesTeamLoader = new BravesTeamLoader(mongoDB)
        def braves2003 = bravesTeamLoader.loadBraves2003()

        braves2003.lineup.each() {
            Batter batter = it.simBatter.batter
            println "${batter.nameFirst} ${batter.nameLast} ${batter.battingAvg}  ${batter.fieldingPercentage}  ${batter.sluggingPercentage}  ${batter.onBasePercentage}  ${batter.ops}   ${batter.rank}"
        }
    }
}
