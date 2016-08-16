var EditorMain = React.createClass({
	getInitialState: function() {
	    return {

	    };
	  },

	  render: function() {
          var that =this;
		  return (
              <div className="container-fluid">
                  <div className="row body-container">
                      <div className="col-sm-12">
                            <EditorHead/>

                            <div className="left-container">
                                <PageNav/>
                                <EditorCanvas/>
                            </div>
                      </div>
                  </div>
              </div>

        );
        }
    }
);

React.render(
        <EditorMain  />,
        document.getElementById('editor')
      );