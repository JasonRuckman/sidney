/**
 * Copyright 2014 Jason Ruckman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sidney.core.column;

import org.sidney.core.encoding.Decoder;
import org.sidney.core.encoding.Encoder;
import org.sidney.core.encoding.int32.Int32Decoder;
import org.sidney.core.encoding.int32.Int32Encoder;
import org.sidney.core.serde.ReadContext;
import org.sidney.core.serde.WriteContext;

import java.util.Arrays;
import java.util.List;

public class TypeColumnIO extends ColumnIO {
    private Int32Encoder concreteTypeEncoder;
    private Int32Decoder concreteTypeDecoder;

    public TypeColumnIO(Int32Encoder concreteTypeEncoder, Int32Decoder concreteTypeDecoder) {
        this.concreteTypeEncoder = concreteTypeEncoder;
        this.concreteTypeDecoder = concreteTypeDecoder;
    }

    @Override
    public void writeConcreteType(Class<?> type, WriteContext context) {
        concreteTypeEncoder.writeInt(context.getPageHeader().valueForType(type));
    }

    @Override
    public Class readConcreteType(ReadContext context) {
        return context.getPageHeader().readConcreteType(concreteTypeDecoder.nextInt());
    }

    @Override
    public List<Encoder> getEncoders() {
        return Arrays.asList((Encoder) concreteTypeEncoder);
    }

    @Override
    public List<Decoder> getDecoders() {
        return Arrays.asList((Decoder) concreteTypeDecoder);
    }
}