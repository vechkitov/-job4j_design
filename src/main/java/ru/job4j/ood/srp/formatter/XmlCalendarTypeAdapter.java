package ru.job4j.ood.srp.formatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Calendar;

public class XmlCalendarTypeAdapter extends XmlAdapter<String, Calendar> {
    private DateTimeParser<Calendar> parser;

    public XmlCalendarTypeAdapter(DateTimeParser<Calendar> parser) {
        this.parser = parser;
    }

    @Override
    public Calendar unmarshal(String v) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public String marshal(Calendar c) throws Exception {
        return c != null ? parser.parse(c) : "";
    }
}
