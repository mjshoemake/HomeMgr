package baseball

import java.sql.Connection;
import baseball.domain.Season
import baseball.mongo.MongoManager
import baseball.processing.BravesTeamLoader
import baseball.processing.DatabankTeamLoader
import baseball.processing.FileTeamLoader
import baseball.processing.ScheduleLoader
import com.accenture.core.model.DatabaseDriver
import com.accenture.core.utils.ConfigFileLoader
import mjs.common.utils.LogUtils
import org.junit.After
import org.junit.Before
import org.junit.Test

class DatabankTeamLoaderTest {

    def league = [:]
    def season
    MongoManager mongoDB = null
    DatabaseDriver dbDriver = null

    @Before
    void setUp() {
        // MySQL
        Properties dbProps = new ConfigFileLoader("/Users/Dad/Config/").loadPropertiesFile("mysql.properties")
        dbDriver = new DatabaseDriver(dbProps)

        // Mongo
        mongoDB = new MongoManager()
        mongoDB.open("ballsim")
    }

    @After
    void tearDown() {
        mongoDB.close()
        mongoDB = null
    }

    @Test
    void testLoad() {
        def division = []
        // Create loaders
        def dbTeamLoader = new DatabankTeamLoader(mongoDB, dbDriver)

        // Load teams
        def team = dbTeamLoader.loadTeamFromMysql("Braves", 2003)

        LogUtils.println(team.batters, "   ", true)
    }

}
