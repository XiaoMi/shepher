<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">×
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    Delete Permission
                </h4>
            </div>
            <form class="form-horizontal" action="?">
                <div class="modal-body">
                    <fieldset>
                        <div class="form-group">
                            <label for="delete-permission" class="col-lg-2 control-label">PermissionId</label>
                            <div class="col-lg-10">
                                <input name="pid" type="text" class="form-control input-sm" id="delete-permission"
                                       placeholder="Name" readonly/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="permission-cluster" class="col-lg-2 control-label">Cluster</label>
                            <div class="col-lg-10">
                                <input name="cluster" type="text" class="form-control input-sm" id="permission-cluster"
                                       placeholder="Cluster" readonly/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="permission-path" class="col-lg-2 control-label">Path</label>
                            <div class="col-lg-10">
                                <input name="path" type="text" class="form-control input-sm" id="permission-path"
                                       placeholder="Path" readonly/>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default"
                            data-dismiss="modal">Cancel
                    </button>
                    <button type="button" class="btn btn-success"
                            onclick="onButtonClick(this.form, '/permission/delete', 'POST', 0); return false;">
                        Confirm
                    </button>
                </div>
            </form>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->