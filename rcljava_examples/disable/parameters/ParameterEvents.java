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
import org.ros2.rcljava.Subscription;
import org.ros2.rcljava.parameter.ParameterConsumer;
import org.ros2.rcljava.parameter.ParameterVariant;
import org.ros2.rcljava.parameter.SyncParametersClient;

import rcl_interfaces.msg.ParameterEvent;

/**
 *
 * @author Mickael Gaillard <mick.gaillard@gmail.com>
 */
public class ParameterEvents {
    private static final String NODE_NAME = ParameterEvents.class.getName();

    private static void onParameterEvent(final rcl_interfaces.msg.ParameterEvent event)
    {
        // TODO(wjwwood): The message should have an operator<<, which would replace all of this.
        System.out.println("Parameter event: \nnew parameters: \n");
        for (rcl_interfaces.msg.Parameter new_parameter : event.getNew_parameters()) {
            System.out.println(String.format("  %s", new_parameter.getName()));
        }
        System.out.println(" changed parameters:");
        for (rcl_interfaces.msg.Parameter changed_parameter : event.getChanged_parameters()) {
            System.out.println(String.format("  %s", changed_parameter.getName()));
        }
        System.out.println(" deleted parameters:");
        for (rcl_interfaces.msg.Parameter deleted_parameter : event.getDeleted_parameters()) {
            System.out.println(String.format("  %S", deleted_parameter.getName()));
        }
    }

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

        // Setup callback for changes to parameters.
        Subscription<?> sub = parameters_client.onParameterEvent(new ParameterConsumer() {

            @Override
            public void onEvent(ParameterEvent event) {
                ParameterEvents.onParameterEvent(event);
            }
        });

        // Set several different types of parameters.
//        ArrayList<rcl_interfaces.msg.SetParametersResult> set_parameters_results =
            parameters_client.setParameters(Arrays.asList(
                new ParameterVariant<Integer>("foo", 2),
                new ParameterVariant<String>("bar", "hello"),
                new ParameterVariant<Double>("baz", 1.45),
                new ParameterVariant<Boolean>("foobar", true)
        ));

        // Change the value of some of them.
//        set_parameters_results =
            parameters_client.setParameters(Arrays.asList(
                new ParameterVariant<Integer>("foo", 3),
                new ParameterVariant<String>("bar", "world")
        ));

        // TODO(wjwwood): Create and use delete_parameter

//        RCLJava.sleepFor(100);
//        RCLJava.spinSome(node);

        sub.dispose();
        node.dispose();
        RCLJava.shutdown();
    }

}
