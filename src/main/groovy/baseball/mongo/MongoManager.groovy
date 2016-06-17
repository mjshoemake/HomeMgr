package baseball.mongo

import com.mongodb.DB
import com.mongodb.async.SingleResultCallback
import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoCursor
import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.mongodb.WriteResult
import com.mongodb.client.MongoDatabase
import groovy.json.*
import mjs.common.utils.LogUtils
import org.bson.Document
import org.bson.conversions.Bson
import org.bson.types.ObjectId


/**
 * Created by Dad on 6/14/16.
 */
class MongoManager {

    MongoClient client = null
    MongoDatabase db = null

    void open(String database) {
        client = open("localhost", 27017, database)
    }

    void open(String host, int port, String database) {
        //client = new MongoClient(host, port)
        //database = client.getDB(database)
        MongoClientURI uri  = new MongoClientURI("mongodb://$host:$port/$database")
        client = new MongoClient(uri)
        db = client.getDatabase(uri.getDatabase())
    }

    ObjectId generateObjectId() {
        return new ObjectId()
    }

    ObjectId addToCollection(String collection, Object obj) {
        obj._id = generateObjectId()
        String json = new JsonBuilder(obj).toPrettyString()
        addToCollection(collection, json)
        return obj._id
    }

    void addToCollection(String collection, String json) {
        MongoCollection<Document> table = db.getCollection(collection)
        Document doc = Document.parse(json)
        table.insertOne(doc)
    }

    long getCount(String collection) {
        MongoCollection<Document> table = db.getCollection(collection)
        return table.count()
    }

    List findAll(String collection) {
        MongoCollection<Document> table = db.getCollection(collection)
        MongoCursor<Document> iter = table.find().iterator()
        def list = []
        JsonSlurper jsonSlurper = new JsonSlurper()
        while (iter.hasNext()) {
            Document nextDoc = iter.next()
            list << jsonSlurper.parseText(nextDoc.toJson())
        }
        LogUtils.println(list, "   ", true)
        list
    }

    Bson mapToBson(Map map) {
        Bson target = new Bson

    }

    List find(String collection, Map filterMap) {
        Bson filter = mapToBson(filterMap)
        MongoCollection<Document> table = db.getCollection(collection)
        MongoCursor<Document> iter = table.find(filter)
        def list = []
        JsonSlurper jsonSlurper = new JsonSlurper()
        while (iter.hasNext()) {
            Document nextDoc = iter.next()
            list << jsonSlurper.parseText(nextDoc.toJson())
        }
        LogUtils.println(list, "   ", true)
        list
    }

    List find(String key, Object value) {
        iter
    }
}
