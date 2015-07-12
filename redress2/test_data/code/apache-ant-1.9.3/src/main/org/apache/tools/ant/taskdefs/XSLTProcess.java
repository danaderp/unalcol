/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.apache.tools.ant.taskdefs;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathVariableResolver;
import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.DynamicConfigurator;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.PropertyHelper;
import org.apache.tools.ant.types.CommandlineJava;
import org.apache.tools.ant.types.Environment;
import org.apache.tools.ant.types.Mapper;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.PropertySet;
import org.apache.tools.ant.types.Reference;
import org.apache.tools.ant.types.Resource;
import org.apache.tools.ant.types.ResourceCollection;
import org.apache.tools.ant.types.XMLCatalog;
import org.apache.tools.ant.types.resources.FileResource;
import org.apache.tools.ant.types.resources.Resources;
import org.apache.tools.ant.types.resources.Union;
import org.apache.tools.ant.types.resources.FileProvider;
import org.apache.tools.ant.util.FileNameMapper;
import org.apache.tools.ant.util.FileUtils;
import org.apache.tools.ant.util.ResourceUtils;

/**
 * Processes a set of XML documents via XSLT. This is
 * useful for building views of XML based documentation.
 *
 *
 * @since Ant 1.1
 *
 * @ant.task name="xslt" category="xml"
 */

public class XSLTProcess extends MatchingTask implements XSLTLogger {
    /** destination directory */
    private File destDir = null;

    /** where to find the source XML file, default is the project's basedir */
    private File baseDir = null;

    /** XSL stylesheet as a filename */
    private String xslFile = null;

    /** XSL stylesheet as a {@link org.apache.tools.ant.types.Resource} */
    private Resource xslResource = null;

    /** extension of the files produced by XSL processing */
    private String targetExtension = ".html";

    /** name for XSL parameter containing the filename */
    private String fileNameParameter = null;

    /** name for XSL parameter containing the file directory */
    private String fileDirParameter = null;

    /** additional parameters to be passed to the stylesheets */
    private List<Param> params = new ArrayList<Param>();

    /** Input XML document to be used */
    private File inFile = null;

    /** Output file */
    private File outFile = null;

    /** The name of the XSL processor to use */
    private String processor;

    /** Classpath to use when trying to load the XSL processor */
    private Path classpath = null;

    /** The Liaison implementation to use to communicate with the XSL
     *  processor */
    private XSLTLiaison liaison;

    /** Flag which indicates if the stylesheet has been loaded into
     *  the processor */
    private boolean stylesheetLoaded = false;

    /** force output of target files even if they already exist */
    private boolean force = false;

    /** XSL output properties to be used */
    private Vector outputProperties = new Vector();

    /** for resolving entities such as dtds */
    private XMLCatalog xmlCatalog = new XMLCatalog();

    /** Utilities used for file operations */
    private static final FileUtils FILE_UTILS = FileUtils.getFileUtils();

    /**
     * Whether to style all files in the included directories as well.
     *
     * @since Ant 1.5
     */
    private boolean performDirectoryScan = true;

    /**
     * factory element for TraX processors only
     * @since Ant 1.6
     */
    private Factory factory = null;

    /**
     * whether to reuse Transformer if transforming multiple files.
     * @since 1.5.2
     */
    private boolean reuseLoadedStylesheet = true;

    /**
     * AntClassLoader for the nested &lt;classpath&gt; - if set.
     *
     * <p>We keep this here in order to reset the context classloader
     * in execute.  We can't use liaison.getClass().getClassLoader()
     * since the actual liaison class may have been loaded by a loader
     * higher up (system classloader, for example).</p>
     *
     * @since Ant 1.6.2
     */
    private AntClassLoader loader = null;

    /**
     * Mapper to use when a set of files gets processed.
     *
     * @since Ant 1.6.2
     */
    private Mapper mapperElement = null;

    /**
     * Additional resource collections to process.
     *
     * @since Ant 1.7
     */
    private Union resources = new Union();

    /**
     * Whether to use the implicit fileset.
     *
     * @since Ant 1.7
     */
    private boolean useImplicitFileset = true;

    /**
     * The default processor is trax
     * @since Ant 1.7
     */
    public static final String PROCESSOR_TRAX = "trax";

    /**
     * whether to suppress warnings.
     *
     * @since Ant 1.8.0
     */
    private boolean suppressWarnings = false;

    /**
     * whether to fail the build if an error occurs during transformation.
     *
     * @since Ant 1.8.0
     */
    private boolean failOnTransformationError = true;

    /**
     * whether to fail the build if an error occurs.
     *
     * @since Ant 1.8.0
     */
    private boolean failOnError = true;

    /**
     * Whether the build should fail if the nested resource collection
     * is empty.
     *
     * @since Ant 1.8.0
     */
    private boolean failOnNoResources = true;
    
    /**
     * For evaluating template params
     *
     * @since Ant 1.9.3
     */
    private XPathFactory xpathFactory;
    /**
     * For evaluating template params
     *
     * @since Ant 1.9.3
     */
    private XPath xpath;

    /**
     * System properties to set during transformation.
     *
     * @since Ant 1.8.0
     */
    private CommandlineJava.SysProperties sysProperties =
        new CommandlineJava.SysProperties();

    /**
     * Trace configuration for Xalan2.
     *
     * @since Ant 1.8.0
     */
    private TraceConfiguration traceConfiguration;

