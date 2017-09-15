<!DOCTYPE html>
<html lang="en">
<#include "../header.ftl">
<body>
<#include "../nav.ftl">

<div class="container">
    <div class="row placeholder">
        <a href="/clusters/${cluster}/nodes?path=${backPath?url}">
            <button class="btn btn-link rowbtn" id="back-button">< Back</button>
        </a>
        <div class="formatRadio">

        </div>
    </div>
    <div class="row placeholder">
        <table class="table table-striped">
            <thead>
            <tr>
                <th class="col-md-2">ZkVersion</th>
                <th class="col-md-2">Creator</th>
                <th class="col-md-2">Action</th>
                <th class="col-md-2">Reviewer</th>
                <th class="col-md-4">Time</th>
            </tr>
            </thead>
            <tbody>
            <#list snapshots as snapshot>
            <tr>
                <td><a href="/snapshots/${snapshot.id?c}">
                    <#if snapshot_index == 0>
                        current
                    <#else>
                    ${snapshot.zkVersion}
                    </#if>
                </a></td>
                <td>${snapshot.creator}</td>
                <td>${snapshot.actionDetail.description}</td>
                <td>${snapshot.reviewer}</td>
                <td>${snapshot.time?string("yyyy-MM-dd HH:mm:ss")}</td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>
</div>
<!-- /container -->

<#include "../script.ftl">
<script>
</script>

</body>
</html>
