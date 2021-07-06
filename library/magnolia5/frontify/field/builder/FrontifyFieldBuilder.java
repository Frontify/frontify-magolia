package com.acme.frontify.frontify.field.builder;

import com.acme.frontify.frontify.field.definition.FrontifyFieldDefinition;
import info.magnolia.ui.form.config.AbstractFieldBuilder;
import info.magnolia.ui.form.config.GenericValidatorBuilder;
import info.magnolia.ui.form.field.transformer.Transformer;
import info.magnolia.ui.form.validator.definition.ConfiguredFieldValidatorDefinition;

public class FrontifyFieldBuilder extends AbstractFieldBuilder {
    private final FrontifyFieldDefinition definition = new FrontifyFieldDefinition();

    public FrontifyFieldBuilder(String name) {
        this.definition().setName(name);
    }

    public FrontifyFieldDefinition definition() {
        return this.definition;
    }

    public FrontifyFieldBuilder buttonSelectNewLabel(String buttonSelectNewLabel) {
        this.definition().setButtonSelectNewLabel(buttonSelectNewLabel);
        return this;
    }

    public FrontifyFieldBuilder buttonSelectOtherLabel(String buttonSelectOtherLabel) {
        this.definition().setButtonSelectOtherLabel(buttonSelectOtherLabel);
        return this;
    }

    public FrontifyFieldBuilder fieldEditable() {
        this.definition().setFieldEditable(true);
        return this;
    }

    public FrontifyFieldBuilder fieldEditable(boolean fieldEditable) {
        this.definition().setFieldEditable(fieldEditable);
        return this;
    }

    public FrontifyFieldBuilder label(String label) {
        return (FrontifyFieldBuilder)super.label(label);
    }

    public FrontifyFieldBuilder i18nBasename(String i18nBasename) {
        return (FrontifyFieldBuilder)super.i18nBasename(i18nBasename);
    }

    public FrontifyFieldBuilder i18n(boolean i18n) {
        return (FrontifyFieldBuilder)super.i18n(i18n);
    }

    public FrontifyFieldBuilder i18n() {
        return (FrontifyFieldBuilder)super.i18n();
    }

    public FrontifyFieldBuilder description(String description) {
        return (FrontifyFieldBuilder)super.description(description);
    }

    public FrontifyFieldBuilder type(String type) {
        return (FrontifyFieldBuilder)super.type(type);
    }

    public FrontifyFieldBuilder required(boolean required) {
        return (FrontifyFieldBuilder)super.required(required);
    }

    public FrontifyFieldBuilder required() {
        return (FrontifyFieldBuilder)super.required();
    }

    public FrontifyFieldBuilder requiredErrorMessage(String requiredErrorMessage) {
        return (FrontifyFieldBuilder)super.requiredErrorMessage(requiredErrorMessage);
    }

    public FrontifyFieldBuilder readOnly(boolean readOnly) {
        return (FrontifyFieldBuilder)super.readOnly(readOnly);
    }

    public FrontifyFieldBuilder readOnly() {
        return (FrontifyFieldBuilder)super.readOnly();
    }

    public FrontifyFieldBuilder defaultValue(String defaultValue) {
        return (FrontifyFieldBuilder)super.defaultValue(defaultValue);
    }

    public FrontifyFieldBuilder styleName(String styleName) {
        return (FrontifyFieldBuilder)super.styleName(styleName);
    }

    public FrontifyFieldBuilder validator(ConfiguredFieldValidatorDefinition validatorDefinition) {
        return (FrontifyFieldBuilder)super.validator(validatorDefinition);
    }

    public FrontifyFieldBuilder validator(GenericValidatorBuilder validatorBuilder) {
        return (FrontifyFieldBuilder)super.validator(validatorBuilder);
    }

    public FrontifyFieldBuilder transformerClass(Class<? extends Transformer<?>> transformerClass) {
        return (FrontifyFieldBuilder)super.transformerClass(transformerClass);
    }
}
