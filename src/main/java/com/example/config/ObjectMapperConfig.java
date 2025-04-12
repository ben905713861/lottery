/**
 * @author 作者: Ben
 * @version 创建时间: 2020年12月5日
 * @description
 */

package com.example.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

@Configuration
public class ObjectMapperConfig {
    /**
     * Date格式化字符串
     */
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * DateTime格式化字符串
     */
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * Time格式化字符串
     */
    private static final String TIME_FORMAT = "HH:mm:ss";

    @Bean
    public ObjectMapper objectMapper() {
        var objectMapper = new ObjectMapper();
        objectMapper.setTimeZone(TimeZone.getDefault());
        objectMapper.setDateFormat(new SimpleDateFormat(DATETIME_FORMAT));
        objectMapper.registerModule(new Jdk8Module());
        var module = new JavaTimeModule();
        module.addDeserializer(LocalDateTime.class,
            new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT)));
        module.addSerializer(LocalDateTime.class,
            new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT)));
        module.addDeserializer(LocalDate.class,
                new LocalDateDeserializer(DateTimeFormatter.ofPattern(DATE_FORMAT)));
        module.addSerializer(LocalDate.class,
                new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT)));
        module.addSerializer(Enum.class, new JsonSerializer<>() {
                @Override
                public void serialize(Enum iEnum, JsonGenerator jsonGenerator,
                                      SerializerProvider serializerProvider) throws IOException {
                    jsonGenerator.writeObject(iEnum.ordinal());
                }
            }
        );
        objectMapper.registerModule(module);
        // 实体类没有上传表单中字段时候，不报错
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    @Bean
    @Primary
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder
                .serializerByType(LocalDateTime.class,
                        new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT)))
                .serializerByType(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(TIME_FORMAT)))
                .deserializerByType(LocalDateTime.class,
                        new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .deserializerByType(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DATE_FORMAT)))
                .deserializerByType(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(TIME_FORMAT)));
    }

    @Bean
    public Converter<String, Date> dateConverter() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                SimpleDateFormat formatter = new SimpleDateFormat(DATETIME_FORMAT);
                try {
                    return formatter.parse(source);
                } catch (Exception e) {
                    throw new RuntimeException(String.format("Error parsing %s to Date", source));
                }
            }
        };
    }

    @Bean
    public Converter<String, LocalDate> localDateConverter() {
        return new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String source) {
                return LocalDate.parse(source, DateTimeFormatter.ofPattern(DATE_FORMAT));
            }
        };
    }

    @Bean
    public Converter<String, LocalDateTime> localDateTimeConverter() {
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
                return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(DATETIME_FORMAT));
            }
        };
    }
}
