var Tab = React.createClass({
	getInitialState: function() {
	    return {

	    };
	  },

	  render: function() {
		  return (
		  <ul className="nav nav-tabs">
            <li>
                <a href="#">页面</a>
            </li>
            <li>
                 <a href="#">图层</a>
            </li>
		  </ul>
        );
        }
    }
);

React.render(
        <EditorHead  />,
        document.getElementById('menu')
      );