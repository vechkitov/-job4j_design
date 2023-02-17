package ru.job4j.ood.srp.formatter;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Calendar;

public class GsonCalendarTypeAdapter implements JsonSerializer<Calendar> {
    private final DateTimeParser<Calendar> parser;

    public GsonCalendarTypeAdapter(DateTimeParser<Calendar> parser) {
        this.parser = parser;
    }

    @Override
    public JsonElement serialize(Calendar c, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(c != null ? parser.parse(c) : "");
    }
}
