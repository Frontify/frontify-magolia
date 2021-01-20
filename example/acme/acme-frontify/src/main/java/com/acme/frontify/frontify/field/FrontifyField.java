package com.acme.frontify.frontify.field;

import com.acme.frontify.frontify.field.definition.FrontifyFieldDefinition;
import com.acme.frontify.frontify.field.models.FrontifyAsset;
import com.acme.frontify.frontify.field.utils.FrontifyUtils;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.*;
import com.vaadin.v7.data.util.converter.Converter;
import com.vaadin.v7.ui.CustomField;
import com.vaadin.v7.ui.HorizontalLayout;
import com.vaadin.v7.ui.TextField;
import com.vaadin.v7.ui.VerticalLayout;
import info.magnolia.context.MgnlContext;
import info.magnolia.objectfactory.ComponentProvider;
import info.magnolia.ui.api.app.AppController;
import info.magnolia.ui.api.context.UiContext;
import info.magnolia.ui.form.field.converter.IdentifierToPathConverter;
import info.magnolia.ui.form.field.definition.ContentPreviewDefinition;
import info.magnolia.ui.form.field.factory.LinkFieldFactory;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.EventObject;
import java.util.Optional;

public class FrontifyField extends CustomField<String> {
    public static final String FRONTIFY_POPUP_STYLE_NAME = "popupStyleNameForFrontifyLibrary";
    public static final String FRONTIFY_DOCROOT_PATH = "docroot/frontify.html";
    public static final String FRONTIFY_BROWSER_FRAME_WIDTH = "1000px";
    public static final String FRONTIFY_BROWSER_FRAME_HEIGHT = "600px";
    public static final String FRONTIFY_SELECT_TITLE = "Select from Frontify";
    private static final Logger log = LoggerFactory.getLogger(LinkFieldFactory.class);
    private final VerticalLayout rootLayout;
    private final HorizontalLayout linkLayout;
    private final TextField frontifyTextField;
    private final Button frontifySelectButton;
    private final BrowserFrame browserFrame;
    private PopupView frontifyPopupView;
    private Component contentPreview;

    private String buttonCaptionNew;
    private String buttonCaptionOther;
    private boolean isFieldEditable;

    public FrontifyField() {
        this.rootLayout = new VerticalLayout();
        this.linkLayout = new HorizontalLayout();
        this.frontifyTextField = new TextField();
        this.frontifySelectButton = new NativeButton();
        this.browserFrame = new BrowserFrame();
        this.frontifyPopupView = new PopupView(null, this.browserFrame);
    }

    /** @deprecated */
    @Deprecated
    public FrontifyField(FrontifyFieldDefinition frontifyFieldDefinition,
                         ComponentProvider componentProvider) {
        this();
    }

    /** @deprecated */
    @Deprecated
    public FrontifyField(FrontifyFieldDefinition frontifyFieldDefinition,
                         AppController appController,
                         UiContext uiContext,
                         ComponentProvider componentProvider) {
        this();
    }

