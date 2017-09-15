<!DOCTYPE html>
<html lang="en">
<#include "../header.ftl">
<body>
<#include "../nav.ftl">

<div class="container">
    <div>
        <table class="table table-striped">
            <caption class="caption"><h4>Joined Teams</h4></caption>
        <#if teams?has_content>
            <thead>
            <tr>
                <th class="col-md-4">Team Name</th>
                <th class="col-md-4">Role</th>
            </tr>
            </thead>
            <tbody>
                <#list teams as team>
                <tr>
                    <#if team.userId?c == user.id?c && team.role == "MASTER">
                        <td><a href="/teams/${team.teamId?c}/manage">${team.teamName}</a></td>
                    <#else >
                        <td><a href="/teams/${team.teamId?c}/members">${team.teamName}</a></td>
                    </#if>
                    <td>${team.role.description}</td>
                </tr>
                </#list>
            </tbody>
        </#if>
        </table>
    </div>

<#if !teams?has_content >
    <div class="jumbotron">
        You haven't joined any teams yet.
    </div>
</#if>

</div>
<!-- /container -->


<#include "../script.ftl">

</body>
</html>