    /**
     * Creates a new XSLTProcess Task.
     */
    public XSLTProcess() {
    } //-- XSLTProcess

    /**
     * Whether to style all files in the included directories as well;
     * optional, default is true.
     *
     * @param b true if files in included directories are processed.
     * @since Ant 1.5
     */
    public void setScanIncludedDirectories(boolean b) {
        performDirectoryScan = b;
    }

    /**
     * Controls whether the stylesheet is reloaded for every transform.
     *
     * <p>Setting this to true may get around a bug in certain
     * Xalan-J versions, default is false.</p>
     * @param b a <code>boolean</code> value
     * @since Ant 1.5.2
     */
    public void setReloadStylesheet(boolean b) {
        reuseLoadedStylesheet = !b;
    }

    /**
     * Defines the mapper to map source to destination files.
     * @param mapper the mapper to use
     * @exception BuildException if more than one mapper is defined
     * @since Ant 1.6.2
     */
    public void addMapper(Mapper mapper) {
        if (mapperElement != null) {
            handleError("Cannot define more than one mapper");
        } else {
            mapperElement = mapper;
        }
    }

    /**
     * Adds a collection of resources to style in addition to the
     * given file or the implicit fileset.
     *
     * @param rc the collection of resources to style
     * @since Ant 1.7
     */
    public void add(ResourceCollection rc) {
        resources.add(rc);
    }

    /**
     * Add a nested &lt;style&gt; element.
     * @param rc the configured Resources object represented as &lt;style&gt;.
     * @since Ant 1.7
     */
    public void addConfiguredStyle(Resources rc) {
        if (rc.size() != 1) {
            handleError("The style element must be specified with exactly one"
                        + " nested resource.");
        } else {
            setXslResource(rc.iterator().next());
        }
    }

    /**
     * API method to set the XSL Resource.
     * @param xslResource Resource to set as the stylesheet.
     * @since Ant 1.7
     */
    public void setXslResource(Resource xslResource) {
        this.xslResource = xslResource;
    }

    /**
     * Adds a nested filenamemapper.
     * @param fileNameMapper the mapper to add
     * @exception BuildException if more than one mapper is defined
     * @since Ant 1.7.0
     */
    public void add(FileNameMapper fileNameMapper) throws BuildException {
       Mapper mapper = new Mapper(getProject());
       mapper.add(fileNameMapper);
       addMapper(mapper);
    }

    /**
     * Executes the task.
     *
     * @exception BuildException if there is an execution problem.
     * @todo validate that if either in or out is defined, then both are
     */
    @Override
    public void execute() throws BuildException {
        if ("style".equals(getTaskType())) {
            log("Warning: the task name <style> is deprecated. Use <xslt> instead.",
                    Project.MSG_WARN);
        }
        File savedBaseDir = baseDir;

        DirectoryScanner scanner;
        String[]         list;
        String[]         dirs;

        String baseMessage =
            "specify the stylesheet either as a filename in style attribute "
            + "or as a nested resource";

        if (xslResource == null && xslFile == null) {
            handleError(baseMessage);
            return;
        }
        if (xslResource != null && xslFile != null) {
            handleError(baseMessage + " but not as both");
            return;
        }
        if (inFile != null && !inFile.exists()) {
            handleError("input file " + inFile + " does not exist");
            return;
        }
        try {
            setupLoader();

            if (sysProperties.size() > 0) {
                sysProperties.setSystem();
            }

            Resource styleResource;
            if (baseDir == null) {
                baseDir = getProject().getBaseDir();
            }
            liaison = getLiaison();

            // check if liaison wants to log errors using us as logger
            if (liaison instanceof XSLTLoggerAware) {
                ((XSLTLoggerAware) liaison).setLogger(this);
            }
            log("Using " + liaison.getClass().toString(), Project.MSG_VERBOSE);

            if (xslFile != null) {
                // If we enter here, it means that the stylesheet is supplied
                // via style attribute
                File stylesheet = getProject().resolveFile(xslFile);
                if (!stylesheet.exists()) {
                    File alternative = FILE_UTILS.resolveFile(baseDir, xslFile);
                    /*
                     * shouldn't throw out deprecation warnings before we know,
                     * the wrong version has been used.
                     */
                    if (alternative.exists()) {
                        log("DEPRECATED - the 'style' attribute should be "
                            + "relative to the project's");
                        log("             basedir, not the tasks's basedir.");
                        stylesheet = alternative;
                    }
                }
                FileResource fr = new FileResource();
                fr.setProject(getProject());
                fr.setFile(stylesheet);
                styleResource = fr;
            } else {
                styleResource = xslResource;
            }

            if (!styleResource.isExists()) {
                handleError("stylesheet " + styleResource + " doesn't exist.");
                return;
            }

            // if we have an in file and out then process them
            if (inFile != null && outFile != null) {
                process(inFile, outFile, styleResource);
                return;
            }
            /*
             * if we get here, in and out have not been specified, we are
             * in batch processing mode.
             */

            //-- make sure destination directory exists...
            checkDest();

            if (useImplicitFileset) {
                scanner = getDirectoryScanner(baseDir);
                log("Transforming into " + destDir, Project.MSG_INFO);

                // Process all the files marked for styling
                list = scanner.getIncludedFiles();
                for (int i = 0; i < list.length; ++i) {
                    process(baseDir, list[i], destDir, styleResource);
                }
                if (performDirectoryScan) {
                    // Process all the directories marked for styling
                    dirs = scanner.getIncludedDirectories();
                    for (int j = 0; j < dirs.length; ++j) {
                        list = new File(baseDir, dirs[j]).list();
                        for (int i = 0; i < list.length; ++i) {
                            process(baseDir, dirs[j] + File.separator + list[i], destDir,
                                    styleResource);
                        }
                    }
                }
            } else { // only resource collections, there better be some
                if (resources.size() == 0) {
                    if (failOnNoResources) {
                        handleError("no resources specified");
                    }
                    return;
                }
            }
            processResources(styleResource);
        } finally {
            if (loader != null) {
                loader.resetThreadContextLoader();
                loader.cleanup();
                loader = null;
            }
            if (sysProperties.size() > 0) {
                sysProperties.restoreSystem();
            }
            liaison = null;
            stylesheetLoaded = false;
            baseDir = savedBaseDir;
        }
    }

