42
        AbstractBeanDefinitionReader.java 173
public int loadBeanDefinitions(Resource... resources) throws BeanDefinitionStoreException {
           Assert.notNull(resources, "Resource array must not be null");
           int counter = 0;
            for (Resource resource : resources) {
              counter += loadBeanDefinitions(resource);//go43
           }
           return counter;
        }
        43
        XmlBeanDefinitionReader.java
public int loadBeanDefinitions(Resource resource) throws BeanDefinitionStoreException {
           return loadBeanDefinitions(new EncodedResource(resource));//引入Encoderesource


        }
        44
        XmlBeanDefinitionReader.java
public int loadBeanDefinitions(EncodedResource encodedResource) throws BeanDefinitionStoreException {
           Assert.notNull(encodedResource, "EncodedResource must not be null");
           if (logger.isInfoEnabled()) {
              logger.info("Loading XML bean definitions from " + encodedResource.getResource());
           }

           Set<EncodedResource> currentResources = this.resourcesCurrentlyBeingLoaded.get();//null
           if (currentResources == null) {//false 不执行
              currentResources = new HashSet<EncodedResource>(4);//创建有4个位置的hashset
              this.resourcesCurrentlyBeingLoaded.set(currentResources);// resourcesCurrentlyBeingLoaded是一个线程变量
//private final ThreadLocal<Set<EncodedResource>> resourcesCurrentlyBeingLoaded =
              //new NamedThreadLocal<Set<EncodedResource>>("XML bean definition resources currently being loaded");
           }
           if (!currentResources.add(encodedResource)) {// 把file [C:\Users\dozen.zhang\Documents\calendar\target\classes\config\xml \applicationContext.xml]加入到集合中
//file [C:\Users\dozen.zhang\Documents\calendar\target\classes\config\xml\spring-context-config.xml]
              throw new BeanDefinitionStoreException(
                    "Detected cyclic loading of " + encodedResource + " - check your import definitions!");
           }
           try {
              InputStream inputStream = encodedResource.getResource().getInputStream();//return new FileInputStream(this.file);
              try {
                 InputSource inputSource = new InputSource(inputStream);//包裹一层
                 if (encodedResource.getEncoding() != null) {
                    inputSource.setEncoding(encodedResource.getEncoding());
                 }
                 return doLoadBeanDefinitions(inputSource, encodedResource.getResource());//go45 创建DocumentBuilderFactory 利用factory生成docuemntbuilder documentBuilder设置许多解析xsd 就是applicationcontext.xml上配置的xsd 设置错误处理 simplesaxerrorhandler
              }
              finally {
                 inputStream.close();
              }
           }
           catch (IOException ex) {
              throw new BeanDefinitionStoreException(
                    "IOException parsing XML document from " + encodedResource.getResource(), ex);
           }
           finally {
              currentResources.remove(encodedResource);
              if (currentResources.isEmpty()) {
                 this.resourcesCurrentlyBeingLoaded.remove();
              }
           }
        }

        45
        XmlBeanDefinitionReader.java
        rotected int doLoadBeanDefinitions(InputSource inputSource, Resource resource)
              throws BeanDefinitionStoreException {
           try {
              int validationMode = getValidationModeForResource(resource);//检查有没有doctype字段 3
              Document doc = this.documentLoader.loadDocument(
                    inputSource, getEntityResolver(), this.errorHandler, validationMode, isNamespaceAware());//go46 ResourceEntityResolver  namespaceAware =false
              return registerBeanDefinitions(doc, resource);//go48
           }
           catch (BeanDefinitionStoreException ex) {
              throw ex;
           }
           catch (SAXParseException ex) {
              throw new XmlBeanDefinitionStoreException(resource.getDescription(),
                    "Line " + ex.getLineNumber() + " in XML document from " + resource + " is invalid", ex);
           }
           catch (SAXException ex) {
              throw new XmlBeanDefinitionStoreException(resource.getDescription(),
                    "XML document from " + resource + " is invalid", ex);
           }
           catch (ParserConfigurationException ex) {
              throw new BeanDefinitionStoreException(resource.getDescription(),
                    "Parser configuration exception parsing XML from " + resource, ex);
           }
           catch (IOException ex) {
              throw new BeanDefinitionStoreException(resource.getDescription(),
                    "IOException parsing XML document from " + resource, ex);
           }
           catch (Throwable ex) {
              throw new BeanDefinitionStoreException(resource.getDescription(),
                    "Unexpected exception parsing XML document from " + resource, ex);
           }
        }
        46 DefaultDocumentLoader.java

