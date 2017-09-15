<!DOCTYPE html>
<html lang="en">
<#include "../header.ftl">
<body>
<#include "../nav.ftl">

<div class="container-fluid">
<#include "sider.ftl">
    <div class="col-md-9">
        <div class="row placeholder">
            <form id="search" action="?" method="GET">
                <div class="input-group" id="pathGroup">
                    <input type="text" class="form-control" placeholder="/path" name="path" id="path" 
                    value="${path!}"
                    onkeydown="onSearch(event)">
                    <span class="input-group-btn">
                  <button type="submit" class="btn btn-success">Search</button>
                </span>
                </div>
            </form>
        </div>
        <form id="form-index" action="?" method="?">
            <div class="row placeholder">
                <button class="btn btn-success rowbtn" id="delete-button"
                        onclick="showDeleteModal(); return false;" <#if !hasDeletePermission || hasChild!>disabled</#if>>Delete
                </button>
                <button class="btn btn-success rowbtn" id="create-button" onclick="onCreate('${path?url}'); return false;">Create
                </button>
                <button class="btn btn-success rowbtn" id="update-button"
                        onclick="showUpdateModal(); return false;" <#if !hasPermission>disabled</#if>>Update
                </button>
                <button class="btn btn-success rowbtn" id="import-button" onclick="showImportModal(); return false;" <#if !hasPermission>disabled</#if>>Import
                </button>
                <label class="checkbox" for="editable">
                    <input type="checkbox" value="" id="editable" data-toggle="checkbox"
                           <#if !hasPermission>disabled</#if>>
                    Editable
                </label>
                <div class="formatRadio">
                    <button class="btn btn-primary btn-xs" id="apply-button"
                            onclick="onApply('${cluster}' ,'${path?url}'); return false;">Apply
                        Permission
                    </button>
                    <button class="btn btn-primary btn-xs" id="snapshot-button"
                            onclick="showSnapshotModal(); return false;">Snapshot
                    </button>
                    <button class="btn btn-primary btn-xs" id="stat-button"
                            onclick="showStatModal(); return false;">Stat
                    </button>
                </div>
            </div>
            <!-- Main component for a primary marketing message or call to action -->
            <div class="row placeholder">
                <textarea id="data" name="data" class="small">${data!}</textarea>
            </div>
            <input type="hidden" class="form-control" placeholder="Search" name="path" id="path" value="${path!}">
            <input type="hidden" class="form-control" placeholder="Search" name="cluster" id="cluster" value="${cluster!}">
        </form>
    </div>

</div> <!-- /container -->

<#include "snapshot-modal.ftl">
<#include "delete-modal.ftl">
<#include "update-modal.ftl">
<#include "stat-modal.ftl">
<#include "import-modal.ftl">
<#include "../script.ftl">

<script>
    var editor = CodeMirror.fromTextArea(document.getElementById("data"), {
        lineNumbers: true,
        theme: 'dracula',
        readOnly: true
    });

    $('#editable').on('change.radiocheck', function (event) {
        var isChecked = $("#editable").is(":checked");
        editor.setOption("readOnly", !isChecked);
    });

    function showSnapshotModal() {
        $('#snapshotModal').modal({
            keyboard: true
        })
    }

    function showStatModal() {
        $('#statModal').modal({
            keyboard: true
        })
    }

    function onSearch(event) {
        if (event.keyCode == 13) {
            $("#search").submit();
        }
    }

    function showUpdateModal() {
        $('#updateModal').modal({
            keyboard: true
        })
    }

    function showDeleteModal() {
        $('#deleteModal').modal({
            keyboard: true
        })
    }

    function showImportModal() {
        $('#importModal').modal({
            keyboard: true
        })
    }

    function onButtonClick(form, action, path, method) {
        form.attr('action', action);
        form.attr('method', method);
        form.submit();
    }

    function onImportButtonClick(form, action, method) {
        form.action = action;
        form.method = method;
        form.submit();
    }

    function onCreate(path) {
        location.href = "/clusters/${cluster}/nodes/create?path=" + path;
    }

    function onApply(cluster, path) {
        location.href = "/teams/apply?path=" + path + "&cluster=" + cluster;
    }
</script>

</body>
</html>
