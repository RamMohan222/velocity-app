package com.velocity.renderer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class Main {

    public static void main(final String[] args) throws IOException {
        initialise();
    }

    public static void initialise() throws IOException {
        final Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/resources/velocity.properties"));
        Velocity.init(properties);

        final NullPointerException ne = new NullPointerException("Invalid value.");
        final VelocityContext context = new VelocityContext();
        context.put("title", NullPointerException.class);
        context.put("stackTrace", getStackTrace(ne));

        final StringWriter writer = new StringWriter();
        Velocity.mergeTemplate("exception_email.html.vm", StandardCharsets.UTF_8.name(), context,
                writer);
        final String html = writer.toString();
        System.out.println(html);
    }

    private static String getStackTrace(final Throwable throwable) {
        final StringWriter writer = new StringWriter();
        throwable.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }
}
