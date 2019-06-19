package com.pi4j.binding;

/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: LIBRARY  :: Java Library (API)
 * FILENAME      :  Binding.java
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

import com.pi4j.context.Context;
import com.pi4j.util.Descriptor;

public interface Binding {
    String name();
    String id();

    default String getName() { return name(); }
    default String getId() { return id(); }

    void initialize(Context context) throws Exception;
    void terminate(Context context) throws Exception;

    default void describe(Descriptor descriptor) {
        descriptor.add(name() + " (" + id() + ") <" + getClass().getName() + ">");
    }

    default Descriptor describe() {
        Descriptor descriptor = Descriptor.create("-----------------------------------\r\n" + "Pi4J - Binding Information\r\n" + "-----------------------------------");
        describe(descriptor);
        return descriptor;
    }
}
