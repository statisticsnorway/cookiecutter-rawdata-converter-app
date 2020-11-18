package {{cookiecutter.base_package}}.{{cookiecutter.project_slug}};

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.ssb.dlp.pseudo.core.FieldPseudonymizer;
import no.ssb.rawdata.converter.core.convert.RawdataConverter;
import no.ssb.rawdata.converter.core.convert.RawdataConverterFactory;
import no.ssb.rawdata.converter.core.convert.ValueInterceptorChain;
import no.ssb.rawdata.converter.core.exception.RawdataConverterException;
import no.ssb.rawdata.converter.core.job.ConverterJobConfig;
import no.ssb.rawdata.converter.core.pseudo.FieldPseudonymizerFactory;
import no.ssb.rawdata.converter.util.Json;

import javax.inject.Singleton;

@Singleton
@RequiredArgsConstructor
@Slf4j
public class DefaultRawdataConverterFactory implements RawdataConverterFactory {
    private final FieldPseudonymizerFactory pseudonymizerFactory;
    private final {{cookiecutter.converter_name}}RawdataConverterConfig defaultRawdataConverterConfig;

    @Override
    public RawdataConverter newRawdataConverter(ConverterJobConfig jobConfig, String converterConfigJson) {
        {{cookiecutter.converter_name}}RawdataConverterConfig converterConfig = defaultRawdataConverterConfig;
        if (converterConfigJson != null) {
            try {
                converterConfig = Json.toObject({{cookiecutter.converter_name}}RawdataConverterConfig.class, converterConfigJson);
            }
            catch (Exception e) {
                throw new RawdataConverterException("Invalid {{cookiecutter.converter_name}}RawdataConverterConfig params: " + converterConfigJson, e);
            }
        }

        return newRawdataConverter(jobConfig, converterConfig);
    }

    public RawdataConverter newRawdataConverter(ConverterJobConfig jobConfig, {{cookiecutter.converter_name}}RawdataConverterConfig converterConfig) {
        ValueInterceptorChain valueInterceptorChain = new ValueInterceptorChain();

        if (jobConfig.getPseudoRules() != null && ! jobConfig.getPseudoRules().isEmpty()) {
            FieldPseudonymizer fieldPseudonymizer = pseudonymizerFactory.newFieldPseudonymizer(jobConfig);
            valueInterceptorChain.register(fieldPseudonymizer::pseudonymize);
        }

        // Make sure the converterConfig is not null
        if (converterConfig == null) {
            converterConfig = (defaultRawdataConverterConfig == null) ? new {{cookiecutter.converter_name}}RawdataConverterConfig() : defaultRawdataConverterConfig;
        }

        return new {{cookiecutter.converter_name}}RawdataConverter(converterConfig, valueInterceptorChain);
    }

}