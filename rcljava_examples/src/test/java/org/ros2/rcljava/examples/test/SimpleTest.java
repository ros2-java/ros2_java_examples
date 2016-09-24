package org.ros2.rcljava.examples.test;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.ros2.rcljava.NativeUtils;
import org.ros2.rcljava.Node;
import org.ros2.rcljava.Publisher;
import org.ros2.rcljava.QoSProfile;
import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.service.Client;

import example_interfaces.srv.AddTwoInts;

public class SimpleTest {
    private static final String NODE_NAME = SimpleTest.class.getName();
    private static Logger logger = Logger.getLogger(RCLJava.LOG_NAME);

    @BeforeClass
    public static void setUp() {
        logger.setLevel(Level.ALL);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);
        handler.setLevel(Level.ALL);
    }

    /**
     * Test.
     */
    @Test
    public void testSample() {
        // Initialize RCL
        RCLJava.rclJavaInit();

        // Let's create a Node
        Node node = RCLJava.createNode(NODE_NAME);

        // Testing Publisher
        //-------------------------------------------------------------------------------------------------------------

        // Publishers are type safe, make sure to pass the message type
        Publisher<std_msgs.msg.String> chatter_pub =
                node.<std_msgs.msg.String>createPublisher(
                    std_msgs.msg.String.class,
                    "chatter",
                    QoSProfile.PROFILE_DEFAULT);

        std_msgs.msg.String msg = new std_msgs.msg.String();
        msg.setData("Hello World");

        System.out.println("Publishing: \"" + msg.getData() + "\"");
        chatter_pub.publish(msg);

        // Testing Client
        //-------------------------------------------------------------------------------------------------------------

//        Client<AddTwoInts> client = node.<AddTwoInts>createClient(
//                AddTwoInts.class,
//                "add_two_ints",
//                QoSProfile.PROFILE_SERVICES_DEFAULT);
//
//        // Set request.
//        AddTwoInts.Request request = new AddTwoInts.Request();
//        request.setA(2);
//        request.setB(3);

        // List native libraries
        StringBuilder msgLog = new StringBuilder();
        for (String key : NativeUtils.getLoadedLibraries(SimpleTest.class.getClassLoader())) {
            msgLog.append(key);
            msgLog.append("\n");
        }
        logger.fine("Native libraries : \n" + msgLog.toString());

        // Sleep a little bit between each message
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Release all.
        chatter_pub.dispose();
        node.dispose();
        RCLJava.shutdown();

        Assert.assertTrue(true);
    }
}
