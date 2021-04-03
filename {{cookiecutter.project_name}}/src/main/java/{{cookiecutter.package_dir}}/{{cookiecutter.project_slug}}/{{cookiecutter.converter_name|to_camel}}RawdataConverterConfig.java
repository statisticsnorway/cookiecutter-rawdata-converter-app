package {{cookiecutter.base_package}}.{{cookiecutter.project_slug}};

import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Data;

@ConfigurationProperties("rawdata.converter.{{cookiecutter.project_slug}}")
@Data
public class {{cookiecutter.converter_name|to_camel}}RawdataConverterConfig {

    /**
     * <p>Some config param</p>
     *
     * <p>Defaults to "default value"</p>
     *
     * TODO: Remove this
     */
    private String someParam = "default value";

}