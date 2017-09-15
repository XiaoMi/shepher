<!DOCTYPE html>
<html lang="en">
<#include "../header.ftl">
<body>
<#include "../nav.ftl">

<div class="container">
    <div class="row placeholder">
        <a href="/clusters/${snapshot.cluster}/nodes?path=${backPath?url}">
            <button class="btn btn-link rowbtn" id="back-button">< Back</button>
        </a>
        <div class="formatRadio">

        </div>
    </div>
    <div class="row placeholder">
        <table class="table table-bordered info-table">
            <tbody>
            <tr>
                <td class="col-md-2 active">Cluster</td>
                <td class="col-md-4">${snapshot.cluster}</td>
                <td class="col-md-2 active">Path</td>
                <td class="col-md-4">${snapshot.path}</td>
            </tr>
            <tr>
                <td class="col-md-2 active">Creator</td>
                <td class="col-md-4">${snapshot.creator}</td>
                <td class="col-md-2 active">Action</td>
                <td class="col-md-4">${snapshot.actionDetail.description}</td>
            </tr>
            <tr>
                <td class="col-md-2 active">ZkVersion</td>
                <td class="col-md-4">${snapshot.zkVersion?c}</td>
                <td class="col-md-2 active">ZkMtime</td>
                <td class="col-md-4">${snapshot.zkMtime?string("yyyy-MM-dd HH:mm:ss")}</td>
            </tr>
            <tr>
                <td class="col-md-2 active">Reviewer</td>
                <td class="col-md-4">${snapshot.reviewer}</td>
                <td class="col-md-2 active">Time</td>
                <td class="col-md-4">${snapshot.time?string("yyyy-MM-dd HH:mm:ss")}</td>
            </tr>
            </tbody>
        </table>
    </div>

    <!-- Main component for a primary marketing message or call to action -->
    <div class="row placeholder">
        <textarea id="data" name="data" class="small">${data!}</textarea>
    </div>
</div>
<!-- /container -->

<#include "../script.ftl">
<script>
    var editor = CodeMirror.fromTextArea(document.getElementById("data"), {
        lineNumbers: true,
        theme: 'dracula',
        readOnly: true
    });
</script>

</body>
</html>
