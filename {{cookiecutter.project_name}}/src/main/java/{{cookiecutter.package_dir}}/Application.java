package {{cookiecutter.base_package}}.{{cookiecutter.project_slug}};

import io.micronaut.runtime.Micronaut;
import lombok.extern.slf4j.Slf4j;
import no.ssb.rawdata.converter.app.RawdataConverterApplication;
import no.ssb.rawdata.converter.util.MavenArtifactUtil;

@Slf4j
public class Application extends RawdataConverterApplication {

    public static void main(String[] args) {
        log.info("{{cookiecutter.project_name}} version: {}", MavenArtifactUtil.findArtifactVersion("{{cookiecutter.base_package}}", "{{cookiecutter.project_name}}").orElse("unknown"));
        Micronaut.run(Application.class, args);
    }

}