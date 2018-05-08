package messaging;

import com.arangodb.ArangoDatabase;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.linkedin.replica.editInfo.config.Configuration;
import com.linkedin.replica.editInfo.database.DatabaseConnection;
import com.linkedin.replica.editInfo.database.DatabaseSeed;
import com.linkedin.replica.editInfo.database.handlers.impl.ArangoEditInfoHandler;
import com.linkedin.replica.editInfo.messaging.ClientMessagesReceiver;
import com.linkedin.replica.editInfo.models.*;
import com.rabbitmq.client.*;
import org.json.simple.parser.ParseException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.print.DocFlavor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;

public class ClientMessagesTest {
    private static Configuration config;
    private static String QUEUE_NAME;
    private static ClientMessagesReceiver messagesReceiver;
    private static ArangoDatabase arangoDb;
    private static ArangoEditInfoHandler arangoHandler;

    private static ConnectionFactory factory;
    private static Connection connection;
    private static Channel channel;

    @BeforeClass
    public static void init() throws IOException, TimeoutException, ParseException, SQLException, ClassNotFoundException {
        String rootFolder = "src/main/resources/config/";
        Configuration.init(rootFolder + "app.config",
                rootFolder + "arango.test.config",
                rootFolder + "commands.config",rootFolder+"controller.config",rootFolder+"cache.config");
        DatabaseConnection.init();
        config = Configuration.getInstance();

        // init message receiver
        QUEUE_NAME = config.getAppConfigProp("rabbitmq.queue.client");
        messagesReceiver = new ClientMessagesReceiver();

        // init db
        arangoDb = DatabaseConnection.getInstance().getArangoDriver().db(
                Configuration.getInstance().getArangoConfigProp("db.name")
        );

//        arangoDb.createCollection(
//                config.getArangoConfigProp("collection.companies.name")
//        );
//        arangoDb.createCollection(
//                config.getArangoConfigProp("collection.users.name")
//        );

        arangoHandler = new ArangoEditInfoHandler();

        factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
        DatabaseSeed databaseSeed  = new DatabaseSeed();
//        databaseSeed.insertUsers();
//        databaseSeed.insertCompanies();
    }

    @Test
    public void testMessages() throws IOException, InterruptedException {
        JsonObject object = new JsonObject();
        object.addProperty("companyId", "12");
        object.addProperty("commandName", "company.get");
        byte[] message = object.toString().getBytes();
        final String corrId = UUID.randomUUID().toString();

        String replyQueueName = channel.queueDeclare().getQueue();

        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();

        channel.basicPublish("", QUEUE_NAME, props, message);

        final BlockingQueue<String> response = new ArrayBlockingQueue<String>(1);

        channel.basicConsume(replyQueueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                if (properties.getCorrelationId().equals(corrId)) {
                    response.offer(new String(body));
                }
            }
        });
        String resMessage = response.take();
        JsonObject resObject = new JsonParser().parse(resMessage).getAsJsonObject();
        System.out.println(resObject+"hereee");

        assertEquals("", "microsoft3",  resObject.get("results").getAsJsonObject().get("companyName").getAsString());
    }
    @AfterClass
    public static void clean() throws IOException, TimeoutException {
        // close message queue connection
        messagesReceiver.closeConnection();
        channel.close();
        connection.close();
        // clean db
//        arangoDb.collection(
//                config.getArangoConfigProp("collection.companies.name")
//        ).drop();
//        arangoDb.collection(
//                config.getArangoConfigProp("collection.users.name")
//        ).drop();
        DatabaseConnection.getInstance().closeConnections();
    }
}