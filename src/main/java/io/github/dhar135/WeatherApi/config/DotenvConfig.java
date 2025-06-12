package io.github.dhar135.WeatherApi.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class DotenvConfig extends PropertyPlaceholderConfigurer {

    public DotenvConfig() {
        setIgnoreResourceNotFound(true);
        setSystemPropertiesMode(SYSTEM_PROPERTIES_MODE_OVERRIDE);
        Resource[] resources = new Resource[] { new ClassPathResource(".env") };
        setLocations(resources);
    }

    @Override
    protected void processProperties(@NotNull ConfigurableListableBeanFactory beanFactoryToProcess,
                                     @NotNull java.util.Properties props) throws BeansException {
        Dotenv dotenv = Dotenv.load();

        for (DotenvEntry entry : dotenv.entries()) {
            props.setProperty(entry.getKey(), entry.getValue());
        }

        super.processProperties(beanFactoryToProcess, props);
    }
}