<div class="modal fade" id="addModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">×
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    Grant Permission <span id="grant-permission"></span>
                </h4>
            </div>
            <form class="form-horizontal" id="add-form" action="/permission/add" method="post">
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
                            <div class="col-lg-8 ">
                                <input name="path" type="text" class="form-control input-sm"
                                       placeholder="path (start with /)">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="team" class="col-lg-2 control-label">Team</label>
                            <div class="col-lg-8">
                                <input name="team" type="text" class="form-control input-sm" placeholder="team name">
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-success">
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