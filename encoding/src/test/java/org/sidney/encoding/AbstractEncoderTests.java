package org.sidney.encoding;

import org.sidney.core.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xerial.snappy.SnappyInputStream;
import org.xerial.snappy.SnappyOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;

public abstract class AbstractEncoderTests<E extends Encoder, D extends Decoder, T> {
    private Logger logger = LoggerFactory.getLogger(getRunningClass());

    protected abstract BiConsumer<E, T> encodingFunction();

    protected abstract IntFunction<T> dataSupplier();

    protected abstract BiConsumer<D, T> dataConsumerAndAsserter();

    protected abstract List<EncoderDecoderPair<E, D>> pairs();

    protected abstract Class getRunningClass();

    protected void runAll() {
        List<EncoderDecoderPair<E, D>> pairs = pairs();
        pairs.forEach(
            (pair) -> {
                logger.info(
                    String.format(
                        "Testing %s with %s.",
                        pair.getEncoder().getClass(),
                        pair.getDecoder().getClass()
                    )
                );
                try {
                    for (int i = 0; i < 8; i++) {
                        logAndRun(pair, i);
                        logAndRunWithCompression(pair, i);
                    }

                    for (int i = 8; i < 1024000; i += 65536) {
                        logAndRun(pair, i);
                        logAndRunWithCompression(pair, i);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        );
    }

    private void logAndRunWithCompression(EncoderDecoderPair<E, D> pair, int size) throws IOException {
        pair.getEncoder().reset();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStream gos = new SnappyOutputStream(baos);
        T t = dataSupplier().apply(size);
        encodingFunction().accept(pair.getEncoder(), t);
        pair.getEncoder().writeToStream(gos);

        gos.close();
        baos.close();

        byte[] bytes = baos.toByteArray();
        logger.info(String.format("Size in bytes: %s", bytes.length));
        pair.getDecoder().readFromStream(new SnappyInputStream(Bytes.wrap(bytes)));
        dataConsumerAndAsserter().accept(pair.getDecoder(), t);
    }

    private void logAndRun(EncoderDecoderPair<E, D> pair, int size) throws IOException {
        pair.getEncoder().reset();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        T t = dataSupplier().apply(size);
        encodingFunction().accept(pair.getEncoder(), t);
        pair.getEncoder().writeToStream(baos);

        baos.close();

        byte[] bytes = baos.toByteArray();
        logger.info(String.format("Size in bytes: %s", bytes.length));
        pair.getDecoder().readFromStream(Bytes.wrap(bytes));
        dataConsumerAndAsserter().accept(pair.getDecoder(), t);
    }
}