    /**
     * Set whether to check dependencies, or always generate;
     * optional, default is false.
     *
     * @param force true if always generate.
     */
    public void setForce(boolean force) {
        this.force = force;
    }

    /**
     * Set the base directory;
     * optional, default is the project's basedir.
     *
     * @param dir the base directory
     **/
    public void setBasedir(File dir) {
        baseDir = dir;
    }

    /**
     * Set the destination directory into which the XSL result
     * files should be copied to;
     * required, unless <tt>in</tt> and <tt>out</tt> are
     * specified.
     * @param dir the name of the destination directory
     **/
    public void setDestdir(File dir) {
        destDir = dir;
    }

    /**
     * Set the desired file extension to be used for the target;
     * optional, default is html.
     * @param name the extension to use
     **/
    public void setExtension(String name) {
        targetExtension = name;
    }

    /**
     * Name of the stylesheet to use - given either relative
     * to the project's basedir or as an absolute path; required.
     *
     * @param xslFile the stylesheet to use
     */
    public void setStyle(String xslFile) {
        this.xslFile = xslFile;
    }

    /**
     * Set the optional classpath to the XSL processor
     *
     * @param classpath the classpath to use when loading the XSL processor
     */
    public void setClasspath(Path classpath) {
        createClasspath().append(classpath);
    }

    /**
     * Set the optional classpath to the XSL processor
     *
     * @return a path instance to be configured by the Ant core.
     */
    public Path createClasspath() {
        if (classpath == null) {
            classpath = new Path(getProject());
        }
        return classpath.createPath();
    }

    /**
     * Set the reference to an optional classpath to the XSL processor
     *
     * @param r the id of the Ant path instance to act as the classpath
     *          for loading the XSL processor
     */
    public void setClasspathRef(Reference r) {
        createClasspath().setRefid(r);
    }

    /**
     * Set the name of the XSL processor to use; optional, default trax.
     *
     * @param processor the name of the XSL processor
     */
    public void setProcessor(String processor) {
        this.processor = processor;
    }

    /**
     * Whether to use the implicit fileset.
     *
     * <p>Set this to false if you want explicit control with nested
     * resource collections.</p>
     * @param useimplicitfileset set to true if you want to use implicit fileset
     * @since Ant 1.7
     */
    public void setUseImplicitFileset(boolean useimplicitfileset) {
        useImplicitFileset = useimplicitfileset;
    }

    /**
     * Add the catalog to our internal catalog
     *
     * @param xmlCatalog the XMLCatalog instance to use to look up DTDs
     */
    public void addConfiguredXMLCatalog(XMLCatalog xmlCatalog) {
        this.xmlCatalog.addConfiguredXMLCatalog(xmlCatalog);
    }

    /**
     * Pass the filename of the current processed file as a xsl parameter
     * to the transformation. This value sets the name of that xsl parameter.
     *
     * @param fileNameParameter name of the xsl parameter retrieving the
     *                          current file name
     */
    public void setFileNameParameter(String fileNameParameter) {
        this.fileNameParameter = fileNameParameter;
    }

    /**
     * Pass the directory name of the current processed file as a xsl parameter
     * to the transformation. This value sets the name of that xsl parameter.
     *
     * @param fileDirParameter name of the xsl parameter retrieving the
     *                         current file directory
     */
    public void setFileDirParameter(String fileDirParameter) {
        this.fileDirParameter = fileDirParameter;
    }

    /**
     * Whether to suppress warning messages of the processor.
     *
     * @since Ant 1.8.0
     */
    public void setSuppressWarnings(boolean b) {
        suppressWarnings = b;
    }

    /**
     * Whether to suppress warning messages of the processor.
     *
     * @since Ant 1.8.0
     */
    public boolean getSuppressWarnings() {
        return suppressWarnings;
    }    

    /**
     * Whether transformation errors should make the build fail.
     *
     * @since Ant 1.8.0
     */
    public void setFailOnTransformationError(boolean b) {
        failOnTransformationError = b;
    }

    /**
     * Whether any errors should make the build fail.
     *
     * @since Ant 1.8.0
     */
    public void setFailOnError(boolean b) {
        failOnError = b;
    }

    /**
     * Whether the build should fail if the nested resource collection is empty.
     *
     * @since Ant 1.8.0
     */
    public void setFailOnNoResources(boolean b) {
        failOnNoResources = b;
    }

    /**
     * A system property to set during transformation.
     *
     * @since Ant 1.8.0
     */
    public void addSysproperty(Environment.Variable sysp) {
        sysProperties.addVariable(sysp);
    }

