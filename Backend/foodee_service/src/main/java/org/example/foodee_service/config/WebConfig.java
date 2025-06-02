package org.example.foodee_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String dishImagesPath = Paths.get("dishImages").toAbsolutePath().toString()
                .replace("\\", "/");
        String dishImagesLocation = "file:///" + dishImagesPath + "/";

        // Chuẩn hóa đường dẫn cho RestaurantImages
        String restaurantImagesPath = Paths.get("RestaurantImages").toAbsolutePath().toString()
                .replace("\\", "/");
        String restaurantImagesLocation = "file:///" + restaurantImagesPath + "/";

        // Chuẩn hóa đường dẫn cho userAvatar
        String userAvatarPath = Paths.get("user_avatar").toAbsolutePath().toString()
                .replace("\\", "/");
        String userAvatarLocation = "file:///" + userAvatarPath + "/";

        // Đăng ký cả 2 thư mục
        registry.addResourceHandler("/images/**")
                .addResourceLocations(dishImagesLocation, restaurantImagesLocation, userAvatarLocation)
                .setCachePeriod(0)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        converters.add(converter);
    }
}
