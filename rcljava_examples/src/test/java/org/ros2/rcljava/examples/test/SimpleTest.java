package org.ros2.rcljava.examples.test;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.ros2.rcljava.NativeUtils;
import org.ros2.rcljava.qos.QoSProfile;
import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.node.Node;
import org.ros2.rcljava.node.topic.Publisher;


public class SimpleTest {
    private static final String NODE_NAME = SimpleTest.class.getName();
    private static final Logger logger = LoggerFactory.getLogger(SimpleTest.class);

    @BeforeClass
    public static void setUp() {

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
                    QoSProfile.DEFAULT);

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
        logger.debug("Native libraries : \n" + msgLog.toString());

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
