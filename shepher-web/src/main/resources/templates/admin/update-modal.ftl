<div class="modal fade" id="updateModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">×
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    Update Cluster
                </h4>
            </div>
            <form class="form-horizontal" id="update-cluster" action="?">
                <div class="modal-body">
                    <fieldset>
                        <div class="form-group">
                            <label for="update-name" class="col-lg-2 control-label">Name</label>
                            <div class="col-lg-10">
                                <input name="name" type="text" class="form-control input-sm" id="update-name"
                                       placeholder="Name" readonly/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="update-config" class="col-lg-2 control-label">Config</label>
                            <div class="col-lg-10">
                                <input name="config" type="text" class="form-control input-sm" id="update-config"
                                       placeholder="Config"/>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default"
                            data-dismiss="modal">Cancel
                    </button>
                    <button type="button" class="btn btn-success"
                            onclick="onButtonClick(this.form, '/admin/update', 'POST'); return false;">
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