    /**
     * A set of system properties to set during transformation.
     *
     * @since Ant 1.8.0
     */
    public void addSyspropertyset(PropertySet sysp) {
        sysProperties.addSyspropertyset(sysp);
    }

    /**
     * Enables Xalan2 traces and uses the given configuration.
     *
     * <p>Note that this element doesn't have any effect with a
     * processor other than trax or if the Transformer is not Xalan2's
     * transformer implementation.</p>
     *
     * @since Ant 1.8.0
     */
    public TraceConfiguration createTrace() {
        if (traceConfiguration != null) {
            throw new BuildException("can't have more than one trace"
                                     + " configuration");
        }
        traceConfiguration = new TraceConfiguration();
        return traceConfiguration;
    }

    /**
     * Configuration for Xalan2 traces.
     *
     * @since Ant 1.8.0
     */
    public TraceConfiguration getTraceConfiguration() {
        return traceConfiguration;
    }

    /**
     * Load processor here instead of in setProcessor - this will be
     * called from within execute, so we have access to the latest
     * classpath.
     *
     * @param proc the name of the processor to load.
     * @exception Exception if the processor cannot be loaded.
     */
    private void resolveProcessor(String proc) throws Exception {
        if (proc.equals(PROCESSOR_TRAX)) {
            liaison = new org.apache.tools.ant.taskdefs.optional.TraXLiaison();
        } else {
            //anything else is a classname
            Class clazz = loadClass(proc);
            liaison = (XSLTLiaison) clazz.newInstance();
        }
    }

    /**
     * Load named class either via the system classloader or a given
     * custom classloader.
     *
     * As a side effect, the loader is set as the thread context classloader
     * @param classname the name of the class to load.
     * @return the requested class.
     * @exception Exception if the class could not be loaded.
     */
    private Class loadClass(String classname) throws Exception {
        setupLoader();
        if (loader == null) {
            return Class.forName(classname);
        }
        return Class.forName(classname, true, loader);
    }

    /**
     * If a custom classpath has been defined but no loader created
     * yet, create the classloader and set it as the context
     * classloader.
     */
    private void setupLoader() {
        if (classpath != null && loader == null) {
            loader = getProject().createClassLoader(classpath);
            loader.setThreadContextLoader();
        }
    }

    /**
     * Specifies the output name for the styled result from the
     * <tt>in</tt> attribute; required if <tt>in</tt> is set
     *
     * @param outFile the output File instance.
     */
    public void setOut(File outFile) {
        this.outFile = outFile;
    }

    /**
     * specifies a single XML document to be styled. Should be used
     * with the <tt>out</tt> attribute; ; required if <tt>out</tt> is set
     *
     * @param inFile the input file
     */
    public void setIn(File inFile) {
        this.inFile = inFile;
    }

    /**
     * Throws a BuildException if the destination directory hasn't
     * been specified.
     * @since Ant 1.7
     */
    private void checkDest() {
        if (destDir == null) {
            handleError("destdir attributes must be set!");
        }
    }

    /**
     * Styles all existing resources.
     *
     * @param stylesheet style sheet to use
     * @since Ant 1.7
     */
    private void processResources(Resource stylesheet) {
        for (Resource r : resources) {
            if (!r.isExists()) {
                continue;
            }
            File base = baseDir;
            String name = r.getName();
            FileProvider fp = r.as(FileProvider.class);
            if (fp != null) {
                FileResource f = ResourceUtils.asFileResource(fp);
                base = f.getBaseDir();
                if (base == null) {
                    name = f.getFile().getAbsolutePath();
                }
            }
            process(base, name, destDir, stylesheet);
        }
    }

    /**
     * Processes the given input XML file and stores the result
     * in the given resultFile.
     *
     * @param baseDir the base directory for resolving files.
     * @param xmlFile the input file
     * @param destDir the destination directory
     * @param stylesheet the stylesheet to use.
     * @exception BuildException if the processing fails.
     */
    private void process(File baseDir, String xmlFile, File destDir, Resource stylesheet)
            throws BuildException {

        File   outF = null;
        File   inF = null;

        try {
            long styleSheetLastModified = stylesheet.getLastModified();
            inF = new File(baseDir, xmlFile);

            if (inF.isDirectory()) {
                log("Skipping " + inF + " it is a directory.", Project.MSG_VERBOSE);
                return;
            }
            FileNameMapper mapper = null;
            if (mapperElement != null) {
                mapper = mapperElement.getImplementation();
            } else {
                mapper = new StyleMapper();
            }

            String[] outFileName = mapper.mapFileName(xmlFile);
            if (outFileName == null || outFileName.length == 0) {
                log("Skipping " + inFile + " it cannot get mapped to output.", Project.MSG_VERBOSE);
                return;
            } else if (outFileName == null || outFileName.length > 1) {
                log("Skipping " + inFile + " its mapping is ambiguos.", Project.MSG_VERBOSE);
                return;
            }
            outF = new File(destDir, outFileName[0]);

            if (force || inF.lastModified() > outF.lastModified()
                    || styleSheetLastModified > outF.lastModified()) {
                ensureDirectoryFor(outF);
                log("Processing " + inF + " to " + outF);
                configureLiaison(stylesheet);
                setLiaisonDynamicFileParameters(liaison, inF);
                liaison.transform(inF, outF);
            }
        } catch (Exception ex) {
            // If failed to process document, must delete target document,
            // or it will not attempt to process it the second time
            log("Failed to process " + inFile, Project.MSG_INFO);
            if (outF != null) {
                outF.delete();
            }
            handleTransformationError(ex);
        }

    } //-- processXML

