package de.groupon.sample.config;

import com.google.gson.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.*;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

public class GSONHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private Type type = null;


    private static final ThreadLocal<Gson> threadSafeGsonBuilder = new ThreadLocal<Gson>() {
        @Override
        protected Gson initialValue() {
            return new GsonBuilder().excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT)
                    .registerTypeAdapter(JsonNull.class, new JsonSerializer<JsonNull>() {
                        public JsonElement serialize(JsonNull src, Type typeOfSrc, JsonSerializationContext context) {
                            return new JsonPrimitive("");
                        }
                    }).create();
        }
    };

    static Gson gson() {
        return threadSafeGsonBuilder.get();
    }

    public GSONHttpMessageConverter() {
        super(new MediaType("application", "json", DEFAULT_CHARSET), new MediaType("application", "x-javascript", DEFAULT_CHARSET));
    }


    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return canRead(mediaType);
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return canWrite(mediaType);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        final Reader json = new InputStreamReader(inputMessage.getBody(), getCharset(inputMessage.getHeaders()));

        try {
            return gson().fromJson(json, clazz);
        } catch (JsonSyntaxException ex) {
            throw new HttpMessageNotReadableException("Could not read JSON: " + ex.getMessage(), ex);
        } catch (JsonIOException ex) {
            throw new HttpMessageNotReadableException("Could not read JSON: " + ex.getMessage(), ex);
        } catch (JsonParseException ex) {
            throw new HttpMessageNotReadableException("Could not read JSON: " + ex.getMessage(), ex);
        }
    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        serialize(o,httpOutputMessage);
    }

    protected void serialize(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        final Class<? extends Object> aClass = o.getClass();
        final OutputStream out = outputMessage.getBody();
        final OutputStreamWriter w = new OutputStreamWriter(out, DEFAULT_CHARSET);

        gson().toJson(o, w);
        w.flush();

    }

    private Charset getCharset(HttpHeaders headers) {
        if (headers != null && headers.getContentType() != null && headers.getContentType().getCharSet() != null) {
            return headers.getContentType().getCharSet();
        }

        return DEFAULT_CHARSET;
    }
}
