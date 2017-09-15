<div class="modal fade" id="acceptModal" tabindex="-1" role="dialog"
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
                        Accept review for path: ${reviewRequest.path}
                    </div>
                </fieldset>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">Cancel
                </button>
                <button type="button" class="btn btn-success"
                        onclick="onButtonClick($('#diff-form'), '/reviews/${reviewRequest.id?c}/accept', 'POST'); return false;">
                    Confirm
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->