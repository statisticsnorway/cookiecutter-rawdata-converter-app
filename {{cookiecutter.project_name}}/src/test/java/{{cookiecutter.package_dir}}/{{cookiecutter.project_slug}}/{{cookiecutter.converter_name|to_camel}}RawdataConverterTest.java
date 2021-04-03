package {{cookiecutter.base_package}}.{{cookiecutter.project_slug}};

import no.ssb.rawdata.converter.core.convert.ConversionResult;
import no.ssb.rawdata.converter.core.convert.ValueInterceptorChain;
import no.ssb.rawdata.converter.test.message.RawdataMessageFixtures;
import no.ssb.rawdata.converter.test.message.RawdataMessages;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Set;

@Disabled
public class {{cookiecutter.converter_name|to_camel}}RawdataConverterTest {

    static RawdataMessageFixtures fixtures;

    @BeforeAll
    static void loadFixtures() {
        fixtures = RawdataMessageFixtures.init("sometopic");
    }

    @Disabled
    @Test
    void shouldConvertRawdataMessages() {
        RawdataMessages messages = fixtures.rawdataMessages("sometopic"); // TODO: replace with topicname
        {{cookiecutter.converter_name |to_camel}}RawdataConverterConfig config = new {{cookiecutter.converter_name |to_camel}}RawdataConverterConfig();
        // TODO: Set app config

        {{cookiecutter.converter_name |to_camel}}RawdataConverter converter = new {{cookiecutter.converter_name |to_camel}}RawdataConverter(config, new ValueInterceptorChain());
        converter.init(messages.index().values());
        ConversionResult res = converter.convert(messages.index().get("123456")); // TODO: replace with message position
    }

}
