package com.example.uimodule.frontify.field.definition;

import info.magnolia.i18nsystem.I18nText;
import info.magnolia.ui.form.field.converter.IdentifierToPathConverter;
import info.magnolia.ui.form.field.definition.ConfiguredFieldDefinition;
import info.magnolia.ui.form.field.definition.ContentPreviewDefinition;

public class FrontifyFieldDefinition extends ConfiguredFieldDefinition {
    private String buttonSelectNewLabel;
    private String buttonSelectOtherLabel;
    private String targetWorkspace = "frontify-app";
    private boolean fieldEditable = true;
    private String placeholder;

    private IdentifierToPathConverter identifierToPathConverter;
    private ContentPreviewDefinition contentPreviewDefinition;

    public FrontifyFieldDefinition() {

    }

    public String getTargetWorkspace() {
        return this.targetWorkspace;
    }

    public void setTargetWorkspace(String targetWorkspace) {
        this.targetWorkspace = targetWorkspace;
    }

    @I18nText
    public String getButtonSelectNewLabel() {
        return this.buttonSelectNewLabel;
    }

    @I18nText
    public String getButtonSelectOtherLabel() {
        return this.buttonSelectOtherLabel;
    }

    public boolean isFieldEditable() {
        return this.fieldEditable;
    }

    public void setButtonSelectNewLabel(String buttonSelectNewLabel) {
        this.buttonSelectNewLabel = buttonSelectNewLabel;
    }

    public void setButtonSelectOtherLabel(String buttonSelectOtherLabel) {
        this.buttonSelectOtherLabel = buttonSelectOtherLabel;
    }

    public void setFieldEditable(boolean fieldEditable) {
        this.fieldEditable = fieldEditable;
    }

    @I18nText
    public String getPlaceholder() {
        return this.placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    @I18nText
    public String getButtonSelectNewDefaultLabel() {
        return "buttons.select.new";
    }

    @I18nText
    public String getButtonSelectOtherDefaultLabel() {
        return "buttons.select.another";
    }

    public IdentifierToPathConverter getIdentifierToPathConverter() {
        return this.identifierToPathConverter;
    }

    public ContentPreviewDefinition getContentPreviewDefinition() {
        return this.contentPreviewDefinition;
    }

    public void setIdentifierToPathConverter(IdentifierToPathConverter identifierToPathConverter) {
        this.identifierToPathConverter = identifierToPathConverter;
    }

    public void setContentPreviewDefinition(ContentPreviewDefinition contentPreviewDefinition) {
        this.contentPreviewDefinition = contentPreviewDefinition;
    }
}

