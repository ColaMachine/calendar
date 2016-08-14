var EditorCanvas = React.createClass({
	getInitialState: function() {
	    return {
            titleEdit:false,
            title:"页面1"
	    };
	  },

	  render: function() {

		  return (
            <div className="canvas-area col-xs-11 col-sm-7">
                <div className="mask-left"></div>
                <div className="mask-right"></div>
                <div className="canvas-viewport">
                    <div className="canvas-window">
                        <div className="mask-top"> </div>
                        <div className="mask-bottom"></div>
                        <div className="canvas-animation">
                            <div className="bg-layer selected"></div>
                            <div className="elements-layer"></div>
                        </div>

                    </div>


                </div>

            </div>

        );
        }
    }
);

