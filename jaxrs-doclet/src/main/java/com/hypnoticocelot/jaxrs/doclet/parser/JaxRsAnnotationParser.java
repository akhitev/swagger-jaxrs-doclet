package com.hypnoticocelot.jaxrs.doclet.parser;

import com.google.common.base.Function;
import com.hypnoticocelot.jaxrs.doclet.DocletOptions;
import com.hypnoticocelot.jaxrs.doclet.Recorder;
import com.hypnoticocelot.jaxrs.doclet.ServiceDoclet;
import com.hypnoticocelot.jaxrs.doclet.model.*;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.RootDoc;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.google.common.collect.Maps.uniqueIndex;

public class JaxRsAnnotationParser {

    private final DocletOptions options;
    private final RootDoc rootDoc;

    public JaxRsAnnotationParser(DocletOptions options, RootDoc rootDoc) {
        this.options = options;
        this.rootDoc = rootDoc;
    }

    public boolean run() {
        try {
            Collection<ApiDeclaration> declarations = new ArrayList<ApiDeclaration>();
            for (ClassDoc classDoc : rootDoc.classes()) {
                ApiClassParser classParser = new ApiClassParser(options, classDoc);
                Collection<Api> apis = classParser.parse();
                if (apis.isEmpty()) {
                    continue;
                }

                Map<String, Model> models = uniqueIndex(classParser.models(), new Function<Model, String>() {
                    @Override
                    public String apply(Model model) {
                        return model.getId();
                    }
                });
                // The idea (and need) for the declaration is that "/foo" and "/foo/annotated" are stored in separate
                // Api classes but are part of the same resource.
                declarations.add(new ApiDeclaration(options.getApiVersion(), options.getApiBasePath(), classParser.getRootPath(), apis, models));
            }
            writeApis(declarations);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void writeApis(Collection<ApiDeclaration> apis) throws IOException {
        List<ResourceListingAPI> resources = new LinkedList<ResourceListingAPI>();
        File outputDirectory = options.getOutputDirectory();
        File specDirectory = new File(outputDirectory,"spec-files/");
        specDirectory.mkdir();
        Recorder recorder = options.getRecorder();

        for (ApiDeclaration api : apis) {
            String resourceName = api.getResourcePath().replaceFirst("/", "").replaceAll("/", "_").replaceAll("[\\{\\}]", "");
            resources.add(new ResourceListingAPI("/" + resourceName + ".{format}", ""));
            File apiFile = new File(specDirectory, resourceName + ".json");
            recorder.record(apiFile, api);
        }

        //write out json for api
        ResourceListing listing = new ResourceListing(options.getApiVersion(), options.getDocBasePath(), resources);
        File docFile = new File(specDirectory, "resources.json");
        recorder.record(docFile, listing);

        // Copy swagger-ui into the output directory.
        final ZipInputStream swaggerZip = new ZipInputStream(ServiceDoclet.class.getResourceAsStream("/swagger-ui.zip"));
        ZipEntry entry = swaggerZip.getNextEntry();
        while (entry != null) {
            final File swaggerFile = new File(outputDirectory, entry.getName());
            if (entry.isDirectory()) {
                if (!swaggerFile.isDirectory() && !swaggerFile.mkdirs()) {
                    throw new RuntimeException("Unable to create directory: " + swaggerFile);
                }
            } else {
                recorder.record(swaggerFile, swaggerZip);
            }

            entry = swaggerZip.getNextEntry();
        }
        swaggerZip.close();
    }

}
