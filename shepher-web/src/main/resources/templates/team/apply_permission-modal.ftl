<div class="modal fade" id="applyPermissionModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">×
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    Team Apply Permission <span id="apply-permission"></span>
                </h4>
            </div>
            <form class="form-horizontal" id="apply-form" action="/teams/${currentTeam.id?c}/apply" method="post">
                <div class="modal-body">
                    <fieldset>
                        <div class="form-group">
                            <label for="cluster" class="col-lg-2 control-label">Cluster</label>
                            <div class="col-lg-8">
                                <select class="form-control input-sm" id="cluster-select" name="cluster">
                                <#list clusters as cluster >
                                    <option value="${cluster.name}">${cluster.name}</option>
                                </#list>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="path" class="col-lg-2 control-label">Path</label>
                            <div class="col-lg-8" id="path-div">
                                <input name="path" id="path" type="text" class="form-control input-sm"
                                       placeholder="path">
                                <label for="path-error" id="path-error" hidden class="control-label">Path should start
                                    with slash / </label>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="modal-footer">
                    <button type="submit" id="apply-submit" class="btn btn-success">
                        Apply
                    </button>
                </div>
            </form>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->