public Document loadDocument(InputSource inputSource, EntityResolver entityResolver,
              ErrorHandler errorHandler, int validationMode, boolean namespaceAware) throws Exception {
           DocumentBuilderFactory factory = createDocumentBuilderFactory(validationMode, namespaceAware);//DocumentBuilderFactory
           if (logger.isDebugEnabled()) {
              logger.debug("Using JAXP provider [" + factory.getClass().getName() + "]");
           }
           DocumentBuilder builder = createDocumentBuilder(factory, entityResolver, errorHandler);
           return builder.parse(inputSource);//返回45
        }
        47 同上
protected DocumentBuilder createDocumentBuilder(
              DocumentBuilderFactory factory, EntityResolver entityResolver, ErrorHandler errorHandler)
              throws ParserConfigurationException {

           DocumentBuilder docBuilder = factory.newDocumentBuilder();
           if (entityResolver != null) {//执行
              docBuilder.setEntityResolver(entityResolver);//ResourceEntityResolver
           }
           if (errorHandler != null) {
              docBuilder.setErrorHandler(errorHandler);//SimSaxErrorHandler
           }
           return docBuilder;//返回46
        }
        48
        xmlbeandefinitionReader.java
public int registerBeanDefinitions(Document doc, Resource resource) throws BeanDefinitionStoreException {
           BeanDefinitionDocumentReader documentReader = createBeanDefinitionDocumentReader();//DefaultbeanDefinitionDocumentReader return BeanDefinitionDocumentReader.class.cast(BeanUtils.instantiateClass(this.documentReaderClass));
           documentReader.setEnvironment(this.getEnvironment());
           int countBefore = getRegistry().getBeanDefinitionCount();//DefaultListablebeanFactory//0
           documentReader.registerBeanDefinitions(doc, createReaderContext(resource));//go49 go50
           return getRegistry().getBeanDefinitionCount() - countBefore;
        }
        49
        tongshang
protected XmlReaderContext createReaderContext(Resource resource) {
           if (this.namespaceHandlerResolver == null) {
              this.namespaceHandlerResolver = createDefaultNamespaceHandlerResolver();//new DefaultNamespaceHandlerResolver
           }
           return new XmlReaderContext(resource, this.problemReporter, this.eventListener,
                 this.sourceExtractor, this, this.namespaceHandlerResolver);
        }返回48 new XmlReaderContext 
        50
        DefaultBeanDefinitionDocumentreaer.java
public void registerBeanDefinitions(Document doc, XmlReaderContext readerContext) {
           this.readerContext = readerContext;
           logger.debug("Loading bean definitions");
           Element root = doc.getDocumentElement();//获取整个文档的根节点
           doRegisterBeanDefinitions(root);//go51 back49 进行注册
        }
        //51 as below
protected void doRegisterBeanDefinitions(Element root) {
           String profileSpec = root.getAttribute(PROFILE_ATTRIBUTE);//profile
           if (StringUtils.hasText(profileSpec)) {//false
              Assert.state(this.environment != null, "Environment must be set for evaluating profiles");
              String[] specifiedProfiles = StringUtils.tokenizeToStringArray(
                    profileSpec, BeanDefinitionParserDelegate.MULTI_VALUE_ATTRIBUTE_DELIMITERS);
              if (!this.environment.acceptsProfiles(specifiedProfiles)) {
                 return;
              }
           }
           BeanDefinitionParserDelegate parent = this.delegate;//null
           this.delegate = createHelper(this.readerContext, root, parent);//go52

           preProcessXml(root);//什么都没做
           parseBeanDefinitions(root, this.delegate);// resolver spring-context-config.xml recurse root 第一次是applicationcontext.xml的beans节点
           postProcessXml(root);8

           this.delegate = parent;
        }
        52
        DefaultBeanDefinitionDocumentReader.java