    @Override
    protected Component initContent() {
        // Create the browser frame for the popup
        this.browserFrame.setSource(new ExternalResource(getBasePath() + FRONTIFY_DOCROOT_PATH));
        this.browserFrame.setStyleName(FRONTIFY_POPUP_STYLE_NAME);
        this.browserFrame.setWidth(FRONTIFY_BROWSER_FRAME_WIDTH);
        this.browserFrame.setHeight(FRONTIFY_BROWSER_FRAME_HEIGHT);

        // Create the popup
        this.frontifyPopupView.setHideOnMouseOut(false);
        this.frontifyPopupView.setSizeFull();
        this.frontifyPopupView.setResponsive(true);

        this.addStyleName("linkfield");
        this.rootLayout.setSizeFull();
        this.rootLayout.setSpacing(true);
        this.frontifyTextField.setWidth(100.0F, Unit.PERCENTAGE);
        this.frontifyTextField.setNullRepresentation("");
        this.frontifyTextField.setNullSettingAllowed(true);
        this.frontifyTextField.setImmediate(true);
        this.frontifyTextField.addValueChangeListener((event) -> {
            // get the new value
            String newValue = String.valueOf(Optional.ofNullable(event.getProperty().getValue()).orElse(""));
            if(newValue.charAt(0) == '{') {
                try {
                    try {
                        // get asset id
                        FrontifyAsset frontifyAsset = FrontifyUtils.json2class(newValue);
                        try {
                            String path = frontifyAsset.getGenericUrl();
                            // search if exist
                            Node node = FrontifyUtils.getFrontifyAssetById(frontifyAsset.getId());
                            if (node == null) {
                                // save in db
                                Node newNode = null;
                                try {
                                    newNode = FrontifyUtils.saveInJCR(frontifyAsset);
                                } catch (NoSuchMethodException e) {
                                    e.printStackTrace();
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                                path = newNode.getPath();
                            } else {
                                // return path to the existing asset
                                Node existInDb = FrontifyUtils.getFrontifyAssetById(frontifyAsset.getId());
                                path = existInDb.getPath();
                            }

                            setValueAndHidePopup(path);
                        } catch (RepositoryException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (JSONException err){
                    log.debug("Error", err.toString());
                }
            } else {
                setValueAndHidePopup(newValue);
            }
        });
        this.linkLayout.setSizeFull();
        this.linkLayout.addComponent(this.frontifyTextField);
        this.linkLayout.setExpandRatio(this.frontifyTextField, 1.0F);
        this.linkLayout.setComponentAlignment(this.frontifyTextField, Alignment.MIDDLE_LEFT);
        if (!this.frontifyTextField.isReadOnly()) {
            this.frontifySelectButton.addStyleName("magnoliabutton");
            this.linkLayout.addComponent(this.frontifySelectButton);
            this.linkLayout.setExpandRatio(this.frontifySelectButton, 0.0F);
            this.linkLayout.setComponentAlignment(this.frontifySelectButton, Alignment.MIDDLE_RIGHT);
        }

        this.setButtonCaption(FRONTIFY_SELECT_TITLE);
        this.linkLayout.addComponent(this.frontifyPopupView);
        this.rootLayout.addComponent(this.linkLayout);
        this.addValueChangeListener((event) -> {
            this.updateComponents(this.getValue());
        });
        this.updateComponents(this.getValue());

        return this.rootLayout;
    }

    public TextField getTextField() {
        return this.frontifyTextField;
    }

    public PopupView getFrontifyPopupView() {
        return frontifyPopupView;
    }

    public BrowserFrame getBrowserFrame() {
        return browserFrame;
    }

    public Button getSelectButton() {
        return this.frontifySelectButton;
    }

    public String getValue() {
        return (String)super.getValue();
    }

    public void setContentPreview(Component contentPreviewComponent) {
        if (this.contentPreview != null) {
            this.rootLayout.removeComponent(this.contentPreview);
        }

        contentPreviewComponent.setVisible(StringUtils.isNotBlank((CharSequence)this.getValue()));
        this.rootLayout.addComponentAsFirst(contentPreviewComponent);
        this.contentPreview = contentPreviewComponent;
    }

    public void setValue(String newValue) throws ReadOnlyException, Converter.ConversionException {
        super.setValue(newValue);
    }

    protected void setInternalValue(String newValue) {
        super.setInternalValue(newValue);
        this.frontifyTextField.setValue(newValue);
    }

    public void setReadOnly(boolean readOnly) {
        super.setReadOnly(readOnly);
        this.frontifyTextField.setReadOnly(readOnly);
    }

    private void updateComponents(String currentValue) {
        if (!this.isFieldEditable && StringUtils.isNotBlank(currentValue)) {
            this.frontifyTextField.setReadOnly(true);
            if (this.linkLayout.getComponentIndex(this.frontifySelectButton) != -1) {
                this.linkLayout.removeComponent(this.frontifySelectButton);
            }
        } else {
            this.setButtonCaption(currentValue);
        }

    }

    private void setButtonCaption(String value) {
        if (StringUtils.isNotBlank(value)) {
            this.frontifySelectButton.setCaption(this.buttonCaptionOther);
            this.frontifySelectButton.setDescription(this.buttonCaptionOther);
        } else {
            this.frontifySelectButton.setCaption(this.buttonCaptionNew);
            this.frontifySelectButton.setDescription(this.buttonCaptionNew);
        }

    }

    private String getBasePath() {
        return MgnlContext.getContextPath() + "/";
    }

    public void setButtonCaptionNew(String buttonCaptionNew) {
        this.buttonCaptionNew = buttonCaptionNew;
    }

    public void setButtonCaptionOther(String buttonCaptionOther) {
        this.buttonCaptionOther = buttonCaptionOther;
    }

    public void setTextFieldConverter(Converter textFieldConverter) {
        this.setConverter(textFieldConverter);
    }

    public void setFieldEditable(boolean isFieldEditable) {
        this.isFieldEditable = isFieldEditable;
    }

    public boolean isEmpty() {
        return StringUtils.isEmpty(this.getValue());
    }

    @Override
    public Class<? extends String> getType() {
        return String.class;
    }

    private void setValueAndHidePopup(String newValue) {
        this.setValue(newValue);
        frontifyPopupView.setPopupVisible(false);
    }
}
