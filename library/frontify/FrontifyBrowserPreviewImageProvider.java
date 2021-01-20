package com.acme.frontify.frontify;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.ui.imageprovider.ImageProvider;
import info.magnolia.ui.vaadin.integration.jcr.JcrNodeItemId;
import org.apache.commons.lang3.StringUtils;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

public class FrontifyBrowserPreviewImageProvider implements ImageProvider {
    @Override
    public Object getThumbnailResource(Object o, String s) {
        Resource resource = null;

        if (o instanceof JcrNodeItemId) {
            String photoId = ((JcrNodeItemId) o).getUuid();
            if (StringUtils.isNotBlank(photoId)) {
                if (s != null) {
                    String uuid = ((JcrNodeItemId) o).getUuid();
                    String workspace = ((JcrNodeItemId) o).getWorkspace();
                    Node imageNode = null;
                    try {
                        imageNode = NodeUtil.getNodeByIdentifier(workspace, uuid);
                        String previewUrl = PropertyUtil.getString(imageNode, "previewUrl");
                        if (StringUtils.isNotBlank(previewUrl)) {
                            resource = new ExternalResource(previewUrl);
                        }
                    } catch (RepositoryException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return resource;
    }

    @Override
    public String resolveIconClassName(String s) {
        return null;
    }

    @Override
    public String getPortraitPath(Object o) {
        return null;
    }

    @Override
    public String getThumbnailPath(Object o) {
        return null;
    }
}