protected BeanDefinitionParserDelegate createHelper(
              XmlReaderContext readerContext, Element root, BeanDefinitionParserDelegate parentDelegate) {

           BeanDefinitionParserDelegate delegate = new BeanDefinitionParserDelegate(readerContext, environment);
           delegate.initDefaults(root, parentDelegate);
           return delegate;//返回51
        }
        53
        BeanDefinitionParserDelegate.java
public void initDefaults(Element root, BeanDefinitionParserDelegate parent) {
           populateDefaults(this.defaults, (parent != null ? parent.defaults : null), root);//处理属性转换成this.defaults DocumentDefaultDefinition
           this.readerContext.fireDefaultsRegistered(this.defaults);//go55
        }
        54
protected void populateDefaults(DocumentDefaultsDefinition defaults, DocumentDefaultsDefinition parentDefaults, Element root) {
           String lazyInit = root.getAttribute(DEFAULT_LAZY_INIT_ATTRIBUTE);
           if (DEFAULT_VALUE.equals(lazyInit)) {//default
              lazyInit = parentDefaults != null ? parentDefaults.getLazyInit() : FALSE_VALUE;
           }
           defaults.setLazyInit(lazyInit);
           String merge = root.getAttribute(DEFAULT_MERGE_ATTRIBUTE);
           if (DEFAULT_VALUE.equals(merge)) {//default 这些属性都在http://www.springframework.org/schema/beans上面
              merge = parentDefaults != null ? parentDefaults.getMerge() : FALSE_VALUE;
           }
           defaults.setMerge(merge);

           String autowire = root.getAttribute(DEFAULT_AUTOWIRE_ATTRIBUTE);
           if (DEFAULT_VALUE.equals(autowire)) {
              autowire = parentDefaults != null ? parentDefaults.getAutowire() : AUTOWIRE_NO_VALUE;
           }
           defaults.setAutowire(autowire);

           // don't fall back to parentDefaults for dependency-check as it's no
           // longer supported in <beans> as of 3.0. Therefore, no nested <beans>
           // would ever need to fall back to it.
           defaults.setDependencyCheck(root.getAttribute(DEFAULT_DEPENDENCY_CHECK_ATTRIBUTE));

           if (root.hasAttribute(DEFAULT_AUTOWIRE_CANDIDATES_ATTRIBUTE)) {
              defaults.setAutowireCandidates(root.getAttribute(DEFAULT_AUTOWIRE_CANDIDATES_ATTRIBUTE));
           }
           else if (parentDefaults != null) {
              defaults.setAutowireCandidates(parentDefaults.getAutowireCandidates());
           }

           if (root.hasAttribute(DEFAULT_INIT_METHOD_ATTRIBUTE)) {
              defaults.setInitMethod(root.getAttribute(DEFAULT_INIT_METHOD_ATTRIBUTE));
           }
           else if (parentDefaults != null) {
              defaults.setInitMethod(parentDefaults.getInitMethod());
           }

           if (root.hasAttribute(DEFAULT_DESTROY_METHOD_ATTRIBUTE)) {
              defaults.setDestroyMethod(root.getAttribute(DEFAULT_DESTROY_METHOD_ATTRIBUTE));
           }
           else if (parentDefaults != null) {
              defaults.setDestroyMethod(parentDefaults.getDestroyMethod());
           }
           defaults.setSource(this.readerContext.extractSource(root));
        }//返回54
        55
public void fireDefaultsRegistered(DefaultsDefinition defaultsDefinition) {
           this.eventListener.defaultsRegistered(defaultsDefinition);//DocumentDefaultDefinition //没有做任何事情
        }//返回53
        56
        DefaultBeanDefinitionDocumentreader.java
