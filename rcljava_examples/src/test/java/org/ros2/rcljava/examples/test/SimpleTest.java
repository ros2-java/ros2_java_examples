package org.ros2.rcljava.examples.test;

import java.util.logging.Logger;

import org.junit.Test;
import org.ros2.rcljava.NativeUtils;
import org.ros2.rcljava.Node;
import org.ros2.rcljava.Publisher;
import org.ros2.rcljava.QoSProfile;
import org.ros2.rcljava.RCLJava;

import junit.framework.TestCase;

public class SimpleTest extends TestCase {
    private static final String NODE_NAME = SimpleTest.class.getName();
    private static Logger logger = Logger.getLogger(RCLJava.LOG_NAME);

    /**
     * Test.
     */
    @Test
    public void testTrue() {

        // Initialize RCL
        RCLJava.rclJavaInit();

        // Let's create a Node
        Node node = RCLJava.createNode(NODE_NAME);

        std_msgs.msg.String msg = new std_msgs.msg.String();
        // Publishers are type safe, make sure to pass the message type
        Publisher<std_msgs.msg.String> chatter_pub =
                node.<std_msgs.msg.String>createPublisher(
                    std_msgs.msg.String.class,
                    "chatter",
                    QoSProfile.PROFILE_DEFAULT);

        msg.setData("Hello World");

        System.out.println("Publishing: \"" + msg.getData() + "\"");
        chatter_pub.publish(msg);

        StringBuilder msgLog = new StringBuilder();
        for (String key : NativeUtils.getLoadedLibraries(SimpleTest.class.getClassLoader())) {
            msgLog.append(key);
            msgLog.append("\n");
        }
        logger.fine("Native libraries : \n" + msgLog.toString());

//        // Sleep a little bit between each message
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        chatter_pub.dispose();
        node.dispose();
        RCLJava.shutdown();

        TestCase.assertTrue(true);
    }
}
