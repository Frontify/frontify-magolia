package com.example.uimodule.frontify.field;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.v7.ui.HorizontalLayout;
import com.vaadin.v7.ui.Label;
import info.magnolia.cms.util.PathUtil;
import info.magnolia.context.MgnlContext;
import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.objectfactory.ComponentProvider;
import info.magnolia.ui.form.field.component.AbstractContentPreviewComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

public class FrontifyAppLinkSelector extends AbstractContentPreviewComponent {
    public static final String FRONTIFY_PREVIEW_ELEMENT_SIZE = "150px";
    public static final String FRONTIFY_WORKSPACE = "frontify-app";
    public static final String FRONTIFY_FILE_INFO_HEADER = "File info";
    public static final String FRONTIFY_FILE_INFO_TITLE = "Title";
    public static final String FRONTIFY_FILE_INFO_SIZE = "File size";
    private static final Logger log = LoggerFactory.getLogger(FrontifyAppLinkSelector.class);
    private ComponentProvider componentProvider;
    private Node currentItem;
    private final SimpleTranslator i18n;

    @Inject
    public FrontifyAppLinkSelector(String workspace, SimpleTranslator i18n, ComponentProvider componentProvider) {
        super(workspace);
        this.componentProvider = componentProvider;
        this.i18n = i18n;
        this.rootLayout = new HorizontalLayout();
        setCompositionRoot(rootLayout);
    }

    @Override
    protected Component refreshContentDetail(Object o) {
        FormLayout fileInfo = new FormLayout();
        fileInfo.setSizeUndefined();
        fileInfo.addStyleName("file-details");
        fileInfo.addComponent(getFileDetailHeader());
        fileInfo.addComponent(getFileDetailFileName());
        fileInfo.addComponent(getFileDimensions());
        return fileInfo;
    }

    @Override
    protected Component refreshContentPreview(Object o) {
        CssLayout previewLayout = new CssLayout();
        previewLayout.addStyleName("file-preview-area");
        previewLayout.setWidth(FRONTIFY_PREVIEW_ELEMENT_SIZE);
        previewLayout.setHeight(FRONTIFY_PREVIEW_ELEMENT_SIZE);
        // Create the preview Component (Icon / Image thumbnail/...)
        String previewUrl = PropertyUtil.getString((Node) o, "previewUrl");
        Image image = new Image();
        ExternalResource externalResource = new ExternalResource(previewUrl);
        image.setSource(externalResource);
        if (image != null) {
            image.addStyleName("preview-image");
            previewLayout.addComponent(image);
        }

        return previewLayout;
    }

    @Override
    protected Object refreshItem(String s) {
        if (s.charAt(0) != '{') {
            // get Node
            try {
                Session genericSystemSession = MgnlContext.getSystemContext().getJCRSession(FRONTIFY_WORKSPACE);
                // get node by path
                String newPath = s.replaceFirst("/", "");
                Node node = genericSystemSession.getRootNode().getNode(newPath);
                currentItem = node;
                return node;
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * Add Title.
     */
    protected Component getFileDetailHeader() {
        Label label = new Label();
        label.setValue(FRONTIFY_FILE_INFO_HEADER);
        label.addStyleName(ValoTheme.LABEL_BOLD);
        return label;
    }

    /**
     * Add File Name.<br>
     */
    protected Component getFileDetailFileName() {
        Label label = new Label();
        label.setCaption(FRONTIFY_FILE_INFO_TITLE);
        String filename = PropertyUtil.getString(currentItem, "name");
        label.setValue(PathUtil.stripExtension(filename));
        return label;
    }

    /**
     * Add File Dimension.
     */
    protected Component getFileDimensions() {
        Label label = new Label();
        label.setCaption(FRONTIFY_FILE_INFO_SIZE);
        String width = PropertyUtil.getString(currentItem, "width");
        String height = PropertyUtil.getString(currentItem, "height");
        label.setValue(String.valueOf(height) + "x" + String.valueOf(width));
        return label;
    }
}
