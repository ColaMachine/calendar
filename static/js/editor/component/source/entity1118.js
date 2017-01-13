var Entity1118 = function(state, divId) {
    var _state = state || {

    };

    var EntityReact = React.createClass({
        getInitialState: function() {


            return  MapUtil.combine(_state,{data:[]}) ;
        },

         render: function() {
            return (
                <ul className="app-li">

                {

                          this.state.data.map(function(result,index){
                              return  <li key={result.id} className="app-li-it">
                        <div className="app-li-it-pic"><a href={"/static/html/editor/index.html?id=3&goodsid="+result.id}><img src={"/"+result.pic}/></a></div>
                        <div className="app-li-it-content">
                            <div className="app-li-it-title"><a href={"/static/html/editor/index.html?id=3&goodsid="+result.id}><h2>{result.title}</h2><h3>{result.subtitle}</h3></a></div>
                            <div className="app-li-it-text"><span className="src-net">{result.src}|23:12</span><span className="comment-num">评论:2</span><span className="goods-score">评分:98%</span></div>
                        </div>
                    </li>;
                          }.bind(this))
                      }


                </ul>
            );
        },

        componentDidMount:function(){
            Ajax.getJSON("/goods/list.json?curpage=1&pagesize=10",null,function(result){
                if(result.r==AJAX_SUCC){
                    this.setState({data:result.data});
                }
            }.Apply(this));

        },

});
// OtherReact 在这里定义
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
           console.log(_state);
           react.setState(_state);
      }
       return {
           name:name,
           render: render,
           setState:setState,
           state:_state
       }
};
