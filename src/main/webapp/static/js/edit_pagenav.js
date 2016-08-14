var PageNav = React.createClass({
	getInitialState: function() {
	    return {
            arr:[{name:"页面",content:<EditPage/>,active:true},{name:"图层",content:"hello"}]
	    };
	  },
    selectTab:function(data){
      for(var i in this.state.arr){
          if(this.state.arr[i].name!= data){
              this.state.arr[i].active=false;
          }else{
              this.state.arr[i].active=true;
          }
      }
        this.setState(this.state);
    },
	  render: function() {
          var that =this;
		  return (
          <div className="page-navigator col-xs-1 col-sm-5">
              <div className="mask"></div>
              <div>
                  <ul className="nav nav-tabs">
                    {
                      this.state.arr.map(function(result){
                          var data = result.name;
                          return  <li className={ result.active ? 'active' : '' } >
                              <a href="#"  onClick={that.selectTab.bind(this,data)}>{result.name}</a>
                          </li>;
                      })
                    }
                  </ul>
                  <div className="tab-content">
                    {
                        this.state.arr.map(function(result){
                            return <div className={ result.active ? 'tab-pane fade in  active ' : 'tab-pane fade ' } >
                              {result.content}
                            </div>;
                        })
                    }
                  </div>
              </div>
          </div>

        );
        }
    }
);

