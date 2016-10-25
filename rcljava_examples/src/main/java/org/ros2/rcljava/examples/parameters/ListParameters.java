/* Copyright 2016 Open Source Robotics Foundation, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ros2.rcljava.examples.parameters;

import java.util.Arrays;

import org.ros2.rcljava.node.Node;
import org.ros2.rcljava.qos.QoSProfile;
import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.node.parameter.ParameterVariant;
import org.ros2.rcljava.node.parameter.SyncParametersClient;

/**
 *
 * @author Mickael Gaillard <mick.gaillard@gmail.com>
 */
public class ListParameters {
    private static final String NODE_NAME = ListParameters.class.getSimpleName().toLowerCase();

    /**
     * @param args
     */
    public static void main(String[] args) {
        // Initialize RCL
        RCLJava.rclJavaInit();

        // Let's create a new Node
        Node node = RCLJava.createNode(NODE_NAME);

        SyncParametersClient parameters_client = new SyncParametersClient(node, QoSProfile.PARAMETER);

        // Set several different types of parameters.
        parameters_client.setParameters(
                Arrays.<ParameterVariant<?>>asList(
                    new ParameterVariant<Long>("foo", 2L),
                    new ParameterVariant<String>("bar", "hello"),
                    new ParameterVariant<Double>("baz", 1.45),
                    new ParameterVariant<Long>("foo.first", 8L),
                    new ParameterVariant<Long>("foo.second", 42L),
                    new ParameterVariant<Boolean>("foobar", true)
        ));

        // List the details of a few parameters up to a namespace depth of 10.
        rcl_interfaces.msg.ListParametersResult parameters_and_prefixes =
                parameters_client.listParameters(Arrays.asList("foo", "bar"), 10);

        for (String name : parameters_and_prefixes.getNames()) {
            System.out.println(String.format("Parameter name: %s", name));
        }

        for (String prefix : parameters_and_prefixes.getPrefixes()) {
            System.out.println(String.format("Parameter prefix: %s", prefix));
        }

        node.dispose();
        RCLJava.shutdown();
    }

}
