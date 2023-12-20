package buysellmoto.core.ultilities;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class TimestampToDate extends StdDeserializer<LocalDateTime> {

    protected TimestampToDate() {
        this(null);
    }

    protected TimestampToDate(Class<LocalDateTime> t) {
        super(t);
    }

    @Override
    public LocalDateTime deserialize(JsonParser jsonparser, DeserializationContext context) throws IOException {
//        Long timestamp = Long.valueOf(jsonparser.getText());
//        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());

        Long timestamp = Long.valueOf(jsonparser.getText());
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.of(ZoneOffset.UTC.toString()));
    }
}