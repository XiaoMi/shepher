<!DOCTYPE html>
<html lang="en">
<#include "../header.ftl">
<body>
<#include "../nav.ftl">

<div class="container">
    <div>
        <form id="form-manage" action="?" method="POST">
            <table class="table table-striped">
                <caption class="caption"><h4>Super Admin Management</h4></caption>
                <thead>
                <tr>
                    <th class="col-md-2">Team</th>
                    <th class="col-md-2">Cluster</th>
                    <th class="col-md-2">Path</th>
                    <th class="col-md-2">Status</th>
                    <th class="col-md-2">Operation</th>
                </tr>
                </thead>
                <tbody>
                <#list pendings as pending>
                <tr>
                    <td><a href="/teams/${pending.teamId?c}/permission">${pending.teamName}</a></td>
                    <td>${pending.cluster}</td>
                    <td>${pending.path}</td>
                    <td>${pending.status.description}</td>
                    <td>
                        <button class="btn btn-success btn-xs" id="agree-button"
                                onclick="onButtonClick(this.form, '/permission/agree', 'POST', ${pending.id?c})">Agree
                        </button>
                        <button class="btn btn-default btn-xs" id="refuse-button"
                                onclick="onButtonClick(this.form, '/permission/refuse', 'POST', ${pending.id?c})">Refuse
                        </button>
                    </td>
                </tr>
                </#list>
                <#list permissions as permission>
                <tr>
                    <td><a href="/teams/${permission.teamId?c}/permission">${permission.teamName}</a></td>
                    <td>${permission.cluster}</td>
                    <td>${permission.path}</td>
                    <td>${permission.status.description}</td>
                    <td>
                        <button class="btn btn-danger btn-xs" id="delete-button"
                                onclick="showDeleteModal(${permission.id?c}, '${permission.cluster}', '${permission.path}'); return false">
                            Delete
                        </button>
                    </td>

                </tr>
                </#list>
                </tbody>
            </table>
            <input name="pid" id="pid" hidden="hidden"></input>
        </form>
    </div>
    <div>
        <button class="btn btn-success btn-sm pull-right" id="authorize-button"
                onclick="showModal('#addModal');return false;">Authorize
        </button>
    </div>
</div>

<!-- /container -->

<!-- 模态框（Modal） -->
<#include "add-modal.ftl">
<#include "delete-modal.ftl">

<#include "../script.ftl">
<script>
    function onButtonClick(form, action, method, pid) {
        form.action = action;
        form.method = method;
        $("#pid").val(pid);
        form.submit();
    }

    function showDeleteModal(pid, cluster, path) {
        $('#delete-permission').attr('value', pid);
        $('#permission-cluster').attr('value', cluster);
        $('#permission-path').attr('value', path);
        $('#deleteModal').modal({
            keyboard: true
        })
    }
</script>

</body>
</html>
