Default configuration directory used with info.magnolia.init.MagnoliaServletContextListener.

Using the ${servername} and ${webapp} properties you can easily bundle in the same webapp different set of
configurations which are automatically applied depending on the server name (useful for switching between development,
test and production instances where the repository configuration need to be different) or the webapp name (useful to
bundle both the public and admin log4j/jndi/bootstrap configuration in the same war). The values for the placeholders
are provided by MagnoliaInitPaths. By default the initializer will try to search for the file in different location with
different combination of ${servername}, ${contextPath} and ${webapp} in the following order of precedence:

1. WEB-INF/config/${servername}/${contextPath}/magnolia.properties
2. WEB-INF/config/${servername}/${webapp}/magnolia.properties
3. WEB-INF/config/${servername}/magnolia.properties
4. WEB-INF/config/${contextPath}/magnolia.properties
5. WEB-INF/config/${webapp}/magnolia.properties
6. WEB-INF/config/default/magnolia.properties
7. WEB-INF/config/magnolia.properties

DEPRECATION NOTE: this configuration mode is deprecated and will be replaced by profile directories in future versions.
See the following section.

Alternatively, when the MAGNOLIA_PROFILE system property or environment variable is set, its value is used to locate a
directory at code WEB_INF/config/${MAGNOLIA_PROFILE}. Property files in that directory, the WEB-INF/config/shared
directory and the WEB-INF/config/default directory are resolved in the following order of precedence:

1. WEB-INF/config/${MAGNOLIA_PROFILE}/magnolia_${MAGNOLIA_INSTANCE_TYPE}_${MAGNOLIA_STAGE}.properties
2. WEB-INF/config/${MAGNOLIA_PROFILE}/magnolia_${MAGNOLIA_INSTANCE_TYPE}.properties
3. WEB-INF/config/${MAGNOLIA_PROFILE}/magnolia.properties
4. WEB-INF/config/shared/magnolia_${MAGNOLIA_INSTANCE_TYPE}_${MAGNOLIA_STAGE}.properties
5. WEB-INF/config/shared/magnolia_${MAGNOLIA_INSTANCE_TYPE}.properties
6. WEB-INF/config/shared/magnolia.properties
7. WEB-INF/config/default/magnolia_${MAGNOLIA_INSTANCE_TYPE}_${MAGNOLIA_STAGE}.properties
8. WEB-INF/config/default/magnolia_${MAGNOLIA_INSTANCE_TYPE}.properties
9. WEB-INF/config/default/magnolia.properties
