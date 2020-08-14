package view.custom_widget;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

    private String datePattern = "yyyy-MM-dd";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateLabelFormatter that = (DateLabelFormatter) o;
        return Objects.equals(datePattern, that.datePattern) &&
                Objects.equals(dateFormatter, that.dateFormatter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(datePattern, dateFormatter);
    }

    @Override
    public String toString() {
        return "DateLabelFormatter{" +
                "datePattern='" + datePattern + '\'' +
                ", dateFormatter=" + dateFormatter +
                '}';
    }
}
