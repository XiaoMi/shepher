<div class="modal fade" id="createModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">×
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    Add Cluster
                </h4>
            </div>
            <form class="form-horizontal" id="add-cluster" action="?">
                <div class="modal-body">
                    <fieldset>
                        <div class="form-group">
                            <label for="create-name" class="col-lg-2 control-label">Name</label>
                            <div class="col-lg-10">
                                <input name="name" type="text" class="form-control input-sm" id="create-name"
                                       placeholder="Name">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="cluster-config" class="col-lg-2 control-label">Config</label>
                            <div class="col-lg-10">
                                <input name="config" type="text" class="form-control input-sm" id="cluster-config"
                                       placeholder="Config">
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default"
                            data-dismiss="modal">Cancel
                    </button>
                    <button type="button" class="btn btn-success"
                            onclick="onButtonClick(this.form, '/admin/create', 'POST'); return false;">
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