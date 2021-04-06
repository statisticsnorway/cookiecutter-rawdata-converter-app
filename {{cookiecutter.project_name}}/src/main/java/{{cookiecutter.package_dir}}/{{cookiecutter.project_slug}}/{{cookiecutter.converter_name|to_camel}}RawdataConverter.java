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
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;

import java.util.Collection;

@Slf4j
public class {{cookiecutter.converter_name|to_camel}}RawdataConverter implements RawdataConverter {

    private static final String RAWDATA_ITEMNAME_ENTRY = "entry";
    private static final String FIELDNAME_MANIFEST = "manifest";
    private static final String FIELDNAME_COLLECTOR = "collector";
    private static final String FIELDNAME_DATA = "data";

    private final {{cookiecutter.converter_name|to_camel}}RawdataConverterConfig converterConfig;
    private final ValueInterceptorChain valueInterceptorChain;

    private DcManifestSchemaAdapter dcManifestSchemaAdapter;
    private Schema manifestSchema;
    private Schema targetAvroSchema;

    public {{cookiecutter.converter_name|to_camel}}RawdataConverter({{cookiecutter.converter_name |to_camel}}RawdataConverterConfig converterConfig, ValueInterceptorChain valueInterceptorChain) {
        this.converterConfig = converterConfig;
        this.valueInterceptorChain = valueInterceptorChain;
    }

    @Override
    public void init(Collection<RawdataMessage> sampleRawdataMessages) {
        log.info("Determine target avro schema from {}", sampleRawdataMessages);
        RawdataMessage sample = sampleRawdataMessages.stream()
          .findFirst()
          .orElseThrow(() ->
            new {{cookiecutter.converter_name|to_camel}}RawdataConverterException("Unable to determine target avro schema since no sample rawdata messages were supplied. Make sure to configure `converter-settings.rawdata-samples`")
          );

        RawdataMessageAdapter msg = new RawdataMessageAdapter(sample);
        dcManifestSchemaAdapter = DcManifestSchemaAdapter.of(sample);

        manifestSchema = new AggregateSchemaBuilder("dapla.rawdata.manifest")
          .schema(FIELDNAME_COLLECTOR, dcManifestSchemaAdapter.getDcManifestSchema())
          .build();

        String targetNamespace = "dapla.rawdata.{{cookiecutter.project_slug.lower()}}." + msg.getTopic().orElse("dataset");
        targetAvroSchema = new AggregateSchemaBuilder(targetNamespace)
          .schema(FIELDNAME_MANIFEST, manifestSchema)
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

        addManifest(rawdataMessage, resultBuilder);
        // TODO: add converted data

        return resultBuilder.build();
    }

    void addManifest(RawdataMessage rawdataMessage, ConversionResultBuilder resultBuilder) {
        GenericRecord manifest = new GenericRecordBuilder(manifestSchema)
          .set(FIELDNAME_COLLECTOR, dcManifestSchemaAdapter.newRecord(rawdataMessage, valueInterceptorChain))
          .build();

        resultBuilder.withRecord(FIELDNAME_MANIFEST, manifest);
    }

    public static class {{cookiecutter.converter_name|to_camel}}RawdataConverterException extends RawdataConverterException {
        public {{cookiecutter.converter_name|to_camel}}RawdataConverterException(String msg) {
            super(msg);
        }
        public {{cookiecutter.converter_name|to_camel}}RawdataConverterException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}