
var Entity1120= function(state, divId) {
    var name="header";

    var _state = state || {
    };
var EntityReact = React.createClass({
    getInitialState: function() {
        return MapUtil.copy(_state,
        {pic:"",
        content:"",
        title:"",
        subtitle:"",
        src:""
        });
    },

    render: function() {
    return (
        <div >
   <img className="app-top-img" src={"/"+this.state.pic}/>
   <span>来源:{this.state.src}</span><span></span>
   <h1>{this.state.title}</h1>
   <h1>{this.state.subtitle}</h1>
<p dangerouslySetInnerHTML={{__html: this.state.content}} />
  
</div>
    );
    },

    componentDidMount:function(){
    var that =this;
    var id = getQueryString("goodsid");
       Ajax.getJSON("/goods/view.json?id="+id,null,function(result){
        if(result.r==AJAX_SUCC){
             that.setState(result.data.bean);
        }
       });
        },
        getInitialState:function(){
            return _state
        },
    });
    /**
     * 制作页面时返回React对象
     * @returns {*}
     */
   var react =null;
       function render() {
            react=  ReactDOM.render(<EntityReact />, document.getElementById(divId));
            return react;

       }
       function setState(state_){
           for(var i in state_){
                _state[i]=state_[i];
           }
           react.setState(_state);
      }
       return {
           name:name,
           render: render,
           setState:setState,
           state:_state
       }
};
