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

import org.ros2.rcljava.Node;
import org.ros2.rcljava.QoSProfile;
import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.parameter.ParameterVariant;
import org.ros2.rcljava.parameter.SyncParametersClient;

/**
 *
 * @author Mickael Gaillard <mick.gaillard@gmail.com>
 */
public class ListParameters {
    private static final String NODE_NAME = ListParameters.class.getName();

    /**
     * @param args
     */
    public static void main(String[] args) {
        // Initialize RCL
        RCLJava.rclJavaInit();

        // Let's create a new Node
        Node node = RCLJava.createNode(NODE_NAME);

        // TODO(esteve): Make the parameter service automatically start with the node.
//        ParameterService parameter_service = new ParameterService(node, QoSProfile.PROFILE_PARAMETER);
        SyncParametersClient parameters_client = new SyncParametersClient(node, QoSProfile.PROFILE_PARAMETER);

        // Set several different types of parameters.
//        ArrayList<rcl_interfaces.msg.SetParametersResult> set_parameters_results =
            parameters_client.setParameters(Arrays.asList(
                new ParameterVariant<Integer>("foo", 2),
                new ParameterVariant<String>("bar", "hello"),
                new ParameterVariant<Double>("baz", 1.45),
                new ParameterVariant<Integer>("foo.first", 8),
                new ParameterVariant<Integer>("foo.second", 42),
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
