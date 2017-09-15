<!DOCTYPE html>
<html lang="en">
<#include "../header.ftl">
<body>
<#include "../nav.ftl">

<div class="container">
    <div>
        <form id="form-manage" action="?" method="POST">
            <table class="table table-striped">
                <caption class="caption"><h4>${currentTeam.name}</h4></caption>
                <thead>
                <tr>
                    <td>
                        <ul id="myTab" class="nav nav-tabs">
                            <li class="active"><a href="/teams/${currentTeam.id?c}/manage">Management</a></li>
                            <li><a href="/teams/${currentTeam.id?c}/permission">Permission</a></li>
                        </ul>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <th class="col-md-2">Member</th>
                    <th class="col-md-2">Role</th>
                    <th class="col-md-2">Status</th>
                    <th class="col-md-2">Operation</th>
                </tr>
                </thead>
                <tbody>
                <#list pendings as pending>
                <tr>
                    <td>${pending.userName}</td>
                    <td>${pending.role.description}</td>
                    <td>${pending.status.description}</td>
                    <td>
                        <button class="btn btn-success btn-xs" id="agree-button"
                                onclick="onButtonClick(this.form, '/teams/${pending.teamId?c}/agree', 'POST', ${pending.id?c})">
                            Agree
                        </button>
                        <button class="btn btn-default btn-xs" id="refuse-button"
                                onclick="onButtonClick(this.form, '/teams/${pending.teamId?c}/refuse', 'POST', ${pending.id?c})">
                            Refuse
                        </button>
                    </td>
                </tr>
                </#list>
                <#list members as member>
                <tr>
                    <td>${member.userName}</td>
                    <#if member.userId?c == user.id?c || member.userId == currentTeam.owner>
                        <td>${member.role.description}</td>
                        <td>${member.status.description}</td>
                        <td></td>
                    <#else >
                        <td>
                            <form id="user ${member.userId}">
                                <select class="form-select-button"
                                        onchange="postRole(this.form, '/teams/${member.teamId?c}/role', this.value, 'POST' );return false;"
                                        name="role">
                                    <#list roles as role >
                                        <option value="${role.value}"
                                                <#if "${member.role.value}" == "${role.value}">selected</#if>
                                        >${role.description}</option>
                                    </#list>
                                </select>
                                <input type="hidden" class="form-control" name="id" id="id" value="${member.id?c}">
                            </form>
                        </td>
                        <td>${member.status.description}</td>
                        <td>
                            <button class="btn btn-danger btn-xs" id="agree-button"
                                    onclick="onButtonClick(this.form, '/teams/${member.teamId?c}/delete', 'POST', ${member.id?c})">
                                Delete
                            </button>
                        </td>
                    </#if>
                </tr>
                </#list>

                </tbody>
            </table>
            <input name="id" id="id" hidden="hidden"></input>
        </form>
    </div>
    <div>
        <button class="btn btn-success btn-sm pull-right" id="refuse-button"
                onclick="showModal('#addMemberModal');return false;">Add New Member
        </button>
    </div>
</div>
<!-- /container -->

<!-- 模态框（Modal） -->
<#include "add_member-modal.ftl">

<#include "../script.ftl">


<script>
    function postRole(form, action, role, method) {
        form.action = action;
        form.method = method;
        form.setAttribute('role', role);

        form.submit();
    }

    function onButtonClick(form, action, method, id) {
        form.action = action;
        form.method = method;
        $("#id").val(id);
        form.submit();
    }

    $(document).ready(function () {

        $('#add-member-submit').click(function (event) {
            $('#user-error').hide();
            var data = $('#user').val();
            data = $.trim(data);
            if (data.length < 1) {
                $('#user-name-div').addClass('has-error');
                $('#user-error').show();
                event.preventDefault();
            } else {
                $('#user-name-div').removeClass('has-error');
                $('#user-error').hide();
            }
        });

        $('#user').on("input", function () {
            var data = $('#user').val();
            data = $.trim(data);
            if (data.length > 0) {
                $('#user-name-div').removeClass('has-error');
                $('#user-error').hide();
            }
        });
    });

</script>

</body>
</html>
