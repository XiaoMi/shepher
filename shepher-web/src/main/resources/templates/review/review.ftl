<!DOCTYPE html>
<html lang="en">
<#include "../header.ftl">
<body>
<#include "../nav.ftl">

<div class="container-fluid">
    <div class="col-md-12">
        <div class="row placeholder">
            <ol class="breadcrumb diff-breadcrumb">
                <li><a href="/clusters/${cluster}/nodes">${cluster}</a></li>
            <#assign pathUrl=""/>
            <#list paths as p>
                <#if p != "">
                    <#assign pathUrl+="/" + p/>
                    <li><a href="/clusters/${cluster}/nodes?path=${pathUrl?url}">${p}</a></li>
                </#if>
            </#list>
            </ol>
        </div>

        <div class="row placeholder">
            <a href="/clusters/${cluster}/nodes?path=${backPath?url}">
                <button class="btn btn-link" id="back-button">< Back</button>
            </a>
            <form id="diff-form" class="diff-form" action="?" method="?">
            <#if reviewRequest.reviewStatus == 0>
                <button class="btn btn-success" id="accept-button"
                        onclick="showAcceptModal(); return false;" <#if !canReview>disabled</#if>>Accept
                </button>
                <button class="btn btn-default" id="reject-button"
                        onclick="showRejectModal(); return false;" <#if !canReview>disabled</#if>>Reject
                </button>
            <#elseif reviewRequest.reviewStatus gt 0>
                <button class="btn btn-success" id="accepted-button"
                        onclick="return false;" disabled>Accepted
                </button>
            <#else>
                <button class="btn btn-default" id="rejected-button"
                        onclick="return false;" disabled>Rejected
                </button>
            </#if>
            </form>
        </div>

        <!-- Main component for a primary marketing message or call to action -->
        <div class="row placeholder">
            <div id="diff-view" class="diff-view"></div>
            <textarea id="left" name="left" class="small hidden">${content!}</textarea>
            <textarea id="right" name="right" class="small hidden">${newContent!}</textarea>
        </div>
    </div>
</div>
<!-- /container -->
<#include "accept-modal.ftl">
<#include "reject-modal.ftl">
<#include "../script.ftl">
<script>
    var left = $('#left').val();
    var right = $('#right').val();
    var editor = CodeMirror.MergeView(document.getElementById("diff-view"), {
        value: right,
        origLeft: left,
        lineNumbers: true,
        theme: 'dracula',
        allowEditingOriginals: false,
        readOnly: true,
        revertButtons: false,
        showDifferences: true,
        connect: "align",
        collapseIdentical: true
    });

    function showAcceptModal() {
        $('#acceptModal').modal({
            keyboard: true
        })
    }

    function showRejectModal() {
        $('#rejectModal').modal({
            keyboard: true
        })
    }

    function onButtonClick(form, action, method) {
        form.attr('action', action);
        form.attr('method', method);

        form.submit();
    }
</script>

</body>
</html>
