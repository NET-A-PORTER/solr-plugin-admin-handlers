package com.netaporter;

import org.apache.solr.core.CoreContainer;
import org.apache.solr.handler.admin.CoreAdminHandler;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.SolrQueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class MultiAdminHandler extends CoreAdminHandler {

    private static Logger log = LoggerFactory.getLogger(MultiAdminHandler.class);

    protected Map<String, CoreAdminHandler> customRequestHandlers;

    public MultiAdminHandler() throws Exception {
        super();
        customRequestHandlers = getCustomRequestHandlers();
    }

    public MultiAdminHandler(CoreContainer cc) throws Exception {
        super(cc);
        customRequestHandlers = getCustomRequestHandlers();
    }

    @Override
    public void handleRequestBody(SolrQueryRequest req, SolrQueryResponse res) throws Exception {
        String action = req.getParams().get("action");
        CoreAdminHandler customRequestHandler = customRequestHandlers.get(action);

        if(customRequestHandler != null) {
            customRequestHandler.handleRequestBody(req, res);
        } else {
            super.handleRequestBody(req, res);
        }
    }

    protected Map<String,CoreAdminHandler> getCustomRequestHandlers() throws Exception {
        String actionAndClassNamesCsv = System.getProperty("com.netaporter.adminHandlers");
        log.info("com.netaporter.adminHandlers=" + actionAndClassNamesCsv);

        Map<String,CoreAdminHandler> requestHandlers = new HashMap();

        if(actionAndClassNamesCsv != null) {
            String[] actionAndClassNames = actionAndClassNamesCsv.split(",");
            for (String actionAndClassNameStr: actionAndClassNames) {
                String[] actionAndClassName = actionAndClassNameStr.split(":");
                String action = actionAndClassName[0].trim();
                String className = actionAndClassName[1].trim();


                log.info("Loading " + className.trim());
                CoreAdminHandler reqHandler = (CoreAdminHandler) Class.forName(className).newInstance();
                requestHandlers.put(action, reqHandler);
            }
        }
        return requestHandlers;
    }
}
