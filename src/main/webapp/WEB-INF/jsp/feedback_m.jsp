<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal" >Please leave your feedback</button>

<div class="modal" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">New message</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form>
          <div class="form-group">
            <label for="recipient-name" class="col-form-label">Name(Not required):</label>
            <input type="text" class="form-control" id="recipient-name">
          </div>
        <div class="form-group">
          <label for="recipient-email" class="col-form-label">Email(Not required):</label>
          <input type="text" class="form-control" id="recipient-email">
        </div>
          <div class="form-group">
            <label for="message-text" class="col-form-label">Message(Required):</label>
            <textarea required  class="form-control" id="message-text"></textarea>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" id="sendFeedback">Send feedback</button>
      </div>
      <div class="result">
        <label id="label-result">
      </div>
    </div>
  </div>
</div>