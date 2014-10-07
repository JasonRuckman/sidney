package org.sidney.encoding;

import org.sidney.core.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;

public abstract class AbstractEncoderTests<E extends Encoder, D extends Decoder, T> {
    private Logger logger = LoggerFactory.getLogger(getRunningClass());

    protected abstract BiConsumer<E, T> encodingFunction();
    protected abstract IntFunction<T> dataSupplier();
    protected abstract TriConsumer<D, T, byte[]> dataConsumerAndAsserter();
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

                for (int i = 0; i < 1024000; i += 65536) {
                    try {
                        logAndRun(pair, i);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        );
    }

    private void logAndRun(EncoderDecoderPair<E, D> pair, int size) throws IOException {
        pair.getEncoder().reset();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        T t = dataSupplier().apply(size);
        encodingFunction().accept(pair.getEncoder(), t);
        pair.getEncoder().writeToStream(baos);
        pair.getDecoder().readFromStream(Bytes.wrap(baos.toByteArray()));
        dataConsumerAndAsserter().consume(pair.getDecoder(), t, baos.toByteArray());
    }
}
