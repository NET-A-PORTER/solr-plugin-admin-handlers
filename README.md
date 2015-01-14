Solr Multi CoreAdminHandler Plugin
----------------------------------

> The CoreAdminHandler is a special SolrRequestHandler that is used to manage Solr cores. Unlike normal SolrRequestHandlers, the CoreAdminHandler is not attached to a single core. Instead, it manages all the cores running in a single Solr instance. Only one CoreAdminHandler exists for each top-level Solr instance.

[Source: Solr Docs](https://cwiki.apache.org/confluence/display/solr/CoreAdmin+API)

*But what if you want more than one `CoreAdminHandler` plugins in your Solr instance?* This plugin could help.

# Installation

1) Drop [`solr-plugin-admin-handlers-0.1.jar`](http://search.maven.org/remotecontent?filepath=com/netaporter/solr-plugin-admin-handlers/0.1/solr-plugin-admin-handlers-0.1.jar) into the directory specified by the `sharedLib` in your `solr.xml`

2) Add the following to your `solr.xml`

    <str name="adminHandler">com.netaporter.MultiAdminHandler</str>

3) Any `CoreAdminHandler` plugins you wish to install will have probably told you to do the above step for their plugin.
Instead add the following system property

    -Dcom.netaporter.adminHandlers=ACTIONKEY:com.my.lovely.CustomAdminHandler,ACTIONKEY2:com.blah.AnotherAdminHandler

The above will route all requests for `/admin/cores?action=ACTIONKEY` to `com.my.lovely.CustomAdminHandler` and all
requests to `/admin/cores?action=ACTIONKEY2` to `com.blah.AnotherAdminHandler`. Any other requests will be routed to
the default Solr `CoreAdminHandler`.

Currently routing is only limited to only the the action query string parameter.
