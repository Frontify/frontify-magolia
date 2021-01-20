package com.acme.frontify.frontify.field.utils;

import com.acme.frontify.frontify.field.models.FrontifyAsset;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.objectfactory.Components;
import info.magnolia.templating.functions.TemplatingFunctions;
import net.sf.json.JSONSerializer;
import nl.vpro.jcr.criteria.query.AdvancedResult;
import nl.vpro.jcr.criteria.query.AdvancedResultItem;
import nl.vpro.jcr.criteria.query.Criteria;
import nl.vpro.jcr.criteria.query.JCRCriteriaFactory;
import nl.vpro.jcr.criteria.query.criterion.Criterion;
import nl.vpro.jcr.criteria.query.criterion.Restrictions;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.util.ISO9075;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public class FrontifyUtils {
    private static final String FRONTIFY_APP = "frontify-app";
    private static final String FRONTIFY_TYPE = "frontifyType";
    private static final String FRONTIFY_FOLDER_NAME = "frontify";
    private static final String NODE_TYPE_CONTENT_NODE = "mgnl:contentNode";
    private static final String NODE_TYPE_FOLDER_NODE = "mgnl:folder";

    public FrontifyUtils() {

    }

    public static FrontifyAsset json2class(String jsonString) throws IOException, JsonMappingException {
        return json2class(jsonString, FrontifyAsset.class);
    }

    private static <T> T json2class(String jsonString, Class<T> genericClass) throws IOException, JsonMappingException {
        if (isJSONValid(jsonString)) {
            return new ObjectMapper().readValue(jsonString, genericClass);
        }
        return null;
    }

    private static boolean isJSONValid(String jsonString) throws net.sf.json.JSONException {
        if (jsonString.startsWith("\n")) {
            jsonString = jsonString.replaceFirst("\n", "").trim();
        }
        JSONSerializer.toJSON(jsonString);
        return true;
    }

    public static Node getFrontifyAssetById(String id) throws RepositoryException {
        List<Node> frontifyAssets = advancedResultToList(executeCriteria(itemsByGoid(id), FRONTIFY_APP));
        if (StringUtils.isNotBlank(id)) {
            for (Node frontifyAsset : frontifyAssets) {
                return frontifyAsset;
            }
        }
        return null;
    }

    private static List<Node> advancedResultToList(AdvancedResult advancedResult) {
        return advancedResultItemListToNodeList(IteratorUtils.toList(advancedResult.getItems()));
    }

    private static List<Node> advancedResultItemListToNodeList(List<AdvancedResultItem> items) {
        List<Node> result = new LinkedList<>();
        for (AdvancedResultItem advancedResultItem : items) {
            Node node = getTemplatingFunctions().wrapForI18n(advancedResultItem.getJCRNode());
            result.add(node);
        }
        return result;
    }

    private static AdvancedResult executeCriteria(Criteria criteria, String sessionName) throws RepositoryException {
        return executeCriteria(criteria, sessionName, false);
    }

    private static AdvancedResult executeCriteria(Criteria criteria, String sessionName, boolean systemSession) throws RepositoryException {
        return criteria.execute(systemSession ? getSystemSession(sessionName) : getGenericSession(sessionName));
    }

    private static Session getGenericSession(String sessionName) throws RepositoryException {
        return MgnlContext.getJCRSession(sessionName);
    }

    private static Session getSystemSession(String sessionName) throws RepositoryException {
        return MgnlContext.getSystemContext().getJCRSession(sessionName);
    }

    private static TemplatingFunctions getTemplatingFunctions() {
        return Components.getComponent(TemplatingFunctions.class);
    }

    private static Criteria itemsByGoid (String id) {
        return getGenericCriteria(FRONTIFY_TYPE)
                .add(Restrictions.eq("id", id));
    }

    private static Criteria getGenericCriteria(String nodeType, String nodePath) {
        return JCRCriteriaFactory.createCriteria()
                .setBasePath(ISO9075.encodePath(nodePath))
                .add(Restrictions.eq(Criterion.JCR_PRIMARYTYPE, nodeType));
    }

    private static Criteria getGenericCriteria(String nodeType) {
        return getGenericCriteria(nodeType, "/");
    }

    public static Node saveInJCR(FrontifyAsset genericElement) throws RepositoryException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String nodeName = genericElement.getName();
        String nodeType = FRONTIFY_TYPE;
        Session genericSystemSession = MgnlContext.getSystemContext().getJCRSession(FRONTIFY_APP);
        Node rootNode = genericSystemSession.getRootNode();
        boolean frontifyFolder = rootNode.hasNode(FRONTIFY_FOLDER_NAME);
        Node frontifyFolderNode = rootNode;
        if (!frontifyFolder) {
            frontifyFolderNode = rootNode.addNode(FRONTIFY_FOLDER_NAME, NODE_TYPE_FOLDER_NODE);
        } else {
            frontifyFolderNode = rootNode.getNode(FRONTIFY_FOLDER_NAME);
        }

        Node genericNode = initializeSubNode(frontifyFolderNode, nodeName, nodeType);

        System.out.println("saving " + nodeName);
        setPropertiesFromClassWithLanguage(genericElement, genericNode);
        genericSystemSession.save();
        return genericNode;
    }

    private static void setPropertiesFromClassWithLanguage(FrontifyAsset genericElement, Node node) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, RepositoryException {
        Field[] fields = genericElement.getClass().getDeclaredFields();
        if (fields.length == 0) {
            fields = genericElement.getClass().getSuperclass().getDeclaredFields();
        }

        for (Field field : fields) {
            String fieldName = field.getName();
            String methodName = "get" + firstLetterToUpperCase(fieldName);
            Method method = genericElement.getClass().getMethod(methodName);
            Object methodInvokeResult = method.invoke(genericElement);

            if (methodInvokeResult != null) {
                if (methodInvokeResult instanceof String) {
                    PropertyUtil.setProperty(node, fieldName, methodInvokeResult.toString().trim());
                } else if (methodInvokeResult instanceof Integer ||
                        methodInvokeResult instanceof Long ||
                        methodInvokeResult instanceof Double ||
                        methodInvokeResult instanceof Boolean) {
                    PropertyUtil.setProperty(node, fieldName, methodInvokeResult);
                }
            }
        }
    }

    private static Node initializeSubNode(Node node, String subNodeName, String subNodeType) throws RepositoryException {
        if (node.hasNode(subNodeName)) {
            return node.getNode(subNodeName);
        } else {
            if (StringUtils.isNotBlank(subNodeType)) {
                return node.addNode(subNodeName, subNodeType);
            } else {
                return node.addNode(subNodeName, NODE_TYPE_CONTENT_NODE);
            }
        }
    }

    private static String firstLetterToUpperCase(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1, string.length());
    }
}
