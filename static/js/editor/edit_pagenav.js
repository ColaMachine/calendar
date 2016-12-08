var PageNav = React.createClass({
	getInitialState: function() {
	    return {
            arr:[{key:"1",name:"页面",content:<EditTemplateWrap/>,active:true},{key:"2",name:"图层",content:"hello"}]
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
                      this.state.arr.map(function(result,index){
                          var data = result.name;
                          return  <li key={index} className={ result.active ? 'active' : '' } >
                              <a href="#"  onClick={that.selectTab}>{result.name}</a>
                          </li>;
                      })
                    }
                  </ul>
                  <div className="tab-content">
                    {
                        this.state.arr.map(function(result,index){
                            return <div   key={index} className={ result.active ? 'tab-pane fade in  active ' : 'tab-pane fade ' } >
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

