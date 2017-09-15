<!DOCTYPE html>
<html lang="en">
<#include "../header.ftl">
<body>
<#include "../nav.ftl">

<div class="container-fluid">
<#include "sider.ftl">
    <div class="col-md-9">
        <form id="form-create" action="?" method="POST">
            <div class="row placeholder">
                <h6>Parent Node: ${path!}</h6>
                <input type="text" class="form-control placeholder" placeholder="Child Node" name="child" id="child"
                       value="">
                <button class="btn btn-success" id="create-button" onclick="showCreateModal(); return false;">Create
                </button>
                <button class="btn btn-default" id="cancel-button" onclick="onCancel('${path?url}'); return false;">Back
                </button>
                <div class="formatRadio">

                </div>
            </div>
            <!-- Main component for a primary marketing message or call to action -->
            <div class="row placeholder">
                <textarea id="data" name="data" class="small"></textarea>
            </div>
            <input type="hidden" class="form-control" placeholder="Search" name="path" id="path" value="${path!}">
        </form>
    </div>

</div> <!-- /container -->
<!-- 模态框（Modal） -->
<#include "create-modal.ftl">

<#include "../script.ftl">
<script>

    var editor = CodeMirror.fromTextArea(document.getElementById("data"), {
        lineNumbers: true,
        theme: 'dracula'
    });

    function onCancel(path) {
        location.href = "/clusters/${cluster}/nodes?path=" + path;
    }

    function showCreateModal() {
        $('#createModal').modal({
            keyboard: true
        })
    }

    function onButtonClick(form, action, path) {
        form.attr('action', action);
        form.submit();
    }

</script>

</body>
</html>
