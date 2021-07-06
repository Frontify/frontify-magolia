package com.acme.frontify.frontify.field.factory;

import com.acme.frontify.frontify.field.FrontifyField;
import com.acme.frontify.frontify.field.definition.FrontifyFieldDefinition;
import com.vaadin.ui.Button;
import com.vaadin.v7.data.Item;
import com.vaadin.v7.ui.Field;
import info.magnolia.objectfactory.ComponentProvider;
import info.magnolia.ui.api.app.AppController;
import info.magnolia.ui.api.context.UiContext;
import info.magnolia.ui.api.i18n.I18NAuthoringSupport;
import info.magnolia.ui.form.field.component.ContentPreviewComponent;
import info.magnolia.ui.form.field.converter.IdentifierToPathConverter;
import info.magnolia.ui.form.field.converter.Vaadin7FieldValueConverterAdapter;
import info.magnolia.ui.form.field.definition.LinkFieldDefinition;
import info.magnolia.ui.form.field.factory.AbstractFieldFactory;
import info.magnolia.ui.form.field.factory.LinkFieldFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Objects;

public class FrontifyFieldFactory<D extends FrontifyFieldDefinition> extends AbstractFieldFactory<D, String> {
    private static final Logger log = LoggerFactory.getLogger(LinkFieldFactory.class);
    public static final String PATH_PROPERTY_NAME = "transientPathProperty";
    private final AppController appController;
    private final UiContext uiContext;
    private ComponentProvider componentProvider;
    private FrontifyField frontifyField;

    @Inject
    public FrontifyFieldFactory(D definition, Item relatedFieldItem, UiContext uiContext, I18NAuthoringSupport i18nAuthoringSupport, AppController appController, ComponentProvider componentProvider) {
        super(definition, relatedFieldItem, uiContext, i18nAuthoringSupport);
        this.appController = appController;
        this.uiContext = uiContext;
        this.componentProvider = componentProvider;
    }

    /** @deprecated */
    @Deprecated
    public FrontifyFieldFactory(D definition, Item relatedFieldItem, AppController appController, UiContext uiContext, ComponentProvider componentProvider) {
        this(definition, relatedFieldItem, uiContext, (I18NAuthoringSupport)componentProvider.getComponent(I18NAuthoringSupport.class), appController, componentProvider);
    }

    protected Field<String> createFieldComponent() {
        this.frontifyField = new FrontifyField();
        this.frontifyField.setButtonCaptionNew(this.isMessageKey(((FrontifyFieldDefinition)this.definition).getButtonSelectNewLabel()) ? ((FrontifyFieldDefinition)this.definition).getButtonSelectNewDefaultLabel() : ((FrontifyFieldDefinition)this.definition).getButtonSelectNewLabel());
        this.frontifyField.setButtonCaptionOther(this.isMessageKey(((FrontifyFieldDefinition)this.definition).getButtonSelectOtherLabel()) ? ((FrontifyFieldDefinition)this.definition).getButtonSelectOtherDefaultLabel() : ((FrontifyFieldDefinition)this.definition).getButtonSelectOtherLabel());
        this.frontifyField.getSelectButton().setDisableOnClick(false);
        this.frontifyField.getSelectButton().addClickListener(this.createButtonClickListener());
        this.frontifyField.setFieldEditable(((FrontifyFieldDefinition)this.definition).isFieldEditable());

        IdentifierToPathConverter converter = ((FrontifyFieldDefinition)this.definition).getIdentifierToPathConverter();
        if (converter != null) {
            converter.setWorkspaceName(((FrontifyFieldDefinition)this.definition).getTargetWorkspace());
            this.frontifyField.setTextFieldConverter(Vaadin7FieldValueConverterAdapter.wrap(converter));
        }

        if (((FrontifyFieldDefinition)this.definition).getContentPreviewDefinition() != null && ((FrontifyFieldDefinition)this.definition).getContentPreviewDefinition().getContentPreviewClass() != null) {
            ContentPreviewComponent<?> contentPreviewComponent = (ContentPreviewComponent)this.componentProvider.newInstance(((FrontifyFieldDefinition)this.definition).getContentPreviewDefinition().getContentPreviewClass(), new Object[]{((FrontifyFieldDefinition)this.definition).getTargetWorkspace()});
            this.frontifyField.getTextField().addValueChangeListener((event) -> {
                String itemReference = Objects.toString(event.getProperty().getValue(), "");
                contentPreviewComponent.onValueChange(itemReference);
                contentPreviewComponent.setVisible(StringUtils.isNotBlank(itemReference));
            });
            contentPreviewComponent.onValueChange(this.frontifyField.getValue());
            contentPreviewComponent.setVisible(StringUtils.isNotBlank(this.frontifyField.getValue()));
            this.frontifyField.setContentPreview(contentPreviewComponent);
        }

        String placeholder = ((FrontifyFieldDefinition)this.definition).getPlaceholder();
        if (placeholder != null && !this.isMessageKey(placeholder)) {
            this.frontifyField.getTextField().setInputPrompt(placeholder);
        }

        return this.frontifyField;
    }

    private Button.ClickListener createButtonClickListener() {
        return (event) -> {
            this.frontifyField.getFrontifyPopupView().setPopupVisible(true);
            this.frontifyField.getTextField().setId(this.frontifyField.getTextField().getConnectorId());
            this.frontifyField.getBrowserFrame().setId(this.frontifyField.getTextField().getConnectorId());

        };
    }
}
