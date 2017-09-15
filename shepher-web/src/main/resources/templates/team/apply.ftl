<!DOCTYPE html>
<html lang="en">
<#include "../header.ftl">
<body>
<#include "../nav.ftl">


<div class="container">
    <div class="well">
        <h5>
            Create or join a team to get permission of path:${path!}
        </h5>
    </div>

    <ul class="nav nav-tabs">
        <li class="active"><a href="#new" data-toggle="tab">Create</a></li>
        <li><a href="#join" data-toggle="tab">Join Team</a></li>
    </ul>

    <div id="tabContent" class="tab-content">
        <div class="tab-pane fade in active" id="new">
            <form class="form-horizontal" id="new-form" action="/teams/create" method="post">
                <div class="modal-body">
                    <fieldset>
                        <div class="form-group">
                            <label for="team-name" class="col-lg-2 control-label">Team</label>
                            <div class="col-lg-4" id="team-name-div">
                                <input name="name" id="name" type="text" class="form-control" placeholder="Team Name">
                                <label for="team-error" id="team-error" hidden class="control-label">Team name should
                                    NOT be empty.</label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="cluster" class="col-lg-2 control-label">Cluster</label>
                            <div class="col-lg-4">
                                <input name="cluster" type="text" class="form-control" readonly value="${cluster!}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="path" class="col-lg-2 control-label">Path</label>
                            <div class="col-lg-4">
                                <input name="path" type="text" class="form-control" value="${path!}">
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="modal-footer">
                    <button type="submit" id="team-submit" class="btn btn-success">
                        Confirm
                    </button>
                </div>

            </form>
        </div>
        <div class="tab-pane fade" id="join">
            <form class="form-horizontal" id="join-form" action="/teams/join" method="post">
                <div class="modal-body">
                    <fieldset>
                        <div class="form-group">
                            <label for="team" class="col-lg-2 control-label">Teams</label>
                            <div class="col-lg-4">
                            <#if teams?has_content || teamsJoined?has_content>
                                <select class="form-control" id="team-select" name="team">
                                    <#list teamsJoined as teamJoined >
                                        <option value="${teamJoined.id?c}" disabled>${teamJoined.name} (joined)</option>
                                    </#list>
                                    <#list teams as team >
                                        <option value="${team.id?c}">${team.name}</option>
                                    </#list>
                                </select>
                            <#else>
                                <label for="empty-team" class="control-label">No teams to join.</label>
                            </#if>

                            </div>
                        </div>
                        <div class="form-group">
                            <label for="role-name" class="col-lg-2 control-label">Roles</label>
                            <div class="col-lg-4">
                                <select class="form-control" name="role">
                                <#list roles as role >
                                    <option value="${role.value}">${role.description}</option>
                                </#list>
                                </select>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="modal-footer">
                    <button type="submit"  <#if !(teams?has_content)>disabled</#if> class="btn btn-success">
                        Join
                    </button>
                </div>
                <input type="hidden" class="form-control" name="path" id="path" value="${path!}">
                <input type="hidden" class="form-control" name="cluster" id="cluster" value="${cluster!}">
            </form>
        </div>
    </div>
</div>
<#include "../script.ftl">

<script>
    $(document).ready(function () {
        $('#team-submit').click(function (event) {
            $('#team-error').hide();
            var data = $('#name').val();
            data = $.trim(data);
            if (data.length < 1) {
                $('#team-name-div').addClass('has-error');
                $('#team-error').show();
                event.preventDefault();
            } else {
                $('#team-name-div').removeClass('has-error');
                $('#team-error').hide();
            }
        });

        $('#name').on("input", function () {
            var data = $('#name').val();
            data = $.trim(data);
            if (data.length > 0) {
                $('#team-name-div').removeClass('has-error');
                $('#team-error').hide();
            }
        });
    });

</script>

</body>
</html>
