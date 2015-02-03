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
package com.github.jasonruckman.sidney.core.io.string;

import com.github.jasonruckman.sidney.core.io.BaseDecoder;
import com.github.jasonruckman.sidney.core.io.BaseEncoder;
import com.github.jasonruckman.sidney.core.io.Encoding;
import com.github.jasonruckman.sidney.core.io.int32.Int32Decoder;
import com.github.jasonruckman.sidney.core.io.int32.Int32Encoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RLE {
    private static final Encoding RUN_SIZE_ENCODING = Encoding.DELTABITPACKINGHYBRID;

    public static class RLEStringDecoder extends BaseDecoder implements StringDecoder {
        private final StringDecoder valueDecoder = Encoding.PLAIN.newStringDecoder();
        private final Int32Decoder runSizeDecoder = RUN_SIZE_ENCODING.newInt32Decoder();

        private int runSize = 0;
        private String currentRun = null;

        @Override
        public void readFromStream(InputStream inputStream) throws IOException {
            runSize = 0;
            currentRun = null;

            valueDecoder.readFromStream(inputStream);
            runSizeDecoder.readFromStream(inputStream);
        }

        @Override
        public String readString() {
            if (runSize-- == 0) {
                loadNextRun();
                runSize--;
            }
            return currentRun;
        }

        @Override
        public String[] readStrings(int num) {
            String[] strings = new String[num];
            for (int i = 0; i < num; i++) {
                strings[i] = readString();
            }
            return strings;
        }

        private void loadNextRun() {
            currentRun = valueDecoder.readString();
            runSize = runSizeDecoder.nextInt();
        }
    }

    public static class RLEStringEncoder extends BaseEncoder implements StringEncoder {
        private final Int32Encoder runSizeEncoder = RUN_SIZE_ENCODING.newInt32Encoder();
        private final StringEncoder valueEncoder = new Plain.PlainStringEncoder();
        private String currentRun = "";
        private int runSize;
        private boolean isNewRun = true;

        @Override
        public void writeString(String s) {
            if (isNewRun) {
                currentRun = s;
                isNewRun = false;
            } else if (!s.equals(currentRun)) {
                flush();
                currentRun = s;
            }
            ++runSize;
        }

        @Override
        public void writeStrings(String[] s) {
            for (String str : s) {
                writeString(str);
            }
        }

        private void flush() {
            valueEncoder.writeString(currentRun);
            runSizeEncoder.writeInt(runSize);

            currentRun = "";
            runSize = 0;
        }

        @Override
        public void reset() {
            valueEncoder.reset();
            runSizeEncoder.reset();
            currentRun = "";
            runSize = 0;
            isNewRun = true;
        }

        @Override
        public void writeToStream(OutputStream outputStream) throws IOException {
            flush();

            valueEncoder.writeToStream(outputStream);
            runSizeEncoder.writeToStream(outputStream);
        }
    }
}