    /**
     * Process the input file to the output file with the given stylesheet.
     *
     * @param inFile the input file to process.
     * @param outFile the destination file.
     * @param stylesheet the stylesheet to use.
     * @exception BuildException if the processing fails.
     */
    private void process(File inFile, File outFile, Resource stylesheet) throws BuildException {
        try {
            long styleSheetLastModified = stylesheet.getLastModified();
            log("In file " + inFile + " time: " + inFile.lastModified(), Project.MSG_DEBUG);
            log("Out file " + outFile + " time: " + outFile.lastModified(), Project.MSG_DEBUG);
            log("Style file " + xslFile + " time: " + styleSheetLastModified, Project.MSG_DEBUG);
            if (force || inFile.lastModified() >= outFile.lastModified()
                    || styleSheetLastModified >= outFile.lastModified()) {
                ensureDirectoryFor(outFile);
                log("Processing " + inFile + " to " + outFile, Project.MSG_INFO);
                configureLiaison(stylesheet);
                setLiaisonDynamicFileParameters(liaison, inFile);
                liaison.transform(inFile, outFile);
            } else {
                log("Skipping input file " + inFile + " because it is older than output file "
                        + outFile + " and so is the stylesheet " + stylesheet, Project.MSG_DEBUG);
            }
        } catch (Exception ex) {
            log("Failed to process " + inFile, Project.MSG_INFO);
            if (outFile != null) {
                outFile.delete();
            }
            handleTransformationError(ex);
        }
    }

