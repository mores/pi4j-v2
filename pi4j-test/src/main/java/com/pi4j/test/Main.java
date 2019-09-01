package com.pi4j.test;/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: TESTING  :: Unit/Integration Tests
 * FILENAME      :  Main.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2019 Pi4J
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.IOType;
import com.pi4j.io.gpio.analog.AnalogChangeListener;
import com.pi4j.io.gpio.analog.binding.AnalogBindingSync;
import com.pi4j.test.provider.TestAnalogInputProvider;

/**
 * <p>Main class.</p>
 *
 * @author Robert Savage (<a href="http://www.savagehomeautomation.com">http://www.savagehomeautomation.com</a>)
 * @version $Id: $Id
 */
public class Main {

    /**
     * <p>Constructor for Main.</p>
     */
    public Main() {
    }

    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects.
     * @throws java.lang.Exception if any.
     */
    public static void main(String[] args) throws Exception {

        // set logging
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE");

        // Initialize Pi4J with an auto context
        // An auto context includes AUTO-DETECT BINDINGS enabled
        // which will load all detected Pi4J extension libraries
        // (Platforms and Providers) in the class path
        Context pi4j = Pi4J.newContextBuilder()
                .add(TestAnalogInputProvider.newInstance("test-analog-input-provider", "TestAnalogInputProvider"))
                .autoDetect()
                .build();

        // create About class instance
        About about = new About();
        about.enumerateProviders(pi4j);
        about.enumeratePlatforms(pi4j);
        about.describeDeafultPlatform(pi4j);
        for(var ioType : IOType.values()){
            about.enumerateProviders(pi4j, ioType);
        }

//        Serial serial = Serial.instance("/dev/ttyUSB1");
//        serial.open();
//        serial.send("TEST DATA");
//        serial.close();



        var din1 = pi4j.dout().create(11);
        var ain1 = pi4j.ain().create(21, "test-analog-input-provider");
        var input = pi4j.ain().create(98);

        input.name("My Analog Input #1");

        var output1 = pi4j.aout().create(99);
        var output2 = pi4j.aout().create(100);

        input.addListener((AnalogChangeListener) event -> {
            System.out.print("ANALOG INPUT [");
            System.out.print(event.source().address());
            System.out.print("] VALUE CHANGE: ");
            System.out.println(event.value());
        });

        output1.addListener((AnalogChangeListener) event -> {
            System.out.print("ANALOG OUTPUT [");
            System.out.print(event.source().address());
            System.out.print("] VALUE CHANGE: ");
            System.out.println(event.value());
        });
        output2.addListener((AnalogChangeListener) event -> {
            System.out.print("ANALOG OUTPUT [");
            System.out.print(event.source().address());
            System.out.print("] VALUE CHANGE: ");
            System.out.println(event.value());
        });

        input.bind(new AnalogBindingSync(output1, output2));


        System.out.println(input);
        //((TestAnalogInput)input).test(21).test(22).test(23);


        //output.value(12);
        //output.setValue(78);
        //output.value(0x01);


        //AnalogOutput aout1 = AnalogOutput.in
//        DigitalOutput dout1 = DigitalOutput;


//        Descriptor descriptor = Descriptor.create("1");
//        var des1 = descriptor.add("1.1");
//        des1.add("1.1.1");
//        des1.add("1.1.2");
//        var des2 = descriptor.add("1.2");
//        des2.add("1.2.1");
//        des2.add("1.2.2");
//        descriptor.add("1.2--1");
//        descriptor.add("1.2--2");
//        descriptor.add("1.2--3");
//        var des3 = descriptor.add("1.3");
//        var des4 = des3.add("1.3.1");
//        des4.add("1.3.1.1");
//        des4.add("1.3.1.2");
//        des4.add("1.3.1.3");
//        des4.add("1.3.1.4");
//        var des5 = des3.add("1.3.2");
//        des5.add("1.3.2.1");
//        des5.add("1.3.2.2");
//        des5.add("1.3.2.3");
//        des5.add("1.3.2.4");
//        descriptor.print(System.out);

        System.out.println("\r\n\r\n-----------------------------------\r\n" + "Pi4J - Runtime Information\r\n" + "-----------------------------------");
        pi4j.describe().print(System.out);

        // shutdown Pi4J
        pi4j.shutdown();

    }
}
