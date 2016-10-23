package org.ros2.rcljava.examples.parameters;

import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.node.Node;

public class RosParam {

    private static String NAME = RosParam.class.getSimpleName().toLowerCase();

    private static final String HELP =
            "USAGE:\n"+
            "  rosparam_java get <node/variable>\n"+
            "  rosparam_java set <node/variable> <value>\n" +
            "  rosparam_java list <node>";

    enum Usage {
        PARAM_NONE,
        PARAM_GET,
        PARAM_SET,
        PARAM_LIST,
    }

    public static void main(String[] args) throws InterruptedException {
        // Initialize RCL
        RCLJava.rclJavaInit();

        Usage op = Usage.PARAM_NONE;
        Node node = RCLJava.createNode(NAME);

        node.dispose();
        RCLJava.shutdown();
    }
}
