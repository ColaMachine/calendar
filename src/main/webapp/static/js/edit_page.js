var EditPage = React.createClass({
	getInitialState: function() {
	    return {
            titleEdit:false,
            title:"页面1"
	    };
	  },
      showTitleEdit:function(){
//          React.findDOMNode(this.refs.title).hide();
//          React.findDOMNode(this.refs.titleInput).show();
           React.findDOMNode(this.refs.titleInput).style.display="block";
          React.findDOMNode(this.refs.title).style.display="none";
          //this.setState({titleEdit:true});
          React.findDOMNode(this.refs.titleInput).focus();
      },
    handleTitleChange:function(event){
        this.setState({title: event.target.value});

    },
    hideTitleEdit:function(){
        React.findDOMNode(this.refs.titleInput).style.display="none";
        React.findDOMNode(this.refs.title).style.display="block";
       // this.setState({titleEdit:false});
    },
	  render: function() {
           var title =this.state.title;
          var page_title ;
          var page_title_block;
          var hideStyle ={display:'none'};
          if(this.state.titleEdit){//显示input
              page_title= <span style={hideStyle}  ref="title" >{this.state.title}</span>;
              page_title_block=<input type="text" value={this.state.title}  ref="titleInput" onBlur={this.hideTitleEdit} onChange={this.handleTitleChange}></input>;
          }else{
              page_title= <span   ref="title" >{this.state.title}</span>;
              page_title_block=<input  style={hideStyle} type="text" value={this.state.title}  ref="titleInput" onBlur={this.hideTitleEdit} onChange={this.handleTitleChange}></input>;
          }
		  return (
            <div>
              <div className="page-reorder">
                  <ul className="sortable">
                      <li className="page-menu pageActive">
                          <div className="page-thumb-block active" >
                              <div className="page-thumb" >
                                  <div className="page-thumb-bg"  ></div>
                                  <div className="page-thumb-elements" ></div>
                              </div>
                              <span className="page-num"   onClick={this.showTitleEdit}>
                              {page_title}{page_title_block}
                              </span>
                              <div className="icon-button icon-uninstall fa fa-trash" _v-3fb1bde2=""></div>
                              <div className="icon-button icon-copy fa fa-copy" _v-3fb1bde2=""></div>
                          </div>
                      </li>
                  </ul>
              </div>
              <div className="add-blank-page">+</div>
         </div>

        );
        }
    }
);

