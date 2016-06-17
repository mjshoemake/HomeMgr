
package baseball.exec

import baseball.domain.Batter
import baseball.processing.FileTeamLoader
import baseball.mongo.MongoManager
import mjs.common.utils.LogUtils
import org.bson.types.ObjectId

//import org.junit.After
//import org.junit.Before
//import org.junit.Test

class FileTeamLoaderTest {

    static void main(String[] args) {
        MongoManager mongoDB = new MongoManager()
        mongoDB.open("ballsim")

        Batter batter = new Batter(atBats: 57, hits: 19, homers: 5, caughtStealing: 0, doubles: 2, hitByPitch: 1, name: "Bob Horner", position: "3B", stolenBases: 0, strikeouts: 6, teamName: "Braves", year: 1982, triples: 2, walks: 3)
        ObjectId id = mongoDB.addToCollection("batter", batter)
        long count = mongoDB.getCount("batter")
        println "Count: $count   ID: ${id.toString()}"
        def batterList = mongoDB.findAll("batter")
        def newBatterList = mongoDB.find("batter", [position:"3B"])
        LogUtils.println(newBatterList, "   ", true)
        FileTeamLoader loader = new FileTeamLoader(mongoDB)
        //loader.loadTeamFromFile("Phillies", 2003)
    }
}