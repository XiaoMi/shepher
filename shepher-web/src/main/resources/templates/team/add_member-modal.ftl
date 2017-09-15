<div class="modal fade" id="addMemberModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">×
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    Add Member <span id="add-member"></span>
                </h4>
            </div>
            <form class="form-horizontal" id="addMember-form" action="/teams/${currentTeam.id?c}/members/add"
                  method="post">
                <div class="modal-body">
                    <fieldset>
                        <div class="form-group">
                            <label for="role-name" class="col-lg-2 control-label">User</label>
                            <div class="col-lg-8" id="user-name-div">
                                <input name="user" id="user" type="text" class="form-control input-sm"
                                       placeholder="User Name">
                                <label for="user-error" id="user-error" hidden class="control-label">User name should
                                    NOT be empty.</label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="role-name" class="col-lg-2 control-label">Roles</label>
                            <div class="col-lg-8">
                                <select class="form-control input-sm" id="role-select" name="role">
                                <#list roles as role >
                                    <option value="${role.value}">${role.description}</option>
                                </#list>
                                </select>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="modal-footer">
                    <button type="submit" id="add-member-submit" class="btn btn-success">
                        Confirm
                    </button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">
                        Cancel
                    </button>
                </div>
            </form>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->