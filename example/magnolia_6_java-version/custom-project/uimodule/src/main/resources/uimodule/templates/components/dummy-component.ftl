<div>This is the output of the dummy component (which was created by Maven archetype).</div>

<div>
[#--check for db field--]
    [#if content.assetFrontify?has_content]
        [#assign assetFrontify = cmsfn.contentById(content.assetFrontify!, "frontify-app"!)!/]
        [#if assetFrontify?has_content && assetFrontify.genericUrl?has_content]
            <img style="width: 300px;" class="project-" src="${assetFrontify.genericUrl!"default generic url"}" />
        [/#if]
    [/#if]

[#--check for new frontify field--]
    [#if content.newFrontify?has_content]
        [#assign assetFrontify = cmsfn.contentById(content.newFrontify!, "frontify-app"!)!/]
        [#if assetFrontify?has_content && assetFrontify.genericUrl?has_content]
            <img style="width: 300px;" class="project-" src="${assetFrontify.genericUrl!"default generic url"}" />
        [/#if]
    [/#if]
</div>