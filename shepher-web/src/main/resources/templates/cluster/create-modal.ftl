<div class="modal fade" id="createModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">×
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    Confirm Operation
                </h4>
            </div>
            <div class="modal-body">
                <fieldset>
                    <div class="form-group">
                        Create child path for: ${path}
                    </div>
                </fieldset>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">Cancel
                </button>
                <button type="button" class="btn btn-success"
                        onclick="onButtonClick($('#form-create'), '/clusters/${cluster}/nodes/create', '${path}'); return false;">
                    Confirm
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->