protected void parseBeanDefinitions(Element root, BeanDefinitionParserDelegate delegate) {
           if (delegate.isDefaultNamespace(root)) {//判断namespaceurl 是不是 http://www.springframework.org/schema/beans
              NodeList nl = root.getChildNodes();
              for (int i = 0; i < nl.getLength(); i++) {
                 Node node = nl.item(i);
                 if (node instanceof Element) {
                    Element ele = (Element) node;//第一个解析到的是import  <import resource="spring-context-config.xml" />
                    if (delegate.isDefaultNamespace(ele)) {
                       parseDefaultElement(ele, delegate);
                    }
                    else {
                       delegate.parseCustomElement(ele);
                    }
                 }
              }
           }
           else {
              delegate.parseCustomElement(root);
           }
        }

        57
private void parseDefaultElement(Element ele, BeanDefinitionParserDelegate delegate) {
           if (delegate.nodeNameEquals(ele, IMPORT_ELEMENT)) {//http://www.springframework.org/schema/beans
              importBeanDefinitionResource(ele);//go58     
           }
           else if (delegate.nodeNameEquals(ele, ALIAS_ELEMENT)) {
              processAliasRegistration(ele);
           }
           else if (delegate.nodeNameEquals(ele, BEAN_ELEMENT)) {
              processBeanDefinition(ele, delegate);
           }
           else if (delegate.nodeNameEquals(ele, NESTED_BEANS_ELEMENT)) {
              // recurse
              doRegisterBeanDefinitions(ele);
           }
        }


        58

protected void importBeanDefinitionResource(Element ele) {
           String location = ele.getAttribute(RESOURCE_ATTRIBUTE);//resource app-context-confi.xml
           if (!StringUtils.hasText(location)) {//判断是否有文本
              getReaderContext().error("Resource location must not be empty", ele);
              return;
           }
           // Resolve system properties: e.g. "${user.dir}"
           location = environment.resolveRequiredPlaceholders(location);//替换${}

           Set<Resource> actualResources = new LinkedHashSet<Resource>(4);

           // Discover whether the location is an absolute or relative URI
           boolean absoluteLocation = false;
           try {
              absoluteLocation = ResourcePatternUtils.isUrl(location) || ResourceUtils.toURI(location).isAbsolute();//判断app-context-config.xml是不是url
           }
           catch (URISyntaxException ex) {
              // cannot convert to an URI, considering the location relative
              // unless it is the well-known Spring prefix "classpath*:"
           }

           // Absolute or relative?
           if (absoluteLocation) {
              try {
                 int importCount = getReaderContext().getReader().loadBeanDefinitions(location, actualResources);
                 if (logger.isDebugEnabled()) {
                    logger.debug("Imported " + importCount + " bean definitions from URL location [" + location + "]");
                 }
              }
              catch (BeanDefinitionStoreException ex) {
                 getReaderContext().error(
                       "Failed to import bean definitions from URL location [" + location + "]", ele, ex);
              }
           }
           else {
              // No URL -> considering resource location as relative to the current file.
              try {
                 int importCount;
                 Resource relativeResource = getReaderContext().getResource().createRelative(location);//new FileSystemResource //将app-context-config.xml change into file [C:\Users\dozen.zhang\Documents\calendar\target\classes\config\xml\spring-context-config.xml]
                 if (relativeResource.exists()) {
                    importCount = getReaderContext().getReader().loadBeanDefinitions(relativeResource);//转到43 第二次调用
                    actualResources.add(relativeResource);
                 }
                 else {
                    String baseLocation = getReaderContext().getResource().getURL().toString();
                    importCount = getReaderContext().getReader().loadBeanDefinitions(
                          StringUtils.applyRelativePath(baseLocation, location), actualResources);
                 }
                 if (logger.isDebugEnabled()) {
                    logger.debug("Imported " + importCount + " bean definitions from relative location [" + location + "]");
                 }
              }
              catch (IOException ex) {
                 getReaderContext().error("Failed to resolve current resource location", ele, ex);
              }
              catch (BeanDefinitionStoreException ex) {
                 getReaderContext().error("Failed to import bean definitions from relative location [" + location + "]",
                       ele, ex);
              }
           }
           Resource[] actResArray = actualResources.toArray(new Resource[actualResources.size()]);
           getReaderContext().fireImportProcessed(location, actResArray, extractSource(ele));
        }
