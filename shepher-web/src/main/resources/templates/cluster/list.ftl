<!DOCTYPE html>
<html lang="en">
<#include "../header.ftl">
<body>
<#include "../nav.ftl">

<div class="container">
    <table class="table table-striped">
        <caption class="caption"><h4>Shepher Clusters</h4></caption>
        <thead>
        <tr>
            <th class="col-md-4">Name</th>
            <th class="col-md-8">Config</th>
        </tr>
        </thead>
        <tbody>
        <#list clusters as cluster>
        <tr>
            <td><a href="/clusters/${cluster.name}/nodes">${cluster.name}</a></td>
            <td>${cluster.config}</td>
        </tr>
        </#list>

        </tbody>
    </table>
</div>
<!-- /container -->

<#include "../script.ftl">


</body>
</html>
