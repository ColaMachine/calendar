
var Entity1122= function(state, divId) {
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
        <div className="app_bt_fl_nav_bar table">
            <div className="select cell col-20" ><i className="fa fa-home"></i>首页</div>
            <div className="cell col-20" ><i className="fa fa-female"></i>服饰</div>
            <div className="cell col-20" ><i className="fa fa-tv"></i>电器</div>
            <div className="cell col-20" ><i className="fa fa-star-o"></i>最爱</div>
            <div className="cell col-20" ><i className="fa fa-user"></i>我</div>
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
