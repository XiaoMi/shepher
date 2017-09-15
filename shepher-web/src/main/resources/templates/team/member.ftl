<!DOCTYPE html>
<html lang="en">
<#include "../header.ftl">
<body>
<#include "../nav.ftl">

<div class="container">

    <table class="table table-striped">
        <caption class="caption"><h4>${currentTeam.name}</h4></caption>
        <thead>
        <tr>
            <td>
                <ul id="myTab" class="nav nav-tabs">
                    <li class="active"><a href="/teams/${currentTeam.id?c}/members">Members</a></li>
                    <li><a href="/teams/${currentTeam.id?c}/permission">Permission</a></li>
                </ul>
            </td>
            <td></td>
        </tr>
        <tr>
            <th class="col-md-2">Member</th>
            <th class="col-md-2">Role</th>
        </tr>
        </thead>
        <tbody>
        <#list members as member>
        <tr>
            <td>${member.userName}</td>
            <td>${member.role.description}</td>
        </tr>
        </#list>
        </tbody>
    </table>

</div>
<!-- /container -->

<#include "../script.ftl">


</body>
</html>