    /**
     * Ensure the directory exists for a given file
     *
     * @param targetFile the file for which the directories are required.
     * @exception BuildException if the directories cannot be created.
     */
    private void ensureDirectoryFor(File targetFile) throws BuildException {
        File directory = targetFile.getParentFile();
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                handleError("Unable to create directory: "
                            + directory.getAbsolutePath());
            }
        }
    }

    /**
     * Get the factory instance configured for this processor
     *
     * @return the factory instance in use
     */
    public Factory getFactory() {
        return factory;
    }

    /**
     * Get the XML catalog containing entity definitions
     *
     * @return the XML catalog for the task.
     */
    public XMLCatalog getXMLCatalog() {
        xmlCatalog.setProject(getProject());
        return xmlCatalog;
    }

    /**
     * Get an enumeration on the outputproperties.
     * @return the outputproperties
     */
    public Enumeration getOutputProperties() {
        return outputProperties.elements();
    }

    /**
     * Get the Liaison implementation to use in processing.
     *
     * @return an instance of the XSLTLiaison interface.
     */
    protected XSLTLiaison getLiaison() {
        // if processor wasn't specified, use TraX.
        if (liaison == null) {
            if (processor != null) {
                try {
                    resolveProcessor(processor);
                } catch (Exception e) {
                    handleError(e);
                }
            } else {
                try {
                    resolveProcessor(PROCESSOR_TRAX);
                } catch (Throwable e1) {
                    e1.printStackTrace();
                    handleError(e1);
                }
            }
        }
        return liaison;
    }

    /**
     * Create an instance of an XSL parameter for configuration by Ant.
     *
     * @return an instance of the Param class to be configured.
     */
    public Param createParam() {
        Param p = new Param();
        params.add(p);
        return p;
    }

    /**
     * The Param inner class used to store XSL parameters
     */
    public static class Param {
        /** The parameter name */
        private String name = null;

        /** The parameter's value */
        private String expression = null;
        
        /**
         * Type of the expression.
         * @see ParamType
         */
        private String type;

        private Object ifCond;
        private Object unlessCond;
        private Project project;

        /**
         * Set the current project
         *
         * @param project the current project
         */
        public void setProject(Project project) {
            this.project = project;
        }

        /**
         * Set the parameter name.
         *
         * @param name the name of the parameter.
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * The parameter value -
         * can be a primitive type value or an XPath expression.
         * @param expression the parameter's value/expression.
         * @see #setType(java.lang.String) 
         */
        public void setExpression(String expression) {
            this.expression = expression;
        }

        /**
         * @see ParamType
         * @since Ant 1.9.3
         */
        public void setType(String type) {
            this.type = type;
        }
        
        /**
         * Get the parameter name
         *
         * @return the parameter name
         * @exception BuildException if the name is not set.
         */
        public String getName() throws BuildException {
            if (name == null) {
                throw new BuildException("Name attribute is missing.");
            }
            return name;
        }

        /**
         * Get the parameter's value
         *
         * @return the parameter value
         * @exception BuildException if the value is not set.
         * @see #getType()
         */
        public String getExpression() throws BuildException {
            if (expression == null) {
                throw new BuildException("Expression attribute is missing.");
            }
            return expression;
        }

        /**
         * @see ParamType
         * @since Ant 1.9.3
         */
        public String getType() {
            return type;
        }

        /**
         * Set whether this param should be used.  It will be used if
         * the expression evaluates to true or the name of a property
         * which has been set, otherwise it won't.
         * @param ifCond evaluated expression
         * @since Ant 1.8.0
         */
        public void setIf(Object ifCond) {
            this.ifCond = ifCond;
        }

        /**
         * Set whether this param should be used.  It will be used if
         * the expression evaluates to true or the name of a property
         * which has been set, otherwise it won't.
         * @param ifProperty evaluated expression
         */
        public void setIf(String ifProperty) {
            setIf((Object) ifProperty);
        }

        /**
         * Set whether this param should NOT be used. It will not be
         * used if the expression evaluates to true or the name of a
         * property which has been set, otherwise it will be used.
         * @param unlessCond evaluated expression
         * @since Ant 1.8.0
         */
        public void setUnless(Object unlessCond) {
            this.unlessCond = unlessCond;
        }

        /**
         * Set whether this param should NOT be used. It will not be
         * used if the expression evaluates to true or the name of a
         * property which has been set, otherwise it will be used.
         * @param unlessProperty evaluated expression
         */
        public void setUnless(String unlessProperty) {
            setUnless((Object) unlessProperty);
        }

        /**
         * Ensures that the param passes the conditions placed
         * on it with <code>if</code> and <code>unless</code> properties.
         * @return true if the task passes the "if" and "unless" parameters
         */
        public boolean shouldUse() {
            PropertyHelper ph = PropertyHelper.getPropertyHelper(project);
            return ph.testIfCondition(ifCond)
                && ph.testUnlessCondition(unlessCond);
        }
    } // Param
    
    /**
     * Enum for types of the parameter expression.
     *
     * <p>The expression can be:</p>
     * <ul>
     * <li>primitive type that will be parsed from the string value e.g.
     * {@linkplain Integer#parseInt(java.lang.String)}</li>
     * <li>XPath expression that will be evaluated (outside of the transformed
     * document - on empty one) and casted to given type. Inside XPath
     * expressions the Ant variables (properties) can be used (as XPath
     * variables - e.g. $variable123). n.b. placeholders in form of
     * ${variable123} will be substituted with their values before evaluating the
     * XPath expression (so it can be used for dynamic XPath function names and
     * other hacks).</li>
     * </ul>
     * <p>The parameter will be then passed to the XSLT template.</p>
     *
     * <p>Default type (if omited) is primitive String. So if the expression is e.g
     * "true" with no type, in XSLT it will be only a text string, not true
     * boolean.</p>
     * 
     * @see Param#setType(java.lang.String)
     * @see Param#setExpression(java.lang.String)
     * @since Ant 1.9.3
     */
    public enum ParamType {

        STRING,
        BOOLEAN,
        INT,
        LONG,
        DOUBLE,
        XPATH_STRING,
        XPATH_BOOLEAN,
        XPATH_NUMBER,
        XPATH_NODE,
        XPATH_NODESET;
        
        public static final Map<ParamType, QName> XPATH_TYPES;

        static {
            Map<ParamType, QName> m = new EnumMap<ParamType, QName>(ParamType.class);
            m.put(XPATH_STRING, XPathConstants.STRING);
            m.put(XPATH_BOOLEAN, XPathConstants.BOOLEAN);
            m.put(XPATH_NUMBER, XPathConstants.NUMBER);
            m.put(XPATH_NODE, XPathConstants.NODE);
            m.put(XPATH_NODESET, XPathConstants.NODESET);
            XPATH_TYPES = Collections.unmodifiableMap(m);
        }
    }

    /**
     * Create an instance of an output property to be configured.
     * @return the newly created output property.
     * @since Ant 1.5
     */
    public OutputProperty createOutputProperty() {
        OutputProperty p = new OutputProperty();
        outputProperties.addElement(p);
        return p;
    }

    /**
     * Specify how the result tree should be output as specified
     * in the <a href="http://www.w3.org/TR/xslt#output">
     * specification</a>.
     * @since Ant 1.5
     */
    public static class OutputProperty {
        /** output property name */
        private String name;

        /** output property value */
        private String value;

        /**
         * @return the output property name.
         */
        public String getName() {
            return name;
        }

        /**
         * set the name for this property
         * @param name A non-null String that specifies an
         * output property name, which may be namespace qualified.
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the output property value.
         */
        public String getValue() {
            return value;
        }

        /**
         * set the value for this property
         * @param value The non-null string value of the output property.
         */
        public void setValue(String value) {
            this.value = value;
        }
    }

    /**
     * Initialize internal instance of XMLCatalog.
     * Initialize XPath for parameter evaluation.
     * @throws BuildException on error
     */
    @Override
    public void init() throws BuildException {
        super.init();
        xmlCatalog.setProject(getProject());
        
        xpathFactory = XPathFactory.newInstance();
        xpath = xpathFactory.newXPath();
        xpath.setXPathVariableResolver(new XPathVariableResolver() {
            public Object resolveVariable(QName variableName) {
                return getProject().getProperty(variableName.toString());
            }
        });
    }

    /**
     * Loads the stylesheet and set xsl:param parameters.
     *
     * @param stylesheet the file from which to load the stylesheet.
     * @exception BuildException if the stylesheet cannot be loaded.
     * @deprecated since Ant 1.7
     */
    protected void configureLiaison(File stylesheet) throws BuildException {
        FileResource fr = new FileResource();
        fr.setProject(getProject());
        fr.setFile(stylesheet);
        configureLiaison(fr);
    }

    /**
     * Loads the stylesheet and set xsl:param parameters.
     *
     * @param stylesheet the resource from which to load the stylesheet.
     * @exception BuildException if the stylesheet cannot be loaded.
     * @since Ant 1.7
     */
    protected void configureLiaison(Resource stylesheet) throws BuildException {
        if (stylesheetLoaded && reuseLoadedStylesheet) {
            return;
        }
        stylesheetLoaded = true;

        try {
            log("Loading stylesheet " + stylesheet, Project.MSG_INFO);
            // We call liaison.configure() and then liaison.setStylesheet()
            // so that the internal variables of liaison can be set up
            if (liaison instanceof XSLTLiaison2) {
                ((XSLTLiaison2) liaison).configure(this);
            }
            if (liaison instanceof XSLTLiaison3) {
                // If we are here we can set the stylesheet as a
                // resource
                ((XSLTLiaison3) liaison).setStylesheet(stylesheet);
            } else {
                // If we are here we cannot set the stylesheet as
                // a resource, but we can set it as a file. So,
                // we make an attempt to get it as a file
                FileProvider fp =
                    stylesheet.as(FileProvider.class);
                if (fp != null) {
                    liaison.setStylesheet(fp.getFile());
                } else {
                    handleError(liaison.getClass().toString()
                                + " accepts the stylesheet only as a file");
                    return;
                }
            }
            for (Param p : params) {
                if (p.shouldUse()) {
                    Object evaluatedParam = evaluateParam(p);
                    if (liaison instanceof XSLTLiaison4) {
                        ((XSLTLiaison4)liaison).addParam(p.getName(), evaluatedParam);
                    } else {
                        if (evaluatedParam == null || evaluatedParam instanceof String) {
                            liaison.addParam(p.getName(), (String)evaluatedParam);
                        } else {
                            log("XSLTLiaison '" + liaison.getClass().getName()
                                    + "' supports only String parameters. Converting parameter '" + p.getName()
                                    + "' to its String value '" + evaluatedParam, Project.MSG_WARN);
                            liaison.addParam(p.getName(), String.valueOf(evaluatedParam));
                        }
                    }
                }
            }
        } catch (Exception ex) {
            log("Failed to transform using stylesheet " + stylesheet, Project.MSG_INFO);
            handleTransformationError(ex);
        }
    }
    
    /**
     * Evaluates parameter expression according to its type.
     *
     * @param param parameter from Ant build file
     * @return value to be passed to XSLT as parameter
     * @throws IllegalArgumentException if param type is unsupported
     * @throws NumberFormatException if expression of numeric type is not
     * desired numeric type
     * @throws XPathExpressionException if XPath expression can not be compiled
     * @since Ant 1.9.3
     */
    private Object evaluateParam(Param param) throws XPathExpressionException {
        String typeName = param.getType();
        String expression = param.getExpression();

        ParamType type;

        if (typeName == null || "".equals(typeName)) {
            type = ParamType.STRING; // String is default
        } else {
            try {
                type = ParamType.valueOf(typeName);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid XSLT parameter type: " + typeName, e);
            }
        }

        switch (type) {
            case STRING:
                return expression;
            case BOOLEAN:
                return Boolean.parseBoolean(expression);
            case DOUBLE:
                return Double.parseDouble(expression);
            case INT:
                return Integer.parseInt(expression);
            case LONG:
                return Long.parseLong(expression);
            default: // XPath expression
                QName xpathType = ParamType.XPATH_TYPES.get(type);
                if (xpathType == null) {
                    throw new IllegalArgumentException("Invalid XSLT parameter type: " + typeName);
                } else {
                    XPathExpression xpe = xpath.compile(expression);
                    // null = evaluate XPath on empty XML document
                    return xpe.evaluate((Object) null, xpathType);
                }
        }
    }

    /**
     * Sets file parameter(s) for directory and filename if the attribute
     * 'filenameparameter' or 'filedirparameter' are set in the task.
     *
     * @param  liaison    to change parameters for
     * @param  inFile     to get the additional file information from
     * @throws Exception  if an exception occurs on filename lookup
     *
     * @since Ant 1.7
     */
    private void setLiaisonDynamicFileParameters(
        XSLTLiaison liaison, File inFile) throws Exception {
        if (fileNameParameter != null) {
            liaison.addParam(fileNameParameter, inFile.getName());
        }
        if (fileDirParameter != null) {
            String fileName = FileUtils.getRelativePath(baseDir, inFile);
            File file = new File(fileName);
            // Give always a slash as file separator, so the stylesheet could be sure about that
            // Use '.' so a dir+"/"+name would not result in an absolute path
            liaison.addParam(fileDirParameter, file.getParent() != null ? file.getParent().replace(
                    '\\', '/') : ".");
        }
    }

    /**
     * Create the factory element to configure a trax liaison.
     * @return the newly created factory element.
     * @throws BuildException if the element is created more than one time.
     */
    public Factory createFactory() throws BuildException {
        if (factory != null) {
            handleError("'factory' element must be unique");
        } else {
            factory = new Factory();
        }
        return factory;
    }

    /**
     * Throws an exception with the given message if failOnError is
     * true, otherwise logs the message using the WARN level.
     *
     * @since Ant 1.8.0
     */
    protected void handleError(String msg) {
        if (failOnError) {
            throw new BuildException(msg, getLocation());
        }
        log(msg, Project.MSG_WARN);
    }


    /**
     * Throws an exception with the given nested exception if
     * failOnError is true, otherwise logs the message using the WARN
     * level.
     *
     * @since Ant 1.8.0
     */
    protected void handleError(Throwable ex) {
        if (failOnError) {
            throw new BuildException(ex);
        } else {
            log("Caught an exception: " + ex, Project.MSG_WARN);
        }
    }

    /**
     * Throws an exception with the given nested exception if
     * failOnError and failOnTransformationError are true, otherwise
     * logs the message using the WARN level.
     *
     * @since Ant 1.8.0
     */
    protected void handleTransformationError(Exception ex) {
        if (failOnError && failOnTransformationError) {
            throw new BuildException(ex);
        } else {
            log("Caught an error during transformation: " + ex,
                Project.MSG_WARN);
        }
    }

    /**
     * The factory element to configure a transformer factory
     * @since Ant 1.6
     */
    public static class Factory {

        /** the factory class name to use for TraXLiaison */
        private String name;

        /**
         * the list of factory attributes to use for TraXLiaison
         */
        private Vector attributes = new Vector();

        /**
         * @return the name of the factory.
         */
        public String getName() {
            return name;
        }

        /**
         * Set the name of the factory
         * @param name the name of the factory.
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * Create an instance of a factory attribute.
         * @param attr the newly created factory attribute
         */
        public void addAttribute(Attribute attr) {
            attributes.addElement(attr);
        }

        /**
         * return the attribute elements.
         * @return the enumeration of attributes
         */
        public Enumeration getAttributes() {
            return attributes.elements();
        }

        /**
         * A JAXP factory attribute. This is mostly processor specific, for
         * example for Xalan 2.3+, the following attributes could be set:
         * <ul>
         *  <li>http://xml.apache.org/xalan/features/optimize (true|false) </li>
         *  <li>http://xml.apache.org/xalan/features/incremental (true|false) </li>
         * </ul>
         */
        public static class Attribute implements DynamicConfigurator {

            /** attribute name, mostly processor specific */
            private String name;

            /** attribute value, often a boolean string */
            private Object value;

            /**
             * @return the attribute name.
             */
            public String getName() {
                return name;
            }

            /**
             * @return the output property value.
             */
            public Object getValue() {
                return value;
            }

            /**
             * Not used.
             * @param name not used
             * @return null
             * @throws BuildException never
             */
            public Object createDynamicElement(String name) throws BuildException {
                return null;
            }

            /**
             * Set an attribute.
             * Only "name" and "value" are supported as names.
             * @param name the name of the attribute
             * @param value the value of the attribute
             * @throws BuildException on error
             */
            public void setDynamicAttribute(String name, String value) throws BuildException {
                // only 'name' and 'value' exist.
                if ("name".equalsIgnoreCase(name)) {
                    this.name = value;
                } else if ("value".equalsIgnoreCase(name)) {
                    // a value must be of a given type
                    // say boolean|integer|string that are mostly used.
                    if ("true".equalsIgnoreCase(value)) {
                        this.value = Boolean.TRUE;
                    } else if ("false".equalsIgnoreCase(value)) {
                        this.value = Boolean.FALSE;
                    } else {
                        try {
                            this.value = new Integer(value);
                        } catch (NumberFormatException e) {
                            this.value = value;
                        }
                    }
                } else {
                    throw new BuildException("Unsupported attribute: " + name);
                }
            }
        } // -- class Attribute
    } // -- class Factory

    /**
     * Mapper implementation of the "traditional" way &lt;xslt&gt;
     * mapped filenames.
     *
     * <p>If the file has an extension, chop it off.  Append whatever
     * the user has specified as extension or ".html".</p>
     *
     * @since Ant 1.6.2
     */
    private class StyleMapper implements FileNameMapper {
        public void setFrom(String from) {
        }
        public void setTo(String to) {
        }
        public String[] mapFileName(String xmlFile) {
            int dotPos = xmlFile.lastIndexOf('.');
            if (dotPos > 0) {
                xmlFile = xmlFile.substring(0, dotPos);
            }
            return new String[] {xmlFile + targetExtension};
        }
    }

    /**
     * Configuration for Xalan2 traces.
     *
     * @since Ant 1.8.0
     */
    public final class TraceConfiguration {
        private boolean elements, extension, generation, selection, templates;

        /**
         * Set to true if the listener is to print events that occur
         * as each node is 'executed' in the stylesheet.
         */
        public void setElements(boolean b) {
            elements = b;
        }

        /**
         * True if the listener is to print events that occur as each
         * node is 'executed' in the stylesheet.
         */
        public boolean getElements() {
            return elements;
        }

        /**
         * Set to true if the listener is to print information after
         * each extension event.
         */
        public void setExtension(boolean b) {
            extension = b;
        }

        /**
         * True if the listener is to print information after each
         * extension event.
         */
        public boolean getExtension() {
            return extension;
        }

        /**
         * Set to true if the listener is to print information after
         * each result-tree generation event.
         */
        public void setGeneration(boolean b) {
            generation = b;
        }

        /**
         * True if the listener is to print information after each
         * result-tree generation event.
         */
        public boolean getGeneration() {
            return generation;
        }

        /**
         * Set to true if the listener is to print information after
         * each selection event.
         */
        public void setSelection(boolean b) {
            selection = b;
        }

        /**
         * True if the listener is to print information after each
         * selection event.
         */
        public boolean getSelection() {
            return selection;
        }

        /**
         * Set to true if the listener is to print an event whenever a
         * template is invoked.
         */
        public void setTemplates(boolean b) {
            templates = b;
        }

        /**
         * True if the listener is to print an event whenever a
         * template is invoked.
         */
        public boolean getTemplates() {
            return templates;
        }

        /**
         * The stream to write traces to.
         */
        public java.io.OutputStream getOutputStream() {
            return new LogOutputStream(XSLTProcess.this);
        }
    }

}