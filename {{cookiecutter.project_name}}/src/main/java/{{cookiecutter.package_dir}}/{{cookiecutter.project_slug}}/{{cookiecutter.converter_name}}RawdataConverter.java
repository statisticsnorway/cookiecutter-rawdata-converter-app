package {{cookiecutter.base_package}}.{{cookiecutter.project_slug}};

import lombok.extern.slf4j.Slf4j;
import no.ssb.rawdata.api.RawdataMessage;
import no.ssb.rawdata.converter.core.convert.ConversionResult;
import no.ssb.rawdata.converter.core.convert.ConversionResult.ConversionResultBuilder;
import no.ssb.rawdata.converter.core.convert.RawdataConverter;
import no.ssb.rawdata.converter.core.convert.ValueInterceptorChain;
import no.ssb.rawdata.converter.core.exception.RawdataConverterException;
import no.ssb.rawdata.converter.core.schema.AggregateSchemaBuilder;
import no.ssb.rawdata.converter.core.schema.DcManifestSchemaAdapter;
import no.ssb.rawdata.converter.util.RawdataMessageAdapter;
import org.apache.avro.Schema;

import java.util.Collection;

@Slf4j
public class {{cookiecutter.converter_name}}RawdataConverter implements RawdataConverter {

    private static final String RAWDATA_ITEMNAME_ENTRY = "entry";
    private static final String FIELDNAME_DC_MANIFEST = "dcManifest";
    private static final String FIELDNAME_DATA = "data";

    private final {{cookiecutter.converter_name}}RawdataConverterConfig converterConfig;
    private final ValueInterceptorChain valueInterceptorChain;

    private DcManifestSchemaAdapter dcManifestSchemaAdapter;
    private Schema targetAvroSchema;

    public {{cookiecutter.converter_name}}RawdataConverter({{cookiecutter.converter_name}}RawdataConverterConfig converterConfig, ValueInterceptorChain valueInterceptorChain) {
        this.converterConfig = converterConfig;
        this.valueInterceptorChain = valueInterceptorChain;
    }

    @Override
    public void init(Collection<RawdataMessage> sampleRawdataMessages) {
        log.info("Determine target avro schema from {}", sampleRawdataMessages);
        RawdataMessage sample = sampleRawdataMessages.stream()
          .findFirst()
          .orElseThrow(() ->
            new {{cookiecutter.converter_name}}RawdataConverterException("Unable to determine target avro schema since no sample rawdata messages were supplied. Make sure to configure `converter-settings.rawdata-samples`")
          );

        RawdataMessageAdapter msg = new RawdataMessageAdapter(sample);
        dcManifestSchemaAdapter = DcManifestSchemaAdapter.of(sample);

        String targetNamespace = "dapla.rawdata.{{cookiecutter.project_slug.lower()}}." + msg.getTopic().orElse("dataset");

        targetAvroSchema = new AggregateSchemaBuilder(targetNamespace)
          .schema(FIELDNAME_DC_MANIFEST, dcManifestSchemaAdapter.getDcManifestSchema())
          // .schema(FIELDNAME_DATA, dataSchema)
          .build();
    }

    public DcManifestSchemaAdapter dcManifestSchemaAdapter() {
        if (dcManifestSchemaAdapter == null) {
            throw new IllegalStateException("dcManifestSchemaAdapter is null. Make sure RawdataConverter#init() was invoked in advance.");
        }

        return dcManifestSchemaAdapter;
    }

    @Override
    public Schema targetAvroSchema() {
        if (targetAvroSchema == null) {
            throw new IllegalStateException("targetAvroSchema is null. Make sure RawdataConverter#init() was invoked in advance.");
        }

        return targetAvroSchema;
    }

    @Override
    public boolean isConvertible(RawdataMessage rawdataMessage) {
        return true;
    }

    @Override
    public ConversionResult convert(RawdataMessage rawdataMessage) {
        ConversionResultBuilder resultBuilder = ConversionResult.builder(targetAvroSchema, rawdataMessage);

        RawdataMessageAdapter.print(rawdataMessage); // TODO: Remove this ;-)
        addDcManifest(rawdataMessage, resultBuilder);
        // TODO: add converted data

        return resultBuilder.build();
    }

    void addDcManifest(RawdataMessage rawdataMessage, ConversionResultBuilder resultBuilder) {
        resultBuilder.withRecord(FIELDNAME_DC_MANIFEST, dcManifestSchemaAdapter().newRecord(rawdataMessage));
    }

    public static class {{cookiecutter.converter_name}}RawdataConverterException extends RawdataConverterException {
        public {{cookiecutter.converter_name}}RawdataConverterException(String msg) {
            super(msg);
        }
        public {{cookiecutter.converter_name}}RawdataConverterException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}