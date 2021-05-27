package com.example.uimodule.frontify.field.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "project",
        "creator_name",
        "created",
        "modifier_name",
        "modified",
        "object_type",
        "media_type",
        "title",
        "description",
        "name",
        "ext",
        "width",
        "height",
        "filesize",
        "asset_status",
        "tags",
        "generic_url",
        "preview_url",
        "download_url"
})
public class FrontifyAsset {

    @JsonProperty("id")
    private String id;
    @JsonProperty("project")
    private String project;
    @JsonProperty("creator_name")
    private String creatorName;
    @JsonProperty("created")
    private String created;
    @JsonProperty("modifier_name")
    private String modifierName;
    @JsonProperty("modified")
    private String modified;
    @JsonProperty("object_type")
    private String objectType;
    @JsonProperty("media_type")
    private String mediaType;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("name")
    private String name;
    @JsonProperty("ext")
    private String ext;
    @JsonProperty("width")
    private String width;
    @JsonProperty("height")
    private String height;
    @JsonProperty("filesize")
    private String filesize;
    @JsonProperty("asset_status")
    private String assetStatus;
    @JsonProperty("tags")
    private List<String> tags;
    @JsonProperty("generic_url")
    private String genericUrl;
    @JsonProperty("preview_url")
    private String previewUrl;
    @JsonProperty("download_url")
    private String downloadUrl;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("project")
    public String getProject() {
        return project;
    }

    @JsonProperty("project")
    public void setProject(String project) {
        this.project = project;
    }

    @JsonProperty("creator_name")
    public String getCreatorName() {
        return creatorName;
    }

    @JsonProperty("creator_name")
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    @JsonProperty("created")
    public String getCreated() {
        return created;
    }

    @JsonProperty("created")
    public void setCreated(String created) {
        this.created = created;
    }

    @JsonProperty("modifierName")
    public String getModifierName() {
        return modifierName;
    }

    @JsonProperty("modifierName")
    public void setModifierName(String modifierName) {
        this.modifierName = modifierName;
    }

    @JsonProperty("modified")
    public String getModified() {
        return modified;
    }

    @JsonProperty("modified")
    public void setModified(String modified) {
        this.modified = modified;
    }

    @JsonProperty("object_type")
    public String getObjectType() {
        return objectType;
    }

    @JsonProperty("object_type")
    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    @JsonProperty("media_type")
    public String getMediaType() {
        return mediaType;
    }

    @JsonProperty("media_type")
    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("ext")
    public String getExt() {
        return ext;
    }

    @JsonProperty("ext")
    public void setExt(String ext) {
        this.ext = ext;
    }

    @JsonProperty("width")
    public String getWidth() {
        return width;
    }

    @JsonProperty("width")
    public void setWidth(String width) {
        this.width = width;
    }

    @JsonProperty("height")
    public String getHeight() {
        return height;
    }

    @JsonProperty("height")
    public void setHeight(String height) {
        this.height = height;
    }

    @JsonProperty("filesize")
    public String getFilesize() {
        return filesize;
    }

    @JsonProperty("filesize")
    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    @JsonProperty("asset_status")
    public String getAssetStatus() {
        return assetStatus;
    }

    @JsonProperty("asset_status")
    public void setAssetStatus(String assetStatus) {
        this.assetStatus = assetStatus;
    }

    @JsonProperty("tags")
    public List<String> getTags() {
        return tags;
    }

    @JsonProperty("tags")
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @JsonProperty("generic_url")
    public String getGenericUrl() {
        return genericUrl;
    }

    @JsonProperty("generic_url")
    public void setGenericUrl(String genericUrl) {
        this.genericUrl = genericUrl;
    }

    @JsonProperty("preview_url")
    public String getPreviewUrl() {
        return previewUrl;
    }

    @JsonProperty("preview_url")
    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    @JsonProperty("download_url")
    public String getDownloadUrl() {
        return downloadUrl;
    }

    @JsonProperty("download_url")
    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
